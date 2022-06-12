package server.Commands;

import Controller.CollectionManager;
import Controller.PersonBuilder;
import common.Data.Person;
import client.UI.AppConsole;

public class ReplaceIfGreaterCommand extends AbstractCommand{
    private CollectionManager collection;
    private PersonBuilder personBuilder;

    public ReplaceIfGreaterCommand(CollectionManager collectionManager,
                                   PersonBuilder persBuild){
        super("replace_if_greater {element}", "Replace person with inputted key, if new person is more then old");
        this.collection = collectionManager;
        this.personBuilder = persBuild;
    }

    @Override
    public boolean execute(String args){
        Person oldPerson;
        Person newPerson;

        try {
            if (args.isEmpty()) throw new IllegalArgumentException();
            long keyToReplace = Long.parseLong(args);
            if (collection.containsKey(keyToReplace)) {
                oldPerson = collection.getByKey(keyToReplace);
                newPerson = personBuilder.setPerson();

                if (oldPerson.compareTo(newPerson) < 0){
                    collection.replace(oldPerson, newPerson);
                    AppConsole.println("Replaced successfully");
                    return true;
                }
                else  {
                    AppConsole.println("your Person is less than old");
                    return false;
                }
            } else {
                AppConsole.println("Didn't find Person with such key");
                return false;
            }

        }catch (IllegalArgumentException exception){
            AppConsole.printError("Illegal format of key inputted");
        }
        return false;
    }
}
