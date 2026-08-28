class Solution {
    public boolean isMatch(String s, String p) {
        int i = 0;  
        int j = 0;  
        int starIdx = -1;  
        int iIdx = -1;     

        while (i < s.length()) {
          
            if (j < p.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '?')) {
                i++;
                j++;
            }
          
            else if (j < p.length() && p.charAt(j) == '*') {
                starIdx = j;   
                iIdx = i;      
                j++;           
            }
        
            else if (starIdx != -1) {
                j = starIdx + 1;  
                iIdx++;           
                i = iIdx;      
            }
           
            else {
                return false;
            }
        }

        while (j < p.length() && p.charAt(j) == '*') {
            j++;
        }

       
        return j == p.length();
    }
}