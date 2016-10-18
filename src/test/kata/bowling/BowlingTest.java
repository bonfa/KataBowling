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
        assertThat(new Bowling("1").total(), is(1));
        assertThat(new Bowling("2").total(), is(2));
        assertThat(new Bowling("3").total(), is(3));
        assertThat(new Bowling("4").total(), is(4));
        assertThat(new Bowling("5").total(), is(5));
        assertThat(new Bowling("6").total(), is(6));
        assertThat(new Bowling("7").total(), is(7));
        assertThat(new Bowling("8").total(), is(8));
        assertThat(new Bowling("9").total(), is(9));
    }

}
