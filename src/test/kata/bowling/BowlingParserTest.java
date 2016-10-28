package test.kata.bowling;

import com.kata.bowling.*;
import com.kata.bowling.frame.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingParserTest {

    public static final Frame NULL_FRAME = new NullFrame();

    @Test
    public void no_frames() throws Exception {
        assertThat(new BowlingParser().parse(null), is(new ArrayList<Frame>()));
        assertThat(new BowlingParser().parse(""), is(new ArrayList<Frame>()));
    }

    @Test
    public void one_two_trials_frame() throws Exception {
        List<Frame> frames = new BowlingParser().parse("1-");
        Frame scoreFrame = new ScoreFrame(1, 0);
        scoreFrame.add(NULL_FRAME);

        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(scoreFrame));
        assertThat(frames.get(0).getScore(), is(1));
    }

    @Test
    public void one_strike_frame() throws Exception {
        List<Frame> frames = new BowlingParser().parse("X");
        Frame strikeFrame =  new StrikeFrame();
        strikeFrame.add(NULL_FRAME);
        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(strikeFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    @Test
    public void one_spare_frame() throws Exception {
        List<Frame> frames = new BowlingParser().parse("4/");
        Frame spareFrame = new SpareFrame(4);
        spareFrame.add(NULL_FRAME);

        assertThat(frames.size(), is(1));
        assertThat(frames.get(0), is(spareFrame));
        assertThat(frames.get(0).getScore(), is(10));
    }

    @Test
    public void all_numbers() throws Exception {
        List<Frame> frames = new BowlingParser().parse("1-2-3-4-5-6-7-8-9-");
        assertThat(frames.size(), is(9));

        ScoreFrame f_8 = new ScoreFrame(9, 0);
        f_8.add(new NullFrame());
        ScoreFrame f_7 = new ScoreFrame(8, 0);
        f_7.add(f_8);
        ScoreFrame f_6 = new ScoreFrame(7, 0);
        f_6.add(f_7);
        ScoreFrame f_5 = new ScoreFrame(6, 0);
        f_5.add(f_6);
        ScoreFrame f_4 = new ScoreFrame(5, 0);
        f_4.add(f_5);
        ScoreFrame f_3 = new ScoreFrame(4, 0);
        f_3.add(f_4);
        ScoreFrame f_2 = new ScoreFrame(3, 0);
        f_2.add(f_3);
        ScoreFrame f_1 = new ScoreFrame(2, 0);
        f_1.add(f_2);
        ScoreFrame f_0 = new ScoreFrame(1, 0);
        f_0.add(f_1);

        assertThat(frames.get(0), is(f_0));
        assertThat(frames.get(1), is(f_1));
        assertThat(frames.get(2), is(f_2));
        assertThat(frames.get(3), is(f_3));
        assertThat(frames.get(4), is(f_4));
        assertThat(frames.get(5), is(f_5));
        assertThat(frames.get(6), is(f_6));
        assertThat(frames.get(7), is(f_7));
        assertThat(frames.get(8), is(f_8));
    }

    //TODO add a test with 3 zeros at the end

    @Test
    public void four_trials() throws Exception {
        List<Frame> frames = new BowlingParser().parse("4--5");

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

    @Test(expected = BowlingParser.FrameParseException.class)
    public void five_trials_without_strikes() throws Exception {
        List<Frame> frames = new BowlingParser().parse("4--5-");

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

    @Test(expected = BowlingParser.FrameParseException.class)
    public void name() throws Exception {
        List<Frame> frames = new BowlingParser().parse("4p");
    }

    @Test
    public void five_trials_with_strikes() throws Exception {
        List<Frame> frames = new BowlingParser().parse("4-X5/");
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
        List<Frame> frames = new BowlingParser().parse("XXX");
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
        List<Frame> frames = new BowlingParser().parse("-/-/2/");
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

    @Test
    public void the_eleventh_and_twelfth_serve_only_as_bonus_of_tenth_frame() throws Exception {
        List<Frame> frames = new BowlingParser().parse("------------------XXX");

        assertThat(frames.size(), is(10));

        Frame frame_12_known_by_10th = new StrikeFrame();
        frame_12_known_by_10th.add(NULL_FRAME);
        Frame frame_11_known_by_10th = new StrikeFrame();
        frame_11_known_by_10th.add(frame_12_known_by_10th);
        Frame frame_10 = new StrikeFrame();
        frame_10.add(frame_11_known_by_10th);
        assertThat(frames.get(9), is(frame_10));
    }


}
