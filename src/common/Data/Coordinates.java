package common.Data;

import java.util.Objects;

public class Coordinates implements Comparable<Coordinates>{
    private long x;
    private int y;

    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "(" +
                "x = " + x +
                ", y = " + y +
                ')';
    }

    public long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Coordinates o) {
        Integer a = this.hashCode();
        Integer b = o.hashCode();
        return a.compareTo(b);
    }
}
