package com.urielsalis.codecrafters.shell;
import com.urielsalis.codecrafters.shell.commands.Command;
import com.urielsalis.codecrafters.shell.commands.EchoCommand;
import com.urielsalis.codecrafters.shell.commands.ExitCommand;
import com.urielsalis.codecrafters.shell.commands.ParsedCommand;
import com.urielsalis.codecrafters.shell.commands.TypeCommand;
import java.io.File;
import java.util.Optional;
import java.util.Scanner;
public class Shell {
  private final Command[] commands = new Command[] {
      new ExitCommand(), new EchoCommand(), new TypeCommand(this)};
  private final Scanner scanner = new Scanner(System.in);
  private String prompt;
  private File currentDirectory = new File("");
  public Shell(String prompt) { this.prompt = prompt; }
  public String read() { return scanner.nextLine(); }
  public void eval(String input) {
    final ParsedCommand parsedCommand = ParsedCommand.from(input);
    for (Command command : commands) {
      if (command.matches(parsedCommand.commandName())) {
        command.execute(parsedCommand.args());
        return;
      }
    }
    final Optional<String> resolvedFullPath =
        resolveCommandFullPath(parsedCommand.commandName());
    resolvedFullPath.ifPresentOrElse(
        fullPath
        -> executeCommand(fullPath, parsedCommand.args()),
        ()
            -> System.out.println(parsedCommand.commandName() +
                                  ": command not found"));
  }
  private void executeCommand(String fullPath, String args) {
    try {
      final Process process =
          new ProcessBuilder(fullPath, args).inheritIO().start();
      process.waitFor();
    } catch (Exception e) {
      System.out.println("Error executing command: " + e.getMessage());
    }
  }
  public void print() { System.out.print(prompt); }
  public Command[] getBuiltIns() { return commands; }
  public Optional<String> resolveCommandFullPath(String args) {
    final String[] paths = System.getenv("PATH").split(":");
    File file = new File(args);
    File file = new File(currentDirectory, args);
    if (file.exists()) {
      return Optional.of(file.getAbsolutePath());
    }
    for (String path : paths) {
      file = new File(path + "/" + args);
      if (file.exists()) {
        return Optional.of(file.getAbsolutePath());
      }
    }
    return Optional.empty();
  }
  public File getCurrentDirectory() { return currentDirectory; }