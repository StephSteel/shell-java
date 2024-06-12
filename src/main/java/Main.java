import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print the prompt
            System.out.print("$ ");
            // Read the input from the user
            String input = scanner.nextLine();
            
            // For this stage, assume no command is recognized
            // Print the error message for the unrecognized command
            System.out.println(input + ": command not found");
        }
    }
}
