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

    public BowlingParser() {
        map = PARSING_RULES;
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

    private List<Frame> parseFrames(String frameString) {
        List<Frame> frames = parse(getSingleTrialScores(frameString));
        throwExceptionIfThereAreLessThanTenFrames(frames);
        throwExceptionIfThereAreMoreThanTwelveFrames(frames);
        throwExceptionIfThereAreFramesWithScoreGreaterThanTen(frames);
        throwExceptionIfThereAreScoreFramesWithScoreGreaterThanTen(frames);
        return frames;
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

    private List<Frame> parse(int[] trialScores) {
        List<Frame> frames = new ArrayList<>();
        int currentNumberOfTrials = 0;
        while (currentNumberOfTrials < trialScores.length) {
            frames.add(parseFrame(trialScores, currentNumberOfTrials));
            currentNumberOfTrials = updateCurrentNumberOfTrials(trialScores[currentNumberOfTrials], currentNumberOfTrials);
        }
        return frames;
    }

    private Frame parseFrame(int[] trialScores, int currentNumberOfTrials) {
        if (trialScores[currentNumberOfTrials] == STRIKE) {
            return new StrikeFrame();
        } else {
            return parseTwoTrialsFrame(trialScores, currentNumberOfTrials);
        }
    }

    private Frame parseTwoTrialsFrame(int[] trialScores, int currentNumberOfTrials) {
        int firstTrialScore = getTrialScoreAtPosition(trialScores, currentNumberOfTrials);
        int secondTrialScore = getTrialScoreAtPosition(trialScores, currentNumberOfTrials + 1);
        if (secondTrialScore == SPARE) {
            return new SpareFrame(firstTrialScore);
        } else {
            return new ScoreFrame(firstTrialScore, secondTrialScore);
        }
    }

    private int getTrialScoreAtPosition(int[] trialScores, int index) {
        int trialScore = 0;
        if (index < trialScores.length) {
            trialScore = trialScores[index];
        }
        return trialScore;
    }

    private int updateCurrentNumberOfTrials(int trialScore, int currentNumberOfTrials) {
        if (trialScore == STRIKE) {
            currentNumberOfTrials++;
        } else {
            currentNumberOfTrials += 2;
        }
        return currentNumberOfTrials;
    }

    private void throwExceptionIfThereAreLessThanTenFrames(List<Frame> frames) {
        if (frames.size() < 10) {
            throw new TooFewFramesException();
        }
    }

    private void throwExceptionIfThereAreMoreThanTwelveFrames(List<Frame> frames) {
        if (frames.size() > 12) {
            throw new TooManyFramesException();
        }
    }

    private void throwExceptionIfThereAreFramesWithScoreGreaterThanTen(List<Frame> frames) {
        for (Frame frame : frames) {
            if (frame.getScore() > 10) {
                throw new FrameScoreMoreThanTenException();
            }
        }
    }

    private void throwExceptionIfThereAreScoreFramesWithScoreGreaterThanTen(List<Frame> frames) {
        for (Frame frame : frames) {
            if (frame instanceof ScoreFrame && frame.getScore() == 10) {
                throw new WrongSpareFormatException();
            }
        }
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
