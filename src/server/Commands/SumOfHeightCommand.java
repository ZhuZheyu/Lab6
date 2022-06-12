package server.Commands;

import Controller.CollectionManager;
import common.Data.Person;
import client.UI.AppConsole;

public class SumOfHeightCommand extends AbstractCommand{
    private CollectionManager collection;

    public SumOfHeightCommand(CollectionManager collectionManager){
        super("sum_of_height", "Sum of height of all persons");
        this.collection = collectionManager;
    }

    @Override
    public boolean execute(String args) {
        try {
            if (!args.isEmpty()) throw new IllegalArgumentException();

            long heightSum = 0;
            for (Person p : collection.getCollection()) {
                heightSum += p.getHeight();
            }

            AppConsole.println("Sum of height: " + heightSum);
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Invalid command format");
        }
        return true;
    }
}
