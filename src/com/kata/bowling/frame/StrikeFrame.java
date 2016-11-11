package com.kata.bowling.frame;

public class StrikeFrame implements Frame {

    @Override
    public int getFirstTrialScore() {
        return 10;
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public String toString() {
        return "StrikeFrame{} " + super.toString();
    }
}
