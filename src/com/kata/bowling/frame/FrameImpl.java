package com.kata.bowling.frame;

abstract class FrameImpl implements Frame {

    Frame nextFrame;

    @Override
    public void add(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }

    @Override
    public String toString() {
        return "FrameImpl{" +
                "nextFrame=" + nextFrame.toString() +
                '}';
    }
}
