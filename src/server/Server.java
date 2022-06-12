package server;

import server.Commands.*;
import client.UI.AppConsole;

import java.io.IOException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args){ /*
        try (Scanner userScanner = new Scanner((System.in))) {

            PersonBuilder personBuilder = new PersonBuilder(userScanner);
            FileManager fileManager = new FileManager();
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(
                    new ClearCommand(collectionManager),
                    new CountGreaterThanNationalityCommand(collectionManager, personBuilder),
                    new FilterGreaterThanHairColorCommand(collectionManager, personBuilder),
                    new InfoCommand(collectionManager),
                    new InsertCommand(collectionManager, personBuilder),
                    new RemoveKeyCommand(collectionManager),
                    new RemoveLowerKeyCommand(collectionManager),
                    new ReplaceIfGreaterCommand(collectionManager, personBuilder),
                    new ReplaceIfLowerCommand(collectionManager, personBuilder),
                    new SaveCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new SumOfHeightCommand(collectionManager),
                    new UpdateIdCommand(collectionManager, personBuilder));
            AppConsole appConsole = new AppConsole(commandManager, userScanner, personBuilder);
            appConsole.interactiveMode();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

             */
    }
}
