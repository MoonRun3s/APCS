import java.util.*;

class Deck {
  ArrayList<Card> cardDeck = new ArrayList<Card>();
  public int CARDS_REMAINING;

  Deck() {
    for (int i = 1; i <= 13; i++) {
      cardDeck.add(new Card(i, "Hearts"));
      cardDeck.add(new Card(i, "Diamonds"));
      cardDeck.add(new Card(i, "Spades"));
      cardDeck.add(new Card(i, "Clubs"));
      Collections.shuffle(cardDeck);
      CARDS_REMAINING = cardDeck.size();
    }
    SystemMessages.addMessage("\u001b[35m[ DECK ]\u001b[0m Successful initialization.");
  }

  void returnCard(Card c) {
    c.isHidden(false);
    if (c.isAce()) {c.setHeldValue(11);}
    cardDeck.add(c);
    Collections.shuffle(cardDeck);
    SystemMessages.addMessage("\u001b[35m[ DECK ]\u001b[0m Returned " +c+ ".");
  }

  Card drawCard() {
    CARDS_REMAINING = cardDeck.size()-1;
    if (CARDS_REMAINING == 0) {
      SystemMessages.addMessage("\u001b[35m[ DECK ]\u001b[0m CAUTION! No indexOutOfBounds safeguard on ArrayList cardDeck.");
    }
    return cardDeck.remove(0);
  }  
}
