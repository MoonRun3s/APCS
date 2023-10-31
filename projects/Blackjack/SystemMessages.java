import java.util.*;

public final class SystemMessages {
  private static ArrayList<String> messages = new ArrayList<String>();
  private SystemMessages() {}

  public static void addMessage(String msg) {
    messages.add(msg);
  }

  public static void displayMessages() {
    for (String s : messages) {System.out.println(s);}
    messages.clear();
  }
}
