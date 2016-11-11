package com.kata.bowling.frame;

public class NullFrame implements Frame {
    @Override
    public int getFirstTrialScore() {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public boolean isSpare() {
        return false;
    }

    @Override
    public boolean isStrike() {
        return false;
    }
}
