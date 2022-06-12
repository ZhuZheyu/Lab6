package Commands;

import Controller.CollectionManager;
import Controller.PersonBuilder;
import UI.AppConsole;

public class InsertCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final PersonBuilder personBuilder;

    public InsertCommand(CollectionManager collectionManager, PersonBuilder personBuilder){
        super("insert {element}", "Add element you inputted in collection");
        this.collectionManager = collectionManager;
        this.personBuilder = personBuilder;
    }

    @Override
    public boolean execute(String args){
        try {
            if (args.isEmpty()) throw new IllegalArgumentException();

            AppConsole.println("Create person");
            collectionManager.addToCollection(personBuilder.setPerson());
            AppConsole.println("Person has been added successfully");

            return true;
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Wrong number of elements");
        }

       return false;
    }
}
