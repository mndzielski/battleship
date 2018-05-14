package pl.edu.wat.wcy.isi.pz.battleship.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.edu.wat.wcy.isi.pz.battleship.exceptions.NoShipsException;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.BaseShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.DraggableShip;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlShipsFactory {

    private static final String XML_FILE = "xml.file";

    private static final String PLAYER_SHIP = "playerShip";
    private static final String OPPONENT_SHIP = "opponentShip";
    private static final String SHIP = "ship";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String ORIENTATION = "orientation";
    private static final String LENGTH = "length";


    public List<DraggableShip> getPlayerShips() {
        final List<DraggableShip> ships = readXmlFile(PLAYER_SHIP).stream().map(p -> new DraggableShip(p.getPosition().get(), p.getOrientation(), p.getLength())).collect(Collectors.toList());
        if(ships.isEmpty()) throw new NoShipsException();
        return ships;
    }

    public List<BaseShip> getOpponentShips() {
        final List<BaseShip> ships = readXmlFile(OPPONENT_SHIP);
        if(ships.isEmpty()) throw new NoShipsException();
        return ships;
    }

    private List<BaseShip> readXmlFile(final String parent) {
        List<BaseShip> ships = new ArrayList<>();

        try {
            InputStream xmlFile = ResourcesLoader.getXML(ProgramSettings.getInstance().property(XML_FILE));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(parent);
            Element playerShip = (Element)nList.item(0);

            nList = playerShip.getElementsByTagName(SHIP);

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String x = eElement.getElementsByTagName(X).item(0).getTextContent();
                    String y = eElement.getElementsByTagName(Y).item(0).getTextContent();
                    String orientation = eElement.getElementsByTagName(ORIENTATION).item(0).getTextContent();
                    String length = eElement.getElementsByTagName(LENGTH).item(0).getTextContent();

                    ships.add(BaseShip.builder(x, y, orientation, length));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return ships;
    }
}
