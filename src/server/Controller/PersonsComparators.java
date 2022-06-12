package server.Controller;

import common.Data.Person;

import java.util.Comparator;
import java.util.HashMap;

public class PersonsComparators {
    private static HashMap<String, Comparator> comparatorMap = new HashMap<>();
    static {
        comparatorMap.put("id", new idComparator());
        comparatorMap.put("name", new nameComparator());
        comparatorMap.put("coordinates", new coordinateComparator());
        comparatorMap.put("creationDate", new creationDateComparator());
        comparatorMap.put("height", new heightComparator());
        comparatorMap.put("eyeColor", new eyeColorComparator());
        comparatorMap.put("hairColor", new hairColorComparator());
        comparatorMap.put("nationality", new nationalityComparator());
        comparatorMap.put("location", new locationComparator());
    }

    public static Comparator<Person> getComparatorByName(String comparaorName){
        return comparatorMap.get(comparaorName);
    }

    private static class idComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }

    private static class nameComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    private static class coordinateComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getCoordinates().compareTo(o2.getCoordinates());
        }
    }

    private static class creationDateComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getCreationDate().compareTo(o2.getCreationDate());
        }
    }

    private static class heightComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getHeight().compareTo(o2.getHeight());
        }
    }

    private static class eyeColorComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getEyeColor().compareTo(o2.getEyeColor());
        }
    }

    private static class hairColorComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getHairColor().compareTo(o2.getHairColor());
        }
    }

    private static class nationalityComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getNationality().compareTo(o2.getNationality());
        }
    }

    private static class locationComparator implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getLocation().compareTo(o2.getLocation());
        }
    }
}
