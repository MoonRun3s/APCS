/*Given a string, return the longest substring that appears at both the beginning and end of the string without overlapping. For example, sameEnds("abXab") is "ab".


sameEnds("abXYab") → "ab"
sameEnds("xx") → "x"
sameEnds("xxx") → "x"*/

public String sameEnds(String string) {
  if (string.length()<2) {
    return "";
  }
  
  int scanLength = 1;
  String matching = "";
  for (int i = 1; i < string.length()/2+1; i++) {
    String front = string.substring(0, i);
    String rear = string.substring(string.length()-scanLength);
    if (front.equals(rear)) {
      matching = front;
    }
    scanLength++;
  }
  
  return matching;
}
