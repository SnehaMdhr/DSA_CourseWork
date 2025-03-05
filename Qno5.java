/*
 * Question 5
Optimizing a Network with Multiple Objectives
Problem:
Suppose you are hired as software developer for certain organization and you are tasked with creating a
GUI application that helps network administrators design a network topology that is both cost-effective
and efficient for data transmission. The application needs to visually represent servers and clients as
nodes in a graph, with potential network connections between them, each having associated costs and
bandwidths. The goal is to enable the user to find a network topology that minimizes both the total cost
and the latency of data transmission.
Approach:
1. Visual Representation of the Network:
o Design the GUI to allow users to create and visualize a network graph where each node
represents a server or client, and each edge represents a potential network connection. The
edges should display associated costs and bandwidths.
2. Interactive Optimization:
o Implement tools within the GUI that enable users to apply algorithms or heuristics to
optimize the network. The application should provide options to find the best combination
of connections that minimizes the total cost while ensuring all nodes are connected.
3. Dynamic Path Calculation:
o Include a feature where the user can calculate the shortest path between any pair of nodes
within the selected network topology. The GUI should display these paths, taking into
account the bandwidths as weights.
4. Real-time Evaluation:
o Provide real-time analysis within the GUI that displays the total cost and latency of the
current network topology. If the user is not satisfied with the results, they should be able
to adjust the topology and explore alternative solutions interactively.
Example:
 Input: The user inputs a graph in the application, representing servers, clients, potential
connections, their costs, and bandwidths.
 Output: The application displays the optimal network topology that balances cost and latency,
and shows the shortest paths between servers and clients on the GUI
 */
