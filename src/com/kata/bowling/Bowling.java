package com.kata.bowling;

import com.kata.bowling.frame.Frame;
import com.kata.bowling.frame.NullFrame;

import java.util.List;
import java.util.Scanner;

public class Bowling {

    private static final int MAX_NUMBER_OF_FRAMES = 10;
    private final List<Frame> frames;

    public Bowling(List<Frame> frames) {
        this.frames = frames;
    }

    public int total() {
        int totalScores = 0;
        if (frames != null && frames.size() > 0) {
            totalScores = getTotalScores(totalScores);
        }
        return totalScores;
    }

    private int getTotalScores(int totalScores) {
        for (int i = 0; i < MAX_NUMBER_OF_FRAMES; i++) {
            Frame frame = getFrameAt(i);
            totalScores += frame.getScore();
            if (frame.isSpare()) {
                Frame nextFrame = getFrameAt(i + 1);
                totalScores += nextFrame.getFirstTrialScore();
            } else if (frame.isStrike()) {
                Frame nextFrame = getFrameAt(i + 1);
                totalScores += nextFrame.getScore();
                if (isStrike(nextFrame)) {
                    Frame nextNextFrame = getFrameAt(i + 2);
                    totalScores += nextNextFrame.getFirstTrialScore();
                }
            }
        }
        return totalScores;
    }

    private Frame getFrameAt(int index) {
        Frame nextFrame;
        if (index < frames.size()) {
            nextFrame = frames.get(index);
        } else {
            nextFrame = new NullFrame();
        }
        return nextFrame;
    }


    private boolean isStrike(Frame frame) {
        return frame.isStrike();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            BowlingParser parser = new BowlingParser();
            List<Frame> frames = parser.parse(scanner.nextLine());
            Bowling bowling = new Bowling(frames);
            int total = bowling.total();
            System.out.println("total = " + total);
        }
        scanner.close();
    }

}
