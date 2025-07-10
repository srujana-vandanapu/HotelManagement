public class Room {
    private int roomNumber;
    private String type;
    private boolean isBooked;

    public Room(int roomNumber, String type){
        this.type = type;
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public String getType(){
        return type;
    }

    public boolean isBooked(){
        return isBooked;
    }

    public void bookRoom(boolean book){
        isBooked = book;
    }
}
