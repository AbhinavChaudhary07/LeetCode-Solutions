class Solution { //solution through binary search
    public int searchInsert(int[] nums, int target) {
        int l , m = 0 , n , mid = 0 ;
        l = nums.length;
        n = l-1;
        while ( m <= n )
        {
            mid = ( m + n ) / 2;
            if (nums[mid] == target)
            return mid;
            else if (nums[mid] < target)
            m = mid + 1;
            else 
            n = mid - 1 ;
        }
        return m;
    }
}