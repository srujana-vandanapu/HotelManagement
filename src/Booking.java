public class Booking {
    private static int idCounter = 100;
    private int bookingId;
    private String roomType;
    private int roomNumber;
    private String guestName;

    public Booking(String guestName, String roomType, int roomNumber){
        this.bookingId = idCounter++;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
