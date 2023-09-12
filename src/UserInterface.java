import java.util.Scanner;
import universe.universe;
import worldObjects.entity;

/*
	A global object that defines the basic user interface for generating and modifying a universe.
	Most of these methods were previously contained in the main "App.java" class, 
	but were moved to here to enable more specific methods for the interface.
	I also like separating methods away from the main class.
*/

public class UserInterface 
{
	private static Scanner sc = new Scanner(System.in);
	private static universe uni;
	private static double ls;
	private static String pushOrPull = "push";
	private static int framerate = 10;

	/**
	 * Verifies that the next input is, in fact, the right type.
	 * @param inputType The type you wish to test for
	 */
	public static void inputProtector(String inputType, String question)
	{
		System.out.print(question);
		switch (inputType)
		{
			case "int":
				while (!sc.hasNextInt())
				{
					sc.nextLine(); 
					System.out.print("Not an integer: ");
				} break;
			case "double":
			while (!sc.hasNextDouble())
			{
				sc.nextLine(); 
				System.out.print("Not a double: ");
			} break;
			case "bool":
			while (!sc.hasNextBoolean())
			{
				sc.nextLine(); 
				System.out.print("Not a boolean: ");
			} break;
		}
	}

	/**
	 * Stops the program for a set amount of time
	 * @param time How long to stop in milliseconds
	 * @throws InterruptedException
	 */
	public static void sleep(int time) throws InterruptedException
	{
		Thread.sleep(time);
	}

	public static void runSimulation() throws InterruptedException
	{
		inputProtector("int", "How many frames? "); int frames = sc.nextInt();
		inputProtector("int", "How many to skip? "); int skip = sc.nextInt();
		Collect(uni, frames, skip, framerate);
	}

	/**
	 * Takes input from the user related to generating a universe object
	 */
	public static void generateUniverse()
	{
		inputProtector("int", "Height of Universe: "); int h = sc.nextInt();
		inputProtector("int", "Width of Universe: "); int w = sc.nextInt();
		inputProtector("double", "Gravitational Constant: "); double g = sc.nextDouble();
		inputProtector("double", "Distance Multiplier: "); double mul = sc.nextDouble();
		inputProtector("double", "Light Speed: "); ls = sc.nextDouble();
		uni = new universe(h, w, g, mul);
	}

