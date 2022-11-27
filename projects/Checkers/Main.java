import java.util.Scanner;

class Main {
  static Scanner keyboardScanner = new Scanner(System.in);
  static CheckerPiece[][] checkerBoardMatrix = new CheckerPiece[8][8];
  static boolean win = false;
  static int wPieces = 12, bPieces = 12;
  static CheckerPiece memory = null;
  static boolean lastTurnWasJump = false;

  public static String move(int selRow, int selCol, int tarRow, int tarCol, CheckerPiece selectedPiece, boolean turn) {
    checkerBoardMatrix[tarRow][tarCol] = checkerBoardMatrix[selRow][selCol];
    checkerBoardMatrix[selRow][selCol] = null;
    if (tarRow == 7 || tarRow == 0) {selectedPiece.setKing(true);}
    lastTurnWasJump = turn;
    return "Successfully moved to target position.";
  }
  
  public static String targetService(int selRow, int selCol, int tarRow, int tarCol) {
    CheckerPiece selectedPiece = checkerBoardMatrix[selRow][selCol];
    boolean isKing = selectedPiece.isKing();
    String team = selectedPiece.getTeam();
    
    if (Math.abs(tarRow - selRow) == 1 && Math.abs(tarCol - selCol) == 1 && !lastTurnWasJump) {
      if (checkerBoardMatrix[tarRow][tarCol] == null) {
        if (isKing) {
          return move(selRow, selCol, tarRow, tarCol, selectedPiece, false);
        } else if ((team == "B" && tarRow > selRow) || (team == "W" && tarRow < selRow)) {
          return move(selRow, selCol, tarRow, tarCol, selectedPiece, false);
        }
      }
    } else if (Math.abs(tarRow - selRow) == 2 && Math.abs(tarCol - selCol) == 2) {
        if (checkerBoardMatrix[tarRow][tarCol] == null) {
          int rowOffset, colOffset;
          if (isKing) {
            if (tarRow > selRow) {rowOffset = 1;} else {rowOffset = -1;}
            if (tarCol > selCol) {colOffset = 1;} else {colOffset = -1;}
            if (checkerBoardMatrix[selRow + rowOffset][selCol + colOffset] != null) {
              CheckerPiece pieceToDie = checkerBoardMatrix[selRow + rowOffset][selCol + colOffset];
              if (pieceToDie.getTeam() != team) {
                checkerBoardMatrix[selRow + rowOffset][selCol + colOffset] = null;
                if (team == "B") {wPieces-=1;} else {bPieces-=1;}
                return move(selRow, selCol, tarRow, tarCol, selectedPiece, true);
              }
            }
          } else if ((team == "B" && tarRow > selRow) || (team == "W" && tarRow < selRow)) {
            if (tarRow > selRow) {rowOffset = 1;} else {rowOffset = -1;}
            if (tarCol > selCol) {colOffset = 1;} else {colOffset = -1;}
            if (checkerBoardMatrix[selRow + rowOffset][selCol + colOffset] != null) {
              CheckerPiece pieceToDie = checkerBoardMatrix[selRow + rowOffset][selCol + colOffset];
              if (pieceToDie.getTeam() != team) {
                checkerBoardMatrix[selRow + rowOffset][selCol + colOffset] = null;
                if (team == "B") {wPieces-=1;} else {bPieces-=1;}
                return move(selRow, selCol, tarRow, tarCol, selectedPiece, true);
              }
            }
          }
        }
      }
    return "Failed to move to target position: target is illegal or single jumps only.";
  }

  public static String selection(CheckerPiece selPiece, int selRow, int selColumn, boolean override) {
    printBoard();
    System.out.println("Piece at {" + selColumn + ", " + selRow + "} selected in future references. Enter a target position with XY:");
    String targetSelection = keyboardScanner.nextLine();
    if (targetSelection.length() == 2) {
      int targetColumn = Integer.valueOf(targetSelection.charAt(0)) - 48;
      int targetRow = Integer.valueOf(targetSelection.charAt(1)) - 48;
      if (targetColumn <= 8 && targetRow <= 8 && targetColumn > 0 && targetRow > 0) {
        String result = targetService(selRow-1, selColumn-1, targetRow-1, targetColumn-1);
        if (result.substring(0, 1).equals("S")) {memory = checkerBoardMatrix[targetRow-1][targetColumn-1];}
        printBoard();
        return result;
      }
    }
    printBoard();
    if (override) {memory = null;}
    return "Invalid action.";
  }
  
  public static void printBoard() {
    System.out.print("\033[H\033[2J");
    System.out.println("C H E C K E R S\n===============================================\nY^\n");
    for (int row = 0; row < checkerBoardMatrix.length; row++) {
      System.out.print(row + 1 + "^|\t  ");
      for (int column = 0; column < checkerBoardMatrix[row].length; column++) {
        if (checkerBoardMatrix[row][column]!= null) {System.out.print(checkerBoardMatrix[row][column] + " ");} else {System.out.print(" ·  ");}
        if (column == 7) {System.out.println("\n");}
      }
    }
    System.out.println("    X [1 - 2- -3 - 4- -5 - 6- -7 - 8]\n");
  }

  public static String masterGame() {
      System.out.println("Choose a piece by entering the X and Y^ (ex. \"13\" for X=1 and Y=3):");
      String pieceSelection = keyboardScanner.nextLine();
      if (pieceSelection.length() == 2) {
        int selColumn = Integer.valueOf(pieceSelection.charAt(0)) - 48;
        int selRow = Integer.valueOf(pieceSelection.charAt(1)) - 48;
        if (selColumn <= 8 && selRow <= 8 && selColumn > 0 && selRow > 0) {
          if (checkerBoardMatrix[selRow-1][selColumn-1] != null) {
            CheckerPiece selPiece = checkerBoardMatrix[selRow-1][selColumn-1];
            if (memory == null) {
              memory = selPiece;
              return selection(selPiece, selRow, selColumn, true);
            } else if ((selPiece == memory && lastTurnWasJump) || (selPiece.getTeam() != memory.getTeam())) {
              if (selPiece.getTeam() != memory.getTeam()) {lastTurnWasJump = false;}
              return selection(selPiece, selRow, selColumn, false);
            }
          }
        }
      }
    printBoard();
    return "Invalid action.";
  }

  public static void main(String[] args) {
    String team = "B";
    for (int row = 0; row < 8; row++) {
      if (row == 3) {row = 5; team = "W";}
      for (int column = 0; column < 8; column++) {
        if ((column+row)%2 != 0) {
          checkerBoardMatrix[row][column] = new CheckerPiece(team);
        }
      }
    }

    printBoard();
    while (!win) {
      System.out.println(masterGame());
      if (wPieces == 0 || bPieces == 0) {win = true;}
    }
    printBoard();
    System.out.print("Game over, ");
    if (wPieces == 0) {System.out.println("black wins.");} else {System.out.println("white wins.");}
    keyboardScanner.close();
  }
}
