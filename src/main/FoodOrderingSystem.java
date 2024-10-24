package main;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Customer {
    private String customerId;
    private String username;
    private String password;

    public Customer(String customerId, String username, String password) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Order {
    private String orderId;
    private String customerId;
    private String orderType;
    private String item;

    public Order(String orderId, String customerId, String orderType, String item) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderType = orderType;
        this.item = item;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDetails() {
        return "Order ID: " + orderId + ", Type: " + orderType + ", Item: " + item;
    }
}

public class FoodOrderingSystem {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Order> orders = new HashMap<>();
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    private String generateUniqueId(String prefix) {
        long timestamp = System.currentTimeMillis();
        int randomNum = random.nextInt(1000);
        return prefix + "-" + timestamp + "-" + randomNum;
    }

    public void registerCustomer(String username, String password) {
        // Generate customer ID only when registering
        String customerId = generateUniqueId("CUST");
        Customer newCustomer = new Customer(customerId, username, password);
        customers.put(username, newCustomer); // Use username as the key for easier lookup
    }

    public Customer login(String username, String password) {
        Customer customer = customers.get(username);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null; // Login failed
    }

    public Order placeOrder(Customer customer, String orderType) {
        System.out.print("Enter the item you want to order: ");
        String item = scanner.nextLine();
        String orderId = generateUniqueId("ORDER");
        Order order = new Order(orderId, customer.getCustomerId(), orderType, item);
        orders.put(orderId, order);
        return order;
    }

    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();

        // Get user input for registration
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine(); // Password input is now hidden
        system.registerCustomer(username, password);
        
        // User logs in
        System.out.print("Login username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Login password: ");
        String loginPassword = scanner.nextLine(); // Password input is now hidden

        Customer loggedInCustomer = system.login(loginUsername, loginPassword);
        if (loggedInCustomer != null) {
            System.out.println("Login successful for Customer ID: " + loggedInCustomer.getCustomerId());

            // Choose dining or takeaway
            System.out.print("Choose order type (dining/takeaway): ");
            String orderType = scanner.nextLine().toLowerCase();

            if (orderType.equals("dining") || orderType.equals("takeaway")) {
                Order order = system.placeOrder(loggedInCustomer, orderType);
                System.out.println("Order placed: " + order.getOrderDetails());
            } else {
                System.out.println("Invalid order type!");
            }
        } else {
            System.out.println("Login failed!");
        }

        scanner.close();
    }
}