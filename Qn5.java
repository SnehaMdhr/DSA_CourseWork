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
// The Java-based program employs Swing for its GUI to build network topology 
// designs. Users can visualize network structures within this program through
//  an interface which enables them to add nodes as devices and create edges that 
//  determine the connections between these nodes. The program contains MST 
//  calculation through Kruskal's algorithm function for network optimization 
//  and displays the Minimum Spanning Tree of the network. The system enables
//   users to perform shortest-path queries using Dijkstra’s algorithm and therefore
//    offers optimized network routes based on connection structures. The program 
//    accepts user interaction through node and edge additions or modifications or 
//    deletions to serve effectively in network topology simulation tasks and network
//     optimization needs. The application implements a user-friendly interface 
//     suitable for individuals who lack experience in network design.
/*
 * This program is a graphical user interface (GUI) application for designing a network topology.
 * It allows the user to add nodes and edges to represent a network and perform operations like:
 * - Add Nodes: Add a node to the network.
 * - Add Edges: Add an edge between two nodes with a cost and bandwidth.
 * - Optimize Network: Display the minimum spanning tree of the network.
 * - Calculate Shortest Path: Find the shortest path between two nodes in the network.
 * 
 * The network is represented as a graph, with nodes connected by edges. The program also uses a panel
 * to visually display the graph and interact with the network.
 */

 import java.awt.*; // Importing the AWT package for GUI components
 import java.util.*; // Importing the utilities package for data structures like List and Map
 import javax.swing.*; // Importing the Swing package for creating the GUI
 
 // Main class for the Network Topology GUI
 public class Qn5 extends JFrame { // Inheriting JFrame to create a window for the application
     private Graph graph; // Instance variable to hold the graph object
     private GraphPanel graphPanel; // Instance variable to hold the GraphPanel (the part where graph is drawn)
 
     // Constructor for the Q5 class
     public Qn5() { // Constructor to initialize the network topology GUI
         graph = new Graph(); // Create a new Graph object to represent the network
         graphPanel = new GraphPanel(graph); // Create a panel to display the graph
 
         // Set up the JFrame properties
         setTitle("Network Topology Designer"); // Set the title of the window
         setSize(800, 600); // Set the dimensions of the window (800px width and 600px height)
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
         setLayout(new BorderLayout()); // Use a BorderLayout for organizing components
 
         add(graphPanel, BorderLayout.CENTER); // Add the graph panel to the center of the frame
 
         // Create a control panel for buttons that will be placed at the bottom
         JPanel controlPanel = new JPanel(); // JPanel to hold control buttons
         JButton addNodeButton = new JButton("Add Node"); // Button for adding nodes
         JButton addEdgeButton = new JButton("Add Edge"); // Button for adding edges
         JButton optimizeButton = new JButton("Optimize Network"); // Button for optimizing network
         JButton calculatePathButton = new JButton("Calculate Path"); // Button for calculating shortest path
 
         // Add buttons to the control panel
         controlPanel.add(addNodeButton); // Add the 'Add Node' button to the control panel
         controlPanel.add(addEdgeButton); // Add the 'Add Edge' button to the control panel
         controlPanel.add(optimizeButton); // Add the 'Optimize Network' button to the control panel
         controlPanel.add(calculatePathButton); // Add the 'Calculate Path' button to the control panel
         add(controlPanel, BorderLayout.SOUTH); // Add the control panel to the bottom of the frame
 
         // Action listener for adding a node
         addNodeButton.addActionListener(e -> {
             String nodeName = JOptionPane.showInputDialog("Enter node name:"); // Prompt for node name
             if (nodeName != null && !nodeName.trim().isEmpty()) { // Check if input is valid (not null or empty)
                 graph.addNode(nodeName); // Add the node to the graph
                 graphPanel.repaint(); // Repaint the graph to show the new node
             } else {
                 JOptionPane.showMessageDialog(null, "Node name cannot be empty."); // Show error if name is invalid
             }
         });
 
         // Action listener for adding an edge
         addEdgeButton.addActionListener(e -> {
             String fromNode = JOptionPane.showInputDialog("Enter from node:"); // Prompt for the 'from' node
             String toNode = JOptionPane.showInputDialog("Enter to node:"); // Prompt for the 'to' node
             String costStr = JOptionPane.showInputDialog("Enter cost:"); // Prompt for the edge cost
             String bandwidthStr = JOptionPane.showInputDialog("Enter bandwidth:"); // Prompt for edge bandwidth
 
             // Check if all input fields are filled
             if (fromNode != null && toNode != null && costStr != null && bandwidthStr != null) {
                 try {
                     int cost = Integer.parseInt(costStr); // Parse cost to integer
                     int bandwidth = Integer.parseInt(bandwidthStr); // Parse bandwidth to integer
                     if (cost < 0 || bandwidth < 0) { // Ensure cost and bandwidth are non-negative
                         JOptionPane.showMessageDialog(null, "Cost and bandwidth must be non-negative."); // Error message
                     } else {
                         graph.addEdge(fromNode, toNode, cost, bandwidth); // Add the edge to the graph
                         graphPanel.repaint(); // Repaint the graph to reflect the new edge
                     }
                 } catch (NumberFormatException ex) { // Handle invalid number format for cost or bandwidth
                     JOptionPane.showMessageDialog(null, "Invalid cost or bandwidth."); // Show error message
                 }
             } else {
                 JOptionPane.showMessageDialog(null, "All fields must be filled."); // Show error if any field is empty
             }
         });
 
         // Action listener for optimizing the network (finding minimum spanning tree)
         optimizeButton.addActionListener(e -> {
             List<Edge> mstEdges = graph.getMinimumSpanningTree(); // Get the edges of the minimum spanning tree
             StringBuilder result = new StringBuilder("Minimum Spanning Tree Edges:\n"); // StringBuilder to format the result
             for (Edge edge : mstEdges) { // Iterate through the edges
                 result.append(edge.getDestination().getName()).append(" (Cost: ").append(edge.getCost()).append(")\n"); // Append edge details
             }
             JOptionPane.showMessageDialog(null, result.toString()); // Show the result in a dialog
         });
 
         // Action listener for calculating the shortest path between two nodes
         calculatePathButton.addActionListener(e -> {
             String fromNode = JOptionPane.showInputDialog("Enter start node:"); // Prompt for the start node
             String toNode = JOptionPane.showInputDialog("Enter end node:"); // Prompt for the end node
             if (fromNode != null && toNode != null) { // Ensure both inputs are not null
                 List<Node> path = graph.findShortestPath(fromNode, toNode); // Find the shortest path
                 if (path != null) { // If a path is found
                     StringBuilder result = new StringBuilder("Shortest Path:\n"); // StringBuilder to format the result
                     for (Node node : path) { // Iterate through nodes in the path
                         result.append(node.getName()).append(" -> "); // Append node names
                     }
                     result.setLength(result.length() - 4); // Remove the last "->"
                     JOptionPane.showMessageDialog(null, result.toString()); // Show the result in a dialog
                 } else {
                     JOptionPane.showMessageDialog(null, "No path found."); // Show error if no path is found
                 }
             }
         });
     }
 
     // Main method to run the application
     public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             Qn5 gui = new Qn5(); // Create an instance of the Qn5 GUI
             gui.setVisible(true); // Make the GUI visible
         });
     }
 }
 
 // Class representing the graph structure
 class Graph {
     private final Map<String, Node> nodes = new HashMap<>(); // Map to store nodes by their names
 
     // Method to add a node to the graph
     public void addNode(String name) {
         nodes.put(name, new Node(name)); // Add a new node to the graph
     }
 
     // Method to add an edge between two nodes
     public void addEdge(String from, String to, int cost, int bandwidth) {
         Node source = nodes.get(from); // Get the source node
         Node destination = nodes.get(to); // Get the destination node
         if (source != null && destination != null) { // Ensure both nodes exist
             source.addEdge(new Edge(destination, cost, bandwidth)); // Add the edge to the source node
         } else {
             JOptionPane.showMessageDialog(null, "One or both nodes do not exist."); // Show error if nodes do not exist
         }
     }
 
     // Method to get all nodes in the graph
     public Collection<Node> getNodes() {
         return nodes.values(); // Return all nodes in the graph
     }
 
     // Method to get the minimum spanning tree of the graph
     public List<Edge> getMinimumSpanningTree() {
         List<Edge> mstEdges = new ArrayList<>(); // List to store edges of the minimum spanning tree
         Set<Node> visited = new HashSet<>(); // Set to track visited nodes
         PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getCost)); // Priority queue to process edges
 
         if (!nodes.isEmpty()) { // If the graph is not empty
             Node startNode = nodes.values().iterator().next(); // Get the first node as the start node
             visited.add(startNode); // Mark the start node as visited
             edgeQueue.addAll(startNode.getEdges()); // Add the start node's edges to the queue
 
             // Loop until all edges are processed
             while (!edgeQueue.isEmpty()) {
                 Edge edge = edgeQueue.poll(); // Get the edge with the smallest cost
                 Node destination = edge.getDestination(); // Get the destination node
                 if (!visited.contains(destination)) { // If the destination node is not visited
                     visited.add(destination); // Mark the destination node as visited
                     mstEdges.add(edge); // Add this edge to the MST
                     edgeQueue.addAll(destination.getEdges()); // Add destination node's edges to the queue
                 }
             }
         }
         return mstEdges; // Return the edges of the minimum spanning tree
     }
 
     // Method to find the shortest path between two nodes
     public List<Node> findShortestPath(String startName, String endName) {
         Node startNode = nodes.get(startName); // Get the start node
         Node endNode = nodes.get(endName); // Get the end node
         if (startNode == null || endNode == null)
             return null; // Return null if either node does not exist
 
         Map<Node, Integer> distances = new HashMap<>(); // Map to store shortest distances
         Map<Node, Node> previousNodes = new HashMap<>(); // Map to store previous nodes in the path
         PriorityQueue<Node> nodeQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Priority queue for nodes
 
         // Initialize distances and previous nodes
         for (Node node : nodes.values()) {
             distances.put(node, Integer.MAX_VALUE); // Set the initial distance to infinity
             previousNodes.put(node, null); // Set the previous node to null
         }
         distances.put(startNode, 0); // Set the start node's distance to 0
         nodeQueue.add(startNode); // Add start node to the priority queue
 
         // Process nodes in the queue
         while (!nodeQueue.isEmpty()) {
             Node current
 