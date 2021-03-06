package com.kata.bowling.frame;

public class SpareFrame implements Frame {
    private int firstTrialScore;

    public SpareFrame(int firstTrialScore) {

        this.firstTrialScore = firstTrialScore;
    }

    @Override
    public int getFirstTrialScore() {
        return firstTrialScore;
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public boolean isSpare() {
        return true;
    }

    @Override
    public boolean isStrike() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpareFrame that = (SpareFrame) o;

        return firstTrialScore == that.firstTrialScore;

    }

    @Override
    public String toString() {
        return "SpareFrame{" +
                "firstTrialScore=" + firstTrialScore +
                "} " + super.toString();
    }
}
