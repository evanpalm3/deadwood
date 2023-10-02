package cs345.deadwood.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder class to create SetScene objects. (Example for implementing the builder pattern without an interface.)
 */
public class SetSceneBuilder {

    private String name;
    private IArea area;
    private List<IArea> blankAreas;
    private List<String> neighborNames;

    private List<IArea> takes;
    private List<IRole> roles = new ArrayList<>();

    public SetSceneBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SetSceneBuilder setArea(IArea area) {
        this.area = area;
        return this;
    }


    public SetSceneBuilder setBlankAreas(List<IArea> areas) { // the argument could have been a list, but this is a better design because now the caller doesn't need to create a list.
        blankAreas = areas;
        return this;
    }

    public SetSceneBuilder setNeighborName(List<String> names) {
        neighborNames = names;
        return this;
    }

    /**
     * Method to add take. Note that unlike the setBlankAreas method, this doesn't take a list of areas but instead takes just one area and adds it the list.
     * Both are correct ways to set/update attributes in the builder class.
     * The benefit of doing it this way (as done in addTake method) is that the caller doesn't need to keep a list of takes.
     * @param take
     * @return
     */
    public SetSceneBuilder setTakes(List<IArea> takes) {
        this.takes = takes;
        return this;
    }

    /**
     * Method to add role. This method could also have been written to take "List<IRole> roleList" as its argument
     * and assign that to roles attributes.
     * @param role
     * @return
     */
    public SetSceneBuilder addRole(IRole role) {
        roles.add(role);
        return this;
    }

    public SetScene getSetScene() {
        Collections.reverse(takes); // Takes are present in board.xml in descending order by number. We need it in ascending order, so reverse it.
        SetScene set = new SetScene(name, area, blankAreas, neighborNames, takes, roles);
        return set;
    }
}
