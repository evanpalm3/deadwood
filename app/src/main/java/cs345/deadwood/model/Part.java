package cs345.deadwood.model;

public class Part implements IRole{
    String name;
    int level;
    String line;
    IArea area;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String getLine() {
        return line;
    }

    @Override
    public IArea getArea() {
        return area;
    }
}
