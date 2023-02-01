import java.util.HashMap;

class Mine {
    private boolean mine, flagged, dug;
    private int surroundingMineCount;
    HashMap<Integer, String> numerics = new HashMap<>();

    public Mine(boolean newMineValue) {
      mine = newMineValue;
      numerics.put(0, "🟦");
      numerics.put(1, "1️⃣ ");
      numerics.put(2, "2️⃣ ");
      numerics.put(3, "3️⃣ ");
      numerics.put(4, "4️⃣ ");
      numerics.put(5, "5️⃣ ");
      numerics.put(6, "6️⃣ ");
      numerics.put(7, "7️⃣ ");
      numerics.put(8, "8️⃣ ");
    }

    void setSurroundingValue(int newSurroundingValue) {
        surroundingMineCount = newSurroundingValue;
    }

    void toggleFlag() {
        flagged = !flagged;
    }

    void dig() {
        if (!flagged) {
            dug = true;
        }
    }

    boolean isMine() {return mine;}
    boolean isFlagged() {return flagged;}
    boolean isDug() {return dug;}
    int surroundingMineValue() {return surroundingMineCount;}

    public String toString() {
        if (dug) {
            if (!mine) {
                return numerics.get(surroundingMineCount);
            } else {
                return "💣";
            }
        } else {
            if (flagged) {
                return "🚩";
            } else {
                return "⬛";
            }
        }
    }
}
