package com.kata.bowling.frame;

abstract class FrameImpl implements Frame {

    Frame nextFrame;

    @Override
    public void add(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
