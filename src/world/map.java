package world;

import java.util.ArrayList;
import java.util.Arrays;

/*
	This is the parent of the Universe object.
	I wrote this as part of another project and decided that it would be better as a portable library.
	Basically just an array of containerObjects, which themselves just contain an object.
	This makes it far easier to make the map portable.
*/

public abstract class map
{
	protected int[] size;
	protected containerObject[][] objectMap;
	protected ArrayList<containerObject> entityReferenceList = new ArrayList<containerObject>();

	/**
	 * Constructs a map of given size
	 * @param v_size The map's vertical size
	 * @param h_size The map's horizontal size
	 */
	public map(int v_size, int h_size)
	{
		this.size = new int[] {v_size, h_size};
		this.objectMap = new containerObject[v_size][h_size];
		for (int i = 0; i < this.objectMap.length; i++)
			Arrays.fill(this.objectMap[i], new containerObject(null, " "));
	}

	/**
	 * Returns the size of the map
	 * @return The map's size
	 */
	public int[] getSize() { return this.size; }

	/**
	 * Returns a single object from the map
	 * @param y The object's y
	 * @param x The object's x
	 * @return The object found at the coordinates
	 */
	public containerObject getObject(int y, int x) { return objectMap[y][x]; }

	/**
	 * Returns an entire list of the entities contained in the universe
	 * @return The entity list
	 */
	public ArrayList<containerObject> getEntityReferenceList() {return this.entityReferenceList;}

	/**
	 * Returns a string of the map
	 */
	public String toString()
	{
		String returnString = "/-";
		for (int i=0; i < this.size[1]; i++)
			returnString += "--";
		returnString += "\\\n";
		for (int y=0; y < this.size[0]; y++)
		{
			returnString += "| ";
			for (int x=0; x < this.size[1]; x++)
				returnString += this.objectMap[y][x] + " ";
			returnString += "|";
			returnString += "\n";
		}
		returnString += "\\";
		for (int i=0; i < this.size[1]; i++)
			returnString += "--";
		returnString += "-/";
		return returnString;
	}

	/**
	 * Adds a new entity to the list
	 * @param newEntity
	 */
	protected void updateEntityList(containerObject newEntity)
	{
		this.entityReferenceList.add(newEntity);
	}

	/**
	 * Removes a known entity from the list
	 * @param oldEntity
	 */
	protected void removeEntityFromList(containerObject oldEntity)
	{
		this.entityReferenceList.remove(oldEntity);
	}

	/**
	 * Places new objects on the map
	 * @param percentChance Roughly the percentage of objects the map will contain
	 * @param representedObject The object to populate the map with
	 * @param symbol The symbol of the objects populating the map
	 */
	public void populate(double percentChance, Object representedObject, String symbol)
	{
		containerObject placeholder;
		for (int r=0; r<this.size[0]; r++)
			for (int c=0; c<this.size[1]; c++)
				if (Math.random() < percentChance)
					if (this.objectMap[r][c].getRepresentedObject() == null)
					{
						placeholder = new containerObject(representedObject, symbol, new double[] {r, c});
						this.objectMap[r][c] = placeholder;
						this.updateEntityList(placeholder);
					}
	}

	/**
	 * Removes all entities from the map
	 */
	public void empty()
	{
		for (int y=0; y<this.size[0]; y++)
		for (int x=0; x<this.size[1]; x++)
		this.objectMap[y][x] = new containerObject(null, " ");
		this.entityReferenceList = new ArrayList<containerObject>();
	}

	/**
	 * Places a single object into the map
	 * @param y The object's y coordinate
	 * @param x The object's x coordinate
	 * @param representedObject The object
	 * @param symbol The object's symbol
	 */
	public void placeObject(int y, int x, Object representedObject, String symbol)
	{
		containerObject obj = new containerObject(representedObject, symbol, new double[] {y, x});
		this.objectMap[y][x] = obj;
		this.updateEntityList(obj);
	}

	/**
	 * Pushes an object a set distance.
	 * If the edge of the universe is reached, it loops around, making a sort of spherical universe
	 * When colliding with another object, it pushes that object half the distance it was.
	 * @param y The object's y shift
	 * @param x The object's x shift
	 * @param object The object
	 */
	public int pushObject(double y, double x, Object object)
	{
		int condition = 0;
		if (y == 0 && x == 0) return 0;
		int[] objectLoc = findObject(object);
		if (objectLoc == null) return 0;
		double yLoc = objectLoc[0]+y;
		double xLoc = objectLoc[1]+x;

		while (yLoc > size[0]-1) yLoc -= size[0]-1;
		while (xLoc > size[1]-1) xLoc -= size[1]-1;
		while (yLoc < 0) yLoc += size[0]-1;
		while (xLoc < 0) xLoc += size[1]-1;

		if (this.objectMap[(int)yLoc][(int)xLoc].getRepresentedObject() == object) return 0;
		if (this.objectMap[(int)yLoc][(int)xLoc].getRepresentedObject() != null)
		{ condition = pushObject_(y*0.1, x*0.1, this.objectMap[(int)yLoc][(int)xLoc].getRepresentedObject(), 0); }
		if (condition == 1) 
		{
			return 0;
		}

		containerObject temp = this.objectMap[objectLoc[0]][objectLoc[1]];
		this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
		this.objectMap[(int)yLoc][(int)xLoc] = temp;
		return 0;
	}

	/**
	 * Helper method for recursive pushing. Helps to prevent stack overflow errors.
	 * @param y Vertical shift
	 * @param x Horizontal shift
	 * @param object Object to shift
	 * @param loop Iterator
	 */
	private int pushObject_(double y, double x, Object object, int loop)
	{
		int condition = 0;

		if (y == 0 && x == 0) return 1;
		int[] objectLoc = findObject(object);
		if (objectLoc == null) return 1;
		double yLoc = objectLoc[0]+y;
		double xLoc = objectLoc[1]+x;

		while (yLoc > size[0]-1) yLoc -= size[0]-1;
		while (xLoc > size[1]-1) xLoc -= size[1]-1;
		while (yLoc < 0) yLoc += size[0]-1;
		while (xLoc < 0) xLoc += size[1]-1;

		if (this.objectMap[(int)yLoc][(int)xLoc].getRepresentedObject() == object) return 1;
		if (this.objectMap[(int)yLoc][(int)xLoc].getRepresentedObject() != null && loop <= 2*this.size[0]) 
		{
			condition = pushObject_(y*0.1, x*0.1, this.objectMap[(int)yLoc][(int)xLoc].getRepresentedObject(), loop+1);
		}
		if (loop >= 2*this.size[0] || condition == 1)
		{
			return 1;
		}

		containerObject temp = this.objectMap[objectLoc[0]][objectLoc[1]];
		this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
		this.objectMap[(int)yLoc][(int)xLoc] = temp;
		return 0;
	}

	/**
	 * Finds the coordinates of an object
	 * @param lostObject the object trying to be found
	 * @return The object's coordinates
	 */
	public int[] findObject(Object lostObject)
	{
		for (int y=0; y<this.size[0]; y++)
			for (int x=0; x<this.size[1]; x++)
				if (this.objectMap[y][x].getRepresentedObject() == lostObject)
					return new int[] {y, x};
				return null;
	}
}