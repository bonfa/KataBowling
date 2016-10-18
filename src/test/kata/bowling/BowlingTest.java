package test.kata.bowling;

import com.kata.bowling.Bowling;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingTest {

    @Test
    public void one_miss() throws Exception {
        assertThat(new Bowling("-").total(), is(0));
    }

    @Test
    public void one_strike() throws Exception {
        assertThat(new Bowling("x").total(), is(10));
    }

    @Test
    public void one_hit() throws Exception {
        assertThat(new Bowling("2").total(), is(2));
    }

    @Test
    public void another_hit() throws Exception {
        assertThat(new Bowling("4").total(), is(4));
    }

//    @Test#
//    public void one_hit() throws Exception {
//        assertThat(new Bowling("2").total(), is(2));
//    }
}
