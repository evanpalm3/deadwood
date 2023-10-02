package cs345.deadwood.model;

import java.util.List;

public class SetScene extends Set implements ISetScene {

    private List<IArea> takes;
    private List<IRole> roles;
    private ICard sceneCard;

    public SetScene(String name, IArea area, List<IArea> blankAreas, List<String> neighborNames, List<IArea> takes, List<IRole> roles) {
        super(name, area, blankAreas, neighborNames);
        this.takes = takes;
        this.roles = roles;

        // No sceneCard in the constructor because this isn't initialized when sets are created.
        // The card gets assigned when the game starts. So we set this value with a setter function.
    }

    @Override
    public List<IArea> getTakes() {
        return takes;
    }

    @Override
    public List<IRole> getRoles() {
        return roles;
    }

    @Override
    public ICard getSceneCard() {
        return sceneCard;
    }

    public void setSceneCard(ICard sceneCard) {
        this.sceneCard = sceneCard;
    }
}