	/**
	 * Takes inputs related to populating the universe with likewise objects
	 */
	public static void populateUniverse()
	{
		double a;
		inputProtector("int", "Y-Coordinate of Point 1: "); int y1 = sc.nextInt();
		inputProtector("int", "X-Coordinate of Point 1: "); int x1 = sc.nextInt(); 
		inputProtector("int", "Y-Coordinate of Point 2: "); int y2 = sc.nextInt();
		inputProtector("int", "X-Coordinate of Point 2: "); int x2 = sc.nextInt();
		inputProtector("double", "Percent of Area Populated: "); double pop = sc.nextDouble();
		System.out.print("Symbol for Entities: "); String st = sc.next();
		inputProtector("double", "Mass of Entities: "); double m = sc.nextDouble();
		inputProtector("double", "Speed of Entities: "); double s = sc.nextDouble();

		// Whether each object should have an arbitrary angle or one set by the user.
		System.out.print("Random angles (t/f)? "); 
		boolean r = false;
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
	
	/**
	 * Returns some basic information on the universe's definitions
	 * @return
	 */
	public static String uniInfo()
	{
		return "Gravity: " + uni.getG() + "\nLight Speed: " + ls + "\nSize: " + uni.getSize()[0] + ":" + uni.getSize()[0] + "\nPush Or Pull: " + pushOrPull
		+ "\nFramerate: " + framerate;
	}

	/**
	 * The top level menu, mostly options relating to data collection
	 * @return Escape Code
	 * @throws InterruptedException
	 */
	public static int mainMenu() throws InterruptedException
	{
		System.out.print("> [M]odify [V]iew [S]imulate [R]egenerate [I]nfo [F]ramerate [P]ush Or Pull [Q]uit | ");
		String answer = sc.next();
		switch (answer)
		{
			case "i":
			case "I":
				System.out.println(uniInfo());
				break;
			case "m":
			case "M":
				while (modMenu() == 0);
				break;
			case "v":
			case "V":
				System.out.println(uni);
				break;
			case "f":
			case "F":
				inputProtector("int", "Framerate? "); framerate = sc.nextInt();
				break;
			case "s":
			case "S":
				runSimulation();
				break;
			case "r":
			case "R":
				generateUniverse();
				break;
			case "p":
			case "P":
				if (pushOrPull == "push") pushOrPull = "pull";
				else pushOrPull = "push";
				break;
			case "q":
			case "Q":
				return 1;
		}
		return 0;
	}

	/**
	 * Second level menu, mostly involving large changes to the universe such as population
	 * @return
	 */
	public static int modMenu() throws InterruptedException
	{
		System.out.print("-> [P]opulate [G]ravity [M]odify singularities [E]rase [V]iew [R]eturn | ");
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
				inputProtector("double", "G: ");
				uni.setG(sc.nextDouble());
				break;
			case "E":
			case "e":
				uni.empty();
				break;
			case "s":
			case "S":
				runSimulation();
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

	/**
	 * The final level of menus, mostly relating to individual objects and their rules.
	 * @return
	 */
	private static int singularityMenu()
	{
		System.out.print("--> [A]ngle [S]peed [L]ightspeed [F]reeze [P]lace [R]eturn | ");
		String ans = sc.next();
		switch (ans)
		{
			case "a":
			case "A":
				inputProtector("double", "Angle: "); double angle = sc.nextDouble();
				uni.startingAngle(angle);
				break;
			case "s":
			case "S":
				inputProtector("double", "Speed: "); double speed = sc.nextDouble();
				uni.startingSpeed(speed);
				break;
			case "l":
			case "L":
				inputProtector("double", "Lightspeed: "); ls = sc.nextDouble();
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

	/**
	 * Interface for placing a single object on the universe
	 */
	private static void placeSingularity()
	{
		int y;
		int x;
		double mass;
		double angle;
		double speed;
		String symbol;
		inputProtector("int", "Y: "); y = sc.nextInt();
		inputProtector("int", "X: "); x = sc.nextInt();
		inputProtector("double", "Mass: "); mass = sc.nextDouble();
		inputProtector("double", "Speed: "); speed = sc.nextDouble();
		inputProtector("double", "Angle: "); angle = sc.nextDouble();
		System.out.print("Symbol: "); symbol = sc.next();
		uni.placeObject(y, x, new entity(mass, angle, speed, ls), symbol);
	}

	/**
	 * Pre-renders a collection of frames and displays them
	 * @param Uni The universe to generate frames for
	 * @param frames The number of frames to generate
	 * @throws InterruptedException
	 */
	private static void Collect(universe Uni, int frames, int skip, int framerate) throws InterruptedException
	{
		System.out.println("Universal ID: " + Uni.getID());

		if (frames <= 0) return;
		String[] film = new String[frames];
		System.out.println(Uni);

		// Checks if the starting universe and its next frame are the same. If so, continue looping and checking until a new frame is made.
		String u1 = Uni.toString();
		film[0] = u1;
		String u2 = Uni.update2(pushOrPull);
		int count = 0;
		if (skip > 0)
		{
			System.out.println("skipping until update... ");
			while (u1.equals(u2))
			{
				u2 = Uni.update2(pushOrPull);
				System.out.print(count + "-");
				count++;
				if (count > skip)
				{
					System.out.println(skip + " frames skipped. Failure to update.");
					return;
				}
			} System.out.println("\n" + count + " frames skipped.");
		}
		if (frames == 1) return;
		System.out.print("Generating film 1, 2, ");

		// Generates the frames for the video.
		film[1] = Uni.toString();
		film = generateFilm(film, 2, Uni);
		for(int i = 0; i < 20; i++) System.out.println();

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
			sleep(1000/framerate);
		}
	}

	/**
	 * Generates film for the Collect() method
	 * @param film The film to generate frames for
	 * @param index The location of the film to start generating frames from
	 * @param Universe The universe frames are being generated from
	 * @return The complete film
	 * @throws InterruptedException
	 */
	private static String[] generateFilm(String[] film, int index, universe Universe) throws InterruptedException
	{
		// Updates the universe and adds its string to the film.
		// If there is a time-out error, wait a short time and then continue.
			for (int i = index; i < film.length; i++)
			{
				index = i;
				System.out.print((1+index) + ", ");
				film[i] = Universe.update2(pushOrPull) + " " + (i+1) + "/" + film.length;
			}  System.out.println();
		return film;
	}
}
