package worldObjects;

/*
	The objects that are being placed within space.
	I have been conflicted on an official name,
	so Entity, Object, Singularity, and Rock are used interchangeably 
*/

public class entity {
	private int id = 0;
	private double mass = 0;

	private double angle = 0;
	private double speed = 0;

	private double lightSpeed = Math.sqrt(3);

	/**
	 * Constructs a new object with a given mass and default parameters
	 * @param mass The object's mass
	 */
	public entity(double mass)
	{
		this.mass = mass;
		this.id = (int) (Math.random() * 1000000);
		this.fix();
	}

	/**
	 * Constructs a new object with all parameters given
	 * @param mass The object's mass
	 * @param angle The object's initial angle
	 * @param speed The object's initial speed
	 * @param ls The light speed of the object
	 */
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

	/**
	 * Returns the arbitrary ID of the object
	 * @return
	 */
	public int getId() { return this.id; }

	/**
	 * Returns the mass of the object
	 * @return
	 */
	public double getMass() { return this.mass; }

	/**
	 * Returns the object's current speed
	 * @return
	 */
	public double getSpeed() { return this.speed; }

	/**
	 * Returns the object's current angle
	 * @return
	 */
	public double getAngle() { return this.angle; }

	/**
	 * Returns the maximum speed of the object
	 * @return
	 */
	public double getLS() { return this.lightSpeed; }

	/**
	 * Translates all instance variables to a string
	 */
	public String toString()
	{
		return "ID: " + id + "\nMass: " + mass + " Kg\nAngle: " + angle + " radians\nSpeed: " + speed;
	}

	/**
	 * Changes the object's maximum speed
	 * @param ls
	 */
	public void setLS(double ls) { this.lightSpeed = ls; }

	/**
	 * Adds a given mass to the object
	 * @param mass
	 */
	public void addMass(double mass) { this.mass += mass; }

	/**
	 * Changes values to be useable for other methods
	 * Angle is kept positive,
	 * The speed is kept within the bounds of lightSpeed
	 */
	public void fix()
	{
		double pi = Math.PI;
		while (this.angle > 2*pi) this.angle -= 2*pi;
		while (this.angle < 0) this.angle += 2*pi;
		if (this.speed > this.lightSpeed) this.speed = this.lightSpeed;
		if (this.speed < -this.lightSpeed) this.speed = -this.lightSpeed;
	}

	/**
	 * Nudges the angle and speed of the object towards a given vector.
	 * Accounts for gravity for the purposes of the simulator
	 * @param angle Angle of the vector
	 * @param speed Speed of the vector
	 * @param r Distance away from the object
	 */
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

	/**
	 * If a gentle acceleration is not your boat, try just changing the values abruptly.
	 * @param angle The new angle
	 * @param speed The new speed
	 */
	public void changeVelocity(double angle, double speed)
	{
		this.angle = angle;
		this.speed = speed;
		this.fix();
	}
}
