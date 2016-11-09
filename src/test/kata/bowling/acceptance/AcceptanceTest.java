package test.kata.bowling.acceptance;

import com.kata.bowling.Bowling;
import com.kata.bowling.BowlingParser;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcceptanceTest {

    @Test
    public void all_strikes() {
        assertThat(new Bowling().total(new BowlingParser().parse("XXXXXXXXXXXX")), is(300));
    }

    @Test
    public void all_spares() {
        assertThat(new Bowling().total(new BowlingParser().parse("5/5/5/5/5/5/5/5/5/5/5")), is(150));
    }

    @Test
    public void no_strikes_and_no_spares() {
        assertThat(new Bowling().total(new BowlingParser().parse("9-9-9-9-9-9-9-9-9-9-")), is(90));
    }

}
