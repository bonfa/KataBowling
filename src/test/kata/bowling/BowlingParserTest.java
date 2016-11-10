package test.kata.bowling;

import com.kata.bowling.*;
import com.kata.bowling.frame.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingParserTest {

    @Test(expected = BowlingParser.TooFewFramesException.class)
    public void no_frames() throws Exception {
        new BowlingParser().parse(null);
    }

    @Test(expected = BowlingParser.TooFewFramesException.class)
    public void empty_input() throws Exception {
        new BowlingParser().parse("");
    }

    @Test(expected = BowlingParser.TooFewFramesException.class)
    public void not_enough_frames() throws Exception {
        new BowlingParser().parse("112233445566778899");
    }

    @Test(expected = BowlingParser.TooManyFramesException.class)
    public void too_many_frames() throws Exception {
        new BowlingParser().parse("112233445566778899--111213");
    }

    @Test(expected = BowlingParser.FrameParseException.class)
    public void invalid_characters() throws Exception {
        new BowlingParser().parse("1p2p3p4p5p6p7p8p9p-p");
    }

    @Test
    public void ten_trials_with_no_strikes_or_spare_as_last_frame() throws Exception {
        List<Frame> frames = new BowlingParser().parse("1-2-3-4-5-6-7-8-9---");

        assertThat(frames.size(), is(10));
        assertThat(frames.get(0), is(new ScoreFrame(1, 0)));
        assertThat(frames.get(1), is(new ScoreFrame(2, 0)));
        assertThat(frames.get(2), is(new ScoreFrame(3, 0)));
        assertThat(frames.get(3), is(new ScoreFrame(4, 0)));
        assertThat(frames.get(4), is(new ScoreFrame(5, 0)));
        assertThat(frames.get(5), is(new ScoreFrame(6, 0)));
        assertThat(frames.get(6), is(new ScoreFrame(7, 0)));
        assertThat(frames.get(7), is(new ScoreFrame(8, 0)));
        assertThat(frames.get(8), is(new ScoreFrame(9, 0)));
        assertThat(frames.get(9), is(new ScoreFrame(0, 0)));
    }

    @Test
    public void a_spare_at_tenth_frame_gives_one_more_trial() throws Exception {
        List<Frame> frames = new BowlingParser().parse("----------------9--/4");

        assertThat(frames.size(), is(11));
        assertThat(frames.get(9), is(new SpareFrame(0)));
        assertThat(frames.get(10), is(new ScoreFrame(4, 0)));
    }

    @Test
    public void a_strike_at_tenth_frame_gives_two_more_trials() throws Exception {
        List<Frame> frames = new BowlingParser().parse("----------------9-X44");

        assertThat(frames.size(), is(11));
        assertThat(frames.get(9), is(new StrikeFrame()));
        assertThat(frames.get(10), is(new ScoreFrame(4, 4)));
    }

    @Test(expected = BowlingParser.FrameScoreMoreThanTenException.class)
    public void a_frame_with_total_more_than_ten() throws Exception {
        new BowlingParser().parse("492-3-4-5-6-7-8-9---");
    }

    @Test(expected = BowlingParser.WrongSpareFormatException.class)
    public void a_frame_with_total_equals_to_ten_without_spare_or_strike() throws Exception {
        new BowlingParser().parse("552-3-4-5-6-7-8-9---");
    }

    @Test
    public void the_eleventh_and_twelfth_frames_serve_only_as_bonus_of_tenth_frame() throws Exception {
        List<Frame> frames = new BowlingParser().parse("------------------XXX");

        assertThat(frames.size(), is(12));
        assertThat(frames.get(9), is(new StrikeFrame()));
        assertThat(frames.get(10), is(new StrikeFrame()));
        assertThat(frames.get(11), is(new StrikeFrame()));
    }


}
