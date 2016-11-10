package test.kata.bowling;

import com.kata.bowling.*;
import com.kata.bowling.frame.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingTest {

    private static final Frame NULL_FRAME = new NullFrame();
    private FrameInnestor frameInnestor;

    @Before
    public void setUp() throws Exception {
        frameInnestor = new FrameInnestor();
    }

    @Test
    public void no_frames() {
        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(null), is(0));
        assertThat(bowling.total(new ArrayList<>()), is(0));
    }

    @Test
    public void a_single_frame() {
        List<Frame> frames = new ArrayList<>();
        Frame frame = new ScoreFrame(4, 0);
        frames.add(frame);
        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(frames), is(4));
    }

    @Test
    public void a_single_spare_frame() {
        List<Frame> frames = new ArrayList<>();
        Frame frame = new SpareFrame(4);
        frame.add(NULL_FRAME);
        frames.add(frame);
        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(frames), is(10));
    }

    @Test
    public void a_single_strike_frame() {
        List<Frame> frames = new ArrayList<>();
        Frame frame = new StrikeFrame();
        frame.add(NULL_FRAME);
        frames.add(frame);
        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(frames), is(10));
    }

    @Test
    public void more_frames_without_strikes_or_spares() throws Exception {
        List<Frame> frames = new ArrayList<>();
        Frame frame_1 = new ScoreFrame(2, 0);
        Frame frame_2 = new ScoreFrame(4, 3);
        Frame frame_3 = new ScoreFrame(5, 4);
        frames.add(frame_1);
        frames.add(frame_2);
        frames.add(frame_3);
        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(frames), is(2 + 7 + 9));
    }

    @Test
    public void more_frames_with_spares() throws Exception {
        List<Frame> frames = new ArrayList<>();

        Frame frame_3 = new ScoreFrame(5, 4);
        Frame frame_2 = new ScoreFrame(4, 3);
        frame_2.add(frame_3);
        Frame frame_1 = new SpareFrame(4);
        frame_1.add(frame_2);

        frames.add(frame_1);
        frames.add(frame_2);
        frames.add(frame_3);

        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(frames), is((10 + 4) + 7 + 9));
    }

    @Test
    public void more_frames_with_strikes() throws Exception {
        List<Frame> frames = new ArrayList<>();

        Frame frame_3 = new ScoreFrame(5, 4);
        Frame frame_2 = new ScoreFrame(4, 3);
        frame_2.add(frame_3);
        Frame frame_1 = new StrikeFrame();
        frame_1.add(frame_2);

        frames.add(frame_1);
        frames.add(frame_2);
        frames.add(frame_3);

        Bowling bowling = new Bowling(frameInnestor);
        assertThat(bowling.total(frames), is((10 + 7) + 7 + 9));
    }

}
