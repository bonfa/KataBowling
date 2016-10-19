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
    public void two_trials_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("1-");
        Frame scoreFrame = new ScoreFrame(1);

        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(scoreFrame));
        assertThat(frames.get(0).getScore(), is(1));
    }

    @Test
    public void strike_frame() throws Exception {
        List<Frame> frames = new FrameParser().parse("x");
        Frame scoreFrame = new StrikeFrame();
        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(scoreFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    private class FrameParser {
        List<Frame> parse(String frames) {
            ArrayList<Frame> frames1 = new ArrayList<>();
            if (frames != null && !frames.isEmpty()) {
                Frame frame;
                if (frames.equals("x")) {
                    frame = new StrikeFrame();
                }
                else {
                    frame = new ScoreFrame(1);
                }
                frames1.add(frame);
            }
            return frames1;
        }
    }

    private interface Frame {
        int getScore();
    }

    private class ScoreFrame implements Frame {
        private int score;

        ScoreFrame(int score) {
            this.score = score;
        }

        @Override
        public int getScore() {
            return score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ScoreFrame frame = (ScoreFrame) o;

            return score == frame.score;
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
}
