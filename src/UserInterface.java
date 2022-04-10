import java.util.Scanner;
import universe.universe;
import worldObjects.entity;

/*
	A global object that defines the basic user interface for generating and modifying a universe.
	Most of these methods were previously contained in the main "App.java" class, 
	but were moved to here to enable more specific methods for the interface.
*/

public class UserInterface 
{

	/*--- ------------*\
	  STATIC VARIABLES
	\*----------------*/

	private static Scanner sc = new Scanner(System.in);
	private static universe uni;
	private static double ls;

	/*--------------*\
	  STATIC METHODS
	\*--------------*/

	// Waits a set amount of time
	public static void sleep(int time) throws InterruptedException
	{
		Thread.sleep(time);
	}

	// Takes inputs related to the generation of a new universe
	public static void generateUniverse()
	{
		System.out.print("Height of Universe: "); int h = sc.nextInt();
		System.out.print("Width of Universe: "); int w = sc.nextInt();
		System.out.print("Gravitational Constant: "); double g = sc.nextDouble();
		System.out.print("Light Speed: "); ls = sc.nextDouble();
		uni = new universe(h, w, g);
	}

	// Takes inputs related to filling a universe with a set percentage of objects.
	public static void populateUniverse()
	{
		double a;
		System.out.print("Y-Coordinate of Point 1: "); int y1 = sc.nextInt();
		System.out.print("X-Coordinate of Point 1: "); int x1 = sc.nextInt();
		System.out.print("Y-Coordinate of Point 2: "); int y2 = sc.nextInt();
		System.out.print("X-Coordinate of Point 2: "); int x2 = sc.nextInt();
		System.out.print("Percent of Area Populated: "); double pop = sc.nextDouble();
		System.out.print("Symbol for Entities: "); String st = sc.next();
		System.out.print("Mass of Entities: "); double m = sc.nextDouble();
		System.out.print("Speed of Entities: "); double s = sc.nextDouble();

		// Whether each object should have an arbitrary angle or one set by the user.
		System.out.print("Random angles (t/f)? "); boolean r = false;
		switch(sc.next())
		{
			case "f":
				r = false;
				break;
			default:
				r = true; 
		}
		if (r)
		{	
			if (y1 == y2 && x1 == x2)
			 	uni.populate2(pop, new entity(m, 0, s, ls), st);
			else
				uni.populate2(pop, new entity(m, 0, s, ls), st, y1, x1, y2+1, x2+1);
		}
		else
		{
			System.out.print("Angle of Entities: "); a = sc.nextDouble();
			if (y1 == y2 && x1 == x2)
				uni.populate2(pop, new entity(m, a, s, ls), st);
			else
				uni.populate2(pop, new entity(m, a, s, ls), st, y1, x1, y2+1, x2+1);
		}
	}
	// Returns information about the universe to more easily adjust values accurately
	public static String uniInfo()
	{
		return "Gravity: " + uni.getG() + "\nLight Speed: " + ls + "\nSize: " + uni.getSize()[0] + ":" + uni.getSize()[0];
	}

	// The top-level menu
	public static int mainMenu() throws InterruptedException
	{
		System.out.print("1 - [M]odify [V]iew [S]imulate [R]egenerate [A]nswers [I]nfo [Q]uit | ");
		String answer = sc.next();
		switch (answer)
		{
			case "i":
			case "I":
				System.out.println(uniInfo());
			case "a":
			case "A":
				System.out.println("https://docs.google.com/document/d/1rbEDiOm4Hl2W-yNnxA0DhDNRuCe0kEvGRObMU0aKhRw/edit?usp=sharing");
				break;
			case "m":
			case "M":
				while (modMenu() == 0);
				break;
			case "v":
			case "V":
				System.out.println(uni);
				break;
			case "s":
			case "S":
				System.out.print("How many frames? "); int frames = sc.nextInt();
				Collect(uni, frames);
				break;
			case "r":
			case "R":
				generateUniverse();
				break;
			case "q":
			case "Q":
				return 1;
		}
		return 0;
	}

