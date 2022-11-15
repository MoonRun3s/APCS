/*Given n>=0, create an array length n*n with the following pattern, 
shown here for n=3 : {0, 0, 1,    0, 2, 1,    3, 2, 1} (spaces added to show the 3 groups).


squareUp(3) → [0, 0, 1, 0, 2, 1, 3, 2, 1]
squareUp(2) → [0, 1, 2, 1]
squareUp(4) → [0, 0, 0, 1, 0, 0, 2, 1, 0, 3, 2, 1, 4, 3, 2, 1]*/

public int[] squareUp(int n) {
  int[] rt = new int[n*n];
  
  int counter = 1;
  int cycle = n;
  for (int i = 0; i < rt.length; i++) {
    if (cycle > counter) {
      rt[i] = 0;
    } else {
      rt[i] = cycle;
    }
    cycle--;
    if (cycle == 0) {counter++; cycle = n;}
  }
  
  return rt;
}
