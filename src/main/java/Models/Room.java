package Models;
import Models.Base.ObjectPlusPlus;

import java.util.*;
import java.time.LocalDate;

public class Room extends ObjectPlusPlus {
    private static String roleNameRoom = "room";
    private static String roleNameEvent = "event";

    //private static List<Room> roomExtent = new ArrayList<>();
    private static int roomIDcounter = 1;
    private int roomID;
    private String name;
    private int maxPersonCapacity;
    private boolean isProjectorInstalled;
    private List<Map<String, Object>> reservations;

    public Room(int maxPersonCapacity,boolean isProjectorInstalled){
        super();
        if(maxPersonCapacity > 0){
            setRoomID(roomIDcounter++);
            setIsProjectorInstalled(isProjectorInstalled);
            this.reservations = new ArrayList<>();
            setMaxPersonCapacity(maxPersonCapacity);
//            roomExtent.add(this);
        }else throw new IllegalArgumentException("MaxPersonCapacity musi być większe od 0");

    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPersonCapacity() {
        return maxPersonCapacity;
    }

    public void setMaxPersonCapacity(int maxPersonCapacity) {
        this.maxPersonCapacity = maxPersonCapacity;
    }

    public boolean getIsProjectorInstalled() {
        return isProjectorInstalled;
    }

    public void setIsProjectorInstalled(boolean projectorInstalled) {
        isProjectorInstalled = projectorInstalled;
    }

    public List<Map<String, Object>> getReservations() {
        return new ArrayList<>(reservations);
    }

    public void setReservation(Event event, LocalDate dateFrom, LocalDate dateTo){
        Map<String,Object> reservation = new HashMap<>();
        reservation.put("event", event);
        reservation.put("dateFrom", dateFrom);
        reservation.put("dateTo", dateTo);
        reservations.add(reservation);
        if(!event.hasReservation(this, dateFrom,dateTo)){
            event.setRoom(this,dateFrom,dateTo);
        }
        this.addLink(roleNameEvent,roleNameRoom,event);
    }
    public boolean hasReservation(Event event, LocalDate dateFrom, LocalDate dateTo) {
        for (Map<String, Object> reservation : reservations) {
            Event reservationEvent = (Event) reservation.get("event");
            LocalDate reservationDateFrom = (LocalDate) reservation.get("dateFrom");
            LocalDate reservationDateTo = (LocalDate) reservation.get("dateTo");

            if (reservationEvent.equals(event) &&
                    reservationDateFrom.equals(dateFrom) &&
                    reservationDateTo.equals(dateTo)) {
                return true;
            }
        }
        return false;
    }

//    public void removeReservation(Event event, LocalDate dateFrom, LocalDate dateTo){
//        Map<String,Object> reservation = new HashMap<>();
//        reservation.put("event", event);
//        reservation.put("dateFrom", dateFrom);
//        reservation.put("dateTo", dateTo);
//        reservations.remove(reservation);
//        if(event.getReservedRooms().contains(reservation)){
//            event.releaseRoom(this,dateFrom,dateTo);
//        }
//    }

    public static List<Room> findAvailableRooms(LocalDate dateFrom, LocalDate dateTo) {
        try{
            Iterable<Room> roomExtent = getExtent(Room.class);

            List<Room> availableRooms = new ArrayList<>();
            for (Room room : roomExtent) {
                if (room.isRoomAvailable(dateFrom,dateTo)) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }catch (Exception e){
            throw new IllegalArgumentException("Problem");
        }
    }
    public boolean isRoomAvailable(LocalDate dateFrom, LocalDate dateTo){
        boolean isAvailable = true;
        for (Map<String, Object> reservation : this.getReservations()) {
            LocalDate reservedFrom = (LocalDate) reservation.get("dateFrom");
            LocalDate reservedTo = (LocalDate) reservation.get("dateTo");
            if (!(dateTo.isBefore(reservedFrom) || dateFrom.isAfter(reservedTo))) {
                isAvailable = false;
                break;
            }
        }
        return isAvailable;
    }

    public boolean isRoomSuitableForEvent(int maxAttendeeNumber, LocalDate dateFrom, LocalDate dateTo){
        boolean isRoomSuitable = false;
        if(maxAttendeeNumber <= this.getMaxPersonCapacity() && isRoomAvailable(dateFrom,dateTo)){
            isRoomSuitable = true;
        }
        return isRoomSuitable;
    }

    public String toString() {
        return "Sala( " +
                "ID " + roomID +
                ", '" + name + '\'' +
                ", ilość osób " + maxPersonCapacity +
                ", projector " + (isProjectorInstalled? "Tak)":"Nie)");
    }
}

