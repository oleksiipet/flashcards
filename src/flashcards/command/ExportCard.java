package flashcards.command;

import flashcards.ActionsLogger;
import flashcards.Flashcards;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Scanner;

public class ExportCard implements Command {

  private Flashcards flashcards;
  private Scanner scanner;
  private ActionsLogger logger;

  public ExportCard(Flashcards flashcards, Scanner scanner, ActionsLogger logger) {
    this.flashcards = flashcards;
    this.scanner = scanner;
    this.logger = logger;
  }

  @Override
  public void execute() {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    logger.log(fileName);
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
        PrintWriter print = new PrintWriter(writer)) {
      for (Entry<String, String> entry : flashcards.getCards().entrySet()) {
        print.printf("%s\n%s\n", entry.getKey(), entry.getValue());
      }
    } catch (IOException e) {
      System.out.printf("0 cards have been saved.\n\n");
      return;
    }
    System.out.printf("%d cards have been saved.\n\n", flashcards.getCards().size());
  }
}
