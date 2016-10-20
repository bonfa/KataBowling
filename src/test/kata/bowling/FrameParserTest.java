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
        List<Frame> frames = new FrameParser().parse("x");
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

    private class FrameParser {
        List<Frame> parse(String frameString) {
            ArrayList<Frame> frames = new ArrayList<>();
            if (frameString != null && !frameString.isEmpty()) {
                Frame frame = parseFrame(frameString);
                frames.add(frame);
            }
            return frames;
        }

        //TODO refactoring
        private Frame parseFrame(String frameString) {
            Frame frame;
            char firstTrialScore = frameString.charAt(0);
            if (firstTrialScore == 'x') {
                frame = new StrikeFrame();
            } else {
                char secondTrialScore = frameString.charAt(1);
                int firstTrialScoreValue = parseSingleTrialScore(firstTrialScore);
                if (secondTrialScore == '/') {
                    frame = new SpareFrame(firstTrialScoreValue);
                } else {
                    int secondTrialScoreValue = parseSingleTrialScore(secondTrialScore);
                    frame = new ScoreFrame(firstTrialScoreValue, secondTrialScoreValue);
                }
            }
            return frame;
        }

        private int parseSingleTrialScore(char firstTrialScore) {
            if (firstTrialScore == '-') {
                return 0;
            } else if (firstTrialScore >= '1' && firstTrialScore <= '9') {
                return Character.getNumericValue(firstTrialScore);
            } else {
                throw new FrameParseException();
            }
        }

        private class FrameParseException extends RuntimeException {}
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
