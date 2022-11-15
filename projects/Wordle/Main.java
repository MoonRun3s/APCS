// group: j** rich***, layla *****, vincent ***, alan ********

import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Wordle wordle = new Wordle();
    Scanner keyboardScanner = new Scanner(System.in);

    wordle.printResult(false);
    while (wordle.guessesRemaining > 0) {
      String guessedWord = keyboardScanner.next();
      guessedWord = guessedWord.toLowerCase();
      if (guessedWord.length() == 5) {
        wordle.checkWord(guessedWord); 
      } else {
        wordle.printResult(false);
        System.out.println("Not a valid guess, try again.");
      }
    }
    System.out.println("Game over, the word was \"" + wordle.getWord() + ".\"");
    System.exit(130);
  }
}
