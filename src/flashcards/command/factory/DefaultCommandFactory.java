package flashcards.command.factory;

import flashcards.ActionsLogger;
import flashcards.Flashcards;
import flashcards.command.AddCard;
import flashcards.command.AskCard;
import flashcards.command.Command;
import flashcards.command.ExitCommand;
import flashcards.command.ExportCard;
import flashcards.command.HardestCard;
import flashcards.command.ImportCard;
import flashcards.command.RemoveCard;
import flashcards.command.ResetStat;
import flashcards.command.WriteLog;
import java.util.Scanner;

class DefaultCommandFactory implements CommandFactory {

  private Flashcards flashcards;
  private ActionsLogger logger;

  public DefaultCommandFactory(Flashcards flashcards, ActionsLogger actionsLogger) {
    this.flashcards = flashcards;
    logger = actionsLogger;
  }

  @Override
  public Command getCommand(String action, Scanner scanner) {
    logger.log(action);
    switch (action) {
      case "add":
        return new AddCard(flashcards, scanner);
      case "remove":
        return new RemoveCard(flashcards, scanner);
      case "import":
        return new ImportCard(flashcards, scanner, logger);
      case "export":
        return new ExportCard(flashcards, scanner, logger);
      case "ask":
        return new AskCard(flashcards, scanner, logger);
      case "exit":
        return new ExitCommand(flashcards);
      case "log":
        return new WriteLog(logger, scanner);
      case "hardest card":
        return new HardestCard(flashcards);
      case "reset stats":
        return new ResetStat(flashcards);
    }
    return null;
  }
}
