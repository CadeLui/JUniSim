package worldObjects;

/*
	The objects that are being placed within space.
	I have been conflicted on an official name,
	so Entity, Object, Singularity, and Rock are used interchangeably 
*/

public class entity {

	/*------------------*\
	  INSTANCE VARIABLES
	\*------------------*/

	private int id = 0;
	private double mass;

	private double angle = 0;
	private double speed = 0;

	private double lightSpeed = Math.sqrt(3);

	/*------------*\
	  CONSTRUCTORS
	\*------------*/

	public entity(double mass)
	{
		this.mass = mass;
		this.id = (int) (Math.random() * 1000000);
		this.fix();
	}

	public entity(double mass, double angle, double speed, double ls)
	{
		this.mass = mass;
		this.id = (int) (Math.random() * 1000000);
		this.speed = speed;
		this.angle = angle;
		this.lightSpeed = ls;
		if (this.angle < 0) this.angle += 2*Math.PI;
		this.fix();
	}

	/*---------*\
	  ACCESSORS
	\*---------*/

	public int getId() { return this.id; }
	public double getMass() { return this.mass; }
	public double getSpeed() { return this.speed; }
	public double getAngle() { return this.angle; }
	public double getLS() { return this.lightSpeed; }

	// Translates the variables of an entity for simpler debugging
	public String toString()
	{
		return "ID: " + id + "\nMass: " + mass + " Kg\nAngle: " + angle + " radians\nSpeed: " + speed;
	}

	/*---------*\
	  MODIFIERS
	\*---------*/

	public void setLS(double ls) { this.lightSpeed = ls; }
	public void addMass(double mass) { this.mass += mass; }

	// Keeps angles positive and within the area of a circle, keeps speeds reasonable
	public void fix()
	{
		double pi = Math.PI;
		while (this.angle > 2*pi) this.angle -= 2*pi;
		while (this.angle < 0) this.angle += 2*pi;
		if (this.speed > this.lightSpeed) this.speed = this.lightSpeed;
		if (this.speed < -this.lightSpeed) this.speed = -this.lightSpeed;
	}

	// Gently adjusts the angle and speed of a singularity to simulate the gentle affect of gravity
	public void accelerate(double angle, double speed, double r)
	{
		double pi = Math.PI;
		if (speed < 0) speed = -speed;
		while (angle < 0) angle += 2*pi;
		while (angle > 2*pi) angle -= 2*pi;

		// If the direction angle is behind the singularity, slow down.
		// If the direction angle is ahead of the singularity, speed up.
		if (this.angle + (pi/2) < angle && this.angle + ((3*pi)/2) > angle)
			this.speed -= speed;
		else
			this.speed += speed;
		
		// Subtract the smaller angle from the greater angle, divide that by r^2, and adjust accordingly.
		if (this.angle > angle)
			// Adjusts angle towards the right.
			this.angle = this.angle - ((this.angle-angle)/(r*r));
		else
			// Adjusts angle towards the left.
			this.angle = this.angle + ((angle-this.angle)/(r*r));

		this.fix();
	}

	// Screw being gentle, just change everything to a specific value.
	public void changeVelocity(double angle, double speed)
	{
		this.angle = angle;
		this.speed = speed;
		this.fix();
	}
}
