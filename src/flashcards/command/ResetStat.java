package flashcards.command;

import flashcards.Flashcards;

public class ResetStat implements Command {

  private Flashcards flashcards;

  public ResetStat(Flashcards flashcards) {
    this.flashcards = flashcards;
  }

  @Override
  public void execute() {
    flashcards.resetStats();
    System.out.printf("\n");
  }
}
