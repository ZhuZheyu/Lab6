package server.Commands;

import client.UI.AppConsole;

public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand() {
        super("execute_script {file_name}", "Execute script from file");
    }

    @Override
    public boolean execute(String args) {
        try {
            if (args.isEmpty()) throw new IllegalArgumentException();
            AppConsole.println("Executing the script '" + args + "'");

            return true;
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Wrong argument of command. Use execute_script {file_name}");
        }
        return false;
    }
}
