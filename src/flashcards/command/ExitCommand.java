package flashcards.command;

import flashcards.Flashcards;

public class ExitCommand implements Command {

  private Flashcards flashcards;

  public ExitCommand(Flashcards flashcards) {
    this.flashcards = flashcards;
  }

  @Override
  public void execute() {
    System.out.println("Bye bye!");
    flashcards.setTerminated(true);
  }
}
