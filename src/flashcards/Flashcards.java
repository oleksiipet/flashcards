package flashcards;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Flashcards {

  private Map<String, String> cardToDefinition;
  private Map<String, String> definitionToCard;

  private Map<String, Integer> stats;

  private boolean terminated;

  public Flashcards() {
    this.cardToDefinition = new LinkedHashMap<>();
    this.definitionToCard = new LinkedHashMap<>();
    this.stats = new HashMap<>();
    this.terminated = false;
  }

  public void addCard(String card, String definition) {
    cardToDefinition.put(card, definition);
    definitionToCard.put(definition, card);
  }

  public void removeCard(String card) {
    String def = cardToDefinition.get(card);
    definitionToCard.remove(def);
    cardToDefinition.remove(card);
  }

  public boolean existsCard(String card) {
    return cardToDefinition.containsKey(card);
  }

  public boolean existsDefinition(String definition) {
    return definitionToCard.containsKey(definition);
  }

  public Map<String, Integer> getStats() {
    return stats;
  }

  public void resetStats() {
    stats.clear();
  }

  public boolean isTerminated() {
    return terminated;
  }

  public void setTerminated(boolean terminated) {
    this.terminated = terminated;
  }

  public Map<String, String> getCards() {
    return cardToDefinition;
  }

  public void incStats(String answer) {
    stats.merge(answer, 1, (x, y) -> x + y);
  }

  public boolean isGuessed(String card, String guess) {
    return definitionToCard.containsKey(guess) && cardToDefinition.get(card).equals(guess);
  }

  public String getDefinition(String card) {
    return cardToDefinition.get(card);
  }

  public String getCard(String definition) {
    return definitionToCard.get(definition);
  }
}
