package flashcards.command;

import flashcards.Flashcards;
import java.util.Scanner;

public class AddCard implements Command {

  private Flashcards flashcards;
  private Scanner scanner;

  public AddCard(Flashcards flashcards, Scanner scanner) {
    this.flashcards = flashcards;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("The card:");
    String card = scanner.nextLine();

    System.out.println("The definition of the card:");
    String definition = scanner.nextLine();

    flashcards.addCard(card, definition);

    System.out.printf("The pair (\"%s\":\"%s\") is added.\n\n", card, definition);
  }
}
