package universe;

import world.containerObject;
import world.map;
import worldObjects.entity;

public class universe extends map 
{
	private double G;


	/*----------------------------------*\
    | Constructors, getters, and setters |
	\*----------------------------------*/
	public universe(int v_size, int h_size) 
	{
		super(v_size, h_size);
		this.G = distanceBetweenPoints(new int[]{0,0}, new int[]{v_size-1, h_size-1})/100000;
	}
	public universe(int v_size, int h_size, double G)
	{
		super(v_size, h_size);
		this.G = G;
	}
	public void setG(double g) { G = g; }
	public double getG() {return G;}
	public int getID()
	{
		entity obj = (entity) entityReferenceList.get((int) (Math.random() * (entityReferenceList.size()-1))).getRepresentedObject();
		return (int) Math.pow((size[0]+size[1]) * (1/G) + entityReferenceList.size() + (findObject(obj)[0] * findObject(obj)[1]), (Math.random() * 3)+1);
	}

	// Randomly places a given object and symbol across the map
	public void populate(double percentChance, entity representedObject, String symbol)
	{
		if (representedObject == null) return;
		double mass = representedObject.getMass();
		double speed = representedObject.getSpeed();
		double angle = representedObject.getAngle();
		double ls = representedObject.getLS();
		for (int y=0; y<this.size[0]; y++)
			for (int x=0; x<this.size[1]; x++)
				if (Math.random() < percentChance)
					if (this.objectMap[y][x].getRepresentedObject() == null)
					{
						containerObject placeholder = new containerObject(new entity(mass, angle, speed, ls), symbol, new int[] {y, x});
						this.objectMap[y][x] = placeholder;
						this.updateEntityList(placeholder);
					}
	}
	// Randomly places an object in a given area while assigning random angles
	public void populate2(double percentChance, entity representedObject, String symbol)
	{
		if (representedObject == null) return;
		double mass = representedObject.getMass();
		double speed = representedObject.getSpeed();
		double ls = representedObject.getLS();
		for (int y=0; y<this.size[0]; y++)
			for (int x=0; x<this.size[1]; x++)
				if (Math.random() < percentChance)
					if (this.objectMap[y][x].getRepresentedObject() == null)
					{
						containerObject placeholder = new containerObject(new entity(mass, 2*Math.PI * Math.random(), speed, ls), symbol, new int[] {y, x});
						this.objectMap[y][x] = placeholder;
						this.updateEntityList(placeholder);
					}
	}
	// Randomly places a given object across a section of the map.
	public void populate(double percentChance, entity representedObject, String symbol, int y1, int x1, int y2, int x2)
	{
		if (representedObject == null) return;
		if (y1 > this.size[0]) y1 = this.size[0];
		if (x1 > this.size[1]) x1 = this.size[1];
		if (y2 > this.size[0]) y2 = this.size[0];
		if (x2 > this.size[1]) x2 = this.size[1];
		double mass = representedObject.getMass();
		double speed = representedObject.getSpeed();
		double angle = representedObject.getAngle();
		double ls = representedObject.getLS();
		for (int y=y1; y<y2; y++)
			for (int x=x1; x<x2; x++)
				if (Math.random() < percentChance)
					if (this.objectMap[y][x].getRepresentedObject() == null)
					{
						containerObject newEntity = new containerObject(new entity(mass, angle, speed, ls), symbol, new int[] {y, x});
						this.objectMap[y][x] = newEntity;
						this.updateEntityList(newEntity);
					}
	}
	// Randomly places a given object across a section of the map and assigns random angles.
	public void populate2(double percentChance, entity representedObject, String symbol, int y1, int x1, int y2, int x2)
	{
		if (representedObject == null) return;
		if (y1 > this.size[0]) y1 = this.size[0];
		if (x1 > this.size[1]) x1 = this.size[1];
		if (y2 > this.size[0]) y2 = this.size[0];
		if (x2 > this.size[1]) x2 = this.size[1];
		double mass = representedObject.getMass();
		double speed = representedObject.getSpeed();
		double ls = representedObject.getLS();
		for (int y=y1; y<y2; y++)
			for (int x=x1; x<x2; x++)
				if (Math.random() < percentChance)
					if (this.objectMap[y][x].getRepresentedObject() == null)
					{
						containerObject placeholder = new containerObject(new entity(mass, 2*Math.PI * Math.random(), speed, ls), symbol, new int[] {y, x});
						this.objectMap[y][x] = placeholder;
						this.updateEntityList(placeholder);
					}
	}
	// Sets all the object's angles to be the same.
	public void startingAngle(double angle)
	{
		if (angle > 2*Math.PI) return;
		if (angle < 0)
			for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
			{
				entity guy = (entity) con1.getRepresentedObject();
				entity pal = greatestAttractor(guy);
				if (guy.getAngle() == 0 || pal.getAngle() == 0);
				{
					guy.changeVelocity(getAngleBetweenObjects(guy, pal), guy.getSpeed());
				}
			}
		else
			for (containerObject con : entityReferenceList) if (con.getRepresentedObject() instanceof entity)
			{
				entity singularity = (entity) con1.getRepresentedObject();
				singularity.changeVelocity(angle, singularity.getSpeed());
			}
			
	}
	// Sets all object's speeds to be the same.
	public void startingSpeed(double speed)
	{
		if (speed == 0)
			for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
			{
				entity guy = (entity) con1.getRepresentedObject();
				entity pal = greatestAttractor(guy);
				if (guy.getAngle() == 0 || pal.getAngle() == 0);
				{
					guy.changeVelocity(guy.getAngle(), (getGravityBetweenObjects(guy, pal)/guy.getMass()));
				}
			}
		else
			for (containerObject con : entityReferenceList) if (con.getRepresentedObject() instanceof entity)
			{
				entity singularity = (entity) con.getRepresentedObject();
				singularity.changeVelocity(singularity.getAngle(), speed);
			}
	}
	// Stops every object while preserving angle.
	public void freezeAll()
	{
		for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
		{
			double angle = ((entity) con1.getRepresentedObject()).getAngle();
			((entity) con1.getRepresentedObject()).changeVelocity(angle, 0);
		}
	}
	public static double distanceBetweenPoints(int[] a, int[] b) 
	{
		if (a == null || b == null) return 0;
		return Math.sqrt(Math.pow(a[1]-b[1], 2) + Math.pow(a[0]-b[0], 2));
	}
	public double getGravityBetweenObjects(entity e1, entity e2)
	{
		if (e1 == null || e2 == null) return 0;
		int[] e1Loc = findObject(e1);
		int[] e2Loc = findObject(e2);

		double massProduct = e1.getMass() * e2.getMass();
		double distance = Math.pow(universe.distanceBetweenPoints(e1Loc, e2Loc), 2);
		return (G*massProduct/distance);
	}
	// Finds the angle between two objects.
	public double getAngleBetweenObjects(entity e1, entity e2)
	{
		int[] e1Loc = findObject(e1);
		int[] e2Loc = findObject(e2);
		if (e1Loc == null || e2Loc == null) return 0;
		double a = Math.atan2(e2Loc[0]-e1Loc[0], e2Loc[1]-e1Loc[1]);
		if (a < 0) a += 2*Math.PI;
		return a;
	}
	// Finds the object with the greatest gravitational attraction to a given object.
	public entity greatestAttractor(entity e)
	{
		double greatestG = 0;
		entity greatestE = null;
		for (int y = 0; y < this.objectMap.length; y++)
		for (int x = 0; x < this.objectMap.length; x++)
		{
			if (this.objectMap[y][x].getRepresentedObject() instanceof entity)
			{
				if (e == (entity) this.objectMap[y][x].getRepresentedObject()) continue;
				if (getGravityBetweenObjects(e, (entity) this.objectMap[y][x].getRepresentedObject()) > greatestG)
				{
					greatestG = getGravityBetweenObjects(e, (entity) this.objectMap[y][x].getRepresentedObject());
					greatestE = (entity) this.objectMap[y][x].getRepresentedObject();
				}
			}
		}
		return greatestE;
	}

