import numpy as np

# unsorted int array
# return longest increasing subarray length
# duplicate element is not increasing. only a < b makes a,b an increasing array.

def lis(a):
    print("Target: {}".format(a))
    if len(a) == 0:
        return 0;
    dp = np.ones(len(a), dtype=np.int)
    res = 1
    for i in range(1, len(a)):
        for j in range(0, i):
            if a[j] < a[i]:
                dp[i] = max(dp[i], dp[j] + 1)
        res = max(res, dp[i])
    print("memo: {}".format(dp))
    return res

a = [10, 9, 2, 5, 3, 7, 101, 18]
b = [2, 3, 4, 4]

# a = [10, 9, 2, 5, 3, 7, 101, 18]
# dp =  1, 1, 1, 1, 1, 1, 1  , 1]
#          

# [2, 3, 7, 101]

print("Result: {}".format(lis(a)))
print("Result: {}".format(lis(b)))
