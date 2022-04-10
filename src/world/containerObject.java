package world;

// Object that helps in making the map object portable.

public class containerObject 
{
	protected Object representedObject;
	protected String symbol;
	protected int[] location = new int[2];

	public containerObject(Object representedObject, String symbol, int[] location)
	{
		this.representedObject = representedObject;
		this.symbol = symbol.substring(0, 1);
		this.location = location;
	}

	public containerObject(Object representedObject, String symbol)
	{
		this.representedObject = representedObject;
		this.symbol = symbol.substring(0, 1);
		this.location = new int[]{-1, -1};
	}

	public void setLocation(int[] location) { this.location = location; }
	public int[] getLocation() { return location; }
	public String toString() { return symbol; }
	public Object getRepresentedObject() { return representedObject; }
}