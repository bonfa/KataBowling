package test.kata.bowling;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FrameParserTest {

    @Test
    public void a_single_frame() throws Exception {
        assertThat(new FrameParser().parse(""), is(new ArrayList<Frame>()));
    }

    private class FrameParser {
        List<Frame> parse(String frames) {
            return new ArrayList<>();
        }
    }

    private class Frame {

    }
}