	// Changes the light speed of every entity on the map.
	public void changeLightSpeed(double ls)
	{
		for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
		{
			entity singularity = (entity) con1.getRepresentedObject();
			singularity.setLS(ls);
		}
	}

	// Accelerates every object according to every object, then pushes every object.
	public String update()
	{
		for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
		{
			entity guy = (entity) con1.getRepresentedObject();
			for (containerObject con2 : entityReferenceList) if (con2.getRepresentedObject() instanceof entity)
			{
				if (con2 == con1) continue; 
				entity pal = (entity) con2.getRepresentedObject();
				double distance = distanceBetweenPoints(findObject(guy), findObject(pal));
				guy.accelerate(getAngleBetweenObjects(guy, pal), getGravityBetweenObjects(guy, pal)/guy.getMass(), distance);
			}
			this.pushObject((int) (guy.getSpeed()*Math.sin(guy.getAngle())), (int) (guy.getSpeed()*Math.cos(guy.getAngle())), guy);
		}
		
		//for (int i = 0; i < entityReferenceList.size(); i++)
		//if (entityReferenceList.get(i).getRepresentedObject() instanceof entity)
		//{
		//	entity guy = (entity) entityReferenceList.get(i).getRepresentedObject();
		//	this.pushObject((int) (guy.getSpeed()*Math.sin(guy.getAngle())), (int) (guy.getSpeed()*Math.cos(guy.getAngle())), guy);
		//	//System.out.println(guy + "\n" + container.toString() + "\n" + Arrays.toString(findObject(guy)));
		//}
		return toString();
	}
	// Alternate version of pushObject that merges objects rather than recursively pushes.
	/* public void pushObject(int y, int x, Object object)
	{
		// If object isnt being pushed, skip
		if (y == 0 && x == 0) return;

		// Convert safely from object to containerObject
		int[] objectLoc = findObject(object);
		if (objectLoc == null) return;

		// The location that the object will be pushed to
		int yLoc = objectLoc[0]+y;
		int xLoc = objectLoc[1]+x;

		// Loop to other side of universe when edge is reached
		while (yLoc > size[0]-1) yLoc -= size[0]-1;
		while (xLoc > size[1]-1) xLoc -= size[1]-1;
		while (yLoc < 0) yLoc += size[0]-1;
		while (xLoc < 0) xLoc += size[1]-1;

		// If the destination object is the same as the starting object, return
		if (this.objectMap[yLoc][xLoc].getRepresentedObject() == object) return;

		// If the destination object contains an object, absorb it into the destination object and push the destination a small amount
		if (this.objectMap[yLoc][xLoc].getRepresentedObject() != null)
		{
			entity t1 = (entity) this.objectMap[objectLoc[0]][objectLoc[1]].getRepresentedObject();
			entity t2 = (entity) this.objectMap[yLoc][xLoc].getRepresentedObject();
			if (t2.getMass() >= t1.getMass())
			{
				t2.addMass(t1.getMass());
				removeEntityFromList(this.objectMap[objectLoc[0]][objectLoc[1]]);
				this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
				pushObject(y/2, x/2, this.objectMap[yLoc][xLoc].getRepresentedObject());
			}
			else
			{
				t1.addMass(t1.getMass());
				removeEntityFromList(this.objectMap[yLoc][xLoc]);
				this.objectMap[yLoc][xLoc] = this.objectMap[objectLoc[0]][objectLoc[1]];
				this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
			}
		}
		// Otherwise, just push the object forward.
		else
		{
			containerObject temp = this.objectMap[objectLoc[0]][objectLoc[1]];
			this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
			this.objectMap[yLoc][xLoc] = temp;
		}
	} */
}
