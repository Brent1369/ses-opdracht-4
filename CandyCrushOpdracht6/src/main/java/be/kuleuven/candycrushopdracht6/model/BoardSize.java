package be.kuleuven.candycrushopdracht6.model;

import java.util.ArrayList;

public record BoardSize(int Width, int Height) {
    public BoardSize {

        if (Width <= 0) {
            throw new IllegalArgumentException("Width must be larger than 0");
        }
        if (Height <= 0) {
            throw new IllegalArgumentException("Height must be larger than 0");
        }
    }

    Iterable<Position> positions() {
        ArrayList<Position> allPositions = new ArrayList<>();

        for (int i = 0; i < Height; i++) {
            for (int j = 0; j < Width; j++) {
                allPositions.add(new Position(this, i, j));
            }
        }
        return allPositions;
    }

}