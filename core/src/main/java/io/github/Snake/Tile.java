package io.github.Snake;

public class Tile {
    int x;
    int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Tile other) {
        this.x = other.x;
        this.y = other.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile other = (Tile) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }
}


