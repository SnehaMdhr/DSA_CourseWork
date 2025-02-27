import javax.swing.*;
import java.util.HashMap;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class NetworkTopologyGUI extends JFrame {
    
    private mxGraph graph;
    private Object parent;
    private HashMap<String, Object> nodes;  // Stores nodes (servers/clients)
    private HashMap<String, Integer> edges; // Stores edges (connections with latency)

    public NetworkTopologyGUI() {
        super("Network Topology with HashMap");
        graph = new mxGraph();
        parent = graph.getDefaultParent();
        nodes = new HashMap<>();
        edges = new HashMap<>();
        
        graph.getModel().beginUpdate();
        try {
            // Add nodes
            addNode("Server", 100, 100);
            addNode("Client1", 300, 50);
            addNode("Client2", 300, 150);

            // Add edges with latency
            addEdge("Server", "Client1", 5);
            addEdge("Server", "Client2", 10);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    // Method to add a node to the graph and store it in HashMap
    private void addNode(String name, int x, int y) {
        Object node = graph.insertVertex(parent, null, name, x, y, 80, 30);
        nodes.put(name, node);
    }

    // Method to add an edge between two nodes
    private void addEdge(String from, String to, int latency) {
        if (nodes.containsKey(from) && nodes.containsKey(to)) {
            graph.insertEdge(parent, null, latency + " ms", nodes.get(from), nodes.get(to));
            edges.put(from + "-" + to, latency);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NetworkTopologyGUI frame = new NetworkTopologyGUI();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setVisible(true);
        });
    }
}
