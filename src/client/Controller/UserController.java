package client.Controller;

import common.Exceptions.CommandUsageException;
import common.Exceptions.IncorrectInputScriptException;
import common.Exceptions.ScriptRecursionException;
import common.Interaction.*;
import common.Data.*;
import client.UI.AppConsole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Receives user requests.
 */

public class UserController {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserController(Scanner userScanner) {
        this.userScanner = userScanner;
    }
    /**
     * Receives user input.
     * @param serverResponseCode Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR ||
                            serverResponseCode == ResponseCode.SERVER_EXIT))
                        throw new IncorrectInputScriptException();
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        AppConsole.println("Back to the script '" + scriptStack.pop().getName() + "'...");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            AppConsole.print("> ");
                            AppConsole.println(userInput);
                        }
                    } else {
                        AppConsole.print("> ";
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    AppConsole.println("");
                    AppConsole.printError("An error occurred while entering the command!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        AppConsole.printError("Number of input attempts exceeded!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new IncorrectInputScriptException();
                switch (processingCode) {
                    case OBJECT:
                        PersonRaw personAddRaw = generatePersonAdd();
                        return new Request(userCommand[0], userCommand[1], personAddRaw);
                    case UPDATE_OBJECT:
                        PersonRaw personUpdateRaw = generatePersonUpdate();
                        return new Request(userCommand[0], userCommand[1], personUpdateRaw);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        AppConsole.println("Executing the script '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                AppConsole.printError("Script file not found!");
            } catch (ScriptRecursionException exception) {
                AppConsole.printError("Scripts cannot be called recursively!");
                throw new IncorrectInputScriptException();
            }
        } catch (IncorrectInputScriptException exception) {
            AppConsole.printError("The script was interrupted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }


    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just a help)");
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just an info)");
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just a show)");
                    break;
                case "insert":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key>");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_key":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(about key)");
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just an exit)");
                    break;
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "replace_if_greater":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key> {element}");
                    return ProcessingCode.OBJECT;
                case "remove_lower_key":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key>'");
                    break;
                case "average_of_number_of_participant":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just a command)");
                    break;
                case "filter_starts_with":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<description>");
                    break;
                case "print_field_ascending_description":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("just a 'server_exit'");
                    break;
                default:
                    AppConsole.println("Command '" + command + "' not found. Enter 'help'.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            AppConsole.println("Использование: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }


    /**
     * Generates person to add.
     * @return Person to add.
     * @throws IncorrectInputScriptException When something went wrong in script.
     */
    private PersonRaw generatePersonAdd() throws IncorrectInputScriptException {
        PersonBuilder personBuilder = new PersonBuilder(userScanner);
        if (fileMode()) personBuilder.setFileMode();
        return new PersonRaw(
                personBuilder.setName(),
                personBuilder.setCoordinates(),
                personBuilder.setHeight(),
                personBuilder.setEyeColor(),
                personBuilder.setHairColor(),
                personBuilder.setCountry(),
                personBuilder.setLocation()
        );
    }

    /**
     * Generates band to update.
     * @return Band to update.
     * @throws IncorrectInputScriptException When something went wrong in script.
     */
    private PersonRaw generatePersonUpdate() throws IncorrectInputScriptException {
        PersonBuilder personBuilder = new PersonBuilder(userScanner);
        if (fileMode()) personBuilder.setFileMode();
        String name = personBuilder.askQuestion("Do you want to change the name?") ?
                personBuilder.setName() : null;
        Coordinates coordinates = personBuilder.askQuestion("Do you want to change the coordinates?") ?
                personBuilder.setCoordinates() : null;
        Long height = personBuilder.askQuestion("Do you want to change height?") ?
                personBuilder.setHeight() : -1;
        Color eyeColor = personBuilder.askQuestion("Do you want to change color of eyes?") ?
                personBuilder.setEyeColor() : null;
        Color hairColor = personBuilder.askQuestion("Do you want to change color of hair?") ?
                personBuilder.setHairColor() : null;
        Country studio = personBuilder.askQuestion("Do you want to change the studio?") ?
                personBuilder.askStudio() : null;
        return new PersonRaw(
                name,
                coordinates,
                height,
                description,
                genre,
                studio,
                a
        );
    }

    /**
     * Checks if UserHandler is in file mode now.
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
