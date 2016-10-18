package test.kata.bowling;

import com.kata.bowling.Bowling;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcceptanceTest {


    @Test@Ignore
    public void all_strikes() {
        assertThat(new Bowling("XXXXXXXXXXXX").total(), is(300));
    }

    @Test@Ignore
    public void all_spares() {
        assertThat(new Bowling("5/5/5/5/5/5/5/5/5/5/5").total(), is(150));
    }

    @Test@Ignore
    public void no_strikes_and_no_spares() {
        assertThat(new Bowling("9-9-9-9-9-9-9-9-9-9-").total(), is(90));
    }

}
