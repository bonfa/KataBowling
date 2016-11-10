package test.kata.bowling;

import com.kata.bowling.*;
import com.kata.bowling.frame.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingParserTest {

    private static final Frame NULL_FRAME = new NullFrame();
    private FrameInnestor frameInnestor;

    @Before
    public void setUp() throws Exception {
        frameInnestor = new FrameInnestor();
    }

    @Test(expected = BowlingParser.TooFewFramesException.class)
    public void no_frames() throws Exception {
        new BowlingParser(frameInnestor).parse(null);
    }

    @Test(expected = BowlingParser.TooFewFramesException.class)
    public void empty_input() throws Exception {
        new BowlingParser(frameInnestor).parse("");
    }

    @Test(expected = BowlingParser.TooFewFramesException.class)
    public void not_enough_frames() throws Exception {
        new BowlingParser(frameInnestor).parse("112233445566778899");
    }

    @Test(expected = BowlingParser.TooManyFramesException.class)
    public void too_many_frames() throws Exception {
        new BowlingParser(frameInnestor).parse("112233445566778899--111213");
    }

    @Test(expected = BowlingParser.FrameParseException.class)
    public void invalid_characters() throws Exception {
        new BowlingParser(frameInnestor).parse("1p2p3p4p5p6p7p8p9p-p");
    }

    @Test
    public void ten_trials_with_no_strikes_or_spare_as_last_frame() throws Exception {
        List<Frame> frames = new BowlingParser(frameInnestor).parse("1-2-3-4-5-6-7-8-9---");
        ScoreFrame frame_10 = new ScoreFrame(0, 0);
        frame_10.add(NULL_FRAME);
        ScoreFrame frame_9 = new ScoreFrame(9, 0);
        frame_9.add(frame_10);
        ScoreFrame frame_8 = new ScoreFrame(8, 0);
        frame_8.add(frame_9);
        ScoreFrame frame_7 = new ScoreFrame(7, 0);
        frame_7.add(frame_8);
        ScoreFrame frame_6 = new ScoreFrame(6, 0);
        frame_6.add(frame_7);
        ScoreFrame frame_5 = new ScoreFrame(5, 0);
        frame_5.add(frame_6);
        ScoreFrame frame_4 = new ScoreFrame(4, 0);
        frame_4.add(frame_5);
        ScoreFrame frame_3 = new ScoreFrame(3, 0);
        frame_3.add(frame_4);
        ScoreFrame frame_2 = new ScoreFrame(2, 0);
        frame_2.add(frame_3);
        ScoreFrame frame_1 = new ScoreFrame(1, 0);
        frame_1.add(frame_2);

        assertThat(frames.size(), is(10));
        assertThat(frames.get(0), is(frame_1));
        assertThat(frames.get(1), is(frame_2));
        assertThat(frames.get(2), is(frame_3));
        assertThat(frames.get(3), is(frame_4));
        assertThat(frames.get(4), is(frame_5));
        assertThat(frames.get(5), is(frame_6));
        assertThat(frames.get(6), is(frame_7));
        assertThat(frames.get(7), is(frame_8));
        assertThat(frames.get(8), is(frame_9));
        assertThat(frames.get(9), is(frame_10));
    }

    @Test
    public void a_spare_at_tenth_frame_gives_one_more_trial() throws Exception {
        List<Frame> frames = new BowlingParser(frameInnestor).parse("----------------9--/4");
        Frame frame_10 = new SpareFrame(0);
        ScoreFrame nextFrame = new ScoreFrame(4, 0);
        nextFrame.add(NULL_FRAME);
        frame_10.add(nextFrame);

        assertThat(frames.size(), is(10));
        assertThat(frames.get(9), is(frame_10));
    }

    @Test
    public void a_strike_at_tenth_frame_gives_two_more_trials() throws Exception {
        List<Frame> frames = new BowlingParser(frameInnestor).parse("----------------9-X44");
        Frame frame_10 = new StrikeFrame();
        ScoreFrame nextFrame = new ScoreFrame(4, 4);
        nextFrame.add(NULL_FRAME);
        frame_10.add(nextFrame);

        assertThat(frames.size(), is(10));
        assertThat(frames.get(9), is(frame_10));
    }

    @Test(expected = BowlingParser.FrameScoreMoreThanTenException.class)
    public void a_frame_with_total_more_than_ten() throws Exception {
        new BowlingParser(frameInnestor).parse("492-3-4-5-6-7-8-9---");
    }

    @Test(expected = BowlingParser.WrongSpareFormatException.class)
    public void a_frame_with_total_equals_to_ten_without_spare_or_strike() throws Exception {
        new BowlingParser(frameInnestor).parse("552-3-4-5-6-7-8-9---");
    }

    @Test
    public void the_eleventh_and_twelfth_frames_serve_only_as_bonus_of_tenth_frame() throws Exception {
        List<Frame> frames = new BowlingParser(frameInnestor).parse("------------------XXX");

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
