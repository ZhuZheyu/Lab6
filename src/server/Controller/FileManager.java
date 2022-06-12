package server.Controller;

import common.Data.*;
import common.Exceptions.SaveCollectionException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class FileManager {
    private final Path path;

    public FileManager() throws IOException {
        if (Files.exists(Paths.get("Persons.csv"))) path = Paths.get("Persons.csv");
        else path = Files.createFile(Paths.get("Persons.csv"));
    }

    public LinkedHashMap<Long, Person> readCollection() {
        LinkedHashMap<Long, Person> persons = new LinkedHashMap<>();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))) {
            System.out.println("File reading in progress...");
            while ((line = bufferedReader.readLine()) != null) {
                String[] fileLines = line.split(", ");
                Long height = Long.parseLong(fileLines[3].trim());
                Coordinates coordinates = new Coordinates(Long.parseLong(fileLines[1].trim()), Integer.parseInt(fileLines[2].trim()));
                Location location = new Location(Float.parseFloat(fileLines[7]), Long.parseLong(fileLines[8]), Double.parseDouble(fileLines[9]), fileLines[10]);
                Person person = new Person(fileLines[0], coordinates, height, Color.valueOf(fileLines[4].trim()),
                        Color.valueOf(fileLines[5]), Country.valueOf(fileLines[6]), location);
                persons.put(person.getId(), person);
            }
            bufferedReader.close();
            System.out.println("File reading completed!");
        } catch (IOException e) {
            System.out.println("File " + path + " is not found!");
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid input data format!");
        }
        return persons;
    }

    public void saveCollection(String string) throws SaveCollectionException {
        try (FileOutputStream out = new FileOutputStream(path.toString());
            PrintWriter writer = new PrintWriter(out))
        {
            writer.write(string);
        }
        catch(IOException ex){
            System.out.println("File not found!");
        }

    }

}
