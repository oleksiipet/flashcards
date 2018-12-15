package flashcards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {

  private static final String IMPORT_OPT = "-import";
  private static final String EXPORT_OPT = "-export";

  private static Map<String, String> cardToDefinition = new LinkedHashMap<>();
  private static Map<String, String> definitionToCard = new LinkedHashMap<>();
  private static List<String> logs = new ArrayList<>();
  private static Map<String, Integer> stats = new HashMap<>();

  private static Path importPath = null;
  private static Path exportPath = null;

  public static void main(String[] args) {
    if (args.length > 0) {
      String[][] optPairs = new String[args.length / 2][2];
      int i = 0, j = 0;
      while (i < args.length - 1) {
        optPairs[j][0] = args[i];
        optPairs[j][1] = args[i + 1];
        i += 2;
        j++;
      }
      assignOpts(optPairs);
    }
    if (importPath != null) {
      importCard(importPath);
    }
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.println(
            "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");

        String action = scanner.nextLine();

        logs.add(action);

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
            if (exportPath != null) {
              exportCard(exportPath);
            }
            System.out.println("Bye bye!");
            return;
          case "log":
            log(scanner);
            break;
          case "hardest card":
            hardestCard();
            break;
          case "reset stats":
            resetStats();
            break;
        }
      }
    }
  }

  private static void assignOpts(String[][] optPairs) {
    for (String[] opt : optPairs) {
      switch (opt[0]) {
        case IMPORT_OPT:
          importPath = Paths.get(opt[1]);
          break;
        case EXPORT_OPT:
          exportPath = Paths.get(opt[1]);
          break;
      }
    }
  }

  private static void resetStats() {
    stats.clear();
    System.out.printf("\n");
  }

  private static void hardestCard() {
    Iterator<Entry<String, Integer>> iterator = stats.entrySet().iterator();
    Entry<String, Integer> maxErrors = iterator.hasNext() ? iterator.next() : null;
    if (maxErrors == null) {
      System.out.printf("No stats\n\n");
      return;
    }
    while (iterator.hasNext()) {
      Entry<String, Integer> cur = iterator.next();
      if (cur.getValue() > maxErrors.getValue()) {
        maxErrors = cur;
      }
    }
    System.out.printf("The hardest card is \"%s\". You have %d errors answering it.\n\n",
        maxErrors.getKey(), maxErrors.getValue());
  }

  private static void log(Scanner scanner) {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    logs.add(fileName);
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
        PrintWriter print = new PrintWriter(writer)) {
      for (String entry : logs) {
        print.printf("%s\n", entry);
      }
      logs.clear();
    } catch (IOException e) {
      System.out.printf("The log has not been saved.\n\n");
      return;
    }
    System.out.printf("The log has been saved..\n\n");
  }

  private static void askCard(Scanner scanner) {
    System.out.println("How many times to ask?");
    String num = scanner.nextLine();
    logs.add(num);
    int n = Integer.parseInt(num);
    for (String card : cardToDefinition.keySet()) {
      if (n <= 0) {
        break;
      }
      System.out.printf("Print the definition of \"%s\":\n", card);
      String guess = scanner.nextLine();
      logs.add(guess);
      if (definitionToCard.containsKey(guess) && cardToDefinition.get(card).equals(guess)) {
        System.out.printf("Correct answer.\n");
      } else if (definitionToCard.containsKey(guess)) {
        System.out.printf(
            "Wrong answer (the correct one is \"%s\", you've just written a definition of \"%s\" card).\n",
            cardToDefinition.get(card), definitionToCard.get(guess));
        stats.merge(card, 1, (x, y) -> x + y);
      } else {
        System.out
            .printf("Wrong answer (the correct one is \"%s\").\n", cardToDefinition.get(card));
        stats.merge(card, 1, (x, y) -> x + y);
      }
      n--;
    }
    System.out.printf("\n");
  }

  private static void exportCard(Scanner scanner) {
    System.out.println("File name:");
    String fileName = scanner.nextLine();
    logs.add(fileName);
    exportCard(Paths.get(fileName));
  }

  private static void exportCard(Path fileName) {
    try (BufferedWriter writer = Files.newBufferedWriter(fileName);
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
    logs.add(fileName);
    importCard(Paths.get(fileName));
  }

  private static void importCard(Path fileName) {
    int count = 0;
    try (BufferedReader reader = Files.newBufferedReader(fileName)) {
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
      System.out.printf("0 cards have been read.\n\n");
      return;
    }
    System.out.printf("%d cards have been read.\n\n", count);
  }

  private static void removeCard(Scanner scanner) {
    System.out.println("The card:");
    String card = scanner.nextLine();
    logs.add(card);
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
    logs.add(card);
    System.out.println("The definition of the card:");
    String definition = scanner.nextLine();
    logs.add(definition);
    cardToDefinition.put(card, definition);
    definitionToCard.put(definition, card);
    System.out.printf("The pair (\"%s\":\"%s\") is added.\n\n", card, definition);
  }
}