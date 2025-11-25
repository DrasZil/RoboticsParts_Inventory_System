import java.util.Scanner;

public class ConsoleUtils {
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Mac / Linux
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear screen.");
        }
    }

    public static void pause(Scanner input) {
        System.out.println("Press Enter to continue...");
        input.nextLine();
    }
}

