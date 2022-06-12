package server.Commands;

import Controller.CollectionManager;
import Controller.PersonBuilder;
import common.Data.Color;
import common.Data.Person;
import client.UI.AppConsole;

public class FilterGreaterThanHairColorCommand extends AbstractCommand{
    private CollectionManager collection;
    private PersonBuilder personBuilder;

    public FilterGreaterThanHairColorCommand(CollectionManager collectionManager, PersonBuilder personBuilder){
        super("greater_than_hair_color {color}", "Replace person with inputted key, if new person is less then old. Arguments: id of old person");
        this.collection = collectionManager;
        this.personBuilder = personBuilder;
    }

    @Override
    public boolean execute(String args) {
        if (args.isEmpty()) throw new IllegalArgumentException();

        try {
            Color inputtedColor = Color.valueOf(args);

            for(Person p : collection.getCollection()){
                if(inputtedColor.compareTo(p.getHairColor()) < 0) AppConsole.println(p.toString());
            }
            AppConsole.println("Persons has been deleted");
            return true;
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("You inputted wrong Hair Color");
        }
        return false;
    }
}

