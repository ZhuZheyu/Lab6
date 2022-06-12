package Controller;
import Data.Coordinates;
import Data.Person;
import Exceptions.SaveCollectionException;
import UI.AppConsole;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private LinkedHashMap<Long, Person> personMap = new LinkedHashMap<>();
    private LocalDateTime creationDate;
    private final FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.personMap = fileManager.readCollection();
        this.creationDate = LocalDateTime.now();
    }

    public void addToCollection(Person person){
        personMap.put(person.getId(), person);
    }

    public String toCSV() {
        String csv = "";
        for (Map.Entry entry: personMap.entrySet()) {
            Person person = (Person) entry.getValue();
            Coordinates coordinates = person.getCoordinates();
            csv += person.getName() + ", " + coordinates.getX() + ", "
                    + coordinates.getY() +  ", " + person.getHeight() + ", " + person.getEyeColor() + ", "
                    + person.getHairColor() + ", " + person.getNationality() + ", " + person.getLocation().getX()
                    + ", " + person.getLocation().getY() + ", " + person.getLocation().getZ() + ", " + person.getLocation().getName() + "\n";
        }
        return csv;
    }

    public void saveCollection() {
        try {
            fileManager.saveCollection(toCSV());
        } catch (SaveCollectionException exception) {
            AppConsole.printError("Invalid CSV writing");
        }
    }

    public void deleteCollection(Long key) {
        personMap.remove(key);
    }

    public void clearCollection() {
        personMap.clear();
    }

    public int calculateSum() {
        int summ = 0;
        for (Person p: personMap.values()) {
            summ += p.getHeight();
        }
        return summ;
    }

    public boolean replace(Person toReplace, Person replacer){
        toReplace.setName(replacer.getName());
        toReplace.setCoordinates(replacer.getCoordinates());
        toReplace.setHeight(replacer.getHeight());
        toReplace.setEyeColor(replacer.getEyeColor());
        toReplace.setHairColor(replacer.getHairColor());
        toReplace.setNationality(replacer.getNationality());
        toReplace.setLocation(replacer.getLocation());
        return true;
    }

    public Person[] findPersonWithSuchField(Person p, String fieldName){
        ArrayList<Person> res = new ArrayList<Person>();
        Comparator comp = PersonsComparators.getComparatorByName(fieldName);
        for(Person t : personMap.values()){
            if(comp.compare(t, p) == 0) res.add(t);
        }
        return res.toArray(new Person[res.size()]);

    }

    public Person[] findGreaterThenPersonByField(Person p, String fieldName){
        ArrayList<Person> res = new ArrayList<Person>();
        Comparator comp = PersonsComparators.getComparatorByName(fieldName);
        for(Person t : personMap.values()){
            if(comp.compare(t, p) > 0) res.add(t);
        }
        return res.toArray(new Person[res.size()]);
    }

    public Person[] findLessThenPersonByField(Person p, String fieldName){
        ArrayList<Person> res = new ArrayList<Person>();
        Comparator comp = PersonsComparators.getComparatorByName(fieldName);
        for(Person t : personMap.values()){
            if(comp.compare(t, p) < 0) res.add(t);
        }
        return res.toArray(new Person[res.size()]);
    }

    public Collection<Person> getCollection(){
        return Collections.unmodifiableCollection(personMap.values());
    }

    public Collection<Long> getCollectionKeys(){
        return personMap.keySet();
    }

    public Person getByKey(Long Key){
        return personMap.get(Key);
    }

    public boolean containsKey(long key){
        return personMap.containsKey(key);
    }

    public String information(){
        return "Class of collection: "+ personMap.getClass().getName() +
                "\nDate of creation: " + creationDate.toString() +
                "\nSize of collection: " + personMap.size();
    }
}
