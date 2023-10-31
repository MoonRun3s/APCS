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
  }

  Card drawCard() {
    CARDS_REMAINING = cardDeck.size()-1;
    return cardDeck.remove(0);
  }  
}
