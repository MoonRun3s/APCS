import java.util.*;

class Main {
  static Scanner keyboardScanner = new Scanner(System.in);
  
  static Deck deck = new Deck();
  static ArrayList<Card> dealerHand = new ArrayList<Card>();
  static ArrayList<Card> playerHand = new ArrayList<Card>();
  
  static int playerBalance = 200;
  static int playerWins = 0, rounds = 0, gameBet=10;

  public static int autoCalculateDeckWorth(ArrayList<Card> array) {
    int sum = 0;
    for (Card i : array) {sum+=i.heldValue;}
    if (sum > 21) {
      //check for aces and if present set heldValue to 1
      for (Card i : array) {
        if (i.isAce()) {i.setHeldValue(1);}
      }
      sum = 0;
      for (Card i : array) {sum+=i.heldValue;} // final run, even if over 21 we know its impossible to decrement any further
    }
    return sum;
  }

  public static void printStats(boolean clearPrevious) {
    if (clearPrevious) {System.out.print("\033[H\033[2J");}
    System.out.println("DECK SZ: " +deck.CARDS_REMAINING+ " | TN: " +rounds);
    System.out.println("\nDEALER ===========");
    for (Card i : dealerHand) {System.out.println(i);}
    System.out.println("\n\u001b[47;1m\u001b[30mPLAYER =========== BALANCE: $" +playerBalance+ "/ WINS: " +playerWins+ "\u001b[0m");
    for (Card i : playerHand) {System.out.println(i);}
    System.out.print("\n");
    SystemMessages.displayMessages();
    System.out.print("\n");
  }

  public static void printSplit(boolean clearPrevious, ArrayList<Card> handToPrint) {
    if (clearPrevious) {System.out.print("\033[H\033[2J");}
    System.out.println("DECK SZ: " +deck.CARDS_REMAINING+ " | TN:" +rounds);
    System.out.println("\nDEALER ===========");
    for (Card i : dealerHand) {System.out.println(i);}
    System.out.println("\n\u001b[47;1m\u001b[30mPLAYER =========== BALANCE: $" +playerBalance+ "/ WINS: " +playerWins+ "\u001b[0m");
    System.out.println("\u001b[43;1m ACTIVE ROOT: " +handToPrint.get(0)+ "\u001b[0m");
    for (Card i : handToPrint) {System.out.println("\t" +i);}
    System.out.print("\n");
    SystemMessages.displayMessages();
    System.out.print("\n");
  }

  public static String takeTurn() {
    System.out.println("Enter HIT, SPLIT, DOUBLE or STAY:");
    String command = keyboardScanner.nextLine();
    while (!command.toLowerCase().equals("hit") && !command.toLowerCase().equals("split") && !command.toLowerCase().equals("stay") && !command.toLowerCase().equals("double")) {
      SystemMessages.addMessage("\u001b[31m[ MAIN : takeTurn() ]\u001b[0m Invalid entry.");
      printStats(true);
      command = takeTurn();
    }

    if (command.toLowerCase().equals("double") && (autoCalculateDeckWorth(playerHand) != 11)) {
      SystemMessages.addMessage("\u001b[31m[ MAIN : takeTurn() ]\u001b[0m Not available.");
      printStats(true);
      command = takeTurn();
    }

    if (command.toLowerCase().equals("split")) {
      if (playerHand.size() == 2 && playerHand.get(0).equals(playerHand.get(1))) {
        return command.toLowerCase();
      } else {
        SystemMessages.addMessage("\u001b[31m[ MAIN : takeTurn() ]\u001b[0m Not available.");
        printStats(true);
        command = takeTurn();
      }
    }
    
    return command.toLowerCase();
  }

  public static void dealerTurn(boolean compute) {
    while (autoCalculateDeckWorth(dealerHand) < 17) {
      dealerHand.add(deck.drawCard());
    }

    if (compute) {
      if (autoCalculateDeckWorth(playerHand) > autoCalculateDeckWorth(dealerHand) || autoCalculateDeckWorth(dealerHand) > 21) {
        playerWins++;
        playerBalance+=gameBet;
      } else if (autoCalculateDeckWorth(playerHand) != autoCalculateDeckWorth(dealerHand)) {playerBalance-=gameBet;}
      dealerHand.get(1).isHidden(false);
    }
  }
  
