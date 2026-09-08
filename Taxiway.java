import java.util.ArrayList;
import java.util.List;

public class Taxiway extends AirportPath {

  private final int capacity;
  private final double maxSpeedKnots;
  private final List<Aircraft> aircraftOnTaxiway = new ArrayList<>(); // aircraft currently occupying the taxiway

  /**
   * 
   * @param id:             Taxiway id
   * @param lengthInMeters: Physical length of the taxiway
   * @param capacity:       Maximum number of aircraft allowed on the taxiway
   *                        simultaneously
   * @param maxSpeedKnots:  Ground speed limit for aircraft transit
   */
  public Taxiway(String id, double lengthInMeters, int capacity, double maxSpeedKnots) {
    super(id, lengthInMeters);
    this.capacity = capacity;
    this.maxSpeedKnots = maxSpeedKnots;
  }

  /**
   * Checks whether the taxiway has room for another aircraft.
   * Returns false if the taxiway is already at capacity, the aircraft is
   * null, or the aircraft is already on the taxiway.
   *
   * Implemented by: Kyle
   */
  public boolean canAcceptAircraft(Aircraft aircraft) {
    if (aircraft == null) {
      return false;
    }
    if (aircraftOnTaxiway.contains(aircraft)) {
      return false;
    }
    return getCurrentOccupancy() < capacity;
  }

  /**
   * Adds an aircraft to the taxiway if space allows.
   * Throws OccupancyException if the taxiway is already at full capacity.
   *
   * Implemented by: Kyle
   */
  public boolean enterTaxiway(Aircraft aircraft) throws OccupancyException {
    if (!canAcceptAircraft(aircraft)) {
      throw new OccupancyException(
          "Taxiway " + getPathID() + " is at full capacity ("
          + capacity + "/" + capacity + "). Cannot accept aircraft "
          + (aircraft != null ? aircraft.getAircraftID() : "null") + ".");
    }
    aircraftOnTaxiway.add(aircraft);
    return true;
  }

  /**
   * Removes and returns the next aircraft ready to leave the taxiway
   * (first-in, first-out), or null if the taxiway is empty.
   */
  public Aircraft exitTaxiWay() {
    if (aircraftOnTaxiway.isEmpty()) {
      return null;
    }
    return aircraftOnTaxiway.remove(0);
  }

  // Implemented by: Kyle
  public int getCurrentOccupancy() {
    return aircraftOnTaxiway.size();
  }

  public int getCapacity() {
    return capacity;
  }

  public double getMaxSpeedKnots() {
    return maxSpeedKnots;
  }
}
