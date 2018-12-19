package flashcards.command;

import flashcards.ActionsLogger;
import flashcards.Flashcards;
import java.util.Scanner;

public class AskCard implements Command {

  private Flashcards flashcards;
  private Scanner scanner;
  private ActionsLogger logger;

  public AskCard(Flashcards flashcards, Scanner scanner, ActionsLogger logger) {
    this.flashcards = flashcards;
    this.scanner = scanner;
    this.logger = logger;
  }

  @Override
  public void execute() {
    System.out.println("How many times to ask?");
    String num = scanner.nextLine();
    logger.log(num);
    int n = Integer.parseInt(num);
    for (String card : flashcards.getCards().keySet()) {
      if (n <= 0) {
        break;
      }
      System.out.printf("Print the definition of \"%s\":\n", card);
      String guess = scanner.nextLine();
      logger.log(guess);
      if (flashcards.isGuessed(card, guess)) {
        System.out.printf("Correct answer.\n");
      } else if (flashcards.existsDefinition(guess)) {
        String correctDefinition = flashcards.getDefinition(card);
        String youMeanCard = flashcards.getCard(guess);
        System.out.printf(
            "Wrong answer (the correct one is \"%s\", you've just written a definition of \"%s\" card).\n",
            correctDefinition, youMeanCard);
        flashcards.incStats(card);
      } else {
        String correctCard = flashcards.getDefinition(card);
        flashcards.incStats(card);
        System.out
            .printf("Wrong answer (the correct one is \"%s\").\n", correctCard);
      }
      n--;
    }
    System.out.printf("\n");
  }
}
