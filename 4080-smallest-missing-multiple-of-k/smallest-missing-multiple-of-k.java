class Solution {
    public int missingMultiple(int[] nums, int k) {
        HashMap<Integer,Integer>seen = new HashMap<>();
        for(int val : nums){
            seen.put(val,seen.getOrDefault(val,0)+1);
        }
        Arrays.sort(nums);
        int n = nums.length;
        int m = nums[n-1]+k;
        for(int i=k;i<=m;i++){
            if(i%k==0){
                if(!seen.containsKey(i)){
                    return i;
                }
            }
        }
        return -1;
    }
}