package world;

/**
 * Used by the Map object to store any sort of object with minimal frustration.
 * Mostly helpful because it makes sure every object has a symbol, and if necessary, a location.
 */

public class containerObject 
{
	protected Object representedObject;
	protected String symbol;
	protected double[] location = new double[2];

	/**
	 * Constructs an object with a given location
	 * @param representedObject
	 * @param symbol
	 * @param location
	 */
	public containerObject(Object representedObject, String symbol, double[] location)
	{
		this.representedObject = representedObject;
		this.symbol = symbol;
		this.location = location;
	}

	/**
	 * Constructs an object with an unknown location
	 * @param representedObject
	 * @param symbol
	 */
	public containerObject(Object representedObject, String symbol)
	{
		this.representedObject = representedObject;
		this.symbol = symbol;
		this.location = new double[]{-1, -1};
	}

	/**
	 * Sets the location of the object
	 * @param location
	 */
	public void setLocation(double[] location) { this.location = location; }

	/**
	 * Returns the location of the object
	 * @return
	 */
	public double[] getLocation() { return location; }

	/**
	 * Returns the object's symbol
	 */
	public String toString() { return symbol; }

	/**
	 * Returns the object that the container represents.
	 * @return
	 */
	public Object getRepresentedObject() { return representedObject; }
}