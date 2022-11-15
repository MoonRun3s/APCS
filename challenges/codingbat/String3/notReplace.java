/*Given a string, return a string where every appearance of the lowercase word "is" has been replaced
with "is not". The word "is" should not be immediately preceeded or followed by a letter -- so for example 
the "is" in "this" does not count. (Note: Character.isLetter(char) tests if a char is a letter.)


notReplace("is test") → "is not test"
notReplace("is-is") → "is not-is not"
notReplace("This is right") → "This is not right"*/

public String notReplace(String str) {
  for (int i = 0; i < str.length()-1; i++) {
    String scanned = str.substring(i, i+2);
    if (scanned.equals("is")) {
      boolean frontLetter = false, rearLetter = false;
      if (i>0) {frontLetter = Character.isLetter(str.charAt(i-1));}
      if (i < str.length()-2) {rearLetter = Character.isLetter(str.charAt(i+2));}
      if (frontLetter || rearLetter) {
        i+=2;
      } else {
        str = str.substring(0, i) + "is not" + str.substring(i+2);
      }
    }
  }
  
  return str;
}
