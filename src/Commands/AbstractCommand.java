package Commands;

public class AbstractCommand implements Command{
    private String name;
    private String description;

    public AbstractCommand(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean execute(String args){return false;}
}
