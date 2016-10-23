package test.kata.bowling;

import com.kata.bowling.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FrameParserTest {

    public static final Frame NULL_FRAME = new NullFrame();

    @Test
    public void no_frames() throws Exception {
        assertThat(new FrameParser().parse((String) null), is(new ArrayList<Frame>()));
        assertThat(new FrameParser().parse(""), is(new ArrayList<Frame>()));
    }

    @Test
    public void one_two_trials_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("1-");
        Frame scoreFrame = new ScoreFrame(1, 0);
        scoreFrame.add(NULL_FRAME);

        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(scoreFrame));
        assertThat(frames.get(0).getScore(), is(1));
    }

    @Test
    public void one_strike_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("X");
        Frame strikeFrame =  new StrikeFrame();
        strikeFrame.add(NULL_FRAME);
        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(strikeFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    @Test
    public void one_spare_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("4/");
        Frame spareFrame = new SpareFrame(4);
        spareFrame.add(NULL_FRAME);

        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(spareFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    @Test
    public void four_trials() throws Exception {
        List<Frame> frames = new FrameParser().parse("4--5");

        ScoreFrame frame_2 = new ScoreFrame(0, 5);
        frame_2.add(NULL_FRAME);
        ScoreFrame frame_1 = new ScoreFrame(4, 0);
        frame_1.add(frame_2);

        assertThat(frames.size(), is(2));
        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(0).getScore(), is(4));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(1).getScore(), is(5));
    }

    @Test(expected = FrameParseException.class)
    public void five_trials_without_strikes() throws Exception {
        List<Frame> frames = new FrameParser().parse("4--5-");

        ScoreFrame frame_2 = new ScoreFrame(0, 5);
        frame_2.add(NULL_FRAME);
        ScoreFrame frame_1 = new ScoreFrame(4, 0);
        frame_1.add(frame_2);

        assertThat(frames.size(), is(2));
        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(0).getScore(), is(4));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(1).getScore(), is(5));
    }

    @Test
    public void five_trials_with_strikes() throws Exception {
        List<Frame> frames = new FrameParser().parse("4-X5/");
        SpareFrame frame_3 = new SpareFrame(5);
        frame_3.add(NULL_FRAME);
        StrikeFrame frame_2 = new StrikeFrame();
        frame_2.add(frame_3);
        ScoreFrame frame_1 = new ScoreFrame(4, 0);
        frame_1.add(frame_2);

        assertThat(frames.size(), is(3));
        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(0).getScore(), is(4));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(2), is(frame_3));
        assertThat(frames.get(2).getScore(), is(10));
    }

    @Test
    public void all_strikes() throws Exception {
        List<Frame> frames = new FrameParser().parse("XXX");
        assertThat(frames.size(), is(3));
        StrikeFrame frame_3 = new StrikeFrame();
        frame_3.add(NULL_FRAME);
        StrikeFrame frame_2 = new StrikeFrame();
        frame_2.add(frame_3);
        StrikeFrame frame_1 = new StrikeFrame();
        frame_1.add(frame_2);

        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(2), is(frame_3));
    }

    @Test
    public void all_spares() throws Exception {
        List<Frame> frames = new FrameParser().parse("-/-/2/");
        SpareFrame frame_3 = new SpareFrame(2);
        frame_3.add(NULL_FRAME);
        SpareFrame frame_2 = new SpareFrame(0);
        frame_2.add(frame_3);
        SpareFrame frame_1 = new SpareFrame(0);
        frame_1.add(frame_2);

        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(2), is(frame_3));

        assertThat(frames.size(), is(3));
        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(2), is(frame_3));
    }

    private class FrameParser {

        private static final int STRIKE = -1;
        private static final int SPARE = -2;

        List<Frame> parse(String frameString) {
            ArrayList<Frame> frames = new ArrayList<>();
            if (frameString != null && !frameString.isEmpty()) {
                int[] trialScores = getSingleTrialScores(frameString);
                frames = parse(trialScores);
                updateFrameStructure(frames);
            }
            return frames;
        }

        private void updateFrameStructure(ArrayList<Frame> frames) {
            for (int i = 0; i < frames.size(); i++) {
                Frame frame = getFrameAtIndex(frames, i);
                Frame nextFrame = getFrameAtIndex(frames, i + 1);
                nextFrame.add(getFrameAtIndex(frames, i + 2));
                frame.add(nextFrame);
            }
        }

        private Frame getFrameAtIndex(ArrayList<Frame> frames, int i) {
            Frame frame;
            if (i < frames.size()) {
                frame = frames.get(i);
            }
            else {
                frame = new NullFrame();
            }
            return frame;
        }

        private ArrayList<Frame> parse(int[] trialScores) {
            return parse(0, trialScores);
        }

        private ArrayList<Frame> parse(int numberOfTrialsOffset, int[] trialScores) {
            ArrayList<Frame> frames = new ArrayList<>();
            if (numberOfTrialsOffset >= trialScores.length) {
                return frames;
            } else {
                int numberOfTrialsOfNextFrame = getNextFrameIndex(numberOfTrialsOffset, trialScores);
                Frame frame = parseFrame(numberOfTrialsOffset, numberOfTrialsOfNextFrame, trialScores);
                frames.add(frame);
                frames.addAll(parse(numberOfTrialsOffset + numberOfTrialsOfNextFrame, trialScores));
                return frames;
            }
        }

        private int getNextFrameIndex(int indexOffset, int[] trialScores) {
            int nextFrameIndex = 2;
            if (trialScores[indexOffset] == STRIKE) {
                nextFrameIndex = 1;
            }
            return nextFrameIndex;
        }

        private Frame parseFrame(int numberOfTrialsOffset, int nextFrameIndex, int[] trialScores) {
            Frame frame;
            if (nextFrameIndex == 1) {
                frame = new StrikeFrame();
            } else if (numberOfTrialsOffset + 1 < trialScores.length) {
                int firstTrialScore = trialScores[numberOfTrialsOffset];
                int secondTrialScore = trialScores[numberOfTrialsOffset + 1];
                if (secondTrialScore == SPARE) {
                    frame = new SpareFrame(firstTrialScore);
                } else {
                    frame = new ScoreFrame(firstTrialScore, secondTrialScore);
                }
            } else {
                throw new FrameParseException();
            }
            return frame;
        }

        private int[] getSingleTrialScores(String frameString) {
            char[] charArrays = frameString.toCharArray();
            int[] trialScores = new int[charArrays.length];
            for (int i = 0; i < trialScores.length; i++) {
                trialScores[i] = parseSingleTrialScore(charArrays[i]);
            }
            return trialScores;
        }

        private int parseSingleTrialScore(char stringValue) {
            if (stringValue >= '1' && stringValue <= '9') {
                return Character.getNumericValue(stringValue);
            } else if (stringValue == 'X') {
                return STRIKE;
            } else if (stringValue == '/') {
                return SPARE;
            } else if (stringValue == '-') {
                return 0;
            } else {
                throw new FrameParseException();
            }
        }

    }

    private class FrameParseException extends RuntimeException {
    }

}
