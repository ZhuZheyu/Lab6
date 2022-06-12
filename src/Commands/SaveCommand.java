package Commands;

import Controller.CollectionManager;
import UI.AppConsole;

public class SaveCommand extends  AbstractCommand{
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "Save collection in storage");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String args){
        try {
            if (!args.isEmpty()) throw new IllegalArgumentException();
            collectionManager.saveCollection();
            AppConsole.println("Collection has been saved.");
            return true;
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Wrong command format");
        }
        finally {
            return false;
        }
    }
}
