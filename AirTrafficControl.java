import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class AirTrafficControl implements drawable {
    private static ArrayList<Aircraft> aircraftsInAirport;
    private static ArrayList<Node> airportNavigation;
    private Vector2 Location;
    private boolean OccupiedAirfield = false;
    private static Aircraft flyingAircraft = null;

    
    AirTrafficControl(ArrayList<Aircraft> aircraftCount, ArrayList<Node> airportMap, Vector2 buildingLocation) {
        aircraftsInAirport = aircraftCount;
        airportNavigation = airportMap;
        Location = buildingLocation;
    }

    public boolean getOccupiedAirfield() {
        return OccupiedAirfield;
    }

    // creating a flight path for planes
    public ArrayList<Node> calculateRoute(String NodeID, Node StartingNode) {
        ArrayList<Node> path = new ArrayList<>();
        if (NodeID != null && StartingNode != null) {
        path = findNode(NodeID, path, StartingNode);
        path = shortestPathNode(path);
        } else {
            throw new NullPointerException("Route calculations NodeID or startingNode is null or a incorrect data type!");
        }

        return path;
    }

    // find shortest path
    public ArrayList<Node> shortestPathNode(ArrayList<Node> givenArray) {
        ArrayList<Node> copy = new ArrayList<>(givenArray);
        Node lastNode = null; // used to remove anything beyond the last waypoint
        if (givenArray != null && !givenArray.isEmpty() && copy != null) {
            lastNode = givenArray.get(givenArray.size() - 1);
            for (int i = 0; i < givenArray.size(); i++) { // starting node to check if future nodes are neighbors
                int furtherProgression = 0; // used to compare which node is further down
                for (int c = i; c < givenArray.size(); c++) {
                    if (copy.get(i).checkIfNeighboring(givenArray.get(c)) && copy.get(i).getOccupied() == false) { // detects whether a neighboring node has a vector2
                        if (furtherProgression <= c && (i+1) < givenArray.size()) {
                            copy.set(i + 1, givenArray.get(c));
                            furtherProgression = c;
                        }
                    }
                    
                }
            }
         } else {
                throw new NullPointerException("The path is null, something broke in the path!");
        }

        boolean reachedEnd = false; // detect if the waypoint has reached last
        for (int i = 0; i < copy.size(); i++) {
            if (copy != null && copy.get(i).equals(lastNode)) {
                reachedEnd = true;
            } else if (reachedEnd == true) {
                copy.remove(i); // removes any waypoints beyond the end waypoint
                i--;
            }
        }
        return copy;
    }

    // used to create a navigational arraylist of points on the airport
        public ArrayList<Node> findNode(String TargetedNode, ArrayList<Node> givenArray, Node startingNode) {
        if (givenArray.contains(startingNode)) { // ensure that a node can only be gone on once
            return null;
        }

        if (startingNode.NodeID != null && startingNode.NodeID.equals(TargetedNode)) {
            givenArray.add(startingNode);
            return givenArray;
        }

        if (startingNode.upperNode != null && !givenArray.contains(startingNode.upperNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.upperNode);
        }

        if (startingNode.bottomNode != null && !givenArray.contains(startingNode.bottomNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.bottomNode);
        }

        if (startingNode.leftNode != null && !givenArray.contains(startingNode.leftNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.leftNode);
        }

        if (startingNode.rightNode != null && !givenArray.contains(startingNode.rightNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.rightNode);
        }

        // used to ensure that a plane doesn't get stuck in a loop
        if (startingNode.rightNode != null && startingNode.rightNode.rightNode != null && !givenArray.contains(startingNode.rightNode.rightNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.rightNode.rightNode);
        }
        
        return null; // after checking that all other slots are null, meaning this branch isn't it
    }
    
    // for drawing elements of airtraffic control
    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawer.setColor(Color.GRAY);
        drawer.fillRect(Location.getXPos(), Location.getYPos(), width, height);
        drawer.setColor(Color.CYAN);
        drawer.fillRect(Location.getXPos(), Location.getYPos(), width, height);
    }

    // allows air traffic control to decide whether a plane can takeoff or not
    public void ClearAircraftForTakeOff(Aircraft selectedAircraft, ArrayList<Node> flightOutside, ArrayList<Node> airfieldRef) {
        if (airfieldRef != null && !airfieldRef.isEmpty() && selectedAircraft != null && flightOutside != null && !flightOutside.isEmpty()) {
            if (selectedAircraft.canFly() == true && selectedAircraft.getChosenToFly()) {
                selectedAircraft.setReachedTarget(false);
                System.out.println("Go for takeoff!!!");
                OccupiedAirfield = true;
                AirfieldNodeChanger(airfieldRef);
                flyingAircraft = selectedAircraft;
                flyingAircraft.setFlying(true);
                flyingAircraft.resetIndex();
                flyingAircraft.setFlightPath(flightOutside);
            }
        }
    }

    // changes all airfield nodes to be a value
    public void AirfieldNodeChanger (ArrayList<Node> airfieldRef) {
        if (airfieldRef != null && !airfieldRef.isEmpty())
        for (int i = 0; i < airfieldRef.size(); i++) {
            airfieldRef.get(i).setOccupied(OccupiedAirfield);
        }
    }

    // if aircraft flew off runway, free up runway
    public void checkIfAirfieldIsFree(int width, ArrayList<Node> airfieldRef) {
        if (flyingAircraft != null && flyingAircraft.getXPos() < 0 || flyingAircraft != null && !flyingAircraft.getCurrentNode().getNodeTileRepresentation().equalsIgnoreCase("RUNWAY")) {
            OccupiedAirfield = false;
            AirfieldNodeChanger(airfieldRef);
            flyingAircraft.setSelected(false);
            flyingAircraft = null;
        }
    }

    // detects if a aircraft is onfield
    public void checkIfAAircraftOnAirfield(ArrayList<Node> airfieldRef, Aircraft otherAircraft) {
        if (aircraftsInAirport != null && !aircraftsInAirport.isEmpty() && airfieldRef != null && !airfieldRef.isEmpty() && otherAircraft != null && flyingAircraft == null) {
            for (int i = 0; i < airfieldRef.size(); i++) {
                if (otherAircraft.getCurrentNode() == airfieldRef.get(i)) {
                    OccupiedAirfield = true;
                    AirfieldNodeChanger(airfieldRef);
                    flyingAircraft = otherAircraft;
                    flyingAircraft.setSelected(true);
                    i = airfieldRef.size();
                } else {
                    OccupiedAirfield = false;
                }
            }
        }
    }

    // check if clear for landing
    public void clearForLanding(Aircraft selectedAircraft, Node RUNWAYNode, ArrayList<Node> airfieldRef, ArrayList<Node> airportNav) {
        if (airfieldRef != null && !airfieldRef.isEmpty() && selectedAircraft != null && RUNWAYNode != null && airportNav != null && !airportNav.isEmpty()) {
            Node aircraftNodeRef = selectedAircraft.getFlightPath().get(selectedAircraft.getFlightPath().size()-1);
            if (selectedAircraft.getFlying() && selectedAircraft.compareVectors(aircraftNodeRef.getPosition())) {
                if (OccupiedAirfield == false && flyingAircraft == null && flyingAircraft == null) {
                    selectedAircraft.getCurrentNode().setOccupied(false);
                    flyingAircraft = selectedAircraft;
                    ResetAircraft(flyingAircraft);
                    flyingAircraft.setSelected(true);
                    flyingAircraft.setTarget(RUNWAYNode.getPosition());
                    flyingAircraft.setStatus("GROUNDED");
                    flyingAircraft.setFlying(false);
                    flyingAircraft.setFlightPath(airportNav);
                    OccupiedAirfield = true;
                    AirfieldNodeChanger(airfieldRef);
                    System.out.println("attempting to land!");
                }
            }
        }
    }

    // resets flight path of aircraft
    public void ResetAircraft (Aircraft selectedAircraft) {
        if (selectedAircraft != null) {
            selectedAircraft.resetIndex();
            selectedAircraft.setReachedTarget(false);
        }
    }
}

