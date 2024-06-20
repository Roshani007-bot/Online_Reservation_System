import java.text.SimpleDateFormat;
import java.util.*;

class OnlineReservationSystem {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static int nextPNR = 1000; // Starting PNR number
    private static Map<Integer, Reservation> reservations = new HashMap<>();

    // Inner class to represent a Reservation
    static class Reservation {
        int PNR;
        String trainNumber;
        String trainName;
        String classType;
        Date journeyDate;
        String source;
        String destination;

        public Reservation(int PNR, String trainNumber, String trainName, String classType, Date journeyDate, String source, String destination) {
            this.PNR = PNR;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.classType = classType;
            this.journeyDate = journeyDate;
            this.source = source;
            this.destination = destination;
        }

        @Override
        public String toString() {
            return "PNR: " + PNR + "\nTrain Number: " + trainNumber + "\nTrain Name: " + trainName +
                    "\nClass Type: " + classType + "\nJourney Date: " + journeyDate +
                    "\nFrom: " + source + "\nTo: " + destination;
        }
    }

    // Method to handle login
    private static boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        return USERNAME.equals(enteredUsername) && PASSWORD.equals(enteredPassword);
    }

    // Method to handle reservation
    private static void makeReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();

        System.out.print("Enter Train Name: ");
        String trainName = scanner.nextLine();

        System.out.print("Enter Class Type (e.g., Sleeper, AC): ");
        String classType = scanner.nextLine();

        System.out.print("Enter Date of Journey (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        Date journeyDate;
        try {
            journeyDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Reservation failed.");
            return;
        }

        System.out.print("Enter From (Source): ");
        String source = scanner.nextLine();

        System.out.print("Enter To (Destination): ");
        String destination = scanner.nextLine();

        int PNR = nextPNR++;
        Reservation reservation = new Reservation(PNR, trainNumber, trainName, classType, journeyDate, source, destination);
        reservations.put(PNR, reservation);

        System.out.println("Reservation successful. Your PNR is: " + PNR);
    }

    // Method to handle cancellation
    private static void cancelReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter PNR number to cancel: ");
        int pnr = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        Reservation reservation = reservations.get(pnr);
        if (reservation != null) {
            System.out.println("Details of reservation to cancel:");
            System.out.println(reservation);

            System.out.print("Are you sure you want to cancel this ticket? (Yes/No): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Yes")) {
                reservations.remove(pnr);
                System.out.println("Ticket cancellation successful.");
            } else {
                System.out.println("Ticket cancellation aborted.");
            }
        } else {
            System.out.println("No reservation found with PNR number: " + pnr);
        }
    }

    // Main method to run the system
    public static void main(String[] args) {
        if (!login()) {
            System.out.println("Invalid credentials. Access denied.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Online Reservation System ---");
            System.out.println("1. Make a Reservation");
            System.out.println("2. Cancel a Reservation");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
