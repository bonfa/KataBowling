package com.kata.bowling;

import com.kata.bowling.frame.*;

import java.util.ArrayList;
import java.util.List;

public class FrameParser {

    private static final int STRIKE = -1;
    private static final int SPARE = -2;

    public List<Frame> parse(String frameString) {
        ArrayList<Frame> frames = new ArrayList<>();
        if (frameString != null && !frameString.isEmpty()) {
            int[] trialScores = getSingleTrialScores(frameString);
            frames = parse(trialScores);
            updateFrameStructure(frames);
        }
        return frames;
    }

    private void updateFrameStructure(ArrayList<Frame> frames) {
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = getFrameAtIndex(frames, i);
            Frame nextFrame = getFrameAtIndex(frames, i + 1);
            nextFrame.add(getFrameAtIndex(frames, i + 2));
            frame.add(nextFrame);
        }
    }

    private Frame getFrameAtIndex(ArrayList<Frame> frames, int i) {
        Frame frame;
        if (i < frames.size()) {
            frame = frames.get(i);
        } else {
            frame = new NullFrame();
        }
        return frame;
    }

    private ArrayList<Frame> parse(int[] trialScores) {
        return parse(0, trialScores);
    }

    private ArrayList<Frame> parse(int numberOfTrialsOffset, int[] trialScores) {
        ArrayList<Frame> frames = new ArrayList<>();
        if (numberOfTrialsOffset >= trialScores.length) {
            return frames;
        } else {
            int numberOfTrialsOfNextFrame = getNextFrameIndex(numberOfTrialsOffset, trialScores);
            Frame frame = parseFrame(numberOfTrialsOffset, numberOfTrialsOfNextFrame, trialScores);
            frames.add(frame);
            frames.addAll(parse(numberOfTrialsOffset + numberOfTrialsOfNextFrame, trialScores));
            return frames;
        }
    }

    private int getNextFrameIndex(int indexOffset, int[] trialScores) {
        int nextFrameIndex = 2;
        if (trialScores[indexOffset] == STRIKE) {
            nextFrameIndex = 1;
        }
        return nextFrameIndex;
    }

    private Frame parseFrame(int numberOfTrialsOffset, int nextFrameIndex, int[] trialScores) {
        Frame frame;
        if (nextFrameIndex == 1) {
            frame = new StrikeFrame();
        } else if (numberOfTrialsOffset + 1 < trialScores.length) {
            int firstTrialScore = trialScores[numberOfTrialsOffset];
            int secondTrialScore = trialScores[numberOfTrialsOffset + 1];
            if (secondTrialScore == SPARE) {
                frame = new SpareFrame(firstTrialScore);
            } else {
                frame = new ScoreFrame(firstTrialScore, secondTrialScore);
            }
        } else {
            throw new FrameParseException();
        }
        return frame;
    }

    private int[] getSingleTrialScores(String frameString) {
        char[] charArrays = frameString.toCharArray();
        int[] trialScores = new int[charArrays.length];
        for (int i = 0; i < trialScores.length; i++) {
            trialScores[i] = parseSingleTrialScore(charArrays[i]);
        }
        return trialScores;
    }

    private int parseSingleTrialScore(char stringValue) {
        if (stringValue >= '1' && stringValue <= '9') {
            return Character.getNumericValue(stringValue);
        } else if (stringValue == 'X') {
            return STRIKE;
        } else if (stringValue == '/') {
            return SPARE;
        } else if (stringValue == '-') {
            return 0;
        } else {
            throw new FrameParseException();
        }
    }

    public static class FrameParseException extends RuntimeException {
    }
}