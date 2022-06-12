package client.Controller;

import client.UI.AppConsole;
import common.Data.*;

import java.util.Scanner;

public class PersonBuilder {
    private Scanner userScanner;
    private boolean fileMode;

    public PersonBuilder(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public Country setCountry() {
        Country country;

        while (true) {
            try {
                AppConsole.println("Input country (UNITED_KINGDOM, GERMANY, CHINA, INDIA, SOUTH_KOREA):");
                AppConsole.print("> ");
                String stringValue = userScanner.nextLine().trim();
                country = Country.valueOf(stringValue);
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return country;
    }

    public Color setHairColor() {
        Color color;

        while (true) {
            try {
                AppConsole.println("Input hair color (BLACK, BLUE, GREEN, RED, BROWN, YELLOW):");
                AppConsole.print("> ");
                String stringValue = userScanner.nextLine().trim();
                color = Color.valueOf(stringValue);
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return color;
    }

    public String setName() {
        String name;

        while (true) {
            try {
                AppConsole.println("Input name:");
                AppConsole.print("> ");
                name = userScanner.nextLine().trim();
                if (name.isEmpty()) throw new IllegalArgumentException();

                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return name;
    }

    public long setX() {
        long xCoordinate;

        while (true) {
            try {
                AppConsole.println("Input X-coordinate:");
                AppConsole.print("> ");
                String stringValue = userScanner.nextLine().trim();
                xCoordinate = Long.parseLong(stringValue);
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return xCoordinate;
    }

    public int setY() {
        int yCoordinate;

        while (true) {
            try {
                AppConsole.println("Input Y-coordinate:");
                AppConsole.print("> ");
                String stringValue = userScanner.nextLine().trim();
                yCoordinate = Integer.parseInt(stringValue);

                if (yCoordinate > 141) throw new IllegalArgumentException();
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return yCoordinate;
    }

    public Coordinates setCoordinates() {
        return new Coordinates(setX(), setY());
    }

    public Long setHeight() {
        Long height;

        while (true) {
            try {
                AppConsole.println("Input height:");
                AppConsole.print("> ");
                String stringValue = userScanner.nextLine().trim();
                height = Long.parseLong(stringValue);
                if (height < 0) throw new IllegalArgumentException();
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return height;
    }

    public Color setEyeColor() {
        Color color;

        while (true) {
            try {
                AppConsole.println("Input eye color (BLACK, BLUE, GREEN, RED, BROWN, YELLOW):");
                AppConsole.print("> ");
                String stringValue = userScanner.nextLine().trim();
                color = Color.valueOf(stringValue);
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return color;
    }

    public Location setLocation() {
        Location location;

        while (true) {
            try {
                AppConsole.println("Input x:");
                AppConsole.print("> ");
                Float x = Float.valueOf(userScanner.nextLine().trim());
                AppConsole.println("Input y:");
                AppConsole.print("> ");
                long y = Long.parseLong(userScanner.nextLine().trim());
                AppConsole.println("Input z:");
                AppConsole.print("> ");
                Double z = Double.parseDouble(userScanner.nextLine().trim());
                AppConsole.println("Input name of location:");
                AppConsole.print("> ");
                String name = userScanner.nextLine().trim();
                if (name.isEmpty()) throw new IllegalArgumentException();
                location = new Location(x, y, z, name);
                break;
            } catch (IllegalArgumentException exception) {
                AppConsole.printError("Invalid value");
            }
        }

        return location;
    }

    public Person setPerson() {
        return new Person(
                setName(),
                setCoordinates(),
                setHeight(),
                setEyeColor(),
                setHairColor(),
                setCountry(),
                setLocation()
        );
    }

    public Scanner getUserScanner() {
        return  this.userScanner;
    }

    public void setUserScanner(Scanner scanner) {
        this.userScanner = scanner;
    }

    public void setUserMode() {
        this.fileMode = false;
    }

    public void setFileMode() {
        this.fileMode = true;
    }

}