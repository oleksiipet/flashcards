package flashcards;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.printf("Input the number of cards:\n");

      int numCards = Integer.parseInt(scanner.nextLine());

      Map<String, String> cardToDefinition = new LinkedHashMap<>();
      Map<String, String> definitionToCard = new LinkedHashMap<>();

      for (int i = 0; i < numCards; i++) {
        System.out.printf("The card #%d:\n", i + 1);
        String card = scanner.nextLine();
        System.out.printf("The definition of the card #%d:\n", i + 1);
        String definition = scanner.nextLine();
        cardToDefinition.put(card, definition);
        definitionToCard.put(definition, card);
      }

      for (String card : cardToDefinition.keySet()) {
        System.out.printf("Print the definition of \"%s\":\n", card);
        String guess = scanner.nextLine();
        if (definitionToCard.containsKey(guess) && cardToDefinition.get(card).equals(guess)) {
          System.out.printf("Correct answer. ");
        } else if (definitionToCard.containsKey(guess)) {
          System.out.printf(
              "Wrong answer (the correct one is \"%s\", you've just written a definition of \"%s\" card). ",
              cardToDefinition.get(card), definitionToCard.get(guess));
        } else {
          System.out
              .printf("Wrong answer (the correct one is \"%s\").", cardToDefinition.get(card));
        }
      }
    }
  }
}