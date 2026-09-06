import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {

                int x = -(nums[i] + nums[j]);

                if (Arrays.binarySearch(nums, j + 1, n, x) >= 0) {
                    set.add(Arrays.asList(nums[i], nums[j], x));
                }
            }
        }

        return new ArrayList<>(set);
    }
}