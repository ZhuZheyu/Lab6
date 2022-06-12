package server.Commands;

import Controller.CollectionManager;
import client.UI.AppConsole;

public class ClearCommand extends AbstractCommand{
    private CollectionManager collection;

    public ClearCommand(CollectionManager collectionManager){
        super("clear", "Clear whole collection");
        this.collection = collectionManager;
    }

    @Override
    public boolean execute(String args) {
        collection.clearCollection();
        AppConsole.println("Collection has been cleared");
        if (collection.getCollection().isEmpty()) return true;
        else return false;
    }
}
