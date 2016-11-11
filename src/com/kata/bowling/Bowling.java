package com.kata.bowling;

import com.kata.bowling.frame.Frame;
import com.kata.bowling.frame.SpareFrame;
import com.kata.bowling.frame.StrikeFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bowling {

    private final List<Frame> frames;

    public Bowling(List<Frame> frames) {
        this.frames = frames;
    }

    //FIXME remove instance of
    public int total() {
        int totalScores = 0;
        if (frames != null && frames.size() > 0) {
            for (int i = 0; i < Math.min(10, frames.size()); i++) {
                Frame frameNew = frames.get(i);
                totalScores += frameNew.getScore();
                if (frameNew instanceof SpareFrame) {
                    if (i + 1 < frames.size()) {
                        totalScores += frames.get(i + 1).getFirstTrialScore();
                    }
                } else if (frameNew instanceof StrikeFrame) {
                    if (i + 1 < frames.size()) {
                        if (frames.get(i + 1) instanceof StrikeFrame) {
                            totalScores += frames.get(i + 1).getFirstTrialScore();
                            if (i + 2 < frames.size()) {
                                totalScores += frames.get(i + 2).getFirstTrialScore();
                            }
                        }
                        else {
                            totalScores += frames.get(i + 1).getScore();
                        }
                    }
                }
            }
        }
        return totalScores;
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
