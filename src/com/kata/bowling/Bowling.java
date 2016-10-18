package com.kata.bowling;

import java.util.HashMap;
import java.util.Map;

public class Bowling {

    private String framesResult;
    private final Map<String, Integer> singleTryScore = new HashMap<String, Integer>() {{
        put("-", 0);
        put("1", 1);
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("/", 10);
        put("x", 10);
    }};

    public Bowling(String framesResult) {

        this.framesResult = framesResult;
    }

    public static void main(String[] args) {
        // write your code here
    }

    public int total() {
        return getSingleTryScore();
    }

    private int getSingleTryScore() {
        int value = 0;
        if (singleTryScore.containsKey(framesResult)) {
            value = singleTryScore.get(framesResult);
        }
        return value;
    }

}
