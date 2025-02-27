/*
 Problem: Find the closest pair of points based on Manhattan distance. If multiple pairs have the same distance, choose the lexicographically smallest pair.

Approach:

Iterate through all pairs of points (i, j) where i < j.

Calculate the Manhattan distance between each pair.

Track the pair with the smallest distance.

If distances are equal, choose the pair with the smallest lexicographical order.

Result:

Return the indices of the closest pair.
Time: O(n^2) (checking all pairs).

Space: O(1) (no additional space used).
 */
import java.util.*; // Import the Java utilities package for Arrays class

class QNo2B { // Define a class named QNo2B

    // Method to find the closest lexicographical pair of points based on Manhattan distance
    public static int[] closestLexicographicalPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Get the number of points
        int minDistance = Integer.MAX_VALUE; // Initialize the minimum distance to a very high value
        int[] result = new int[2]; // Array to store the indices of the closest pair

        // Iterate through all pairs (i, j) where i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate the Manhattan distance between points (i, j)
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Update if a smaller distance is found
                if (distance < minDistance) {
                    minDistance = distance; // Update the minimum distance
                    result[0] = i; // Store index i
                    result[1] = j; // Store index j
                } 
                // If the same distance is found, check lexicographical order
                else if (distance == minDistance) {
                    // Choose the lexicographically smaller pair
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }
        return result; // Return the indices of the closest lexicographical pair
    }

    public static void main(String[] args) {
        int[] x_coords = {1, 2, 3, 2, 4}; // X-coordinates of points
        int[] y_coords = {2, 3, 1, 2, 3}; // Y-coordinates of points

        int[] result = closestLexicographicalPair(x_coords, y_coords); // Find the closest pair
        System.out.println(Arrays.toString(result)); // Expected output: [0, 3]
    }
}

// Output
// [0,3]