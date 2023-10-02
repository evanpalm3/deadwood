package cs345.deadwood.model;

public class Role implements IRole {

    private String name;
    private int level;
    private String line;
    private IArea area;

    public Role(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Role(String name, int level, String line, IArea area) {
        this.name = name;
        this.level = level;
        this.line = line;
        this.area = area;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setArea(IArea area) {
        this.area = area;
    }

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
