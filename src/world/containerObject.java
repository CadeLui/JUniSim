package world;

/**
 * Used by the Map object to store any sort of object with minimal frustration.
 * Mostly helpful because it makes sure every object has a symbol, and if necessary, a location.
 */

public class containerObject 
{
	protected Object representedObject;
	protected String symbol;
	protected int[] location = new int[2];

	/**
	 * Constructs an object with a given location
	 * @param representedObject
	 * @param symbol
	 * @param location
	 */
	public containerObject(Object representedObject, String symbol, int[] location)
	{
		this.representedObject = representedObject;
		this.symbol = symbol.substring(0, 1);
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
		this.symbol = symbol.substring(0, 1);
		this.location = new int[]{-1, -1};
	}

	/**
	 * Sets the location of the object
	 * @param location
	 */
	public void setLocation(int[] location) { this.location = location; }

	/**
	 * Returns the location of the object
	 * @return
	 */
	public int[] getLocation() { return location; }

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