import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print the prompt
            System.out.print("$ ");
            // Read the input from the user
            String input = scanner.nextLine();
            // Split the input into command and arguments
            String[] tokens = input.split(" ");

            // Check if the command is "exit 0"
            if (input.equals("exit 0")) {
                // Exit the program with status 0
                System.exit(0);
            } else if (tokens[0].equals("echo")) {
                // Handle the echo command
                String textToEcho = input.substring(5);
                System.out.println(textToEcho);
            } else if (tokens[0].equals("type")) {
                // Handle the type command
                String command = tokens[1];
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
                // Try to execute the command as an external program
                String pathEnv = System.getenv("PATH");
                boolean found = false;

                if (pathEnv != null) {
                    String[] paths = pathEnv.split(":");
                    for (String path : paths) {
                        File file = new File(path, tokens[0]);
                        if (file.exists() && file.canExecute()) {
                            // Execute the external command with arguments
                            try {
                                ProcessBuilder processBuilder = new ProcessBuilder(tokens);
                                processBuilder.directory(new File(path));
                                Process process = processBuilder.start();

                                // Capture and print the output
                                Scanner processOutputScanner = new Scanner(process.getInputStream());
                                while (processOutputScanner.hasNextLine()) {
                                    System.out.println(processOutputScanner.nextLine());
                                }
                                process.waitFor();
                                found = true;
                                break;
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // If the command is not found in the PATH
                if (!found) {
                    System.out.println(tokens[0] + ": command not found");
                }
            }
        }
    }
}
