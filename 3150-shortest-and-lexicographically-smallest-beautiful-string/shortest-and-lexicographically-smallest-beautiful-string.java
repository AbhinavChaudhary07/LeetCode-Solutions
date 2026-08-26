class Solution {

    public String shortestBeautifulSubstring(String s, int k) {

        int n = s.length();

        String ans = "";
        String subString = "";

        int len = Integer.MAX_VALUE;
        int count1 = 0;

        for (int i = 0; i < n; i++) {

            subString += String.valueOf(s.charAt(i));

            count1 = s.charAt(i) == '1' ? (count1 + 1) : count1;

            if (count1 == k) {

                while (subString.length() > 1 &&
                       subString.charAt(0) == '0') {

                    subString = subString.substring(1);
                }

                if (subString.length() < len ||
                    (subString.length() == len &&
                     (ans.equals("") ||
                      subString.compareTo(ans) < 0))) {

                    ans = subString;
                    len = subString.length();
                }

                if (subString.charAt(0) == '1')
                    count1--;

                subString = subString.substring(1);
            }
        }

        return ans;
    }
}