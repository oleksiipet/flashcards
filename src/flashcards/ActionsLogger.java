package flashcards;

import java.util.ArrayList;
import java.util.List;

public class ActionsLogger {

  private List<String> logs;

  public ActionsLogger() {
    this.logs = new ArrayList<>();
  }

  public List<String> getLogs() {
    return logs;
  }

  public void log(String action) {
    logs.add(action);
  }

  public void clear() {
    logs.clear();
  }
}
