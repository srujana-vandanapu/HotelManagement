import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("Hotel reservation");
            System.out.println("1. bookroom");
            System.out.println("2. cancel Booking");
            System.out.println("3. view bookings");
            System.out.println("4. check availability");
            System.out.println("5. exxit");
            System.out.println("enter your choice");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    hotel.bookRoom();
                    break;
            
                case 2:
                    hotel.cancelBooking();
                    break;
            
                case 3:
                    hotel.viewBookings();
                    break;
            
                case 4:
                    hotel.checkAvailability();
                    break;
            
                case 5:
                    System.out.println("bye bye");
                    return;
                default:
                    System.out.println("invalid choice");
            }
        }
    }
}
