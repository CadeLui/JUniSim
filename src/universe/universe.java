package universe;

import world.containerObject;
import world.map;
import worldObjects.entity;

public class universe extends map 
{
	private double G;
	private double distanceMultiplier;
	private String[] colors = new String[] {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m"};
	private String colorReset = "\u001B[0m";

	/**
	 * Defines a universe with an automatically generated gravitational constant
	 * @param v_size The vertical size of the universe
	 * @param h_size The horizontal size of the universe
	 */
	public universe(int v_size, int h_size) 
	{
		super(v_size, h_size);
		this.G = distanceBetweenPoints(new int[]{0,0}, new int[]{v_size-1, h_size-1})/100000;
	}

	/**
	 * Defines a universe with a user defined gravitational constant
	 * @param v_size The vertical size of the universe
	 * @param h_size The horizontal size of the universe
	 * @param G A user-defined gravitational constant
	 */
	public universe(int v_size, int h_size, double G, double mult)
	{
		super(v_size, h_size);
		this.G = G;
		this.distanceMultiplier = mult;
	}

	/**
	 * Changes the universe's gravitational constant
	 * @param g The new gravitational constant
	 */
	public void setG(double g) { G = g; }

	/**
	 * Changes how much space one block represents in meters
	 * @param mult the new distance multiplier
	 */
	public void setDistanceMultiplier(double mult) { distanceMultiplier = mult; }

	/**
	 * Returns the universe's gravitational constant
	 * @return The gravitational constant
	 */
	public double getG() {return G;}

	/**
	 * Returns the universe's distance multiplier
	 * @return The distance multiplier
	 */
	public double getDistanceMultiplier() { return distanceMultiplier; }

	/**
	 * Generates an arbitrary id for the universe
	 * @return An arbitrary number
	 */
	public int getID()
	{
		entity obj = (entity) entityReferenceList.get((int) (Math.random() * (entityReferenceList.size()-1))).getRepresentedObject();
		return (int) Math.pow((size[0]+size[1]) * (1/G) + entityReferenceList.size() + (findObject(obj)[0] * findObject(obj)[1]), (Math.random() * 3)+1);
	}

	/**
	 * Randomly places a given object and symbol across the map
	 * @param percentChance Roughly how much of the universe will be populated
	 * @param representedObject The object that the universe will be populated with
	 * @param symbol The symbol of the object that the universe will be populated with
	 */
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
						containerObject placeholder = new containerObject(new entity(mass, angle, speed, ls), colors[(int) (Math.random() * colors.length)] + symbol + colorReset, new double[] {y, x});
						this.objectMap[y][x] = placeholder;
						this.updateEntityList(placeholder);
					}
	}

	/** 
	 * Randomly places an object in a given area while assigning random angles
	 * @param percentChance Roughly how much of the universe will be populated
	 * @param representedObject The object that the universe will be populated with
	 * @param symbol The symbol of the object that the universe will be populated with
	 */
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
						containerObject placeholder = new containerObject(new entity(mass, 2*Math.PI * Math.random(), speed, ls), colors[(int) (Math.random() * colors.length)] + symbol + colorReset, new double[] {y, x});
						this.objectMap[y][x] = placeholder;
						this.updateEntityList(placeholder);
					}
	}

	/**
	 * Randomly places a given object across a section of the map.
	 * @param percentChance Roughly how much of the universe will be populated
	 * @param representedObject The object that the universe will be populated with
	 * @param symbol The symbol of the object that the universe will be populated with
	 * @param y1 starting vertical coordinate
	 * @param x1 starting horizontal coordinate
	 * @param y2 ending vertical coordinate
	 * @param x2 ending horizontal coordinate
	 */
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
						containerObject newEntity = new containerObject(new entity(mass, angle, speed, ls), colors[(int) (Math.random() * colors.length)] + symbol + colorReset, new double[] {y, x});
						this.objectMap[y][x] = newEntity;
						this.updateEntityList(newEntity);
					}
	}

	/**
	 * Randomly places a given object across a section of the map and assigns random angles.
	 * @param percentChance Roughly how much of the universe will be populated
	 * @param representedObject The object that the universe will be populated with
	 * @param symbol The symbol of the object that the universe will be populated with
	 * @param y1 starting vertical coordinate
	 * @param x1 starting horizontal coordinate
	 * @param y2 ending vertical coordinate
	 * @param x2 ending horizontal coordinate
	 */
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
						containerObject placeholder = new containerObject(new entity(mass, 2*Math.PI * Math.random(), speed, ls), colors[(int) (Math.random() * colors.length)] + symbol + colorReset, new double[] {y, x});
						this.objectMap[y][x] = placeholder;
						this.updateEntityList(placeholder);
					}
	}

	/**
	 * Sets all the object's angles to be the same.
	 * @param angle the angle the objects will be set to
	 */
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

	/**
	 * Sets all objects to the same speed
	 * @param speed speed to set the objects to
	 */
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
	
	/**
	 * Freezes all objects, but retains their angle.
	 */
	public void freezeAll()
	{
		for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
		{
			double angle = ((entity) con1.getRepresentedObject()).getAngle();
			((entity) con1.getRepresentedObject()).changeVelocity(angle, 0);
		}
	}

	/**
	 * Finds the distance between points
	 * @param a first point
	 * @param b second point
	 * @return the distance between the points
	 */
	public static double distanceBetweenPoints(int[] a, int[] b) 
	{
		if (a == null || b == null) return 0;
		return Math.sqrt(Math.pow(a[1]-b[1], 2) + Math.pow(a[0]-b[0], 2));
	}

	/**
	 * Finds the gravitational force between objects
	 * @param e1 Object pulling
	 * @param e2 Object being pulled
	 * @return The force between them
	 */
	public double getGravityBetweenObjects(entity e1, entity e2)
	{
		if (e1 == null || e2 == null) return 0;
		int[] e1Loc = findObject(e1);
		int[] e2Loc = findObject(e2);

		double massProduct = e1.getMass() * e2.getMass();
		double distance = Math.pow(universe.distanceBetweenPoints(e1Loc, e2Loc), 2) * distanceMultiplier;
		return (G*massProduct/distance);
	}
	
	/**
	 * Finds the angle between objects e1 and e2
	 * @param e1 The object pointing
	 * @param e2 The object being pointed towards
	 * @return The angle from e1 to e2
	 */
	public double getAngleBetweenObjects(entity e1, entity e2)
	{
		int[] e1Loc = findObject(e1);
		int[] e2Loc = findObject(e2);
		if (e1Loc == null || e2Loc == null) return 0;
		double a = Math.atan2(e2Loc[0]-e1Loc[0], e2Loc[1]-e1Loc[1]);
		if (a < 0) a += 2*Math.PI;
		return a;
	}
	
	/**
	 * Finds the object with the greatest gravitational pull to e
	 * @param e The object being tested
	 * @return The object with greatest pull to e
	 */
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

	/**
	 * Modifies the lightspeed of every object in the universe
	 * @param ls The lightspeed to set to
	 */
	public void changeLightSpeed(double ls)
	{
		for (containerObject con1 : entityReferenceList) if (con1.getRepresentedObject() instanceof entity)
		{
			entity singularity = (entity) con1.getRepresentedObject();
			singularity.setLS(ls);
		}
	}

	/**
	 * Runs a cycle of the universe
	 * Selects an object, gets the distance, angle, and gravitation to every other object
	 * Runs the original object's accelerate method using those parameters
	 * Pushes the object according to its new velocity, or pulls if the option is selected
	 * @return The updated result of the toString() method
	 */
	public String update(String pullOrPush)
	{
		for (int i = 0; i < entityReferenceList.size(); i++) if (entityReferenceList.get(i).getRepresentedObject() instanceof entity)
		{
			entity guy = (entity) entityReferenceList.get(i).getRepresentedObject();
			for (int i2 = 0; i2 < entityReferenceList.size(); i2++) if (entityReferenceList.get(i2).getRepresentedObject() instanceof entity)
			{
				if (entityReferenceList.get(i2) == entityReferenceList.get(i)) continue;
				entity pal = (entity) entityReferenceList.get(i2).getRepresentedObject();
				double distance = distanceBetweenPoints(findObject(guy), findObject(pal));
				guy.accelerate(getAngleBetweenObjects(guy, pal), getGravityBetweenObjects(guy, pal)/guy.getMass(), distance);
			}
			if (pullOrPush == "pull")
				i -= this.pullObject((int) (guy.getSpeed()*Math.sin(guy.getAngle())), (int) (guy.getSpeed()*Math.cos(guy.getAngle())), guy);
			else
				i -= this.pushObject((int) (guy.getSpeed()*Math.sin(guy.getAngle())), (int) (guy.getSpeed()*Math.cos(guy.getAngle())), guy);
		}
		return toString();
	}

	/**
	 * Runs a cycle of the universe
	 * Selects an object, gets the distance, angle, and gravitation to every other object
	 * Runs the original object's accelerate method using those parameters
	 * Pushes the object according to its new velocity, or pulls if the option is selected
	 * @return The updated result of the toString() method
	 */
	public String update2(String pullOrPush)
	{
		for (int i = 0; i < entityReferenceList.size(); i++) if (entityReferenceList.get(i).getRepresentedObject() instanceof entity)
		{
			entity guy = (entity) entityReferenceList.get(i).getRepresentedObject();
			double distance = 0;
			double angle = 0;
			double grav = 0;
			for (int i2 = 0; i2 < entityReferenceList.size(); i2++) if (entityReferenceList.get(i2).getRepresentedObject() instanceof entity)
			{
				if (entityReferenceList.get(i2) == entityReferenceList.get(i)) continue;
				entity pal = (entity) entityReferenceList.get(i2).getRepresentedObject();
				distance += distanceBetweenPoints(findObject(guy), findObject(pal));
				angle += getAngleBetweenObjects(guy, pal);
				grav += getGravityBetweenObjects(guy, pal);
			}
			distance /= entityReferenceList.size()-1;
			angle /= entityReferenceList.size()-1;
			grav /= entityReferenceList.size()-1;
			guy.accelerate(angle, grav, distance);
			if (pullOrPush == "pull")
				i -= this.pullObject((int) (guy.getSpeed()*Math.sin(guy.getAngle())), (int) (guy.getSpeed()*Math.cos(guy.getAngle())), guy);
			else
				i -= this.pushObject((int) (guy.getSpeed()*Math.sin(guy.getAngle())), (int) (guy.getSpeed()*Math.cos(guy.getAngle())), guy);
		}
		return toString();
	}

	/**
	 * identical to pushObject(), but instead of pushing other objects when colliding, it merges them into one object of combined mass
	 * pushObject can be found within the parent class
	 * @param y Object's vertical shift
	 * @param x Object's horizontal shift
	 * @param object The object being pushed
	 */
	public int pullObject(int y, int x, Object object)
	{
		// If object isnt being pushed, skip
		if (y == 0 && x == 0) return 0;

		// Convert safely from object to containerObject
		int[] objectLoc = findObject(object);
		if (objectLoc == null) return 0;

		// The location that the object will be pushed to
		int yLoc = objectLoc[0]+y;
		int xLoc = objectLoc[1]+x;

		// Loop to other side of universe when edge is reached
		while (yLoc > size[0]-1) yLoc -= size[0]-1;
		while (xLoc > size[1]-1) xLoc -= size[1]-1;
		while (yLoc < 0) yLoc += size[0]-1;
		while (xLoc < 0) xLoc += size[1]-1;

		// If the destination object is the same as the starting object, return
		if (this.objectMap[yLoc][xLoc].getRepresentedObject() == object) return 0;

		// If the destination object contains an object, absorb it into the destination object and push the destination a small amount
		if (this.objectMap[yLoc][xLoc].getRepresentedObject() != null)
		{
			// Origin object
			entity t1 = (entity) this.objectMap[objectLoc[0]][objectLoc[1]].getRepresentedObject();
			// Destination object
			entity t2 = (entity) this.objectMap[yLoc][xLoc].getRepresentedObject();
			// If the destination mass is greater or equal to than the origin's mass
			if (t2.getMass() >= t1.getMass())
			{
				// Add origin's mass to destination's
				t2.addMass(t1.getMass());
				// Remove the origin from the list
				removeEntityFromList(this.objectMap[objectLoc[0]][objectLoc[1]]);
				// Remove the origin from the map
				this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
				// Move the destination a small amount forward
				pullObject(y/2, x/2, this.objectMap[yLoc][xLoc].getRepresentedObject());
				return 1;
			}
			// If the origin's mass is greather than the destination's mass
			else
			{
				// Add the destination's mass to the origin
				t1.addMass(t2.getMass());
				// Remove the destination from the list
				removeEntityFromList(this.objectMap[yLoc][xLoc]);
				// Move the origin over the destination
				this.objectMap[yLoc][xLoc] = this.objectMap[objectLoc[0]][objectLoc[1]];
				// Clear the origin's shadow
				this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
				return 1;
			}
		}
		// Otherwise, just push the object forward.
		else
		{
			// Store the origin in a temporary variable
			containerObject temp = this.objectMap[objectLoc[0]][objectLoc[1]];
			// Clear the origin's shadow from the map
			this.objectMap[objectLoc[0]][objectLoc[1]] = new containerObject(null, " ");
			// Place the origin that was saved in its new position
			this.objectMap[yLoc][xLoc] = temp;
		}
		return 0;
	}
}
