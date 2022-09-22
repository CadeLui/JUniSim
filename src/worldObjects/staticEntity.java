package worldObjects;

// Alternate version of entity that is not affected by gravity.
// Currently deprecated. Could return in future versions.
public class staticEntity extends entity {
    int id = 0;
	private double mass;

	private double angle = 0;
	private double speed = 0;
    
    public staticEntity(double mass)
    {
        super(mass);
    }
    public void accelerate(double angle, double speed)
    {
        return;
    }
    public void changeVelocity(double angle, double speed)
    {
        return;
    }
    public String toString()
	{
		return "ID: " + id + "\nMass: " + mass + " Kg\nAngle: " + angle + " radians\nSpeed: " + speed;
	}
}
