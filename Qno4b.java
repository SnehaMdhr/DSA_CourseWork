/* Question no 4
 * b)
You have a map of a city represented by a graph with n nodes (representing locations) and edges where
edges[i] = [ai, bi] indicates a road between locations ai and bi. Each location has a value of either 0 or 1,
indicating whether there is a package to be delivered. You can start at any location and perform the
following actions:
Collect packages from all locations within a distance of 2 from your current location.
Move to an adjacent location.
Your goal is to collect all packages and return to your starting location.
Goal:
Determine the minimum number of roads you need to traverse to collect all packages.
Input:
packages: An array of package values for each location.
roads: A 2D array representing the connections between locations.
Output:
The minimum number of roads to traverse.
Note that if you pass a roads several times, you need to count it into the answer several times.
Input: packages = [1, 0, 0, 0, 0, 1], roads = [[0, 1], [1, 2], [2, 3], [3, 4], [4, 5]]
Output:2
Explanation: Start at location 2, collect the packages at location 0, move to location 3, collect the
packages at location 5 then move back to location 2.
Input: packages = [0,0,0,1,1,0,0,1], roads = [[0,1],[0,2],[1,3],[1,4],[2,5],[5,6],[5,7]]
Output: 2
Explanation: Start at location 0, collect the package at lo
 */import java.util.*;

// This class implements a solution to determine the minimum number of roads needed to traverse 
// in a network of cities where packages are located. The traversal counts roads to deliver packages 
// and return back if necessary.
public class Qno4b {

    // Function to find the minimum number of roads to traverse
    public static int minRoadsToTraverse(int[] packages, int[][] roads) {
        int n = packages.length; // Number of nodes (cities)
        List<List<Integer>> graph = new ArrayList<>(); // Create a graph as an adjacency list

        // Initialize the graph with empty lists for each node
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Add the roads (edges) to the graph
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]); // Road between city road[0] and road[1]
            graph.get(road[1]).add(road[0]); // Road between city road[1] and road[0] (undirected)
        }

        boolean[] visited = new boolean[n]; // Array to track visited cities
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS traversal
        int roadsCount = 0; // Variable to count the roads to traverse

        // Start BFS from city 0 (first city)
        queue.offer(0); // Add the starting city to the queue
        visited[0] = true; // Mark the starting city as visited

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            int currentNode = queue.poll(); // Get the current city from the queue

            // Traverse all neighboring cities of the current city
            for (int neighbor : graph.get(currentNode)) {
                if (!visited[neighbor]) { // If the neighbor city hasn't been visited yet
                    visited[neighbor] = true; // Mark the neighbor city as visited
                    if (packages[neighbor] == 1) { // If the city has a package to deliver
                        roadsCount += 2; // Add 2 roads for both delivering and returning
                    }
                    queue.offer(neighbor); // Add the neighbor city to the queue
                }
            }
        }

        // Return the total number of roads to traverse
        return roadsCount;
    }

    // Main method to test the function
    public static void main(String[] args) {
        // Test case 1
        int[] packages1 = { 1, 0, 0, 0, 0, 1 }; // Package locations in cities
        int[][] roads1 = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } }; // Roads between cities
        System.out.println("Output: " + minRoadsToTraverse(packages1, roads1)); // Expected output: 2
    }
}


//Output
//2