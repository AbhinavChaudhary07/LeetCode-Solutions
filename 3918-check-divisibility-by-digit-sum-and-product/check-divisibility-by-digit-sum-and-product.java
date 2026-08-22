class Solution {
    public boolean checkDivisibility(int n) {
        int num = n, sum=0, prod=1;

        while(n!=0)
        {
            int dig = n%10;
             sum+= dig;
             prod *= dig;
             n/=10;
        }
        if(num % (sum+prod)==0) return true;

        return false;
    }
}