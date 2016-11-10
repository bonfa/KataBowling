package com.kata.bowling;

import com.kata.bowling.frame.Frame;

import java.util.List;
import java.util.Scanner;

public class Bowling {

    private final FrameInnestor frameInnestor;
    private final List<Frame> frames;

    public Bowling(List<Frame> frames, FrameInnestor frameInnestor) {
        this.frameInnestor = frameInnestor;
        this.frames = frames;
    }

    public int total() {
        int totalScores = 0;
        if (frames != null && frames.size() > 0) {
            frameInnestor.innest(frames);
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
            Bowling bowling = new Bowling(frames, new FrameInnestor());
            int total = bowling.total();
            System.out.println("total = " + total);
        }
        scanner.close();
    }

}
