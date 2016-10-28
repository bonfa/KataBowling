package com.kata.bowling;

import com.kata.bowling.frame.*;

import java.util.*;

public class BowlingParser {

    private static final int STRIKE = -1;
    private static final int SPARE = -2;
    private final Map<Character, Integer> map;

    public BowlingParser() {
        map = new HashMap<Character, Integer>() {{
            put('X', STRIKE);
            put('/', SPARE);
            put('-', 0);
            put('1', 1);
            put('2', 2);
            put('3', 3);
            put('4', 4);
            put('5', 5);
            put('6', 6);
            put('7', 7);
            put('8', 8);
            put('9', 9);
        }};
    }

    public List<Frame> parse(String frameString) {
        ArrayList<Frame> frames = new ArrayList<>();
        if (hasFrames(frameString)) {
            int[] singleTrialScores = getSingleTrialScores(frameString);
            frames = parse(singleTrialScores);
            updateFrameStructure(frames);
        }
        return frames;
    }

    private boolean hasFrames(String frameString) {
        return frameString != null && !frameString.isEmpty();
    }

    private int[] getSingleTrialScores(String frameString) {
        char[] charArrays = frameString.toCharArray();
        return getSingleTrialScores(charArrays);
    }

    private int[] getSingleTrialScores(char[] charArrays) {
        int[] trialScores = new int[charArrays.length];
        for (int i = 0; i < trialScores.length; i++) {
            trialScores[i] = parseSingleTrialScore(charArrays[i]);
        }
        return trialScores;
    }

    private ArrayList<Frame> parse(int[] trialScores) {
        return parse(0, trialScores);
    }

    private void updateFrameStructure(ArrayList<Frame> frames) {
        addNextTwoFramesToEachOfTheFirstNineFrames(frames);
        keepOnlyTheFirstTenFrames(frames);
    }

    private void addNextTwoFramesToEachOfTheFirstNineFrames(ArrayList<Frame> frames) {
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = getFrameAtIndex(frames, i);
            Frame nextFrame = getFrameAtIndex(frames, i + 1);
            Frame nextNextFrame = getFrameAtIndex(frames, i + 2);
            nextFrame.add(nextNextFrame);
            frame.add(nextFrame);
            if (i == 9) {
                nextNextFrame.add(new NullFrame());
                break;
            }
        }
    }

    private void keepOnlyTheFirstTenFrames(ArrayList<Frame> frames) {
        while (frames.size() > 10) {
            frames.remove(10);
        }
    }

    private Frame getFrameAtIndex(ArrayList<Frame> frames, int index) {
        Frame frame;
        if (index < frames.size()) {
            frame = frames.get(index);
        } else {
            frame = new NullFrame();
        }
        return frame;
    }

    private ArrayList<Frame> parse(int currentNumberOfTrials, int[] trialScores) {
        ArrayList<Frame> frames = new ArrayList<>();
        if (currentNumberOfTrials >= trialScores.length) {
            return frames;
        } else {
            int numberOfTrialsOfNextFrame = getNextFrameIndexOffset(currentNumberOfTrials, trialScores);
            Frame frame = parseFrame(currentNumberOfTrials, numberOfTrialsOfNextFrame, trialScores);
            frames.add(frame);
            frames.addAll(parse(currentNumberOfTrials + numberOfTrialsOfNextFrame, trialScores));
            return frames;
        }
    }

    private int getNextFrameIndexOffset(int indexOffset, int[] trialScores) {
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

    private int parseSingleTrialScore(char stringValue) {
        if (!map.containsKey(stringValue)) {
            throw new FrameParseException();
        }

        return map.get(stringValue);
    }

    public static class FrameParseException extends RuntimeException {
    }
}
