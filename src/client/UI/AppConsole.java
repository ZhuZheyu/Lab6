package client.UI;

import client.Controller.PersonBuilder;
import common.Exceptions.ScriptRecursionException;
import server.Controller.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AppConsole {
    private final CommandManager commandManager;
    private final Scanner scanner;
    private final PersonBuilder personBuilder;
    private final List<String> scriptStack = new ArrayList<>();

    public AppConsole(CommandManager commandManager, Scanner scanner, PersonBuilder personBuilder) {
        this.commandManager = commandManager;
        this.scanner = scanner;
        this.personBuilder = personBuilder;
    }

    public void interactiveMode() {
        String[] userCommand;
        int commandStatus;
        println("App started. Type 'help' to display list of commands");

        try {
            do {
                AppConsole.print("> ");
                userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != 2);
        } catch (NoSuchElementException exception) {
            AppConsole.printError("No user input detected!");
        } catch (IllegalStateException exception) {
            AppConsole.printError("Unexpected error!");
        }
    }

    public int scriptMode(String argument) {
        String[] userCommand;
        int commandStatus;
        scriptStack.add(argument);

        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = personBuilder.getUserScanner();
            personBuilder.setUserScanner(scriptScanner);
            personBuilder.setFileMode();

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                AppConsole.println("> " + String.join(" ", userCommand));

                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);

            } while (commandStatus == 0 && scriptScanner.hasNextLine());
            personBuilder.setUserScanner(tmpScanner);
            personBuilder.setUserMode();

            if (commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                AppConsole.println("Check the script for the correctness of the entered data!");

            return commandStatus;
        } catch (FileNotFoundException exception) {
            AppConsole.printError("Script file not found!");
        } catch (NoSuchElementException exception) {
            AppConsole.printError("The script file is empty!");
        } catch (ScriptRecursionException exception) {
            AppConsole.printError("Scripts cannot be called recursively!");
        } catch (IllegalStateException exception) {
            AppConsole.printError("Unexpected error!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size()-1);
        }
        return 1;
    }

    private int launchCommand(String[] userCommand) {
        if (userCommand[0].equals("execute_script")) {
            if (!commandManager.executeScript(userCommand[1])) return 1;
            else return scriptMode(userCommand[1]);
        } else if (commandManager.isExist(userCommand[0])) {
            commandManager.executeCommandByName(userCommand[0], userCommand[1]);
        } else if (userCommand[0].equals("exit")) return 2;
        else if (userCommand[0].equals("help")) {
            commandManager.displayCommands();
            return 0;
        }  else commandManager.noSuchCommand(userCommand[0]);

        return 0;
    }

    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    public static void printCommand(Object element1, Object element2) {
        System.out.printf("%-37s%-1s%n", element1, element2);
    }

    public static void printError(Object toOut) {
        System.out.println("error: " + toOut);
    }
}