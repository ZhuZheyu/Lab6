package Data;

import java.time.LocalDateTime;
import java.util.Objects;

public class Person implements Comparable<Person>{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле не может быть null

    static private long curID = 0;

    public Person(String name, Coordinates coordinates, long height, Color eyeColor, Color hairColor, Country nationality, Location location){
        this.id = curID++;
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.creationDate = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
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
    public int compareTo(Person o) {
        return this.getHeight().compareTo(o.getHeight());
    }

    @Override
    public String toString() {
        return "Person № " + id +
                "\nName: '" + name + '\'' +
                "\nCoordinates: " + coordinates +
                "\nCreationDate: " + creationDate +
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
                && getId().equals(person.getId())
                && getName().equals(person.getName())
                && getCoordinates().equals(person.getCoordinates())
                && getCreationDate().equals(person.getCreationDate())
                && getEyeColor() == person.getEyeColor()
                && getHairColor() == person.getHairColor()
                && getNationality() == person.getNationality()
                && getLocation().equals(person.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCoordinates(), getCreationDate(), getHeight(), getEyeColor(), getHairColor(), getNationality(), getLocation());
    }
}