  public static boolean runGame(ArrayList<Card> handToPlay, boolean runDealer, boolean computeScore) {
    if (autoCalculateDeckWorth(handToPlay) > 21) {playerBalance-=gameBet; SystemMessages.addMessage("\u001b[31m[ MAIN : runGame() ]\u001b[0m TURN STOPPED: Deck over 21."); return false;}
    String command = takeTurn();
    if (command.equals("stay")) {
      if (runDealer) {dealerTurn(computeScore);}
      if (handToPlay != playerHand) {printSplit(true, handToPlay);} else {printStats(true);}
    } else if (command.equals("hit")) {
      handToPlay.add(deck.drawCard());
      if (handToPlay != playerHand) {printSplit(true, handToPlay);} else {printStats(true);}
      runGame(handToPlay, runDealer, computeScore);
    } else if (command.equals("double")) {
      gameBet = 20;
      handToPlay.add(deck.drawCard());
      if (runDealer) {dealerTurn(computeScore);}
      if (handToPlay != playerHand) {printSplit(true, handToPlay);} else {printStats(true);}
    } else if (command.equals("split")) {
      ArrayList<Card> split1 = new ArrayList<Card>();
      split1.add(playerHand.remove(0));
      split1.add(deck.drawCard());
      ArrayList<Card> split2 = new ArrayList<Card>();
      split2.add(playerHand.remove(0));
      split2.add(deck.drawCard());

      printSplit(true, split1);
      runGame(split1, false, false);
      printSplit(true, split2);
      runGame(split2, false, false);
      dealerTurn(false);

      if (autoCalculateDeckWorth(dealerHand) > 21) {
        SystemMessages.addMessage("\u001b[31m[ MAIN : runGame() > split ]\u001b[0m Dealer lost.");
        
        if (autoCalculateDeckWorth(split1) <= 21) {
          playerWins++; 
          playerBalance+=gameBet; 
          SystemMessages.addMessage("\u001b[31m[ MAIN : runGame() > split ]\u001b[0m Root 1 count as win.");
        } else {playerBalance-=gameBet;}
        
        if (autoCalculateDeckWorth(split2) <= 21) {
          playerWins++;
          playerBalance+=gameBet;
          SystemMessages.addMessage("\u001b[31m[ MAIN : runGame() > split ]\u001b[0m Root 2 count as win.");
        } else {playerBalance-=gameBet;}
      } else {
        if (autoCalculateDeckWorth(split1) > autoCalculateDeckWorth(dealerHand) && autoCalculateDeckWorth(split1) <= 21) {
          SystemMessages.addMessage("\u001b[31m[ MAIN : runGame() > split ]\u001b[0m Root 1 won against dealer.");
          playerWins++; playerBalance+=gameBet;
        } else {playerBalance-=gameBet;}
        if (autoCalculateDeckWorth(split2) > autoCalculateDeckWorth(dealerHand) && autoCalculateDeckWorth(split2) <= 21) {
          SystemMessages.addMessage("\u001b[31m[ MAIN : runGame() > split ]\u001b[0m Root 2 won against dealer.");
          playerWins++; playerBalance+=gameBet;
        } else {playerBalance-=gameBet;}
      }
      playerHand.add(split1.remove(0));
      playerHand.add(split2.remove(0));
      printStats(true);
    }
    return false;
  }
  
  public static void main(String[] args) {
    while (playerBalance >= 10) {
      rounds++;
      
      dealerHand.add(deck.drawCard());
      dealerHand.add(deck.drawCard());
      dealerHand.get(1).isHidden(true);
  
      playerHand.add(deck.drawCard());
      playerHand.add(deck.drawCard());
  
      printStats(true);
      runGame(playerHand, true, true);
      SystemMessages.displayMessages();
      System.out.print("\n");
      System.out.println("Press [ENTER] to start next round.");

      gameBet=10;
      String discard = keyboardScanner.nextLine();
      for (Card c : dealerHand) {deck.returnCard(c);}
      dealerHand.clear();
      for (Card c : playerHand) {deck.returnCard(c);}
      playerHand.clear();
    }

    SystemMessages.addMessage("\u001b[31m[ MAIN ]\u001b[0m You are out of money.");
    printStats(true);
    SystemMessages.displayMessages();
    System.out.println("WIN %: " +((double)playerWins/rounds)+ " | ROUNDS: " +rounds+ " | BUY-IN: $" +gameBet);
  }
}
