import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        buildRooms();
        loadBookingsFromDB();
    }

    private void buildRooms() {
        for (int i = 1; i <= 5; i++) {
            rooms.add(new Room(i, "Single Bedded"));
        }

        for (int i = 6; i <= 10; i++) {
            rooms.add(new Room(i, "Double Bedded"));
        }

        for (int i = 11; i <= 15; i++) {
            rooms.add(new Room(i, "Triple Bedded"));
        }
    }

    public void bookRoom() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Guest Name");
        String name = sc.nextLine();
        System.out.println("Enter Room type");
        String type = sc.nextLine();

        boolean booked = false;
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(type) && !room.isBooked()) {
                room.bookRoom(true);
                Booking booking = new Booking(name, type, room.getRoomNumber());
                bookings.add(booking);
                booked = true;
                try (Connection conn = DBHelper.getConnection()) {
                    String sql = "INSERT INTO bookings (guest_name, room_number, room_type) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, name);
                    stmt.setInt(2, room.getRoomNumber());
                    stmt.setString(3, type);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Database error: " + e.getMessage());
                }

                System.out.println("Booked successfully. Booking ID: " + booking.getBookingId());
                break;
            }
        }
        if (!booked) {
            System.out.println("NO rooms available");
        }

    }

    public void cancelBooking() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter booking id");
        int id = sc.nextInt();

        boolean found = false;
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            if (b.getBookingId() == id) {
                for (Room room : rooms) {
                    if (room.getRoomNumber() == b.getRoomNumber()) {
                        room.bookRoom(false);
                        break;
                    }
                }

                try (Connection conn = DBHelper.getConnection()) {
                    String sql = "DELETE FROM bookings WHERE room_number = ? AND guest_name = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, b.getRoomNumber());
                    stmt.setString(2, b.getGuestName());
                    stmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Database error: " + e.getMessage());
                }
                bookings.remove(i);
                found = true;
                System.out.println("Booking cancelled");
                break;
            }
        }

        if (!found) {
            System.out.println("bookimbg id not found");
        }
    }

    public void viewBookings() {
        System.out.println("Current Bookings from Database:");

        try (Connection conn = DBHelper.getConnection()) {
            String sql = "SELECT id, guest_name, room_number, room_type FROM bookings";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String type = rs.getString("room_type");

                System.out.println("ID: " + id + ", Guest: " + name + ", Room: " + roomNumber + " (" + type + ")");
                found = true;
            }

            if (!found) {
                System.out.println("No bookings found.");
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void loadBookingsFromDB() {
        try (Connection conn = DBHelper.getConnection()) {
            String sql = "SELECT guest_name, room_number, room_type FROM bookings";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String roomType = rs.getString("room_type");

                Booking booking = new Booking(guestName, roomType, roomNumber);
                bookings.add(booking);

                for (Room room : rooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        room.bookRoom(true);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading bookings from DB: " + e.getMessage());
        }
    }

    public void checkAvailability() {
        Map<String, Integer> available = new HashMap<>();
        available.put("Single Bedded", 0);
        available.put("Double Bedded", 0);
        available.put("Triple Bedded", 0);

        for (Room room : rooms) {
            if (!room.isBooked()) {
                available.put(room.getType(), available.get(room.getType()) + 1);
            }
        }

        System.out.println("available rooms");

        for (String type : available.keySet()) {
            System.out.println(type + " " + available.get(type));
        }
    }
}
