/*
Problem: Print a sequence of numbers in the format 0, odd, 0, even, 0, odd, ... up to a given number n, using three threads:

ZeroThread: Prints 0.

OddThread: Prints odd numbers.

EvenThread: Prints even numbers.

Approach:

Use locks and conditions to synchronize the threads.

ZeroThread prints 0 and signals OddThread or EvenThread to print the next number.

OddThread prints odd numbers and signals ZeroThread to print 0.

EvenThread prints even numbers and signals ZeroThread to print 0.

Key Components:

Lock: Ensures only one thread executes at a time.

Conditions:

zeroCondition: Used by ZeroThread to wait and signal.

oddEvenCondition: Used by OddThread and EvenThread to wait and signal.

Flag: isZeroTurn indicates whether it's ZeroThread's turn to print.

Thread Coordination:

ZeroThread prints 0 and signals OddThread or EvenThread.

OddThread prints odd numbers and signals ZeroThread.

EvenThread prints even numbers and signals ZeroThread.

Complexity:
Time: O(n) (each thread runs n times).

Space: O(1) (constant space for locks and conditions).
 */
// Import necessary classes for locks and conditions
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Class to handle printing of numbers (0, even, odd)
class NumberPrinter {
    // Method to print the number 0
    public void printZero() {
        System.out.print("0"); // Print "0" to the console
    }

    // Method to print even numbers
    public void printEven(int num) {
        System.out.print(num); // Print the even number to the console
    }

    // Method to print odd numbers
    public void printOdd(int num) {
        System.out.print(num); // Print the odd number to the console
    }
}

// Class to control the threads and synchronize their execution
class ThreadController {
    // Instance of NumberPrinter to call print methods
    private final NumberPrinter printer = new NumberPrinter();
    // Lock for synchronization between threads
    private final Lock lock = new ReentrantLock();
    // Condition for ZeroThread to wait and signal
    private final Condition zeroCondition = lock.newCondition();
    // Condition for OddThread and EvenThread to wait and signal
    private final Condition oddEvenCondition = lock.newCondition();
    // Tracks the current number to be printed
    private int currentNumber = 1;
    // Flag to indicate if it's ZeroThread's turn to print
    private boolean isZeroTurn = true;

    // Method to start and coordinate the threads
    public void printSequence(int n) {
        // Create a thread for ZeroThread
        Thread zeroThread = new Thread(new ZeroThread(n));
        // Create a thread for OddThread
        Thread oddThread = new Thread(new OddThread(n));
        // Create a thread for EvenThread
        Thread evenThread = new Thread(new EvenThread(n));

        // Start ZeroThread
        zeroThread.start();
        // Start OddThread
        oddThread.start();
        // Start EvenThread
        evenThread.start();

        // Wait for all threads to finish execution
        try {
            zeroThread.join(); // Wait for ZeroThread to finish
            oddThread.join(); // Wait for OddThread to finish
            evenThread.join(); // Wait for EvenThread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption
        }
    }

    // Inner class for ZeroThread
    private class ZeroThread implements Runnable {
        // Maximum number to print
        private final int n;

        // Constructor to initialize n
        public ZeroThread(int n) {
            this.n = n;
        }

        // Run method for ZeroThread
        @Override
        public void run() {
            lock.lock(); // Acquire the lock
            try {
                // Loop until currentNumber exceeds n
                while (currentNumber <= n) {
                    // Wait if it's not ZeroThread's turn
                    while (!isZeroTurn) {
                        zeroCondition.await(); // Wait for signal
                    }
                    // Stop if currentNumber exceeds n
                    if (currentNumber > n) {
                        break; // Exit the loop
                    }
                    printer.printZero(); // Print "0"
                    isZeroTurn = false; // Set flag to false (next turn is OddThread or EvenThread)
                    oddEvenCondition.signalAll(); // Signal OddThread or EvenThread to proceed
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle thread interruption
            } finally {
                lock.unlock(); // Release the lock
            }
        }
    }

    // Inner class for OddThread
    private class OddThread implements Runnable {
        // Maximum number to print
        private final int n;

        // Constructor to initialize n
        public OddThread(int n) {
            this.n = n;
        }

        // Run method for OddThread
        @Override
        public void run() {
            lock.lock(); // Acquire the lock
            try {
                // Loop until currentNumber exceeds n
                while (currentNumber <= n) {
                    // Wait if it's ZeroThread's turn or currentNumber is even
                    while (isZeroTurn || currentNumber % 2 == 0) {
                        oddEvenCondition.await(); // Wait for signal
                    }
                    // Stop if currentNumber exceeds n
                    if (currentNumber > n) {
                        break; // Exit the loop
                    }
                    printer.printOdd(currentNumber); // Print odd number
                    currentNumber++; // Increment currentNumber
                    isZeroTurn = true; // Set flag to true (next turn is ZeroThread)
                    zeroCondition.signal(); // Signal ZeroThread to proceed
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle thread interruption
            } finally {
                lock.unlock(); // Release the lock
            }
        }
    }

    // Inner class for EvenThread
    private class EvenThread implements Runnable {
        // Maximum number to print
        private final int n;

        // Constructor to initialize n
        public EvenThread(int n) {
            this.n = n;
        }

        // Run method for EvenThread
        @Override
        public void run() {
            lock.lock(); // Acquire the lock
            try {
                // Loop until currentNumber exceeds n
                while (currentNumber <= n) {
                    // Wait if it's ZeroThread's turn or currentNumber is odd
                    while (isZeroTurn || currentNumber % 2 != 0) {
                        oddEvenCondition.await(); // Wait for signal
                    }
                    // Stop if currentNumber exceeds n
                    if (currentNumber > n) {
                        break; // Exit the loop
                    }
                    printer.printEven(currentNumber); // Print even number
                    currentNumber++; // Increment currentNumber
                    isZeroTurn = true; // Set flag to true (next turn is ZeroThread)
                    zeroCondition.signal(); // Signal ZeroThread to proceed
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle thread interruption
            } finally {
                lock.unlock(); // Release the lock
            }
        }
    }
}

// Main class to run the program
public class Qno6A {
    // Main method
    public static void main(String[] args) {
        ThreadController controller = new ThreadController(); // Create ThreadController instance
        controller.printSequence(5); // Print sequence up to 5
    }
}

// Output
// 0102030405