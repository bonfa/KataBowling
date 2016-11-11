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
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o != null && getClass() == o.getClass());
    }

    @Override
    public String toString() {
        return "NullFrame{}";
    }
}
