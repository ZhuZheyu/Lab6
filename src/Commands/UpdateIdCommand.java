package Commands;

import Controller.CollectionManager;
import Data.Person;
import Exceptions.NoResultOfSearchException;
import Controller.PersonBuilder;
import UI.AppConsole;

public class UpdateIdCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    private PersonBuilder personBuilder;

    public UpdateIdCommand(CollectionManager collectionManager, PersonBuilder persBuild){
        super("update_id {element}", "Updates element by id");
        this.collectionManager = collectionManager;
        this.personBuilder = persBuild;
    }

    @Override
    public boolean execute(String args) {
        if (args.isEmpty()) throw new IllegalArgumentException();

        Person oldPerson;
        Person[] foundData;

        try {
            AppConsole.println("Input id of person you want to replace");
            long id = Long.parseLong(args);

            oldPerson = Person.getDefaultInstance();
            oldPerson.setId(id);
            foundData = collectionManager.findPersonWithSuchField(oldPerson, "id");

            if (foundData.length == 0) throw new NoResultOfSearchException("Didnt find Person with id" + oldPerson.getId());
            oldPerson = foundData[0];
            collectionManager.replace(oldPerson, personBuilder.setPerson());
            return true;
        } catch (NoResultOfSearchException exception) {
            AppConsole.printError(exception.getMessage());
        } catch( NumberFormatException exception){
            AppConsole.printError("Illegal format of id inputted");
        } finally {
            return false;
        }
    }

}
