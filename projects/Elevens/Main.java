import java.util.*;

class Main {
  static Scanner keyboardScanner = new Scanner(System.in);
  static Deck gameDeck = new Deck();
  static ArrayList<Card> currentHand = new ArrayList<Card>(); 
  static int SETS_MADE = 0, CARDS_REMAINING;
  static boolean gameOver = false;
  
  public static void printDisplay() {
    CARDS_REMAINING = gameDeck.CARDS_REMAINING;
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("SETS MADE: " + SETS_MADE + "/24");
    System.out.println("CARDS IN PLAY: " + (CARDS_REMAINING + currentHand.size()) + "\n\n");
    System.out.println("IN YOUR HAND:");
    
    int i = 1;
    for (Card c : currentHand) {
      System.out.println(i + ") " + c);
      i++;
    }
  }

  public static boolean faceCardPotential() {
    boolean JACK = false, QUEEN = false, KING = false;
    for (Card c : currentHand) {
      if (c.isFaceCard()) {
        switch (c.value()) {
          case 11:
            JACK = true;
            break;
          case 12:
            QUEEN = true;
            break;
          case 13:
            KING = true;
            break;
        }
      }
    }
    if (JACK && QUEEN && KING) {return true;}
    return false;
  }

  public static boolean scanForWinningPotential() {
    if (faceCardPotential()) {return true;}
    for (Card c : currentHand) {
      for (Card cComparison : currentHand) {
        if (c != cComparison) {
          if (c.value() + cComparison.value() == 11) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static boolean isValidIndex(int index) {
    if (index >= 0 && index < currentHand.size()) {return true;}
    return false;
  }

  public static void gameAlertMessage(String message, boolean invalid) {
    if (invalid) {
      printDisplay();
      System.out.println("\n\n\u001b[41m >>> SYSTEM: Illegal play. <<< \u001b[0m");
    } else {
      printDisplay();
      System.out.println("\n\n\u001b[43m >>> SYSTEM: " + message + " <<< \u001b[0m");
    }
  }

  public static boolean game() {
    System.out.println("\n\nSelect cards in hand...");
    System.out.print("No. 1: ");
    int firstCardIndex = keyboardScanner.nextInt() - 1;
    if (isValidIndex(firstCardIndex)) {
      Card firstCard = currentHand.get(firstCardIndex);
      if (firstCard.isFaceCard()) {
        if (faceCardPotential()) {
          System.out.print("\u001b[46m > COMPLETE FACE CARD SET < \u001b[0m\nNo. 2: ");
          int secondCardIndex = keyboardScanner.nextInt() - 1;
          if (isValidIndex(secondCardIndex) && (secondCardIndex != firstCardIndex)) {
            Card secondCard = currentHand.get(secondCardIndex);
            if (secondCard.isFaceCard()) {
              System.out.print("No. 3: ");
              int thirdCardIndex = keyboardScanner.nextInt() - 1;
              if (isValidIndex(thirdCardIndex) && (thirdCardIndex != secondCardIndex) && (thirdCardIndex != firstCardIndex)) {
                Card thirdCard = currentHand.get(thirdCardIndex);
                if (thirdCard.isFaceCard()) {
                  if ((thirdCard.value() != secondCard.value()) && (secondCard.value() != firstCard.value()) && (thirdCard.value() != firstCard.value())) {
                    currentHand.remove(firstCard);
                    currentHand.remove(secondCard);
                    currentHand.remove(thirdCard);
                    SETS_MADE++;
                    for (int i = 0; i < 3; i++) {
                      if (CARDS_REMAINING > 0) {
                        currentHand.add(gameDeck.drawCard());
                        CARDS_REMAINING = gameDeck.CARDS_REMAINING;
                      }
                    }
                    printDisplay();
                    return true;
                  }
                }
              }
            }
          }
        } else {
          gameAlertMessage("Card is unplayable.", false);
          keyboardScanner.nextLine();
          return true;
        }
      } else {
        System.out.print("No. 2: ");
        int secondCardIndex = keyboardScanner.nextInt() - 1;
        if (isValidIndex(secondCardIndex) && (secondCardIndex != firstCardIndex)) {
          Card secondCard = currentHand.get(secondCardIndex);
          if (firstCard.value() + secondCard.value() == 11) {
            currentHand.remove(firstCard);
            currentHand.remove(secondCard);
            SETS_MADE++;
            for (int i = 0; i < 2; i++) {
              if (CARDS_REMAINING > 0) {
                currentHand.add(gameDeck.drawCard());
                CARDS_REMAINING = gameDeck.CARDS_REMAINING;
              }
            }
            printDisplay();
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public static void main(String[] args) {
    for (int i = 0; i < 11; i++) {
      currentHand.add(gameDeck.drawCard());
    }
    
    printDisplay();
    boolean fail = false;
    while (!gameOver) {
      if (!game()) {
        gameAlertMessage("", true);
        keyboardScanner.nextLine();
      }
      if (currentHand.size() < 2 || !scanForWinningPotential()) {gameOver = true; fail = true;}
      if (currentHand.size() == 0) {fail = false;}
    }
    printDisplay();
    if (fail) {
      System.out.println("\n\n\u001b[43m >>> SYSTEM: No playable cards. <<< \u001b[0m");
      System.out.println("\n\nGame over.");
    } else {
      System.out.println("\n\n🎉 YOU WIN! 🎉");
    }
  }
}
