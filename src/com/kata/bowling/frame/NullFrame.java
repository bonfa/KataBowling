package com.kata.bowling.frame;

public class NullFrame implements Frame {

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void add(Frame nextFrame) {
        //nothing to do here
    }

    @Override
    public int getFirstTrialScore() {
        return 0;
    }

    @Override
    public int getNextTwoTrialsScore() {
        return 0;
    }

    @Override
    public Frame copy() {
        return new NullFrame();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o != null && getClass() == o.getClass());
    }

    @Override
    public String toString() {
        return "NullFrame{}";
    }
}
