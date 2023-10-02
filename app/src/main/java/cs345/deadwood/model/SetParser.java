package cs345.deadwood.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetParser extends GameDataParser {

    public SetParser() {
        super("board.xml");
    }

    /**
     * Parse board.xml to create a list of set objects that implement the ISet or ISetScene interface.
     *
     * @return List of Sets in board.xml
     */
    public List<ISet> getSets() {

       HashMap<String, ISet> setMap = new HashMap<>();

        Element rootNode = getRootNode();

        NodeList sets = rootNode.getChildNodes();

        for (int i = 0; i < sets.getLength(); i++) {

            Node set = sets.item(i);

            /* ***** Parse Set Scene ********* */

            if ("set".equals(set.getNodeName())) {

                SetSceneBuilder builder = new SetSceneBuilder();

                builder.setName(set.getAttributes().getNamedItem("name").getNodeValue());

                NodeList children = set.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {

                    Node sub = children.item(j);

                    if ("neighbors".equals(sub.getNodeName())) {
                        builder.setNeighborName(parseNeighborNames(sub));
                    } else if ("area".equals(sub.getNodeName())) {
                        builder.setArea(parseArea(sub));

                    } else if ("parts".equals(sub.getNodeName())) {
                        NodeList subChildren = sub.getChildNodes();

                        for (int k = 0; k < subChildren.getLength(); k++) {
                            Node subChild = subChildren.item(k);

                            if ("part".equals(subChild.getNodeName())) {
                                Role role = new Role(subChild.getAttributes().getNamedItem("name").getNodeValue(),
                                        Integer.parseInt(subChild.getAttributes().getNamedItem("level").getNodeValue()));

                                NodeList subSubChildren = subChild.getChildNodes();

                                for (int l = 0; l < subSubChildren.getLength(); l++) {
                                    Node subSubChild = subSubChildren.item(l);

                                    if ("area".equals(subSubChild.getNodeName())) {
                                        role.setArea(parseArea(subSubChild));
                                    } else if ("line".equals(subSubChild.getNodeName())) {
                                        String line = subSubChild.getTextContent();
                                        role.setLine(line);
                                    }
                                }
                                builder.addRole(role);
                            }
                        }
                    } else if ("blanks".equals(sub.getNodeName())) {
                        builder.setBlankAreas(parseNumberedAreas(sub, "blank"));
                    } else if ("takes".equals(sub.getNodeName())) {
                        builder.setTakes(parseNumberedAreas(sub, "take"));
                    }
                }
                ISet s = builder.getSetScene();
                setMap.put(s.getName(), s);
            }

            /* ***** Parse Office and trailer ********* */
            if ("office".equals(set.getNodeName()) || "trailer".equals(set.getNodeName())) {

                Node neighborsNode = null, areaNode = null, blankAreaNode = null;
                NodeList children = set.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node sub = children.item(j);

                    switch (sub.getNodeName()) {
                        case "neighbors":
                            neighborsNode = sub;
                            break;
                        case "area":
                            areaNode = sub;
                            break;
                        case "blanks":
                            blankAreaNode = sub;
                            break;
                    }
                }

                SetBuilder sBuilder = new SetBuilder();

                String setName = null;
                if ("office".equals(set.getNodeName())) {
                    setName = "Office";
                    sBuilder.setName("Office");
                } else {
                    sBuilder.setName("Trailer");
                    setName = "Trailer";
                }

                sBuilder.setArea(parseArea(areaNode));
                sBuilder.setBlankAreas(parseNumberedAreas(blankAreaNode, "blank"));
                sBuilder.setNeighbors(parseNeighborNames(neighborsNode));

                //Set s = new Set(setName, parseArea(areaNode), parseNumberedAreas(blankAreaNode, "blank"), parseNeighborNames(neighborsNode));
                Set s = sBuilder.createSet();
                setMap.put(s.getName(), s);
            }
        }

        List<ISet> setList = new ArrayList<>();
        /* ********* Add neighbors as ISet *********** */
        for (String setName: setMap.keySet()) {
            Set s = (Set) setMap.get(setName);
            for (String neighborName: s.getNeighborNames()) {
                s.addNeighbors(setMap.get(neighborName));
            }

            setList.add(s);
        }

        return setList;
    }

    private List<String> parseNeighborNames(Node node) {
        List<String> names = new ArrayList<>();

        NodeList children = node.getChildNodes();

        for (int k = 0; k < children.getLength(); k++) {

            Node child = children.item(k);

            if ("neighbor".equals(child.getNodeName())) {
                names.add(getItemValueAsString(child, "name"));
            }
        }
        return names;
    }

    private IArea parseArea(Node node) {
        Area area = new Area(getItemValueAsInt(node, "x"),
                getItemValueAsInt(node, "y"),
                getItemValueAsInt(node, "h"),
                getItemValueAsInt(node, "w"));
        return area;
    }


    private List<IArea> parseNumberedAreas(Node node, String itemName) {

        /*
         * Use array instead of a list because we want to add blank areas at specific index in the list/array.
         * That is, blank area numbered 1 should be at index 0, area numbered 4 should be at index 3 (4-1), and so forth.
         */
        List<IArea> areas = new ArrayList<>();

        NodeList children = node.getChildNodes();

        for (int k = 0; k < children.getLength(); k++) {

            Node child = children.item(k);

            if (itemName.equals(child.getNodeName())) {
                int number = getItemValueAsInt(child, "number");
                NodeList subChildren = child.getChildNodes();
                for (int i = 0; i < subChildren.getLength(); i++) {
                    Node sub = subChildren.item(i);
                    if ("area".equals(sub.getNodeName())) {
                        areas.add(parseArea(sub));
                    }
                }
            }
        }
        return areas;
    }

    /**
     * Helper function to get item value
     *
     * @param node
     * @param item
     * @return
     */
    private String getItemValueAsString(Node node, String item) {
        return node.getAttributes().getNamedItem(item).getNodeValue();
    }

    /**
     * Helper function to get item value as Integer
     *
     * @param node
     * @param item
     * @return
     */
    private int getItemValueAsInt(Node node, String item) {
        return Integer.parseInt(node.getAttributes().getNamedItem(item).getNodeValue());
    }


}
