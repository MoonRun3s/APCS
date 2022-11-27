class CheckerPiece {
  private boolean isKing;
  private String TEAM;

  public CheckerPiece(String inTeam) {
    TEAM = inTeam;
    isKing = false;
  }
  
  String getTeam() {return TEAM;}
  boolean isKing() {return isKing;}

  void setKing(boolean inKing) {isKing = inKing;}
  
  public String toString() {
    if (TEAM.equals("B")) {
      if (isKing) {return " ♔ ";} else {return " ⛀ ";}
    } else {
      if (isKing) {return " ♚ ";} else {return " ⛂ ";}
    }
  }
}
