package cs345.deadwood.model;

import java.util.List;

public class Card implements ICard {

    private String name;
    private String imageName;
    private int budget;
    private int sceneNumber;
    private List<IRole> roles;
    private String sceneDescription;

    /**
     * Basic constructor that take everything about a card in cards.xml except scene description.
     * @param name
     * @param imageName
     * @param budget
     * @param sceneNumber
     * @param roles
     */
    public Card(String name, String imageName, int budget, int sceneNumber, List<IRole> roles) {
        this.name = name;
        this.imageName = imageName;
        this.budget = budget;
        this.sceneNumber = sceneNumber;
        this.roles = roles;
    }

    /**
     * Second constructor that is like the first one but also takes sceneDescription as an argument.
     * @param name
     * @param imageName
     * @param budget
     * @param sceneNumber
     * @param roles
     * @param sceneDescription
     */
    public Card(String name, String imageName, int budget, int sceneNumber, List<IRole> roles, String sceneDescription) {
        this(name, imageName, budget, sceneNumber, roles);
        this.sceneDescription = sceneDescription;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageName() {
        return imageName;
    }

    @Override
    public int getBudget() {
        return budget;
    }

    @Override
    public int getSceneNumber() {
        return sceneNumber;
    }

    @Override
    public List<IRole> getRoles() {
        return roles;
    }
}
