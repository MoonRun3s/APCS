class Wordle {
  private String correctWord;
  private String[] guesses = new String[6];
  static int guessesRemaining = 6;
  
  Wordle() {
    String[] words = {"idiot", "loser", "crown", "weird", "agent", "award", "brain", "block", "chief", "class", "depth", "draft", "entry", "event", "fresh", "frame", "grant", "guide", "hotel", "heart", "index", "issue", "jelly", "judge", "knife", "known", "limit", "leech", "meets", "major", "night", "never", "oscar", "ovals", "price", "plant", "quite", "quail", "raise", "rally", "shine", "sting", "trope", "train", "under", "upper", "veins", "viola", "write", "wings", "xenon", "xylan", "young", "yield", "zebra", "zesty", "beach", "teach", "block", "craft", "sword", "spear", "knife", "armor", "light", "saber", "fight", "death", "frank", "black", "white", "green", "bling", "games", "eagle", "robin", "super", "birth", "llama", "horse", "salty", "spicy", "sweet", "royal", "never", "stone", "blade", "reach", "sleep", "plane", "baked", "fried", "roast", "serve", "cream", "water", "ouija", "beast", "human", "horse", "sheep", "sweat","blast", "grunt", "butch", "dutch", "shell", "vader", "count", "break", "sorry", "carry", "slain", "means", "round", "maybe", "vocal", "swear", "shows", "jesus", "three", "saint", "japan", "korea", "china", "joker", "mourn", "sense", "words", "flesh", "fault", "smart", "watch", "movie", "comet", "brace", "align", "thing", "sloth", "crypt",};
    correctWord = words[(int)(Math.random() * (words.length-1))];
  }
  
  String getWord() {
    return correctWord;
  }
  
  void printResult(boolean win) {
    if (win) {
      System.out.print("\033[H\033[2J");
      System.out.println("W O R D L E\t\t\t\t\t\t\t[Game Over]\n===============================================\n");
      System.out.println("You guessed \"" + correctWord + "\" with " + guessesRemaining + " guess(es) left.");
      System.exit(130);
    } else {
      System.out.print("\033[H\033[2J");
      System.out.println("W O R D L E\t\t\t\t[" + guessesRemaining + " guess(es) remaining]\n===============================================\n");
      for (String i : guesses) {
        if (i != null) {
          System.out.println(i);
        }
      }
    }
  }
  
  void checkWord(String guessedWord) {System.out.println("bob");
    int[] correctWordASCIIcount = new int[26];
    if (guessedWord.equals(correctWord)) {
      printResult(true);
    } else { 
      for (int i = 0; i < 5; i++) {correctWordASCIIcount[correctWord.charAt(i)-97]+=1;}
      System.out.println("a");      
      char[] correctWordArray = correctWord.toCharArray();
      char[] guessedWordArray = guessedWord.toCharArray();
      String output="_____";
      char[] arrayOutput=output.toCharArray();

      for (int i = 0; i < 5; i++) {
        if (guessedWordArray[i] == correctWordArray[i]) {
          arrayOutput[i] = correctWordArray[i];
          correctWordASCIIcount[correctWordArray[i]-97] -= 1;
        }
      }
System.out.println("b");
    for(int z=0; z<26;z++)
        System.out.println(correctWordASCIIcount[z]);
        
      for (int i = 0; i < 5; i++) {
        for (int k = 0; k < 5; k++) {
          if (guessedWordArray[i] == correctWordArray[k] && arrayOutput[i] == '_') {
            if (correctWordASCIIcount[correctWordArray[k]-97] > 0) {
             arrayOutput[i] = '*';
              correctWordASCIIcount[correctWordArray[k]-97] -= 1;
            }
          }
        }
      }

      for(int i=0; i<5; i++){
      if(correctWordArray[i]==guessedWordArray[i]){
        arrayOutput[i]=correctWordArray[i];
      }
    }

      String returnedValue = String.valueOf(arrayOutput);
      guesses[6 - guessesRemaining] = (returnedValue);
      guessesRemaining-=1;
      printResult(false);
    }
  }
}
