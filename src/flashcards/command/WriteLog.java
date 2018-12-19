package flashcards.command;

import flashcards.ActionsLogger;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class WriteLog implements Command {

  private ActionsLogger log;
  private Scanner scanner;

  public WriteLog(ActionsLogger log, Scanner scanner) {
    this.log = log;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    log.log(fileName);
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
        PrintWriter print = new PrintWriter(writer)) {
      for (String entry : log.getLogs()) {
        print.printf("%s\n", entry);
      }
      log.clear();
    } catch (IOException e) {
      System.out.printf("The log has not been saved.\n\n");
      return;
    }
    System.out.printf("The log has been saved..\n\n");
  }
}
