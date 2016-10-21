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
    public void a_singleFrame() {
        List<Frame> frames = new ArrayList<>();
        frames.add(new ScoreFrame(4, 0));
        Bowling bowling = new Bowling();
        assertThat(bowling.total(frames), is(4));
    }

}
