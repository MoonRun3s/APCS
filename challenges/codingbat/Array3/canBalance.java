/*Given a non-empty array, return true if there is a place to split the array so that the sum of the numbers on one 
side is equal to the sum of the numbers on the other side.


canBalance([1, 1, 1, 2, 1]) → true
canBalance([2, 1, 1, 2, 1]) → false
canBalance([10, 10]) → true*/

public boolean canBalance(int[] nums) {
  for (int i = 0; i < nums.length; i++) {
    int sumL = 0;
    int sumR = 0;
    
    for (int k = 0; k < i; k++) {
      sumL+=nums[k];
    }
    
    for (int k = i; k < nums.length; k++) {
      sumR+=nums[k];
    }
    
    if (sumL == sumR) {
      return true;
    }
  }
  
  return false;
}
