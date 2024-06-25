package Org;
import Models.Event;
import Models.Base.ObjectPlus;
import Models.Room;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventApp {
    private static List<Event> events = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {
        // Dodaj przykładowe wydarzenia
        try {
            loadExtents("extents.ser");
            Iterable<Event> eventsExtent = ObjectPlus.getExtent(Event.class);
            for (Event event : eventsExtent) {
                events.add(event);
            }
            Iterable<Room> roomExtent = ObjectPlus.getExtent(Room.class);
            for (Room room : roomExtent) {
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Uruchomienie GUI
        SwingUtilities.invokeLater(EventApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        new EventSelectionWindow(events);
    }
    public static Event getEventById(String eventId) {
        for (Event event : events) {
            if (event.getEventID().equals(eventId)) {
                return event;
            }
        }
        return null; // Jeśli nie znaleziono wydarzenia o podanym ID
    }
    public static Room getRoomById(int roomId) {
        for (Room room : rooms) {
            if (room.getRoomID() == roomId) {
                return room;
            }
        }
        return null; // Jeśli nie znaleziono wydarzenia o podanym ID
    }


    public static void openEventDetailsWindow(Event event) {
        new EventDetailsWindow(event);
    }
    public static void openRoomSelectionWindow(Event event) throws ClassNotFoundException {
        Iterable<Room> roomExtent = ObjectPlus.getExtent(Room.class);
        List<Room> roomList = new ArrayList<>();
        for (Room room : roomExtent) {
            roomList.add(room);
        }
        List<Room> availableRooms = Room.findAvailableRooms(event.getStartDate(),event.getFinishDate());

        new RoomSelectionWindow(availableRooms,event); // Przykład użycia klasy RoomSelectionWindow, która obsługuje wybór sali
    }

    public static void saveExtents(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            ObjectPlus.writeExtents(out);
        }
    }

    private static void loadExtents(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            ObjectPlus.readExtents(in);
        }
    }


}
