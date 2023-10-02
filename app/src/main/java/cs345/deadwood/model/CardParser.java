package cs345.deadwood.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class CardParser extends GameDataParser {

    public CardParser() {
        super("cards.xml");
    }

    /**
     * Parse cards.xml to create a list of Card objects that implement the ICard interface.
     *
     * @return List of cards in cards.xml
     */
    public List<ICard> getCards() {

        List<ICard> cardList = new ArrayList<>();

        Element rootNode = getRootNode();

        NodeList cards = rootNode.getElementsByTagName("card");

        for (int i = 0; i < cards.getLength(); i++) {

            ICardBuilder builder = new CardBuilder();

            Node card = cards.item(i);

            builder.addName(card.getAttributes().getNamedItem("name").getNodeValue());
            builder.addImageName(card.getAttributes().getNamedItem("img").getNodeValue());
            builder.addBudget(Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue()));

            NodeList children = card.getChildNodes();

            for (int j = 0; j < children.getLength(); j++) {

                Node sub = children.item(j);

                if ("scene".equals(sub.getNodeName())) {
                    builder.addSceneNumber(getItemValueAsInt(sub, "number"));
                    builder.addSceneDescription(sub.getTextContent());

                } else if ("part".equals(sub.getNodeName())) {

                    NodeList subChildren = sub.getChildNodes();

                    Role role = new Role(sub.getAttributes().getNamedItem("name").getNodeValue(),
                            Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue()));

                    for (int k = 0; k < subChildren.getLength(); k++) {
                        Node subChild = subChildren.item(k);

                        if ("area".equals(subChild.getNodeName())) {
                            Area area = new Area(getItemValueAsInt(subChild, "x"),
                                    getItemValueAsInt(subChild, "y"),
                                    getItemValueAsInt(subChild, "h"),
                                    getItemValueAsInt(subChild, "w")
                                    );
                            role.setArea(area);
                        } else if ("line".equals(subChild.getNodeName())) {
                            String line = subChild.getTextContent();
                            role.setLine(line);
                        }
                    }
                    builder.addRole(role);
                }
            }

            cardList.add(builder.getCard());
        }

        return cardList;
    }

    /**
     * Helper function to get item value
     * @param node
     * @param item
     * @return
     */
    private String getItemValueAsString(Node node, String item) {
        return node.getAttributes().getNamedItem(item).getNodeValue();
    }

    /**
     * Helper function to get item value as Integer
     * @param node
     * @param item
     * @return
     */
    private int getItemValueAsInt(Node node, String item) {
        return Integer.parseInt(node.getAttributes().getNamedItem(item).getNodeValue());
    }

}
