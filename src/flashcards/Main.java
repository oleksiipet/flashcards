package flashcards;

import flashcards.command.Command;
import flashcards.command.factory.CommandFactory;
import java.util.Scanner;

public class Main {

  private Flashcards flashcards;
  private CommandFactory commandFactory;

  public Main(Flashcards flashcards, CommandFactory commandFactory) {
    this.flashcards = flashcards;
    this.commandFactory = commandFactory;
  }

  public void actionLoop(Scanner scanner) {
    while (!flashcards.isTerminated()) {

      System.out.println(
          "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");

      String action = scanner.nextLine();

      Command command = commandFactory.getCommand(action, scanner);
      command.execute();
    }
  }

  public static void main(String[] args) {
    ActionsLogger actionsLogger = new ActionsLogger();
    Flashcards flashcards = new Flashcards();
    Main main = new Main(flashcards, CommandFactory.getDefault(flashcards,actionsLogger));
    try (Scanner scanner = new Scanner(System.in)) {
      main.actionLoop(scanner);
    }
  }
}