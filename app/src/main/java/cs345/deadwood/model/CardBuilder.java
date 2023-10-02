package cs345.deadwood.model;

import java.util.ArrayList;
import java.util.List;

public class CardBuilder implements ICardBuilder {

    private String name;
    private String imageName;
    private int budget;
    private int sceneNumber;
    private String sceneDescription;
    private List<IRole> roles = new ArrayList<>();

    @Override
    public ICardBuilder addName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ICardBuilder addImageName(String img) {
        this.imageName = img;
        return this;
    }

    @Override
    public ICardBuilder addBudget(int budget) {
        this.budget = budget;
        return this;
    }

    @Override
    public ICardBuilder addSceneNumber(int sceneNumber) {
        this.sceneNumber = sceneNumber;
        return this;
    }

    @Override
    public ICardBuilder addRole(IRole role) {
        if (role != null) {
            roles.add(role);
        }
        return this;
    }

    @Override
    public ICardBuilder addSceneDescription(String description) {
        this.sceneDescription = description;
        return this;
    }

    @Override
    public ICard getCard() {
        return new Card(name, imageName, budget, sceneNumber, roles, sceneDescription);
    }
}
