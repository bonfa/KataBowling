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
        assertThat(new FrameParser().parse(null), is(new ArrayList<Frame>()));
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

    @Test(expected = FrameParser.FrameParseException.class)
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

}
