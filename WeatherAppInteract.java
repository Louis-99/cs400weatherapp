// --== CS400 File Header Information ==--
// Name: Yunzhao Liu
// Email: liu995@wisc.edu
// Team: JB
// Role: Front End Developer
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: N/A
import java.io.IOException;
import java.util.Scanner;

/**
 * entrance of interactive mode
 * 
 * @author Yunzhao Liu
 */

public class WeatherAppInteract {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=> WeatherApp interactive mode\n=> type \"--help\" for help\n=> type \"exit\" to exit");
        System.out.print("> ");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("exit")) {
                break;
            }
            if (line.equals("")) {
                WeatherApp.main(new String[0]);
            } else {
                WeatherApp.main(line.split(" "));
            }
            System.out.print("> ");
        }
        scanner.close();
    }
}
