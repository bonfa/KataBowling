package com.kata.bowling;

public class StrikeFrame extends FrameImpl {
    @Override
    public int getScore() {
        return 10 + nextFrame.getNextTwoTrialsScore();
    }

    @Override
    public int getFirstTrialScore() {
        return 10;
    }

    @Override
    public int getNextTwoTrialsScore() {
        return 10 + nextFrame.getFirstTrialScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StrikeFrame that = (StrikeFrame) o;

        return nextFrame.equals(that.nextFrame);
    }

}
