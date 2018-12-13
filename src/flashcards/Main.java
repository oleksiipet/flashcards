package flashcards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {

  private static Map<String, String> cardToDefinition = new LinkedHashMap<>();
  private static Map<String, String> definitionToCard = new LinkedHashMap<>();

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.println("Input the action (add, remove, import, export, ask, exit):");

        String action = scanner.nextLine();

        switch (action) {
          case "add":
            addCard(scanner);
            break;
          case "remove":
            removeCard(scanner);
            break;
          case "import":
            importCard(scanner);
            break;
          case "export":
            exportCard(scanner);
            break;
          case "ask":
            askCard(scanner);
            break;
          case "exit":
            System.out.println("Bye bye!");
            return;
        }
      }
    }
  }

  private static void askCard(Scanner scanner) {
    System.out.println("How many times to ask?");
    int n = Integer.parseInt(scanner.nextLine());
    for (String card : cardToDefinition.keySet()) {
      if (n <= 0) {
        break;
      }
      System.out.printf("Print the definition of \"%s\":\n", card);
      String guess = scanner.nextLine();
      if (definitionToCard.containsKey(guess) && cardToDefinition.get(card).equals(guess)) {
        System.out.printf("Correct answer.\n");
      } else if (definitionToCard.containsKey(guess)) {
        System.out.printf(
            "Wrong answer (the correct one is \"%s\", you've just written a definition of \"%s\" card).\n",
            cardToDefinition.get(card), definitionToCard.get(guess));
      } else {
        System.out
            .printf("Wrong answer (the correct one is \"%s\").\n", cardToDefinition.get(card));
      }
      n--;
    }
    System.out.printf("\n");
  }

  private static void exportCard(Scanner scanner) {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
        PrintWriter print = new PrintWriter(writer)) {
      for (Entry<String, String> entry : cardToDefinition.entrySet()) {
        print.printf("%s\n%s\n", entry.getKey(), entry.getValue());
      }
    } catch (IOException e) {
      System.out.printf("0 cards have been saved.\n\n");
      return;
    }
    System.out.printf("%d cards have been saved.\n\n", cardToDefinition.size());
  }

  private static void importCard(Scanner scanner) {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    int count = 0;
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
      String card;
      while ((card = reader.readLine()) != null) {
        String def = reader.readLine();
        if (def == null) {
          break;
        }
        cardToDefinition.put(card, def);
        definitionToCard.put(def, card);
        count++;
      }
    } catch (IOException e) {
      System.out.printf("0 cards have been saved.\n\n");
      return;
    }
    System.out.printf("%d cards have been saved.\n\n", count);
  }

  private static void removeCard(Scanner scanner) {
    System.out.println("The card:");
    String card = scanner.nextLine();
    if (cardToDefinition.containsKey(card)) {
      String def = cardToDefinition.get(card);
      definitionToCard.remove(def);
      cardToDefinition.remove(card);
      System.out.println("The card successfully removed\n\n");
    } else {
      System.out.printf("Can't remove \"%s\": there is no such card.\n\n", card);
    }
  }

  private static void addCard(Scanner scanner) {
    System.out.println("The card:");
    String card = scanner.nextLine();
    System.out.println("The definition of the card:");
    String definition = scanner.nextLine();
    cardToDefinition.put(card, definition);
    definitionToCard.put(definition, card);
    System.out.printf("The pair (\"%s\":\"%s\") is added.\n\n", card, definition);
  }
}