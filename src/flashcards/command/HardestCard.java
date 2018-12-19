package flashcards.command;

import flashcards.Flashcards;
import java.util.Iterator;
import java.util.Map.Entry;

public class HardestCard implements Command {

  private Flashcards flashcards;

  public HardestCard(Flashcards flashcards) {
    this.flashcards = flashcards;
  }

  @Override
  public void execute() {
    Iterator<Entry<String, Integer>> iterator = flashcards.getStats().entrySet().iterator();
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
}
