import universe.universe;
import worldObjects.entity;

public class App {
    public static void main(String[] args) throws InterruptedException
    {
        startUI();
    }

    // Use this to activate the UI
    public static void startUI() throws InterruptedException
    {
        UserInterface.generateUniverse();
        while (UserInterface.mainMenu() == 0);
    }

    // Modify this method to test specific methods
    public static void manualTesting()
    {
        universe uni = new universe(20, 20, 0.001);
        entity s1 = new entity(1000, 0, 0, 3);
        entity s2 = new entity(1000, 0, 0, 3);
        uni.placeObject(0, 0, s1, "1");
        uni.placeObject(19, 19, s2, "2");
        System.out.println(uni.getAngleBetweenObjects(s1, s2));
    }
}
