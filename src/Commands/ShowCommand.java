package Commands;

import Controller.CollectionManager;
import Data.Person;
import UI.AppConsole;

public class ShowCommand extends AbstractCommand{
    private CollectionManager collection;

    public ShowCommand(CollectionManager collectionManager){
        super("show", "Print all Persons in collection");
        this.collection = collectionManager;
    }

    @Override
    public boolean execute(String args) {
        try {
            if (!args.isEmpty()) throw  new IllegalArgumentException();

            if (collection.getCollection().size() == 0) AppConsole.println("Collection is empty");

            for (Person p : collection.getCollection()) {
                AppConsole.println(p.toString());
            }
            return true;
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Wrong command format");
        }
        return true;
    }
}
