/*Question no 6
 * b)
Scenario: A Multithreaded Web Crawler
Problem:
You need to crawl a large number of web pages to gather data or index content. Crawling each page
sequentially can be time-consuming and inefficient.
Goal:
Create a web crawler application that can crawl multiple web pages concurrently using multithreading to
improve performance.
Tasks:
Design the application:
Create a data structure to store the URLs to be crawled.
Implement a mechanism to fetch web pages asynchronously.
Design a data storage mechanism to save the crawled data.
Create a thread pool:
Use the ExecutorService class to create a thread pool for managing multiple threads.
Submit tasks:
For each URL to be crawled, create a task (e.g., a Runnable or Callable object) that fetches the web page
and processes the content.
Submit these tasks to the thread pool for execution.
Handle responses:
Process the fetched web pages, extracting relevant data or indexing the content.
Handle errors or exceptions that may occur during the crawling process.
Manage the crawling queue:
Implement a mechanism to manage the queue of URLs to be crawled, such as a priority queue or a
breadth-first search algorithm.
By completing these tasks, you will create a multithreaded web crawler that can efficiently crawl large
numbers of web page
 */

/*
 * Implementation of a Multi-threaded Web Crawler
 * -----------------------------------------------
 * This Java program implements a basic multi-threaded web crawler using the following components:
 * - `ExecutorService`: A thread pool to handle concurrent page fetching.
 * - `ConcurrentLinkedQueue`: A thread-safe queue for storing URLs to be visited.
 * - `synchronizedSet`: A thread-safe set to track visited URLs.
 * - `URL` and `BufferedReader`: To fetch and read web page content.
 * - `FileWriter`: To save crawled data into a text file.
 *
 * Working:
 * 1. The crawler starts from a given URL and extracts links.
 * 2. It uses multiple threads to fetch and parse pages concurrently.
 * 3. Extracted links are added to the queue if they haven’t been visited.
 * 4. The content of each page is stored in a text file.
 * 5. The process continues until the maximum number of pages is reached.
 */

 import java.util.*;  // Importing utility classes for collections
 import java.util.concurrent.*;  // Importing concurrent collections and thread pools
 import java.net.*;  // Importing classes for handling URLs
 import java.io.*;  // Importing classes for file handling and input/output operations
 
 class Qno6B {
     // A thread-safe set to store visited URLs and prevent duplicates
     private final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
 
     // A thread-safe queue to store URLs yet to be crawled
     private final Queue<String> urlQueue = new ConcurrentLinkedQueue<>();
 
     // Thread pool for concurrent execution of tasks
     private final ExecutorService threadPool;
 
     // Maximum number of retries for failed page fetches
     private final int maxRetries = 2;
 
     // Constructor: Initializes the thread pool with the specified number of threads
     public Qno6B(int threadCount) {
         this.threadPool = Executors.newFixedThreadPool(threadCount);
     }
 
     // Method to start the crawling process
     public void startCrawling(String startUrl, int maxPages) {
         // Add the initial URL to the queue
         urlQueue.add(startUrl);
 
         // Continue crawling while there are URLs in the queue and the page limit is not exceeded
         while (!urlQueue.isEmpty() && visitedUrls.size() < maxPages) {
             String url = urlQueue.poll(); // Retrieve and remove the next URL from the queue
 
             // If the URL is valid and hasn't been visited
             if (url != null && !visitedUrls.contains(url)) {
                 visitedUrls.add(url); // Mark the URL as visited
 
                 // Execute the page fetch in a separate thread
                 threadPool.execute(() -> fetchPage(url, 0));
             }
         }
         // Shutdown the thread pool once the crawling is complete
         threadPool.shutdown();
     }
 
     // Method to fetch a web page and extract URLs
     private void fetchPage(String url, int retryCount) {
         try {
             System.out.println("Crawling: " + url); // Print the URL being crawled
 
             // Create a URL object for the given address
             URL website = new URL(url);
 
             // Open a stream to read the page content
             BufferedReader reader = new BufferedReader(new InputStreamReader(website.openStream()));
 
             StringBuilder content = new StringBuilder(); // StringBuilder to store page content
             String line;
 
             // Read the page line by line
             while ((line = reader.readLine()) != null) {
                 content.append(line).append("\n"); // Append the line to content
                 extractUrls(line); // Extract URLs from the current line
             }
 
             reader.close(); // Close the reader
 
             savePageContent(url, content.toString()); // Save the page content to a file
 
         } catch (Exception e) {
             System.out.println("Failed to crawl: " + url); // Print failure message
 
             // Retry fetching the page if the max retry count is not reached
             if (retryCount < maxRetries) {
                 System.out.println("Retrying: " + url);
                 fetchPage(url, retryCount + 1); // Recursively retry fetching the page
             }
         }
     }
 
     // Method to extract URLs from the page content
     private void extractUrls(String content) {
         // Regular expression to match URLs
         String regex = "https?://[\\w\\.-]+";
 
         // Scanner to find URLs in the content
         Scanner scanner = new Scanner(content);
 
         // Search for URLs using the regex pattern
         while (scanner.findInLine(regex) != null) {
             String foundUrl = scanner.match().group(); // Extract matched URL
 
             // If the URL has not been visited, add it to the queue
             if (!visitedUrls.contains(foundUrl)) {
                 urlQueue.add(foundUrl);
                 System.out.println("Found URL: " + foundUrl); // Print the found URL
             }
         }
 
         scanner.close(); // Close the scanner
     }
 
     // Method to save the page content to a file
     private void savePageContent(String url, String content) {
         try {
             String fileName = "crawled_data.txt"; // Name of the file to store crawled data
 
             // Open the file in append mode
             FileWriter writer = new FileWriter(fileName, true);
 
             // Write the URL and its corresponding content to the file
             writer.write("URL: " + url + "\n" + content + "\n\n");
 
             writer.close(); // Close the writer
         } catch (IOException e) {
             System.out.println("Error saving data for: " + url); // Print error message
         }
     }
 
     // Main method to start the crawler
     public static void main(String[] args) {
         Qno6B crawler = new Qno6B(5); // Create a crawler with 5 threads
         crawler.startCrawling("https://www.wikipedia.org", 10); // Start crawling from Wikipedia
     }
 }
 
// Output
// Crawling: https://www.wikipedia.org
// Found URL: http://www.w3.org
// Found URL: http://www.w3.org
// Found URL: http://www.w3.org
// Found URL: http://www.w3.org
// Found URL: https://wikis.world
// Found URL: https://upload.wikimedia.org
// Found URL: https://meta.wikimedia.org
// Found URL: https://donate.wikimedia.org
// Found URL: https://en.wikipedia.org
// Found URL: https://play.google.com
// Found URL: https://itunes.apple.com
// Found URL: https://creativecommons.org
// Found URL: https://foundation.wikimedia.org
// Found URL: https://foundation.wikimedia.org