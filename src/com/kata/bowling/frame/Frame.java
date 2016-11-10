package com.kata.bowling.frame;

public interface Frame {
    int getScoreWithBonus();

    void add(Frame nextFrame);

    int getFirstTrialScore();

    int getNextTwoTrialsScore();

    int getScore();
}