/*
 *  the code creates a GUI application for designing and optimizing network topologies. It allows users to:

Add Nodes and Edges: Users can create network nodes (servers/clients) and connect them with edges (representing connections) that have associated costs and bandwidth.

Optimize the Network: The program computes the Minimum Spanning Tree (MST) using Prim’s algorithm to minimize the total cost while connecting all nodes.

Calculate Shortest Path: It finds the shortest path between two nodes using Dijkstra's algorithm, considering edge costs.

The network graph is visually represented using Swing components, and users can interact with the network, adjust connections, and view optimization results in real-time.
 */
 import java.awt.*;  // Import the AWT package for GUI components
 import java.util.*; // Import utility classes like HashMap, List
 import javax.swing.*; // Import Swing components for GUI elements
 
 // Main class for the network topology application
 public class QNo5 extends JFrame { 
     // Create a Graph object to represent the network topology
     private Graph graph; 
     // Create a GraphPanel to visualize the network graph
     private GraphPanel graphPanel; 
 
     // Constructor for setting up the user interface
     public QNo5() { 
         // Initialize the graph object to manage nodes and edges
         graph = new Graph(); 
         // Initialize the GraphPanel for visualizing the network graph
         graphPanel = new GraphPanel(graph); 
 
         // Set up the window title and size
         setTitle("Network Topology Designer"); 
         setSize(800, 600); 
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the window when the user exits
         setLayout(new BorderLayout()); // Use BorderLayout to organize components
 
         // Add the graphPanel to the center of the window to display the network
         add(graphPanel, BorderLayout.CENTER); 
 
         // Create a control panel for buttons to interact with the graph
         JPanel controlPanel = new JPanel();
         // Add buttons for adding nodes, adding edges, optimizing the network, and calculating paths
         JButton addNodeButton = new JButton("Add Node"); 
         JButton addEdgeButton = new JButton("Add Edge"); 
         JButton optimizeButton = new JButton("Optimize Network");
         JButton calculatePathButton = new JButton("Calculate Path"); 
 
         // Add the buttons to the control panel
         controlPanel.add(addNodeButton);
         controlPanel.add(addEdgeButton);
         controlPanel.add(optimizeButton);
         controlPanel.add(calculatePathButton);
         // Add the control panel to the bottom of the window
         add(controlPanel, BorderLayout.SOUTH);
 
         // Action listener for the "Add Node" button
         addNodeButton.addActionListener(e -> {
             // Prompt the user to input a node name
             String nodeName = JOptionPane.showInputDialog("Enter node name:"); 
             // If the user provides a valid node name
             if (nodeName != null && !nodeName.trim().isEmpty()) { 
                 // Add the node to the graph
                 graph.addNode(nodeName); 
                 // Repaint the graph panel to reflect the new node
                 graphPanel.repaint(); 
             } else {
                 // If no valid name is provided, show an error message
                 JOptionPane.showMessageDialog(null, "Node name cannot be empty."); 
             }
         });
 
         // Action listener for the "Add Edge" button
         addEdgeButton.addActionListener(e -> {
             // Prompt the user for the start node, end node, cost, and bandwidth of the edge
             String fromNode = JOptionPane.showInputDialog("Enter from node:"); 
             String toNode = JOptionPane.showInputDialog("Enter to node:"); 
             String costStr = JOptionPane.showInputDialog("Enter cost:");
             String bandwidthStr = JOptionPane.showInputDialog("Enter bandwidth:"); 
 
             // If all fields are filled, try to parse and add the edge
             if (fromNode != null && toNode != null && costStr != null && bandwidthStr != null) {
                 try {
                     // Convert cost and bandwidth to integers
                     int cost = Integer.parseInt(costStr); 
                     int bandwidth = Integer.parseInt(bandwidthStr); 
                     // If cost or bandwidth is negative, show an error message
                     if (cost < 0 || bandwidth < 0) { 
                         JOptionPane.showMessageDialog(null, "Cost and bandwidth must be non-negative."); 
                     } else {
                         // Add the edge to the graph
                         graph.addEdge(fromNode, toNode, cost, bandwidth); 
                         // Repaint the graph panel to reflect the new edge
                         graphPanel.repaint(); 
                     }
                 } catch (NumberFormatException ex) { 
                     // Show an error message if the input is not valid
                     JOptionPane.showMessageDialog(null, "Invalid cost or bandwidth."); 
                 }
             } else {
                 // Show an error message if any field is missing
                 JOptionPane.showMessageDialog(null, "All fields must be filled."); 
             }
         });
 
         // Action listener for the "Optimize Network" button
         optimizeButton.addActionListener(e -> {
             // Get the minimum spanning tree (MST) edges
             List<Edge> mstEdges = graph.getMinimumSpanningTree(); 
             StringBuilder result = new StringBuilder("Minimum Spanning Tree Edges:\n"); 
             // Display the MST edges in a dialog box
             for (Edge edge : mstEdges) {
                 result.append(edge.getDestination().getName()).append(" (Cost: ").append(edge.getCost()).append(")\n"); 
             }
             JOptionPane.showMessageDialog(null, result.toString()); 
         });
 
         // Action listener for the "Calculate Path" button
         calculatePathButton.addActionListener(e -> {
             // Prompt the user for the start and end nodes for the path
             String fromNode = JOptionPane.showInputDialog("Enter start node:"); 
             String toNode = JOptionPane.showInputDialog("Enter end node:"); 
             // If both nodes are provided
             if (fromNode != null && toNode != null) { 
                 // Find the shortest path between the two nodes
                 List<Node> path = graph.findShortestPath(fromNode, toNode); 
                 // If a path is found, display it in a dialog box
                 if (path != null) { 
                     StringBuilder result = new StringBuilder("Shortest Path:\n");
                     // Display each node in the shortest path
                     for (Node node : path) {
                         result.append(node.getName()).append(" -> "); 
                     }
                     result.setLength(result.length() - 4); // Remove the last " -> "
                     JOptionPane.showMessageDialog(null, result.toString()); 
                 } else {
                     // Show a message if no path is found
                     JOptionPane.showMessageDialog(null, "No path found."); 
                 }
             }
         });
     }
 
     // Main method to launch the GUI application
     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             QNo5 gui = new QNo5(); 
             // Make the GUI visible
             gui.setVisible(true); 
         });
     }
 }
 
 // Graph class to represent the entire network
 class Graph {
     // A map to store nodes, where the key is the node name and the value is the Node object
     private final Map<String, Node> nodes = new HashMap<>(); 
 
     // Method to add a new node
     public void addNode(String name) {
         // Add a node with the specified name
         nodes.put(name, new Node(name)); 
     }
 
     // Method to add an edge between two nodes
     public void addEdge(String from, String to, int cost, int bandwidth) {
         Node source = nodes.get(from); 
         Node destination = nodes.get(to); 
         // If both nodes exist in the graph
         if (source != null && destination != null) { 
             // Create an edge and add it to the source node's edges
             source.addEdge(new Edge(destination, cost, bandwidth)); 
         } else {
             // Show a message if one or both nodes do not exist
             JOptionPane.showMessageDialog(null, "One or both nodes do not exist."); 
         }
     }
 
     // Method to get all the nodes in the graph
     public Collection<Node> getNodes() {
         return nodes.values(); 
     }
 
     // Method to get the Minimum Spanning Tree (MST) using Prim’s algorithm
     public List<Edge> getMinimumSpanningTree() {
         List<Edge> mstEdges = new ArrayList<>(); 
         Set<Node> visited = new HashSet<>(); 
         PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getCost)); 
 
         // If the graph is not empty, start from any node
         if (!nodes.isEmpty()) { 
             Node startNode = nodes.values().iterator().next();
             visited.add(startNode); 
             edgeQueue.addAll(startNode.getEdges()); 
 
             // Add edges to the MST
             while (!edgeQueue.isEmpty()) {
                 Edge edge = edgeQueue.poll(); 
                 Node destination = edge.getDestination(); 
                 // If the destination node is not already visited, add it to the MST
                 if (!visited.contains(destination)) { 
                     visited.add(destination); 
                     mstEdges.add(edge); 
                     edgeQueue.addAll(destination.getEdges()); 
                 }
             }
         }
         return mstEdges; 
     }
 
     // Method to find the shortest path between two nodes using Dijkstra’s algorithm
     public List<Node> findShortestPath(String startName, String endName) {
         Node startNode = nodes.get(startName); 
         Node endNode = nodes.get(endName); 
         // If either of the nodes does not exist, return null
         if (startNode == null || endNode == null)
             return null; 
 
         // Maps to store distances and previous nodes
         Map<Node, Integer> distances = new HashMap<>(); 
         Map<Node, Node> previousNodes = new HashMap<>(); 
         PriorityQueue<Node> nodeQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get)); 
 
         // Initialize distances for all nodes to a large value
         for (Node node : nodes.values()) {
             distances.put(node, Integer.MAX_VALUE); 
             previousNodes.put(node, null);
         }
         // Set the distance of the start node to 0
         distances.put(startNode, 0); 
         nodeQueue.add(startNode); 
 
         // Dijkstra’s algorithm to find the shortest path
         while (!nodeQueue.isEmpty()) {
             Node currentNode = nodeQueue.poll();
             // If the current node is the destination, break
             if (currentNode.equals(endNode))
                 break;
 
             // For each edge of the current node, calculate the shortest path
             for (Edge edge : currentNode.getEdges()) {
                 Node neighbor = edge.getDestination(); 
                 int newDist = distances.get(currentNode) + edge.getCost();
                 // If the new path to the neighbor is shorter, update the distance
                 if (newDist < distances.get(neighbor)) { 
                     distances.put(neighbor, newDist); 
                     previousNodes.put(neighbor, currentNode); 
                     nodeQueue.add(neighbor); 
                 }
             }
         }
 
         // Reconstruct the path from start to end
         List<Node> path = new ArrayList<>();
         for (Node at = endNode; at != null; at = previousNodes.get(at)) {
             path.add(at); 
         }
         Collections.reverse(path); // Reverse the path to get the correct order
         return path.size() > 1 ? path : null; 
     }
 }
 
 // Node class representing a server or client in the network
 class Node {
     private final String name; // Name of the node (e.g., server or client)
     private final List<Edge> edges = new ArrayList<>(); // List of edges connected to this node
 
     // Constructor to initialize the node with its name
     Node(String name) {
         this.name = name; 
     }
 
     // Method to add an edge to the node
     void addEdge(Edge edge) {
         edges.add(edge); 
     }
 
     // Getter for the node's name
     public String getName() {
         return name; 
     }
 
     // Getter for the list of edges connected to this node
     public List<Edge> getEdges() {
         return edges; 
     }
 }
 
 // Edge class representing a connection between two nodes in the network
 class Edge {
     private final Node destination; // The destination node of the edge
     private final int cost; // The cost of the edge
     private final int bandwidth; // The bandwidth of the edge
 
     // Constructor to initialize the edge with a destination, cost, and bandwidth
     Edge(Node destination, int cost, int bandwidth) {
         this.destination = destination; 
         this.cost = cost; 
         this.bandwidth = bandwidth; 
     }
 
     // Getter for the destination node of the edge
     public Node getDestination() {
         return destination;
     }
 
     // Getter for the cost of the edge
     public int getCost() {
         return cost; 
     }
 
     // Getter for the bandwidth of the edge
     public int getBandwidth() {
         return bandwidth; 
     }
 }
 
 // GraphPanel class to visualize the network in a GUI
 class GraphPanel extends JPanel {
     private final Graph graph; // The graph object to visualize
 
     // Constructor to initialize the panel with the graph
     public GraphPanel(Graph graph) {
         this.graph = graph; 
         setBackground(Color.WHITE); // Set the background color of the panel to white
     }
 
     // Override the paintComponent method to draw the graph
     @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g); // Call the superclass method
         drawGraph(g); // Call the method to draw the graph
     }
 
     // Method to draw the graph on the panel
     private void drawGraph(Graphics g) {
         int nodeRadius = 20; // Set the radius of nodes
         int xOffset = 50; // Set the initial X offset for node placement
         int yOffset = 50; // Set the initial Y offset for node placement
         int spacing = 100; // Set the spacing between nodes
         int index = 0; // Initialize the node index
 
         // Draw nodes as circles
         for (Node node : graph.getNodes()) {
             // Calculate the position of the node
             int x = xOffset + (index % 5) * spacing; 
             int y = yOffset + (index / 5) * spacing; 
             g.fillOval(x, y, nodeRadius, nodeRadius); // Draw the node as a circle
             g.drawString(node.getName(), x + 5, y + 15); // Draw the node's name next to it
             index++; // Increment the node index
         }
 
         // Draw edges as lines between nodes
         for (Node node : graph.getNodes()) {
             // Get the position of the node
             int fromX = xOffset + (index % 5) * spacing; 
             int fromY = yOffset + (index / 5) * spacing; 
             // Draw lines for each edge of the node
             for (Edge edge : node.getEdges()) {
                 // Calculate the position of the destination node
                 int toX = xOffset + (index % 5) * spacing;
                 int toY = yOffset + (index / 5) * spacing;
                 g.drawLine(fromX, fromY, toX, toY); // Draw the edge as a line
                 g.drawString(String.format("Cost: %d", edge.getCost()), (fromX + toX) / 2, (fromY + toY) / 2);
                 // Display the cost of the edge in the middle of the line
             }
         }
     }
 }
 