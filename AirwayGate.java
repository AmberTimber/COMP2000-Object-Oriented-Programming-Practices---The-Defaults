public class AirwayGate {

    private final String gateID;
    private boolean status;
    private Aircraft currentPlane;
    private Node gateNode;

    public AirwayGate(String gateID, boolean status, Node gateNode) {
        this.gateID = gateID;
        this.status = status;
        this.gateNode = gateNode;
        this.currentPlane = null;
    }

    public void parkPlane(Aircraft plane) throws OccupancyException {

        if (!status) {
            throw new OccupancyException(
                    "Gate " + gateID + " is closed."
            );
        }

        if (!isFree()) {
            throw new OccupancyException(
                    "Gate " + gateID + " is already occupied."
            );
        }

        currentPlane = plane;
        gateNode.setOccupied(true);
    }

    public Aircraft removePlane() {
        Aircraft departingPlane = currentPlane;

        currentPlane = null;
        gateNode.setOccupied(false);

        return departingPlane;
    }

    public boolean isFree() {
        return status && currentPlane == null;
    }

    public String getGateID() {
        return gateID;
    }

    public boolean getStatus() {
        return status;
    }

    public Aircraft getCurrentPlane() {
        return currentPlane;
    }

    public Node getGateNode() {
        return gateNode;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void displayInfo() {
        System.out.println("Gate ID: " + gateID);
        System.out.println("Status: " + (status ? "Open" : "Closed"));
        System.out.println("Node: " + gateNode.getNodeID());

        if (currentPlane != null) {
            System.out.println("Plane at gate: " + currentPlane.getAircraftID());
        } else {
            System.out.println("No plane currently at the gate.");
        }
    }
}
