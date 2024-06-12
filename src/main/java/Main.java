import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print the prompt
            System.out.print("$ ");
            // Read the input from the user
            String input = scanner.nextLine();

            // Check if the command is "exit 0"
            if (input.equals("exit 0")) {
                // Exit the program with status 0
                System.exit(0);
            } else if (input.startsWith("echo ")) {
                // Handle the echo command
                // Extract and print the text following "echo "
                String textToEcho = input.substring(5);
                System.out.println(textToEcho);
            } else {
                // Handle unrecognized commands
                System.out.println(input + ": command not found");
            }
        }
    }
}
