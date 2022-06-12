package Commands;

import Controller.CollectionManager;
import UI.AppConsole;

public class RemoveLowerKeyCommand extends AbstractCommand{
    private CollectionManager collection;

    public RemoveLowerKeyCommand(CollectionManager collectionManager){
        super("replace_if_lower", "Replace person with inputted key, if new person is less then old");
        this.collection = collectionManager;
    }

    @Override
    public boolean execute(String args) {
        try {
            if (args.isEmpty()) throw new IllegalArgumentException();

            long keyToRemove = Long.parseLong(args);
            for(Long key : collection.getCollectionKeys()){
                if (key < keyToRemove) collection.deleteCollection(key);
            }
            AppConsole.println("Success");

            return true;
        } catch (NumberFormatException exception) {
            AppConsole.printError("Illegal format of key input");
        } finally {
            return false;
        }
    }
}
