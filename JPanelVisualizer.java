import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// this entire class is just used to display stuff on JPanel, pls don't delete as it is useful base 
// for drawing stuff

// should probably extend this class to anything with moveable or anything 
// that needs to be drawn or visualized in the simulator
public class JPanelVisualizer extends JPanel implements ActionListener {
    // important for this class
    private Timer timer;
    private int secondsPerFrame = 10; // in miliseconds

    private int aircraftCount = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private Aircraft[] aircrafts = new Aircraft[1];
    private JFrame JframeRef;
    private ArrayList<Node> flightPath = new ArrayList<>();
    private ArrayList<Node> airportNav = new ArrayList<>();
    private ArrayList<Aircraft> aircraftsOnSite = new ArrayList<>();
    private AirTrafficControl airControl = new AirTrafficControl(aircraftsOnSite,  airportNav,new Vector2(500, 600));
    private Aircraft testFlight;
    private ArrayList<Node> runway = new ArrayList<>();
    private ArrayList<Node> waitingBay = new ArrayList<>();
    private ArrayList<Node> outsideLoop = new ArrayList<>();
    private ArrayList<AirwayGate> allGates = new ArrayList<>();

    // intializes time
    public JPanelVisualizer(JFrame jframePanel) {
        JframeRef = jframePanel;
        /*for (int i = 0; i < aircrafts.length; i++) {
            aircrafts[i] = new CargoPlane("Aircraft " + i, "Harry Potter the " + i, "Hawking404", 30.00, 50,"Fly my minions", 500.00, 250.00);
            int newXpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            int newYpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            aircrafts[i].setTarget(new Vector2(newXpos, newYpos));
        }*/
        Node leftFlyOff = new Node(null, null, null, null, new Vector2(-100, 75), "RUNWAY", "Outside");
        Node leftTopFlyOff = new Node(null, null, null, null, new Vector2(-100, -200), "Outside", "Outside");
        Node rightTopFlyOff = new Node(null, null, null, null, new Vector2(JframeRef.getWidth() + 200, -200), "Outside", "Outside");
        Node rightFlyOff = new Node(null, null, null, null, new Vector2(JframeRef.getWidth() + 100, 75), "Outside", "Outside");
        
        Node airfieldNode1 = new Node(null, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 75), "A1", "RUNWAY");
        Node airfieldNode2 = new Node(null, null, airfieldNode1, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 75), "A2", "RUNWAY");
        Node airfieldNode3 = new Node(null, null, airfieldNode2, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 75), "A3", "RUNWAY");
        Node airfieldNode4 = new Node(null, null, airfieldNode3, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 75), "A4", "RUNWAY");

        Node miniRoadNode1 = new Node(airfieldNode1, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 225), "B1", "WAITINGBAY");
        Node miniRoadNode2 = new Node(airfieldNode2, null, miniRoadNode1, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 225), "B2", "WAITINGBAY");
        Node miniRoadNode3 = new Node(airfieldNode3, null, miniRoadNode2, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 225), "B3", "WAITINGBAY");
        Node miniRoadNode4 = new Node(airfieldNode4, null, miniRoadNode3, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 225), "B4", "WAITINGBAY");

        Node TaxiWayNode1 = new Node(miniRoadNode1, null, null, null, new Vector2(JframeRef.getWidth()/5 * 0 + JframeRef.getWidth()/5, 525), "C1", "TAXIWAY");
        Node TaxiWayNode2 = new Node(miniRoadNode2, null, TaxiWayNode1, null, new Vector2(JframeRef.getWidth()/5 * 1 + JframeRef.getWidth()/5, 525), "C2", "TAXIWAY");
        Node TaxiWayNode3 = new Node(miniRoadNode3, null, TaxiWayNode2, null, new Vector2(JframeRef.getWidth()/5 * 2 + JframeRef.getWidth()/5, 525), "C3", "TAXIWAY");
        Node TaxiWayNode4 = new Node(miniRoadNode4, null, TaxiWayNode3, null, new Vector2(JframeRef.getWidth()/5 * 3 + JframeRef.getWidth()/5, 525), "C4", "TAXIWAY");

        /*Node TestGate1 = new Node(TaxiWayNode1, null, null, null, new Vector2(JframeRef.getWidth()/5 * 1 + JframeRef.getWidth()/5, 600), "D1", "Gate");
        Node TestGate2 = new Node(TaxiWayNode2, null, TestGate1, null, new Vector2(JframeRef.getWidth()/5 * 2 + JframeRef.getWidth()/5, 600), "D2", "Gate");
        Node TestGate3 = new Node(TaxiWayNode3, null, TestGate2, null, new Vector2(JframeRef.getWidth()/5 * 3 + JframeRef.getWidth()/5, 600), "D3", "Gate");
        Node TestGate4 = new Node(TaxiWayNode4, null, TestGate3, null, new Vector2(JframeRef.getWidth()/5 * 4 + JframeRef.getWidth()/5, 600), "D3", "Gate");
*/
        airfieldNode1.setBottomNode(miniRoadNode1);
        airfieldNode1.setRightNode(airfieldNode2);
        airfieldNode2.setBottomNode(miniRoadNode2);
        airfieldNode2.setRightNode(airfieldNode3);
        airfieldNode3.setBottomNode(miniRoadNode3);
        airfieldNode3.setRightNode(airfieldNode4);
        airfieldNode4.setBottomNode(miniRoadNode4);

        miniRoadNode1.setBottomNode(TaxiWayNode1);
        miniRoadNode1.setRightNode(miniRoadNode2);
        miniRoadNode2.setBottomNode(TaxiWayNode2);
        miniRoadNode2.setRightNode(miniRoadNode3);
        miniRoadNode3.setBottomNode(TaxiWayNode3);
        miniRoadNode3.setRightNode(miniRoadNode4);
        miniRoadNode4.setBottomNode(TaxiWayNode4);

        //TaxiWayNode1.setBottomNode(TestGate1);
        TaxiWayNode1.setRightNode(TaxiWayNode2);
        //TaxiWayNode2.setBottomNode(TestGate2);
        TaxiWayNode2.setRightNode(TaxiWayNode3);
        //TaxiWayNode3.setBottomNode(TestGate3);
        TaxiWayNode3.setRightNode(TaxiWayNode4);
        //TaxiWayNode4.setBottomNode(TestGate4);

        /*TestGate1.setRightNode(TestGate2);
        TestGate2.setRightNode(TestGate3);
        TestGate3.setRightNode(TestGate4);*/

        // add to airport nav
        airportNav.add(airfieldNode1);
        airportNav.add(airfieldNode2);
        airportNav.add(airfieldNode3);
        airportNav.add(airfieldNode4);

        airportNav.add(miniRoadNode1);
        airportNav.add(miniRoadNode2);
        airportNav.add(miniRoadNode3);
        airportNav.add(miniRoadNode4);

        airportNav.add(TaxiWayNode1);
        airportNav.add(TaxiWayNode2);
        airportNav.add(TaxiWayNode3);

        // marked runway
        runway.add(airfieldNode1);
        runway.add(airfieldNode2);
        runway.add(airfieldNode3);
        runway.add(airfieldNode4);

        // marked waitingBay
        waitingBay.add(miniRoadNode1);
        waitingBay.add(miniRoadNode2);
        waitingBay.add(miniRoadNode3);
        waitingBay.add(miniRoadNode4);

        // outside loop
        outsideLoop.add(leftFlyOff);
        outsideLoop.add(leftTopFlyOff);
        outsideLoop.add(rightTopFlyOff);
        outsideLoop.add(rightFlyOff);

        flightPath = airControl.calculateRoute("A4", TaxiWayNode1);
        ArrayList<Node> flightPath2 = airControl.calculateRoute("A1", TaxiWayNode4);
        

        testFlight = new CargoPlane("Test aircraft", "Time the greek", "Hawking404", 30.00, 50,"Fly my minions", 500.00, 250.00);
        testFlight.setVector2(JframeRef.getWidth()/9 + (JframeRef.getWidth()/9)/2, 600);
        testFlight.setFlightPath(flightPath);
        aircraftsOnSite.add(testFlight);

        Aircraft testFlight2 = new CargoPlane("Test aircraft2", "Albert Minestein", "Blimper64", 30.00, 50,"Gravity Finder", 500.00, 250.00);
        testFlight2.setVector2(TaxiWayNode4.getXPos()-100, TaxiWayNode4.getYPos());
        testFlight2.setFlightPath(flightPath2);
        aircraftsOnSite.add(testFlight2);


        if (flightPath != null && !flightPath.isEmpty()) {
        for (int i = 0; i < flightPath.size(); i++) {
            System.out.println(flightPath.get(i).getPosition().getXPos() + ", " + flightPath.get(i).getPosition().yPos);
        }
        } else {
            System.out.println("This node stuff isn't working yo, it can't find path");
        }

        timer = new Timer(secondsPerFrame, this); // every secondsPerFrame time, = 1 frame
        timer.start(); // starts the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        // this entire function is used to update this element every frame
        // key note: increase in xPos = more to right, increase in Y makes it go down

            for (int i = 0; i < aircraftsOnSite.size(); i++) {
                // checks if the airfield is clear
                if (airControl.getOccupiedAirfield() == true) {
                    airControl.checkIfAirfieldIsFree(JframeRef.getWidth(), runway);
                }
                airControl.checkIfAAircraftOnAirfield(runway, aircraftsOnSite.get(i));
                
                // checks if plane is flying
                if (!aircraftsOnSite.get(i).getFlying()) {
                aircraftsOnSite.get(i).CheckIfNextPathIsBlocked(); // checks if the path is blocked or not
            if (aircraftsOnSite.get(i).canFly() == true) {
                // if plane can fly
                aircraftsOnSite.get(i).getFlightPath().get(aircraftsOnSite.get(i).getFlightPath().size()-1).setOccupied(false);
                airControl.ClearAircraftForTakeOff(aircraftsOnSite.get(i), outsideLoop, runway); // changes path to outside route
            } else if (aircraftsOnSite.get(i).canFly() == false && aircraftsOnSite.get(i).getStatus().equalsIgnoreCase("GROUNDED") && aircraftsOnSite.get(i).isBlocked() == false) {
                // moves through the airport
                aircraftsOnSite.get(i).MoveThroughFlightPath(1); 
            } else if(aircraftsOnSite.get(i).isBlocked() == true && aircraftsOnSite.get(i).getChosenToFly() == false && inWaitingBay(aircraftsOnSite.get(i)) == false) { 
                // if a aircraft path is being blocked
                Node currentNode = null;
                for (int c = 0; c < aircraftsOnSite.get(i).getFlightPath().size(); c++) {
                    if (aircraftsOnSite.get(i).getFlightPath().get(c).getPosition().compareVectors(aircraftsOnSite.get(i).getVector2())) {
                        currentNode = aircraftsOnSite.get(i).getFlightPath().get(c); // gets its current progress in the navigation
                    }
                }
                String NodeID = aircraftsOnSite.get(i).getFlightPath().get(aircraftsOnSite.get(i).getFlightPath().size()-1).getNodeID();
                currentNode.setOccupied(false);
                flightPath = airControl.calculateRoute(NodeID, currentNode);
                aircraftsOnSite.get(i).setFlightPath(flightPath); // creates new path so it doesn't collide with other aircrafts
                aircraftsOnSite.get(i).setBlocked(false);
                System.out.println("Changed direction");
            } else {
                aircraftsOnSite.get(i).moveTowards(1); // once in flight, moves to target
            }

        } else if (aircraftsOnSite.get(i).getFlying()) { // flying
                aircraftsOnSite.get(i).MoveThroughFlightPath(1); 
                if (aircraftsOnSite.get(i).compareVectors(outsideLoop.get(outsideLoop.size()-1).getPosition())) {
                    flightPath = airControl.calculateRoute("C1", runway.get(0));
                    airControl.clearForLanding(aircraftsOnSite.get(i), runway.get(0), runway, flightPath);
                }
            }
         else { // otherwise moves towards target
            aircraftsOnSite.get(i).moveTowards(1);
            }
        }
            
        
        // updates the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);// put anything drawn after this line
        // draw airfield
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, JframeRef.getWidth(), 150);
        g.setColor(Color.white);
        g.drawLine(0, 75, JframeRef.getWidth(), 75);
        // making road to airfield
        g.setColor(Color.GRAY);
        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/7 * i, 150, JframeRef.getWidth()/7 , 150);
            }
        }
        // making taxiway
        g.fillRect(0, 300, JframeRef.getWidth(), 150);
        // making road to gate
        for (int i = 0; i < 5; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/5 * i, 450, JframeRef.getWidth()/5 , 150);
            }
        }
        // making gates
        g.setColor(Color.GREEN);
        for (int i = 0; i < 10; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/9 * i, 600, JframeRef.getWidth()/9 , 200);
            }
        }
        // Making terminal
        g.setColor(Color.BLUE);
        g.fillRect(0, 800, JframeRef.getWidth(), 200);
        // air traffic control
        airControl.visualRepresentation(g, 50,50);
        //g.drawImage(catImage, JframeRef.getWidth()/2, JframeRef.getHeight() - 300, 150, 150, this);
        // draw plane line
        /*for (int i = 0; i < aircrafts.length; i++) {
            g.setColor(Color.YELLOW);
            g.fillOval(aircrafts[i].getXPos(), aircrafts[i].getYPos(), 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("This is plane " + aircrafts[i].getAircraftID(), aircrafts[i].getXPos(), aircrafts[i].getYPos());
        }*/
       for (int i = 0; i < aircraftsOnSite.size(); i++) {
        g.setColor(Color.YELLOW);
        g.fillOval(aircraftsOnSite.get(i).getXPos(), aircraftsOnSite.get(i).getYPos(), 50, 50);
       }
    }

    private boolean inWaitingBay(Aircraft selectedAircraft) {
        for (int i = 0; i < waitingBay.size(); i++) {
            if (selectedAircraft.getCurrentNode().getNodeTileRepresentation().equals("WAITINGBAY")) {
                return true;
            }
        }
        return false;
    }
}
