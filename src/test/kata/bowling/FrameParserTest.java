package test.kata.bowling;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FrameParserTest {

    @Test
    public void no_frames() throws Exception {
        assertThat(new FrameParser().parse(null), is(new ArrayList<Frame>()));
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

    private class FrameParser {

        private static final int STRIKE = -1;
        private static final int SPARE = -2;

        List<Frame> parse(String frameString) {
            ArrayList<Frame> frames = new ArrayList<>();
            if (frameString != null && !frameString.isEmpty()) {
                int[] trialScores = getSingleTrialScores(frameString);

                int index = 0;
                while (index < trialScores.length) {
                    Frame frame = getFrame(trialScores, index);
                    index = getNewIndexForParsing(frame, index);
                    frames.add(frame);
                }
            }
            return frames;
        }

        private Frame getFrame(int[] trialScores, int i) {
            Frame frame;
            int firstTrialScore = trialScores[i];
            if (firstTrialScore == STRIKE) {
                frame = new StrikeFrame();
            } else {
                int secondTrialScore = trialScores[i + 1];
                if (secondTrialScore == SPARE) {
                    frame = new SpareFrame(firstTrialScore);
                } else {
                    frame = new ScoreFrame(firstTrialScore, secondTrialScore);
                }
            }
            return frame;
        }

        private int getNewIndexForParsing(Frame parsedFrame, int previousIndex) {
            int newIndex = previousIndex + 2;
            if (parsedFrame instanceof StrikeFrame) {
                newIndex = previousIndex + 1;
            }
            return newIndex;
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

        private class FrameParseException extends RuntimeException {
        }
    }

    private interface Frame {
        int getScore();
    }

    private class ScoreFrame implements Frame {
        private int firstTrialScoreValue;
        private int secondTrialScoreValue;

        ScoreFrame(int firstTrialScoreValue, int secondTrialScoreValue) {

            this.firstTrialScoreValue = firstTrialScoreValue;
            this.secondTrialScoreValue = secondTrialScoreValue;
        }

        @Override
        public int getScore() {
            return firstTrialScoreValue + secondTrialScoreValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ScoreFrame frame = (ScoreFrame) o;

            if (firstTrialScoreValue != frame.firstTrialScoreValue) return false;
            return secondTrialScoreValue == frame.secondTrialScoreValue;
        }
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

    private class SpareFrame implements Frame {
        private int firstTrialScore;

        SpareFrame(int firstTrialScore) {

            this.firstTrialScore = firstTrialScore;
        }

        @Override
        public int getScore() {
            return 10;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SpareFrame that = (SpareFrame) o;

            return firstTrialScore == that.firstTrialScore;

        }
    }
}
