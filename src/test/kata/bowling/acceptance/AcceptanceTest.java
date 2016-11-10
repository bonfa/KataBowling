package test.kata.bowling.acceptance;

import com.kata.bowling.Bowling;
import com.kata.bowling.BowlingParser;
import com.kata.bowling.FrameInnestor;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcceptanceTest {

    private FrameInnestor frameInnestor = new FrameInnestor();

    @Test
    public void all_strikes() {
        assertThat(new Bowling(new BowlingParser().parse("XXXXXXXXXXXX"), frameInnestor).total(), is(300));
    }

    @Test
    public void all_spares() {
        assertThat(new Bowling(new BowlingParser().parse("5/5/5/5/5/5/5/5/5/5/5"), frameInnestor).total(), is(150));
    }

    @Test
    public void no_strikes_and_no_spares() {
        assertThat(new Bowling(new BowlingParser().parse("9-9-9-9-9-9-9-9-9-9-"), frameInnestor).total(), is(90));
    }

}
