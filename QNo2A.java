/*
 Short Explanation:
Problem: Distribute candies to employees based on ratings, ensuring:

Each employee gets at least 1 candy.

Employees with higher ratings than neighbors get more candies.

Approach:

Use two passes:

Left-to-Right: Give more candies if an employee has a higher rating than the left neighbor.

Right-to-Left: Give more candies if an employee has a higher rating than the right neighbor.

Result:

Sum the candies from both passes to get the minimum total candies required
Complexity:
Time: O(n)

Space: O(n)
 */
public class QNo2A { // Define a public class named QNo2A

    // Method to determine the minimum number of candies needed for employees based on ratings
    public int employee(int[] ratings) {
        int n = ratings.length; // Get the number of employees
        if (n == 0) return 0; // If no employees, return 0 candies

        // Step 1: Initialize an array where each employee gets at least 1 candy
        int[] employees = new int[n];
        for (int i = 0; i < n; i++) {
            employees[i] = 1;  // Every employee gets 1 candy initially
        }

        // Step 2: Left-to-right pass
        // Ensure employees with higher ratings than their left neighbors get more candies
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                employees[i] = employees[i - 1] + 1; // Give one more candy than the left neighbor
            }
        }

        // Step 3: Right-to-left pass
        // Ensure employees with higher ratings than their right neighbors get more candies
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                // Take the maximum value to satisfy both left-to-right and right-to-left conditions
                employees[i] = Math.max(employees[i], employees[i + 1] + 1);
            }
        }

        // Step 4: Calculate the total number of candies required
        int rewards = 0;
        for (int reward : employees) {
            rewards += reward;  // Sum up all the candies given to employees
        }

        return rewards; // Return the total number of candies needed
    }

    public static void main(String[] args) {
        QNo2A sol = new QNo2A(); // Create an instance of QNo2A

        // Example 1: Employees with ratings [1, 0, 2]
        int[] ratings1 = {1, 0, 2};
        System.out.println(sol.employee(ratings1)); // Expected output: 5

        // Example 2: Employees with ratings [1, 2, 2]
        int[] ratings2 = {1, 2, 2};
        System.out.println(sol.employee(ratings2)); // Expected output: 4
    }
}

// Output
// 5
// 4
