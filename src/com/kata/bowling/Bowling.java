package com.kata.bowling;

import java.util.List;

public class Bowling {

    public Bowling() {
    }

    public int total(List<Frame> frames) {
        return frames.get(0).getScore();
    }

    public static void main(String[] args) {
        // write your code here
    }

}
