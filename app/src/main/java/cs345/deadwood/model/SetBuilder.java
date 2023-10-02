package cs345.deadwood.model;

import java.util.ArrayList;
import java.util.List;

public class SetBuilder {
    private IArea area=null;
    private String name=null;
    private List<ISet> neighbors = null;
    private List<IArea> blankAreas=null;
    private List<String> neighborNames=null;

    public void setArea(IArea area){
        this.area=area;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setBlankAreas(List<IArea> blanks){
        blankAreas=blanks;
    }

    public void setNeighbors(List<String> neighbors){
        this.neighborNames=neighbors;
    }

    public Set createSet(){
        return new Set(name, area, blankAreas, neighborNames);
    }
}