	// Secondary menu for modifying data about the universe as a whole
	public static int modMenu()
	{
		System.out.print("2 - [P]opulate [G]ravity [M]odify singularities [E]rase [V]iew [R]eturn | ");
		String answer = sc.next();
		switch (answer)
		{
			case "p":
			case "P":
				populateUniverse();
				break;
			case "m":
			case "M":
				while (singularityMenu() == 0);
				break;
			case "g":
			case "G":
				System.out.print("G: ");
				uni.setG(sc.nextDouble());
				break;
			case "E":
			case "e":
				uni.empty();
				break;
			case "v":
			case "V":
				System.out.println(uni);
				break;
			case "r":
			case "R":
				return 1;
		}
		return 0;
	}

	// Tertiary menu that allows adjustment of specifically the singularities.
	private static int singularityMenu()
	{
		System.out.print("3 - [A]ngle [S]peed [L]ightspeed [F]reeze [P]lace [R]eturn | ");
		String ans = sc.next();
		switch (ans)
		{
			case "a":
			case "A":
				System.out.println("Angle: "); double angle = sc.nextDouble();
				uni.startingAngle(angle);
				break;
			case "s":
			case "S":
				System.out.println("Speed: "); double speed = sc.nextDouble();
				uni.startingSpeed(speed);
				break;
			case "l":
			case "L":
				System.out.println("Lightspeed: ");
				ls = sc.nextDouble();
				uni.changeLightSpeed(ls);
				break;
			case "f":
			case "F":
				uni.freezeAll();
				break;
			case "p":
			case "P":
				placeSingularity();
				break;
			case "r":
			case "R":
				return 1;
		}
		return 0;
	}

	// Interface for placing a single-larity somewhere in the universe.
	private static void placeSingularity()
	{
		int y;
		int x;
		double mass;
		double angle;
		double speed;
		String symbol;
		System.out.print("Y: "); y = sc.nextInt();
		System.out.print("X: "); x = sc.nextInt();
		System.out.print("Mass: "); mass = sc.nextDouble();
		System.out.print("Speed: "); speed = sc.nextDouble();
		System.out.print("Angle: "); angle = sc.nextDouble();
		System.out.print("Symbol: "); symbol = sc.next();
		uni.placeObject(y, x, new entity(mass, angle, speed, ls), symbol);
	}

	// Collects fresh frames for the universe.
	private static void Collect(universe Uni, int frames) throws InterruptedException
	{
		System.out.println("Universal ID: " + Uni.getID());

		String[] film = new String[frames];
		System.out.println(Uni);
		System.out.print("skipping until update ");

		// Checks if the starting universe and its next frame are the same. If so, continue looping and checking until a new frame is made.
		String u1 = Uni.toString();
		film[0] = u1;
		String u2 = Uni.update();
		int count = 0;
		while (u1.equals(u2))
		{
			u1 = u2;
			u2 = Uni.update();
			System.out.print(".");
			count++;
			if (count > 300)
			{
				System.out.println("300 frames skipped. Failure to update.");
				return;
			}
		} System.out.print("\n" + count + " frames skipped.\nGenerating film 1, 2, ");
		
		// Generates the frames for the video.
		film[1] = Uni.toString();
		film = generateFilm(film, 2, Uni);
		for(int i = 0; i < 100; i++) System.out.println();

		// Confirmation for when the user is ready to watch the video.
		for (;;) 
		{ 
			System.out.println("Enter 'g' when ready: "); 
			String gah = sc.next();
			if (gah.equals("g")) break; 
			System.out.println(gah);
		}
	
		// The video player. Divide 1000 by sleep()'s parameter to get the F/s
		for (String x : film)
		{
			System.out.println(x);
			sleep(250);
		}
	}

	// Helper method for Collect()
	private static String[] generateFilm(String[] film, int index, universe Universe) throws InterruptedException
	{
		// Updates the universe and adds its string to the film.
		// If there is a time-out error, wait a short time and then continue.
		try {
			for (int i = index; i < film.length; i++)
			{
				index = i;
				System.out.print((1+index) + ", ");
				film[i] = Universe.update();
			} 
		} catch (StackOverflowError e) {
			System.out.println(" Exception caught. Continuing. " + e);
			sleep(500);
			generateFilm(film, index, Universe);
		} System.out.println();
		return film;
	}
}
