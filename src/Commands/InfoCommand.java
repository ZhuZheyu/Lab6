package Commands;

import Controller.CollectionManager;
import UI.AppConsole;

public class InfoCommand extends AbstractCommand{
    private CollectionManager collection;


    public InfoCommand(CollectionManager collectionManager){
        super("info", "Print information about collection");
        this.collection = collectionManager;
    }

    @Override
    public boolean execute(String args){
        if (!args.isEmpty()) throw new IllegalArgumentException();

        try {
            AppConsole.println(collection.information());
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Wrong number of arguments");
        }

        return true;
    }
}
