package com.kata.bowling.frame;

public interface Frame {
    int getScore();

    void add(Frame nextFrame);

    int getFirstTrialScore();

    int getNextTwoTrialsScore();
}
