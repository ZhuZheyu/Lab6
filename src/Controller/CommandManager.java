package Controller;

import Commands.*;
import UI.AppConsole;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private Map<String, Command> commands = new HashMap<>();

    public CommandManager(Command... commandArray) {
        for (Command command : commandArray) {
            commands.put(command.getName().split(" ", 0)[0], command);
        }
    }

    public boolean executeCommandByName(String name, String argument) {
        return commands.get(name).execute(argument);
    }

    public boolean displayCommands() {
        for (Command command: commands.values()) {
            AppConsole.printCommand(command.getName(), command.getDescription());
        }
        return true;
    }

    public boolean isExist(String name){
        return commands.containsKey(name);
    }

    public boolean noSuchCommand(String command) {
        AppConsole.println("Command '" + command + "' not found. Type 'help' to display list of commands");
        return false;
    }

    public boolean executeScript(String args) {
        ExecuteScriptCommand executeScriptCommand = new ExecuteScriptCommand();
        return executeScriptCommand.execute(args);
    }

}
