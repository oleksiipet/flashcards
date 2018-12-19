package flashcards.command;

import flashcards.Flashcards;
import java.util.Scanner;

public class RemoveCard implements Command {

  private Flashcards flashcards;
  private Scanner scanner;

  public RemoveCard(Flashcards flashcards, Scanner scanner) {
    this.flashcards = flashcards;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("The card:");
    String card = scanner.nextLine();
    if (flashcards.existsCard(card)) {
      flashcards.removeCard(card);
      System.out.println("The card successfully removed\n\n");
    } else {
      System.out.printf("Can't remove \"%s\": there is no such card.\n\n", card);
    }
  }
}
