import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize the default directory using the system property "user.dir"
        File currentDir = new File(System.getProperty("user.dir"));

        // Create a scanner to read input commands from the standard input stream
        Scanner scanner = new Scanner(System.in);

        // Continuous loop to read and process commands
        while (true) {
            // Print the prompt to indicate the shell is ready for input
            System.out.print("$ ");

            // Read the input command from the user
            String command = scanner.nextLine();

            // Handle the cd command to change the current working directory
            if (command.startsWith("cd ")) {
                // Extract the directory path from the command
                String directory = command.substring(3);

                // Create a new file object for the directory
                File newDir;

                // Check if the directory is a relative path
                if (directory.startsWith("./") || directory.startsWith("../")) {
                    newDir = new File(currentDir, directory).getAbsoluteFile();
                } else {
                    newDir = new File(directory);
                }

                // Check if the directory exists and is a directory
                if (newDir.exists() && newDir.isDirectory()) {
                    // Change the current working directory to the new directory
                    currentDir = newDir;
                } else {
                    // Print an error message if the directory does not exist
                    System.err.println("cd: " + directory + ": No such file or directory");
                }
            }

            // Print the absolute path of the current working directory
            System.out.println(currentDir.getAbsolutePath());
        }
    }
}
