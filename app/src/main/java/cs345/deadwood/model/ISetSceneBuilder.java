package cs345.deadwood.model;

import java.util.List;

public interface ISetSceneBuilder {
    ISetSceneBuilder addTakes(List<IArea> takes);
    ISetSceneBuilder addRoles(List<IRole> roles);
    ISetSceneBuilder addSceneCard(ICard card);
    ISetSceneBuilder addName(String name);
    ISetSceneBuilder addNeighbors(List<ISet> neighbors);
    ISetSceneBuilder addArea(IArea area);
    ISetSceneBuilder addBlankAreas(List<IArea> areas);
    SetScene getSetScene();
}
