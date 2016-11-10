package com.kata.bowling;

import com.kata.bowling.frame.*;

import java.util.*;

public class BowlingParser {

    private static final int STRIKE = -1;
    private static final int SPARE = -2;
    private static final HashMap<Character, Integer> PARSING_RULES = new HashMap<Character, Integer>() {{
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

    private final Map<Character, Integer> map;
    private final FrameInnestor frameInnestor;

    public BowlingParser(FrameInnestor frameInnestor) {
        map = PARSING_RULES;
        this.frameInnestor = frameInnestor;
    }

    public List<Frame> parse(String frameString) {
        if (!hasFrames(frameString)) {
            throw new TooFewFramesException();
        }
        return parseFrames(frameString);
    }

    private boolean hasFrames(String frameString) {
        return frameString != null && !frameString.isEmpty();
    }

    private ArrayList<Frame> parseFrames(String frameString) {
        int[] singleTrialScores = getSingleTrialScores(frameString);
        ArrayList<Frame> frames = parse(singleTrialScores);
        checkFrameLength(frames);
        checkInvalidFrames(frames);
        frameInnestor.innest(frames);
        return frames;
    }

    private void checkInvalidFrames(ArrayList<Frame> frames) {
        for (Frame frame : frames) {
            if (frame.getScore() > 10) {
                throw new FrameScoreMoreThanTenException();
            }
            else if (frame instanceof ScoreFrame && frame.getScore() == 10) {
                throw new WrongSpareFormatException();
            }
        }
    }

    private void checkFrameLength(ArrayList<Frame> frames) {
        if (frames.size() < 10) {
            throw new TooFewFramesException();
        }
        if (frames.size() > 12) {
            throw new TooManyFramesException();
        }
    }

    private int[] getSingleTrialScores(String frameString) {
        return getSingleTrialScores(frameString.toCharArray());
    }

    private int[] getSingleTrialScores(char[] charArrays) {
        int[] trialScores = new int[charArrays.length];
        for (int i = 0; i < trialScores.length; i++) {
            trialScores[i] = parseSingleTrialScore(charArrays[i]);
        }
        return trialScores;
    }

    private int parseSingleTrialScore(char stringValue) {
        if (!map.containsKey(stringValue)) {
            throw new FrameParseException();
        }

        return map.get(stringValue);
    }

    private ArrayList<Frame> parse(int[] trialScores) {
        return parse(0, trialScores);
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
        } else if (numberOfTrialsOffset < trialScores.length) {
            int firstTrialScore = trialScores[numberOfTrialsOffset];
            int secondTrialScore = 0;
            if (numberOfTrialsOffset + 1 < trialScores.length) {
                secondTrialScore = trialScores[numberOfTrialsOffset + 1];
            }
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

    public static class FrameParseException extends RuntimeException {
    }

    public class TooManyFramesException extends RuntimeException {
    }

    public class TooFewFramesException extends RuntimeException {
    }

    public class FrameScoreMoreThanTenException extends RuntimeException {
    }

    public class WrongSpareFormatException extends RuntimeException {
    }

}
