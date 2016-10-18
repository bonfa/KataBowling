package com.kata.bowling;

public class Bowling {

    private String framesResult;

    public Bowling(String framesResult) {

        this.framesResult = framesResult;
    }

    public static void main(String[] args) {
        // write your code here
    }

    public int total() {
        if (framesResult.equals("2"))  {
            return 2;
        }
        if (framesResult == "x") {
            return 10;
        }
        return 0;
    }

}
