package flashcards;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.printf("Input the number of cards:\n");

      int numCards = Integer.parseInt(scanner.nextLine());

      String[] cards = new String[numCards];
      String[] definitions = new String[numCards];

      for (int i = 0; i < numCards; i++) {
        System.out.printf("The card #%d:\n", i + 1);
        cards[i] = scanner.nextLine();
        System.out.printf("The definition of the card #%d:\n", i + 1);
        definitions[i] = scanner.nextLine();
      }

      for (int i = 0; i < numCards; i++) {
        System.out.printf("Print the definition of \"%s\":\n", cards[i]);
        String guess = scanner.nextLine();
        if (definitions[i].equals(guess)) {
          System.out.printf("Correct answer. ");
        } else {
          System.out.printf("Wrong answer (the correct one is \"%s\").", definitions[i]);
        }
      }
    }
  }
}