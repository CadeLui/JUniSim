import universe.universe;
import worldObjects.entity;

public class App {
    public static void main(String[] args) throws InterruptedException
    {
        startUI();
    }

    /**
	 * Starts the UserInterface loop
	 */
    public static void startUI() throws InterruptedException
    {
        UserInterface.generateUniverse();
        while (UserInterface.mainMenu() == 0);
    }

    /**
	 * Method that can be edited to test whatever is necessary.
	 */
    public static void manualTesting()
    {
        universe uni = new universe(10, 10, 0.001);
        entity s1 = new entity(1000, 0, 0, 3);
        entity s2 = new entity(1000, 0, 0, 3);
        uni.placeObject(0, 0, s1, "1");
        uni.placeObject(19, 19, s2, "2");
        System.out.println(uni.getAngleBetweenObjects(s1, s2));
    }
}
