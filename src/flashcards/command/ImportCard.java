package flashcards.command;

import flashcards.ActionsLogger;
import flashcards.Flashcards;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ImportCard implements Command {

  private Flashcards flashcards;
  private Scanner scanner;
  private ActionsLogger logger;

  public ImportCard(Flashcards flashcards, Scanner scanner, ActionsLogger logger) {
    this.flashcards = flashcards;
    this.scanner = scanner;
    this.logger = logger;
  }

  @Override
  public void execute() {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    logger.log(fileName);
    int count = 0;
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
      String card;
      while ((card = reader.readLine()) != null) {
        String def = reader.readLine();
        if (def == null) {
          break;
        }
        flashcards.addCard(card, def);
        count++;
      }
    } catch (IOException e) {
      System.out.printf("0 cards have been saved.\n\n");
      return;
    }
    System.out.printf("%d cards have been saved.\n\n", count);
  }
}
