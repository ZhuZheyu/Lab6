package Commands;

public interface Command {
    boolean execute(String args);
    String getName();
    String getDescription();
}
