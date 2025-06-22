package MainAPDP;


import java.util.Scanner;

// Customer class as before
class Customer {
    String name;
    
    Customer(String name) {
        this.name = name;
    }
}

// Accommodations interface and its implementations remain the same

interface Accommodations {
    void bookAccommodation(Customer customer);
}

class StandardAccommodation implements Accommodations {
    public void bookAccommodation(Customer customer) {
        System.out.println(customer.name + " booked a Standard Accommodation");
    }
}

class DeluxeAccommodation implements Accommodations {
    public void bookAccommodation(Customer customer) {
        System.out.println(customer.name + " booked a Deluxe Accommodation");
    }
}

class SuiteAccommodation implements Accommodations {
    public void bookAccommodation(Customer customer) {
        System.out.println(customer.name + " booked a Suite Accommodation");
    }
}

// BillingMethod interface and its implementations remain the same

interface BillingMethod {
    void processBilling(int amount, Customer customer);
}

class PaypalBilling implements BillingMethod {
    String email;
    
    PaypalBilling(String email) {
        this.email = email;
    }
    
    public void processBilling(int amount, Customer customer) {
        System.out.println(customer.name + " paid $" + amount + " using PayPal (" + email + ")");
    }
}

class CardBilling implements BillingMethod {
    String cardNumber;
    
    CardBilling(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public void processBilling(int amount, Customer customer) {
        System.out.println(customer.name + " paid $" + amount + " using Card (" + cardNumber + ")");
    }
}

// HotelBooking class remains unchanged

class HotelBooking {
    Accommodations createAccommodation(String accommodationType) {
        if (accommodationType.equalsIgnoreCase("Standard")) return new StandardAccommodation();
        if (accommodationType.equalsIgnoreCase("Deluxe")) return new DeluxeAccommodation();
        if (accommodationType.equalsIgnoreCase("Suite")) return new SuiteAccommodation();
        return new StandardAccommodation(); // Default to standard accommodation
    }
    
    BillingMethod createBillingMethod(String billingType, Scanner scanner) {
        if (billingType.equalsIgnoreCase("PayPal")) {
            System.out.print("Enter PayPal email: ");
            return new PaypalBilling(scanner.next());
        }
        if (billingType.equalsIgnoreCase("Card")) {
            System.out.print("Enter card number: ");
            return new CardBilling(scanner.next());
        }
        return null;
    }
}

// Facade Pattern class to simplify the booking process

class BookingFacade {
    private HotelBooking hotelBooking;
    
    public BookingFacade() {
        hotelBooking = new HotelBooking();
    }

    // Simplified method to handle booking
    public void bookHotel(String accommodationType, String billingType, String customerName, int amount, Scanner scanner) {
        Customer customer = new Customer(customerName);

        // Booking accommodation
        Accommodations accommodation = hotelBooking.createAccommodation(accommodationType);
        accommodation.bookAccommodation(customer);
        
        // Processing payment
        BillingMethod billingMethod = hotelBooking.createBillingMethod(billingType, scanner);
        if (billingMethod != null) {
            billingMethod.processBilling(amount, customer);
        }
    }
}

public class ReservationDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a facade for booking
        BookingFacade bookingFacade = new BookingFacade();
        
        // Get customer details
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();
        
        // Get accommodation and billing details
        System.out.print("Enter accommodation type (Standard/Deluxe/Suite): ");
        String accommodationType = scanner.next();
        
        System.out.print("Enter billing method (PayPal/Card): ");
        String billingType = scanner.next();
        
        System.out.print("Enter billing amount: $");
        int amount = scanner.nextInt();
        
        // Use the facade to process the booking
        bookingFacade.bookHotel(accommodationType, billingType, customerName, amount, scanner);
        
        scanner.close();
    }
}
