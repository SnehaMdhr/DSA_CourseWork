/*
 * Question1
a)
You have a material with n temperature levels. You know that there exists a critical temperature f where
0 <= f <= n such that the material will react or change its properties at temperatures higher than f but
remain unchanged at or below f.
Rules:
 You can measure the material's properties at any temperature level once.
 If the material reacts or changes its properties, you can no longer use it for further measurements.
 If the material remains unchanged, you can reuse it for further measurements.
Goal:
Determine the minimum number of measurements required to find the critical temperature.
Input:
 k: The number of identical samples of the material.
 n: The number of temperature levels.
Output:
 The minimum number of measurements required to find the critical temperature.
Example 1:
Input: k = 1, n = 2
Output: 2
Explanation:
Check the material at temperature 1. If its property changes, we know that f = 0.
Otherwise, raise temperature to 2 and check if property changes. If its property changes, we know that f =
1.If its property changes at temperature, then we know f = 2.
Hence, we need at minimum 2 moves to determine with certainty what the value of f is.
Example 2:
Input: k = 2, n = 6
Output: 3
Example 3:
Input: k = 3, n = 14
Output: 4
 */
/*
 Problem: Find the minimum number of measurements (moves) to test n temperature levels using k samples.

Base Case:

If k == 1, return n (each level must be tested individually).

DP Table:

dp[i][j] = max temperature levels testable with i samples and j measurements.

Recurrence Relation:

dp[i][j] = 1 + dp[i-1][j-1] + dp[i][j-1]:

1: Current measurement.

dp[i-1][j-1]: Levels below threshold.

dp[i][j-1]: Levels above threshold.

Iterate:

Increment moves until dp[k][moves] >= n.

Result:

Return the smallest moves satisfying dp[k][moves] >= n.

Complexity:
Time: O(k * moves)

Space: O(k * n)
 */
public class Qno1A { // Define a public class named Qno1A

    // Method to find the minimum number of measurements required
    public static int minMeasurements(int k, int n) {
        // If we have only one sample (k = 1), we need to check every temperature one by one.
        if (k == 1) return n;

        // Create a DP table where dp[i][j] represents the maximum number of temperature
        // levels that can be tested with 'i' samples and 'j' measurements.
        int[][] dp = new int[k + 1][n + 1];

        // Variable to count the number of measurements needed
        int moves = 0;

        // Keep increasing the number of measurements until we can test at least 'n' levels
        while (dp[k][moves] < n) {
            moves++; // Increment the measurement count

            // Loop through each sample count from 1 to k
            for (int i = 1; i <= k; i++) {
                // DP recurrence relation:
                // The maximum number of temperature levels that can be tested is determined by:
                // - One additional test (the current measurement)
                // - The number of levels we can test with one fewer sample (dp[i - 1][moves - 1])
                // - The number of levels we can test with the same number of samples but one fewer measurement (dp[i][moves - 1])
                dp[i][moves] = 1 + dp[i - 1][moves - 1] + dp[i][moves - 1];
            }
        }

        // Return the minimum number of measurements required
        return moves;
    }

    // Main method to test the function with sample cases
    public static void main(String[] args) {
        System.out.println(minMeasurements(1, 2));  // Expected output: 2
        System.out.println(minMeasurements(2, 6));  // Expected output: 3
        System.out.println(minMeasurements(3, 14)); // Expected output: 4
    }
}


//Output
// 2
// 3
// 4

