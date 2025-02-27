import java.util.*;

public class Qno4b {

    public static int minRoadsToTraverse(int[] packages, int[][] roads) {
        int n = packages.length;
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        int roadsCount = 0;
        
        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            for (int neighbor : graph.get(currentNode)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    if (packages[neighbor] == 1) {
                        roadsCount += 2;  // Add 2 to account for the return trip
                    }
                    queue.offer(neighbor);
                }
            }
        }

        return roadsCount;
    }

    public static void main(String[] args) {
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println("Example 1 Output: " + minRoadsToTraverse(packages1, roads1)); // Expected output: 2

        int[] packages2 = {0, 0, 1, 1, 0, 1};
        int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {4, 5}};
        System.out.println("Example 2 Output: " + minRoadsToTraverse(packages2, roads2)); // Expected output: 2
    }
}
