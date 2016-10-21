package test.kata.bowling;

import com.kata.bowling.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingTest {

    @Test
    public void a_single_frame() {
        List<Frame> frames = new ArrayList<>();
        ScoreFrame frame = new ScoreFrame(4, 0);
        frames.add(frame);
        Bowling bowling = new Bowling();
        assertThat(bowling.total(frames), is(frame.getScore()));
    }

    @Test
    public void a_single_spare_frame() {
        List<Frame> frames = new ArrayList<>();
        Frame frame = new SpareFrame(4);
        frames.add(frame);
        Bowling bowling = new Bowling();
        assertThat(bowling.total(frames), is(frame.getScore()));
    }

    @Test
    public void a_single_strike_frame() {
        List<Frame> frames = new ArrayList<>();
        Frame frame = new StrikeFrame();
        frames.add(frame);
        Bowling bowling = new Bowling();
        assertThat(bowling.total(frames), is(frame.getScore()));
    }



}
