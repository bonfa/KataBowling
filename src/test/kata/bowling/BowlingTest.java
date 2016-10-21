package test.kata.bowling;

import com.kata.bowling.Bowling;
import com.kata.bowling.Frame;
import com.kata.bowling.ScoreFrame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingTest {

    @Test
    public void no_frames() {
        Bowling bowling = new Bowling();
        assertThat(bowling.total(null), is(0));
        assertThat(bowling.total(new ArrayList<>()), is(0));
    }

    @Test
    public void a_single_frame() {
        List<Frame> frames = new ArrayList<>();
        ScoreFrame frame = new ScoreFrame(4, 0);
        frames.add(frame);
        Bowling bowling = new Bowling();
        assertThat(bowling.total(frames), is(frame.getScore()));
    }

//
//    @Test
//    public void a_single_spare_frame() {
//        List<Frame> frames = new ArrayList<>();
//        Frame frame = new SpareFrame(4);
//        frames.add(frame);
//        Bowling bowling = new Bowling();
//        assertThat(bowling.total(frames), is(frame.getScore()));
//    }
//
//    @Test
//    public void a_single_strike_frame() {
//        List<Frame> frames = new ArrayList<>();
//        Frame frame = new StrikeFrame();
//        frames.add(frame);
//        Bowling bowling = new Bowling();
//        assertThat(bowling.total(frames), is(frame.getScore()));
//    }
//
//    @Test
//    public void two_frames_without_strikes_or_spares() throws Exception {
//        List<Frame> frames = new ArrayList<>();
//        ScoreFrame frame_1 = new ScoreFrame(2, 0);
//        ScoreFrame frame_2 = new ScoreFrame(4, 3);
//        frames.add(frame_1);
//        frames.add(frame_2);
//        Bowling bowling = new Bowling();
//        assertThat(bowling.total(frames), is(frame_1.getScore() + frame_2.getScore()));
//    }

}
