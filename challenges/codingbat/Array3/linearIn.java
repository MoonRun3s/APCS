/*Given two arrays of ints sorted in increasing order, outer and inner, return true if all of the numbers in inner
appear in outer. The best solution makes only a single "linear" pass of both arrays, taking advantage of the fact
that both arrays are already in sorted order.


linearIn([1, 2, 4, 6], [2, 4]) → true
linearIn([1, 2, 4, 6], [2, 3, 4]) → false
linearIn([1, 2, 4, 4, 6], [2, 4]) → true*/

public boolean linearIn(int[] outer, int[] inner) {
  int pos = 1;
  
  if (inner.length == 0) {return true;} else {
    int prevNum = inner[0];
    for (int i = 1; i < inner.length; i++) {
      if (inner[i] != prevNum) {
        pos++;
        prevNum = inner[i];
      }
    }
  }
  
  int posCounts = 0;
  int[] countedNums = new int[inner.length];
  for (int i = 0; i < outer.length; i++) {
    for (int k = 0; k < inner.length; k++) {
      if (outer[i] == inner[k]) {
        posCounts++;
        for (int c = i; c < outer.length; c++) {
          if (outer[c] == inner[k]) {
            i = c;
          }
        }
        break;
      }
    }
  }
  
  if (posCounts >= pos) {
    return true;
  } else {
    return false;
  }
}
