class Card {
  String suit;
  int kind, heldValue;
  boolean hidden;

  Card(int kindInit, String suitInit) {
    kind = kindInit;
    suit = suitInit;
    hidden = false;
    if (kind == 1) {SystemMessages.addMessage("\u001b[34m[ CARD ]\u001b[0m Ace kind 1 (" +suit+ ") assigned a heldValue of 11."); heldValue = 11;}
    else if (kind > 1 && kind <= 10) {heldValue = kind;}
    else {kind = 10; heldValue = 10;}
  }

  int kind() {return kind;}

  int heldValue() {
    if (heldValue != 0) {return heldValue;}
    else {
      SystemMessages.addMessage("\u001b[34m[ CARD ]\u001b[0m CAUTION! heldValue not assigned (kind " +kind+ " suit " +suit+ "). Will return int 0.");
      return 0;
    }
  }

  boolean isAce() {
    if (kind == 1) {return true;}
    return false;
  }

  boolean equals(Card comparison) {
    if (comparison.heldValue() == this.heldValue() && comparison.kind() == this.kind()) {return true;}
    return false;
  }

  void setHeldValue(int newHeldValue) {
    if (kind == 1) {heldValue = newHeldValue;}
    else {SystemMessages.addMessage("\u001b[34m[ CARD ]\u001b[0m Attmpted to assign new heldValue to non-Ace kind.");}
  }

  void isHidden(boolean bool) {
    hidden = bool;
    SystemMessages.addMessage("\u001b[34m[ CARD ]\u001b[0m Hidden status of kind " +kind+ " suit " +suit+ " set " +hidden+ ".");
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

    if (hidden) {return "**HIDDEN**";}
    else {return printableKind + " of " + suit;}
  }
}
