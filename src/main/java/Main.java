import java.io.File;
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
                String textToEcho = input.substring(5);
                System.out.println(textToEcho);
            } else if (input.startsWith("type ")) {
                // Handle the type command
                String command = input.substring(5);

                // Check if the command is a built-in command
                if (command.equals("echo") || command.equals("exit") || command.equals("type")) {
                    System.out.println(command + " is a shell builtin");
                } else {
                    // Check if the command is an executable in the PATH
                    String pathEnv = System.getenv("PATH");
                    boolean found = false;
                    
                    if (pathEnv != null) {
                        String[] paths = pathEnv.split(":");
                        for (String path : paths) {
                            File file = new File(path, command);
                            if (file.exists() && file.canExecute()) {
                                System.out.println(command + " is " + file.getAbsolutePath());
                                found = true;
                                break;
                            }
                        }
                    }

                    // If the command is not found in the PATH
                    if (!found) {
                        System.out.println(command + ": not found");
                    }
                }
            } else {
                // Handle unrecognized commands
                System.out.println(input + ": command not found");
            }
        }
    }
}
