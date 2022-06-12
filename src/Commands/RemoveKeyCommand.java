package Commands;

import Controller.CollectionManager;
import UI.AppConsole;

public class RemoveKeyCommand extends AbstractCommand{

    private CollectionManager collection;

    public RemoveKeyCommand(CollectionManager collectionManager){
        super("remove_key {null}", "Removes person with inputted key");
        this.collection = collectionManager;
    }

    @Override
    public boolean execute(String args){
        if (args.isEmpty()) throw new IllegalArgumentException();

        try {
            long keyToReplace = Long.parseLong(args);
            if (collection.containsKey(keyToReplace)) {
                collection.deleteCollection(keyToReplace);
                AppConsole.println("Person has been deleted");
                return true;
            }
            else {
                AppConsole.println("Didnt find Person with such key");
                return false;
            }
        } catch ( NumberFormatException e) {
            AppConsole.printError("Illegal format of key inputted");
        } finally {
            return false;
        }
    }

}
