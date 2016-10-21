package com.kata.bowling;

public class SpareFrame implements Frame {
    private int firstTrialScore;

    public SpareFrame(int firstTrialScore) {

        this.firstTrialScore = firstTrialScore;
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpareFrame that = (SpareFrame) o;

        return firstTrialScore == that.firstTrialScore;

    }
}
