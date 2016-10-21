package test.kata.bowling;

import com.kata.bowling.Frame;
import com.kata.bowling.ScoreFrame;
import com.kata.bowling.SpareFrame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FrameParserTest {

    @Test
    public void no_frames() throws Exception {
        assertThat(new FrameParser().parse((String) null), is(new ArrayList<Frame>()));
        assertThat(new FrameParser().parse(""), is(new ArrayList<Frame>()));
    }

    @Test
    public void one_two_trials_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("1-");
        Frame scoreFrame = new ScoreFrame(1, 0);

        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(scoreFrame));
        assertThat(frames.get(0).getScore(), is(1));
    }

    @Test
    public void one_strike_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("X");
        Frame strikeFrame = new StrikeFrame();
        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(strikeFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    @Test
    public void one_spare_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("4/");
        Frame spareFrame = new SpareFrame(4);
        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(spareFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    @Test
    public void four_trials() throws Exception {
        List<Frame> frames = new FrameParser().parse("4--5");
        assertThat(frames.size(), is(2));
        assertThat(frames.get(0), is(new ScoreFrame(4, 0)));
        assertThat(frames.get(0).getScore(), is(4));
        assertThat(frames.get(1), is(new ScoreFrame(0, 5)));
        assertThat(frames.get(1).getScore(), is(5));
    }

    @Test(expected = FrameParseException.class)
    public void five_trials_without_strikes() throws Exception {
        List<Frame> frames = new FrameParser().parse("4--5-");
        assertThat(frames.size(), is(2));
        assertThat(frames.get(0), is(new ScoreFrame(4, 0)));
        assertThat(frames.get(0).getScore(), is(4));
        assertThat(frames.get(1), is(new ScoreFrame(0, 5)));
        assertThat(frames.get(1).getScore(), is(5));
    }

    @Test
    public void five_trials_with_strikes() throws Exception {
        List<Frame> frames = new FrameParser().parse("4-X5/");
        assertThat(frames.size(), is(3));
        assertThat(frames.get(0), is(new ScoreFrame(4, 0)));
        assertThat(frames.get(0).getScore(), is(4));
        assertThat(frames.get(1), is(new StrikeFrame()));
        assertThat(frames.get(2), is(new SpareFrame(5)));
        assertThat(frames.get(2).getScore(), is(10));
    }

    @Test
    public void all_strikes() throws Exception {
        List<Frame> frames = new FrameParser().parse("XXX");
        assertThat(frames.size(), is(3));
        assertThat(frames.get(0), is(new StrikeFrame()));
        assertThat(frames.get(1), is(new StrikeFrame()));
        assertThat(frames.get(2), is(new StrikeFrame()));
    }

    @Test
    public void all_spares() throws Exception {
        List<Frame> frames = new FrameParser().parse("-/-/2/");
        assertThat(frames.size(), is(3));
        assertThat(frames.get(0), is(new SpareFrame(0)));
        assertThat(frames.get(1), is(new SpareFrame(0)));
        assertThat(frames.get(2), is(new SpareFrame(2)));
    }

    private class FrameParser {

        private static final int STRIKE = -1;
        private static final int SPARE = -2;

        List<Frame> parse(String frameString) {
            ArrayList<Frame> frames = new ArrayList<>();
            if (frameString != null && !frameString.isEmpty()) {
                int[] trialScores = getSingleTrialScores(frameString);
                frames = parse(trialScores);
            }
            return frames;
        }

        private ArrayList<Frame> parse(int[] trialScores) {
            return parse(0, trialScores);
        }

        private ArrayList<Frame> parse(int numberOfTrialsOffset, int[] trialScores) {
            ArrayList<Frame> frames = new ArrayList<>();
            if (numberOfTrialsOffset >= trialScores.length) {
                return frames;
            }
            else {
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
            }
            else if (numberOfTrialsOffset + 1 < trialScores.length) {
                int firstTrialScore = trialScores[numberOfTrialsOffset];
                int secondTrialScore = trialScores[numberOfTrialsOffset + 1];
                if (secondTrialScore == SPARE) {
                    frame = new SpareFrame(firstTrialScore);
                } else {
                    frame = new ScoreFrame(firstTrialScore, secondTrialScore);
                }
            }
            else {
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

    private class StrikeFrame implements Frame {
        @Override
        public int getScore() {
            return 10;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return (o != null && getClass() == o.getClass());
        }
    }

}
