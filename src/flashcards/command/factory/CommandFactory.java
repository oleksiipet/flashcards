package flashcards.command.factory;

import flashcards.ActionsLogger;
import flashcards.Flashcards;
import flashcards.command.Command;
import java.util.Scanner;

public interface CommandFactory {

  Command getCommand(String action, Scanner scanner);

  static CommandFactory getDefault(Flashcards flashcards, ActionsLogger actionsLogger) {
    return new DefaultCommandFactory(flashcards,actionsLogger);
  }

}
