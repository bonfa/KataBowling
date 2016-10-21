package com.kata.bowling;

import java.util.List;

public class Bowling {

    public Bowling() {
    }

    public int total(List<Frame> frames) {
        int totalScores = 0;
        if (frames != null && frames.size() > 0) {
            for (Frame frame : frames) {
                totalScores += frame.getScore();
            }
        }
        return totalScores;
    }

    public static void main(String[] args) {
        // write your code here
    }

}
