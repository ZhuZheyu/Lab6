package server.Commands;

import Controller.CollectionManager;
import Controller.PersonBuilder;
import common.Data.Country;
import common.Data.Person;
import client.UI.AppConsole;

public class CountGreaterThanNationalityCommand extends AbstractCommand{
    private CollectionManager collection;
    private PersonBuilder personBuilder;

    public CountGreaterThanNationalityCommand(CollectionManager collectionManager, PersonBuilder personBuilder){
        super("replace_if_lower {nationality}", "Replace person with inputted key, if new person is less then old");
        this.collection = collectionManager;
        this.personBuilder = personBuilder;
    }

    @Override
    public boolean execute(String args) {
        if (!args.isEmpty()) throw new IllegalArgumentException();

        try {
            Country inputNationality = personBuilder.setCountry();
            long nationalitySum = 0;
            for(Person p : collection.getCollection()){
               if (inputNationality.compareTo(p.getNationality()) < 0) nationalitySum++;
            }
            AppConsole.println("Sum of nationalities greater than inputted " + nationalitySum);
            return true;
        } catch (Exception exception) {
            AppConsole.printError("Wrong number of arguments");
        }

        return false;
    }
}
