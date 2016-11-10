package com.kata.bowling.frame;

public class ScoreFrame extends FrameImpl {
    private int firstTrialScoreValue;
    private int secondTrialScoreValue;

    public ScoreFrame(int firstTrialScoreValue, int secondTrialScoreValue) {

        this.firstTrialScoreValue = firstTrialScoreValue;
        this.secondTrialScoreValue = secondTrialScoreValue;
    }

    @Override
    public int getScoreWithBonus() {
        return firstTrialScoreValue + secondTrialScoreValue;
    }

    @Override
    public int getFirstTrialScore() {
        return firstTrialScoreValue;
    }

    @Override
    public int getNextTwoTrialsScore() {
        return getScoreWithBonus();
    }

    @Override
    public int getScore() {
        return getScoreWithBonus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreFrame frame = (ScoreFrame) o;

        if (firstTrialScoreValue != frame.firstTrialScoreValue) return false;
//        if (!nextFrame.equals(frame.nextFrame)) return false;
        return secondTrialScoreValue == frame.secondTrialScoreValue;
    }

    @Override
    public String toString() {
        return "ScoreFrame{" +
                "firstTrialScoreValue=" + firstTrialScoreValue +
                ", secondTrialScoreValue=" + secondTrialScoreValue +
                "} " + super.toString();
    }
}
