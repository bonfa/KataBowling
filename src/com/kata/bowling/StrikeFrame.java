package com.kata.bowling;

public class StrikeFrame implements Frame {
    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o != null && getClass() == o.getClass());
    }
}
