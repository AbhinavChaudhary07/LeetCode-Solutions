class Solution {
    public long findKthSmallest(int[] coins, int k) {
        Arrays.sort(coins);
        List<Integer> newCoins = new ArrayList<>();
        for (int x : coins) {
            boolean flag = true;
            for (int y : newCoins) if (x % y == 0) { flag = false; break; }
            if (flag) newCoins.add(x);
        }
        coins = newCoins.stream().mapToInt(i -> i).toArray();
        int n = coins.length, m = 1 << n;
        int[] bitCount = new int[m];
        long[] lcm = new long[m];
        long left = k, right = (long) coins[0] * k + 1;
        for (int mask = 1; mask < m; mask++) bitCount[mask] = bitCount[mask >> 1] + (mask & 1);
        lcm[0] = 1;
        for (int mask = 1; mask < m; mask++) {
            int preMask = mask & (mask - 1), i = Integer.numberOfTrailingZeros(mask);
            long tmp = lcm[preMask] / gcd(lcm[preMask], coins[i]);
            if (tmp <= right / coins[i]) lcm[mask] = tmp * coins[i];
            else lcm[mask] = right + 1;
        }
        while (left < right) {
            long x = left + (right - left) / 2;
            if (count(x, m, lcm, bitCount) >= k) right = x;
            else left = x + 1;
        }
        return left;
    }
    private long count(long x, int m, long[] lcm, int[] bitCount) {
        long result = 0;
        for (int mask = 1; mask < m; mask++) {
            if (lcm[mask] > x) continue;
            if ((bitCount[mask] & 1) == 1) result += x / lcm[mask];
            else result -= x / lcm[mask];
        }
        return result;
    }
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}