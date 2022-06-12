package common.Interaction;

import common.Data.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for get Bands value.
 */
public class PersonRaw implements Serializable {
    private String name;
    private Coordinates coordinates;
    private Long height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public PersonRaw(String name, Coordinates coordinates, long height, Color eyeColor, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public static Person getDefaultInstance() {
        return new Person("", new Coordinates(0, 0), 0, Color.BLACK, Color.BLACK, Country.CHINA
                ,new Location(0.0F, 0, 0.0, ""));
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person (Raw)" +
                "\nName: '" + name + '\'' +
                "\nCoordinates: " + coordinates +
                "\nHeight: " + height +
                "\nEye Color: " + eyeColor +
                "\nHair Color: " + hairColor +
                "\nNationality: " + nationality +
                "\nLocation: " + location +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getHeight(), person.getHeight())
                && getName().equals(person.getName())
                && getCoordinates().equals(person.getCoordinates())
                && getEyeColor() == person.getEyeColor()
                && getHairColor() == person.getHairColor()
                && getNationality() == person.getNationality()
                && getLocation().equals(person.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoordinates(), getHeight(), getEyeColor(), getHairColor(), getNationality(), getLocation());
    }
}
