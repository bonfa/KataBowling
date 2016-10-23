package com.kata.bowling;

abstract class FrameImpl implements Frame {

    protected Frame nextFrame;

    @Override
    public void add(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
