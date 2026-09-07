public class Taxiway extends AirportPath {
    private int capacity; 
    private double maxSpeedKnots;
    private Aircraft plane; // The plane currently on the taxiway, if any

    public Taxiway(String pathID, double lengthInMeters, int capacity, double maxSpeedKnots){
        super(pathID, lengthInMeters);
        this.capacity = capacity;
        this.maxSpeedKnots = maxSpeedKnots;
        this.plane = null; // Initially, no plane is on the taxiway
    } 

    //setters
    public void setCapacity(int capacity){
      this.capacity = capacity;
    }

    public void setMaxSpeedKnots(double maxSpeedKnots){
      this.maxSpeedKnots = maxSpeedKnots;
    }

    public void setPlane(Aircraft plane) {
        this.plane = plane;
    }

    // getters
    public int getCapacity() {
      return capacity;
    }

    public double getMaxSpeedKnots() {
      return maxSpeedKnots;
    }

    public Aircraft getPlane() {
        return plane;
    }
    
}
