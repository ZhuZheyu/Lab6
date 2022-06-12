package common.Data;

import java.util.Objects;

public class Location implements Comparable<Location>{
    private Float x;
    private long y;
    private Double z;
    private String name;

    public Location(Float x, long y, Double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getY() == location.getY() && getX().equals(location.getX()) && getZ().equals(location.getZ()) && getName().equals(location.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ(), getName());
    }

    @Override
    public String toString() {
        return "(" +
                "x = " + x +
                ", y = " + y +
                ", z = " + z +
                ", name = '" + name + '\'' +
                ')';
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Location o) {
        return ((Integer)(this.hashCode())).compareTo((o.hashCode()));
    }
}
