package com.kata.bowling;

public interface Frame {
    int getScore();

    void add(Frame nextFrame);

    int getFirstTrialScore();
}
