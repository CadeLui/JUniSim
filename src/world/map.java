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

	public map(int v_size, int h_size)
	{
		this.size = new int[] {v_size, h_size};
		this.objectMap = new containerObject[v_size][h_size];
		for (int i = 0; i < this.objectMap.length; i++)
			Arrays.fill(this.objectMap[i], new containerObject(null, " "));
	}

	// Returns the dimensions of the map
	public int[] getSize() { return this.size; }
	public containerObject getObject(int y, int x) { return objectMap[y][x]; }
	public ArrayList<containerObject> getEntityReferenceList() {return this.entityReferenceList;}

	// Converts the map to a printable string.
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
		returnString += "-/\n";
		return returnString;
	}

	// Should only be used as a helper method.
	public void updateEntityList(containerObject newEntity)
	{
		this.entityReferenceList.add(newEntity);
	}

	// Should only be used as a helper method.
	public void removeEntityFromList(containerObject oldEntity)
	{
		this.entityReferenceList.remove(oldEntity);
	}

	// Randomly places a given object and symbol across the map
	public void populate(double percentChance, Object representedObject, String symbol)
	{
		containerObject placeholder;
		for (int r=0; r<this.size[0]; r++)
			for (int c=0; c<this.size[1]; c++)
				if (Math.random() < percentChance)
					if (this.objectMap[r][c].getRepresentedObject() == null)
					{
						placeholder = new containerObject(representedObject, symbol, new int[] {r, c});
						this.objectMap[r][c] = placeholder;
						this.updateEntityList(placeholder);
					}
	}

	// Replaces the universe with empty container objects.
	public void empty()
	{
		for (int y=0; y<this.size[0]; y++)
		for (int x=0; x<this.size[1]; x++)
		this.objectMap[y][x] = new containerObject(null, " ");
		this.entityReferenceList = new ArrayList<containerObject>();
	}

	// Replaces an object at a given coordinate with a new object
	public void placeObject(int y, int x, Object representedObject, String symbol)
	{
		containerObject obj = new containerObject(representedObject, symbol, new int[] {y, x});
		this.objectMap[y][x] = obj;
		this.updateEntityList(obj);
	}

	// Pushes an object and recursively push objects that it collides directly with.
	public void pushObject(int y, int x, Object object)
	{
		if (y == 0 && x == 0) return;
		int[] objectLoc = findObject(object);
		if (objectLoc == null) return;
		int yLoc = objectLoc[0]+y;
		int xLoc = objectLoc[1]+x;

		while (yLoc > size[0]-1) yLoc -= size[0]-1;
		while (xLoc > size[1]-1) xLoc -= size[1]-1;
		while (yLoc < 0) yLoc += size[0]-1;
		while (xLoc < 0) xLoc += size[1]-1;

		if (this.objectMap[yLoc][xLoc].getRepresentedObject() == object) return;
		if (this.objectMap[yLoc][xLoc].getRepresentedObject() != null) pushObject(y, x, this.objectMap[yLoc][xLoc].getRepresentedObject());

		containerObject temp = this.objectMap[objectLoc[0]][objectLoc[1]];
		this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
		this.objectMap[yLoc][xLoc] = temp;
	}

	// Finds a given object and returns its coordinates
	public int[] findObject(Object lostObject)
	{
		for (int y=0; y<this.size[0]; y++)
			for (int x=0; x<this.size[1]; x++)
				if (this.objectMap[y][x].getRepresentedObject() == lostObject)
					return new int[] {y, x};
				return null;
	}
}