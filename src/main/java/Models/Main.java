package Models;

import Models.Base.EmployeeRole;
import Models.Base.ObjectPlus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {

        // Dodawanie pokoi
        for (int i = 1; i <= 10; i++) {
            Room room = new Room(50 + i, i % 2 == 0); // Przykładowe wartości dla każdego pokoju
            room.setName("Room " + i);
        }

            Person person1 = new Person("John", "Doe", "123-456-789", EmployeeRole.OCHRONA);
//
            Event event1 = new Event("Conference", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 3), 100, person1);
            Room room1 = Room.findAvailableRooms(LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 3)).get(0); // Załóżmy, że wybraliśmy pierwszy dostępny pokój
//            event1.setRoom(room1, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 3));

            // Dodawanie drugiego wydarzenia
            Person person2 = new Person("Jane", "Smith", "987-654-321", EmployeeRole.KELNER);
            Event event2 = new Event("Workshop", LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 12), 50, person2);
            Room room2 = Room.findAvailableRooms(LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 12)).get(0); // Załóżmy, że wybraliśmy pierwszy dostępny pokój
//            event2.setRoom(room2, LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 12));

            saveExtents("extents.ser");
            ObjectPlus.showExtent(Person.class);
            ObjectPlus.showExtent(Employee.class);
            ObjectPlus.showExtent(Room.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void saveExtents(String filename) throws IOException {
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
//    }
//
//
//}
