import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main class that contains all the functionality
public class Bookingsystem {
    // User class to manage user details
    static class User {
        private String username;
        private String password;
        private String email;

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public boolean authenticate(String password) {
            return this.password.equals(password);
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    // Booking class to handle flight and hotel bookings
    static class Booking {
        private String bookingType; // "Flight" or "Hotel"
        private String details;
        private double price;

        public Booking(String bookingType, String details, double price) {
            this.bookingType = bookingType;
            this.details = details;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Booking{" +
                    "Type='" + bookingType + '\'' +
                    ", Details='" + details + '\'' +
                    ", Price=" + price +
                    '}';
        }
    }

    // Flight class to manage flight details
    static class Flight {
        private String flightNumber;
        private String departureCity;
        private String arrivalCity;
        private double price;

        public Flight(String flightNumber, String departureCity, String arrivalCity, double price) {
            this.flightNumber = flightNumber;
            this.departureCity = departureCity;
            this.arrivalCity = arrivalCity;
            this.price = price;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Flight{" +
                    "Flight Number='" + flightNumber + '\'' +
                    ", From : '" + departureCity + '\'' +
                    ", To : '" + arrivalCity + '\'' +
                    ", Ticket : " + "Rs." + price +
                    '}';
        }
    }

    // Hotel class to manage hotel details
    static class Hotel {
        private String name;
        private String location;
        private double pricePerNight;

        public Hotel(String name, String location, double pricePerNight) {
            this.name = name;
            this.location = location;
            this.pricePerNight = pricePerNight;
        }

        public String getName() {
            return name;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }

        @Override
        public String toString() {
            return "Hotel{" +
                    "Name : '" + name + '\'' +
                    ", Location : '" + location + '\'' +
                    ", Price per Night : " + pricePerNight +
                    '}';
        }
    }

    // Main BookingSystem class to run the application
    private List<User> users = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();
    private User currentUser;

    public static void main(String[] args) {
        Bookingsystem bookingSystem = new Bookingsystem();
        bookingSystem.run();
    }

    // Run the application
    public void run() {
        Scanner scanner = new Scanner(System.in);
        initializeDummyData();

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. View Flights\n4. Book Flight\n5. View Hotels\n6. Book Hotel\n7. View Bookings\n8. Logout\n0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerUser(scanner);
                case 2 -> loginUser(scanner);
                case 3 -> viewFlights();
                case 4 -> bookFlight(scanner);
                case 5 -> viewHotels();
                case 6 -> bookHotel(scanner);
                case 7 -> viewBookings();
                case 8 -> logout();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Initialize dummy data for flights and hotels
    private void initializeDummyData() {
        flights.add(new Flight("FL123", "Chennai", "Hyderabad", 800));
        flights.add(new Flight("FL456", "Chennai", "Mumbai", 1200));

        hotels.add(new Hotel("Grand Hotel", "Hyderabad", 1200));
        hotels.add(new Hotel("Sunshine Resort", "Mumbai", 3000));
    }

    // Register a new user
    private void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        users.add(new User(username, password, email));
        System.out.println("Registration successful!");
    }

    // Login an existing user
    private void loginUser(Scanner scanner) {
        if (currentUser != null) {
            System.out.println("Already logged in as " + currentUser.getUsername());
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                currentUser = user;
                System.out.println("Login successful! Welcome " + currentUser.getUsername());
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    // View available flights
    private void viewFlights() {
        System.out.println("Available Flights:");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    // Book a flight
    private void bookFlight(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("Please log in first.");
            return;
        }
        viewFlights();
        System.out.print("Enter the flight number to book: ");
        String flightNumber = scanner.nextLine();

        for (Flight flight : flights) {
            if (flightNumber.equals(flight.getFlightNumber())) {
                bookings.add(new Booking("Flight", flight.toString(), flight.getPrice()));
                System.out.println("Flight booked successfully!");
                return;
            }
        }
        System.out.println("Flight not found.");
    }

    // View available hotels
    private void viewHotels() {
        System.out.println("Available Hotels:");
        for (Hotel hotel : hotels) {
            System.out.println(hotel);
        }
    }

    // Book a hotel
    private void bookHotel(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("Please log in first.");
            return;
        }
        viewHotels();
        System.out.print("Enter the hotel name to book: ");
        String hotelName = scanner.nextLine();

        for (Hotel hotel : hotels) {
            if (hotelName.equalsIgnoreCase(hotel.getName())) {
                bookings.add(new Booking("Hotel", hotel.toString(), hotel.getPricePerNight()));
                System.out.println("Hotel booked successfully!");
                return;
            }
        }
        System.out.println("Hotel not found.");
    }

    // View all bookings made by the current user
    private void viewBookings() {
        if (currentUser == null) {
            System.out.println("Please log in first.");
            return;
        }
        System.out.println("Your Bookings:");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    // Logout the current user
    private void logout() {
        if (currentUser != null) {
            System.out.println("Logged out successfully.");
            currentUser = null;
        } else {
            System.out.println("No user is logged in.");
        }
    }
}
