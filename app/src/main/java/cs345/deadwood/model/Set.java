package cs345.deadwood.model;

import java.util.ArrayList;
import java.util.List;

public class Set implements ISet {

    private String name;
    private List<ISet> neighbors = new ArrayList<>();
    private IArea area;
    private List<IArea> blankAreas;
    private List<String> neighborNames;

    public Set(String name, IArea area, List<IArea> blankAreas, List<String> neighborNames) {

        /*
        No List<ISet> neighbors in the constructor because we won't have this list when we create a set.
        We add these neighbors later, once all the sets have been created.
         */

        this.name = name;
        this.area = area;
        this.blankAreas = blankAreas;
        this.neighborNames = neighborNames;
    }

    public void addNeighbors(ISet neighbor) {
        this.neighbors.add(neighbor);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ISet> getNeighbors() {
        return neighbors;
    }

    @Override
    public IArea getArea() {
        return area;
    }

    public List<String> getNeighborNames() {
        return neighborNames;
    }

    @Override
    public List<IArea> getBlankAreas() {
        return blankAreas;
    }
}
