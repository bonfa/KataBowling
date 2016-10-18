package com.kata.bowling;

public class Bowling {

//    private final Map<String, Integer> singleTryScore = new HashMap<String, Integer>() {{
//        put("-", 0);
//        put("1", 1);
//        put("2", 2);
//        put("3", 3);
//        put("4", 4);
//        put("5", 5);
//        put("6", 6);
//        put("7", 7);
//        put("8", 8);
//        put("9", 9);
//        put("/", 10);
//        put("x", 10);
//    }};

    public Bowling() {
    }

    public int total(String framesResult) {
        return getSingleTryScore(framesResult);
    }

    private int getSingleTryScore(String framesResult) {
//        int value = 0;
//        if (singleTryScore.containsKey(framesResult)) {
//            value = singleTryScore.get(framesResult);
//        }
        return 0;
    }

    public static void main(String[] args) {
        // write your code here
    }

}
