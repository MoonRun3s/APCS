class Card {
  String suit;
  int kind;

  Card(int kindInit, String suitInit) {
    kind = kindInit;
    suit = suitInit;
  }

  int value() {return kind;}

  boolean isFaceCard() {
    if (kind > 10) {return true;}
    return false;
  }

  public String toString() {
    String printableKind = "";
    switch (kind) {
      case 1:
        printableKind = "Ace";
        break;
      case 11:
        printableKind = "Jack";
        break;
      case 12:
        printableKind = "Queen";
        break;
      case 13:
        printableKind = "King";
        break;
      default:
        printableKind = String.valueOf(kind);
    }
    return printableKind + " of " + suit;
  }
}
