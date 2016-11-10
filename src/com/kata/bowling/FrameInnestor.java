package com.kata.bowling;

import com.kata.bowling.frame.Frame;
import com.kata.bowling.frame.NullFrame;

import java.util.ArrayList;

public class FrameInnestor {

    public FrameInnestor() {
    }

    public void innest(ArrayList<Frame> frames) {
        addNextTwoFramesToEachOfTheFirstNineFrames(frames);
        keepOnlyTheFirstTenFrames(frames);
    }

    private void addNextTwoFramesToEachOfTheFirstNineFrames(ArrayList<Frame> frames) {
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = getFrameAtIndex(frames, i);
            Frame nextFrame = getFrameAtIndex(frames, i + 1);
            Frame nextNextFrame = getFrameAtIndex(frames, i + 2);
            nextFrame.add(nextNextFrame);
            frame.add(nextFrame);
            if (i == 9) {
                nextNextFrame.add(new NullFrame());
                break;
            }
        }
    }

    private Frame getFrameAtIndex(ArrayList<Frame> frames, int index) {
        Frame frame;
        if (index < frames.size()) {
            frame = frames.get(index);
        } else {
            frame = new NullFrame();
        }
        return frame;
    }

    private void keepOnlyTheFirstTenFrames(ArrayList<Frame> frames) {
        while (frames.size() > 10) {
            frames.remove(10);
        }
    }
}
