package com.kata.bowling;

import com.kata.bowling.frame.Frame;

import java.util.List;
import java.util.Scanner;

public class Bowling {

    public Bowling() {
    }

    public int total(List<Frame> frames) {
        int totalScores = 0;
        if (frames != null && frames.size() > 0) {
            for (Frame frame : frames) {
                totalScores += frame.getScoreWithBonus();
            }
        }
        return totalScores;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            BowlingParser parser = new BowlingParser();
            List<Frame> frames = parser.parse(scanner.nextLine());
            Bowling bowling = new Bowling();
            int total = bowling.total(frames);
            System.out.println("total = " + total);
        }
        scanner.close();
    }

}
