class Solution {
    private static final int[][] EXP = new int[10][4]; // digit -> [e2,e3,e5,e7]
    static {
        EXP[1] = new int[]{0,0,0,0};
        EXP[2] = new int[]{1,0,0,0};
        EXP[3] = new int[]{0,1,0,0};
        EXP[4] = new int[]{2,0,0,0};
        EXP[5] = new int[]{0,0,1,0};
        EXP[6] = new int[]{1,1,0,0};
        EXP[7] = new int[]{0,0,0,1};
        EXP[8] = new int[]{3,0,0,0};
        EXP[9] = new int[]{0,2,0,0};
    }

    public String smallestNumber(String num, long t) {
        int n = num.length();
        long a = 0, b = 0, c = 0, d = 0;
        long tt = t;
        while (tt % 2 == 0) { tt /= 2; a++; }
        while (tt % 3 == 0) { tt /= 3; b++; }
        while (tt % 5 == 0) { tt /= 5; c++; }
        while (tt % 7 == 0) { tt /= 7; d++; }
        if (tt != 1) return "-1";

        // direct check: num itself zero-free and satisfies
        if (num.indexOf('0') < 0) {
            long A = a, B = b, C = c, D = d;
            for (int i = 0; i < n; i++) {
                int digit = num.charAt(i) - '0';
                int[] e = EXP[digit];
                A -= e[0]; B -= e[1]; C -= e[2]; D -= e[3];
            }
            if (A <= 0 && B <= 0 && C <= 0 && D <= 0) return num;
        }

        int z = n;
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') { z = i; break; }
        }
        int maxI = (z < n) ? z : n - 1;

        long[] prefA = new long[maxI + 2];
        long[] prefB = new long[maxI + 2];
        long[] prefC = new long[maxI + 2];
        long[] prefD = new long[maxI + 2];
        for (int i = 0; i <= maxI; i++) {
            int digit = num.charAt(i) - '0';
            int[] e = (digit >= 1 && digit <= 9) ? EXP[digit] : new int[]{0,0,0,0};
            prefA[i+1] = prefA[i] + e[0];
            prefB[i+1] = prefB[i] + e[1];
            prefC[i+1] = prefC[i] + e[2];
            prefD[i+1] = prefD[i] + e[3];
        }

        String answer = null;
        for (int i = maxI; i >= 0; i--) {
            long pA = prefA[i], pB = prefB[i], pC = prefC[i], pD = prefD[i];
            int start = (num.charAt(i) == '0') ? 1 : (num.charAt(i) - '0') + 1;
            int m = n - 1 - i;
            int foundCh = -1;
            long fA = 0, fB = 0, fC = 0, fD = 0;
            for (int ch = Math.max(start, 1); ch <= 9; ch++) {
                int[] e = EXP[ch];
                long totA = a - (pA + e[0]);
                long totB = b - (pB + e[1]);
                long totC = c - (pC + e[2]);
                long totD = d - (pD + e[3]);
                if (feasible(m, totA, totB, totC, totD)) {
                    foundCh = ch;
                    fA = Math.max(totA, 0);
                    fB = Math.max(totB, 0);
                    fC = Math.max(totC, 0);
                    fD = Math.max(totD, 0);
                    break;
                }
            }
            if (foundCh != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(num, 0, i);
                sb.append((char) ('0' + foundCh));
                sb.append(buildSuffix(m, fA, fB, fC, fD));
                answer = sb.toString();
                break;
            }
        }

        if (answer != null) return answer;

        long minSlots = c + d + minDigitsAB(a, b);
        int L = (int) Math.max(n + 1, minSlots);
        return buildSuffix(L, a, b, c, d);
    }

    private long ceilDiv(long x, long y) {
        return x > 0 ? (x + y - 1) / y : 0;
    }

    
    private long minDigitsAB(long A, long B) {
        A = Math.max(A, 0);
        B = Math.max(B, 0);
        if (A == 0 && B == 0) return 0;
        long maxX = Math.min(A, B);
        long best = Long.MAX_VALUE;
        for (long x = 0; x <= maxX; x++) {
            long remA = A - x;
            long remB = B - x;
            long y = ceilDiv(Math.max(remA, 0), 3); // digit 8
            long zC = ceilDiv(Math.max(remB, 0), 2); // digit 9
            long total = x + y + zC;
            if (total < best) best = total;
        }
        return best;
    }

    private boolean feasible(int m, long A, long B, long C, long D) {
        A = Math.max(A, 0); B = Math.max(B, 0);
        C = Math.max(C, 0); D = Math.max(D, 0);
        if (C + D > m) return false;
        long slots = m - (C + D);
        return minDigitsAB(A, B) <= slots;
    }

    private String buildSuffix(int m, long A, long B, long C, long D) {
        StringBuilder res = new StringBuilder();
        for (int pos = 0; pos < m; pos++) {
            int rem = m - pos - 1;
            for (int ch = 1; ch <= 9; ch++) {
                int[] e = EXP[ch];
                long nA = A - e[0], nB = B - e[1], nC = C - e[2], nD = D - e[3];
                if (feasible(rem, nA, nB, nC, nD)) {
                    res.append((char) ('0' + ch));
                    A = Math.max(nA, 0);
                    B = Math.max(nB, 0);
                    C = Math.max(nC, 0);
                    D = Math.max(nD, 0);
                    break;
                }
            }
        }
        return res.toString();
    }
}