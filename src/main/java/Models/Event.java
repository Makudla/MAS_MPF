package Models;

import Models.Base.EventStatus;
import Models.Base.ObjectPlusPlus;

import java.time.LocalDate;
import java.util.*;

public class Event extends ObjectPlusPlus {
    public static int eventIDCounter = 1;
    private String eventID = "ev";
    private static String roleNameEmployee = "specializationEmployee";
    private static String roleNameEvent = "event";
    private static String roleNameRoom = "room";
    private static String roleNameEventSupport = "eventSupport";
    private static String roleNameSchedule = "schedule";

    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas pulvinar mi convallis neque elementum pulvinar. Donec laoreet mauris eros, at viverra diam lobortis ac. ";

    private EventStatus status;
    private int maxAttendeeNumber;

    private List<Map<String, Object>> reservedRooms = new ArrayList<>();// asocjacja z Room

    private ArrayList<EventSupport> receivedSupports = new ArrayList<>();//do Asocjacji z atrybutem
    private Map<String, Employee> assignedEmployees = new TreeMap<>(); //do asocjacji kwalifikowanej
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public Event(String name, LocalDate startDate, LocalDate finishDate, int maxAttendeeNumber, Person person) throws Exception {
        super();
        if(!(name.isEmpty() || startDate == null || finishDate == null || maxAttendeeNumber < 0) && person.hasEmployeeRole()){
            this.setName(name);
            this.setStartDate(startDate);
            this.setFinishDate(finishDate);
            this.eventID += eventIDCounter++;
            this.setMaxAttendeeNumber(maxAttendeeNumber);
            this.assignEmployee(person);
        }else{
            throw new IllegalArgumentException("Dane nie mogą być puste");
        }
    }


    public void setRoom(Room room, LocalDate dateFrom, LocalDate dateTo){
        Map<String,Object> reservation = new HashMap<>();
        reservation.put("room", room);
        reservation.put("dateFrom", dateFrom);
        reservation.put("dateTo", dateTo);
        reservedRooms.add(reservation);
        if(!room.hasReservation(this, dateFrom, dateTo)){
            room.setReservation(this, dateFrom, dateTo);
        }
        this.addLink(roleNameRoom, roleNameEvent, room);
    }

    public boolean hasReservation(Room room, LocalDate dateFrom, LocalDate dateTo) {
        for (Map<String, Object> reservation : reservedRooms) {
            Room reservationRoom = (Room) reservation.get("room");
            LocalDate reservationDateFrom = (LocalDate) reservation.get("dateFrom");
            LocalDate reservationDateTo = (LocalDate) reservation.get("dateTo");

            if (reservationRoom.equals(room) &&
                    reservationDateFrom.equals(dateFrom) &&
                    reservationDateTo.equals(dateTo)) {
                return true;
            }
        }
        return false;
    }

    public void addReceivedSupport(EventSupport receivedSupport) {
        if (!this.receivedSupports.contains(receivedSupport)) {
            this.receivedSupports.add(receivedSupport);
            this.addLink(roleNameEventSupport, roleNameEvent, receivedSupport);
        }
    }

    public ArrayList<EventSupport> getReceivedSupports() {
        return new ArrayList<>(receivedSupports);
    }

    public void assignEmployee(Person person) {
        //sprawdzenie czy pracownik jest juz na liście i czy nie jest pusty
        try {
            ObjectPlusPlus[] obj = person.getLinks(roleNameEmployee);
            Employee e = (Employee) obj[0];
            if (person != null && !assignedEmployees.containsKey(e.getEmployeeID())) {
                this.addEmployeeID(e);
            } else {
                throw new IllegalArgumentException("Pracownik nie może być null i nie może już znajdować się na liście");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEmployeeID(Employee employee) {//kwalifikowana
        if (employee != null) {
            assignedEmployees.put(employee.getEmployeeID(), employee);
            this.addLink(roleNameEmployee, roleNameEvent, employee, employee.getEmployeeID());
        } else {
            throw new IllegalArgumentException("Person nie może być null!");
        }
    }

    public void addSchedule(LocalDate date) {
        Schedule schedule = new Schedule(this, date);
        this.addLink(roleNameSchedule, roleNameEvent, schedule);
    }

    public ArrayList<Employee> getAssignedEmployeesIDs() {
        return new ArrayList<>(assignedEmployees.values());
    }

    public List<Map<String, Object>> getReservedRooms() {
        return new ArrayList<>(reservedRooms);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enum<EventStatus> getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public int getMaxAttendeeNumber() {
        return maxAttendeeNumber;
    }

    public void setMaxAttendeeNumber(int maxAttendeeNumber) {
        this.maxAttendeeNumber = maxAttendeeNumber;
    }

    public int getDurationInDays() {
        return (int) (getStartDate().toEpochDay() - getFinishDate().toEpochDay()) + 1;
    }

    public String getEventID() {
        return this.eventID;
    }

    public String toString() {
        return "EventID: " + eventID + ", " + name;
    }

    public void deleteEvent() throws Exception {
        // Usuwanie połączeń z innymi obiektami
        deleteIfLinkedTo(roleNameSchedule);
        deleteIfLinkedTo(roleNameEventSupport);
        deleteIfLinkedTo(roleNameEmployee);
        deleteIfLinkedTo(roleNameRoom);
    }

    private void deleteIfLinkedTo(String roleName) throws Exception {
        if (anyLink(roleName)) {
            ObjectPlusPlus[] linkedObjects = getLinks(roleName);
            for (ObjectPlusPlus linkedObject : linkedObjects) {
                deleteLink(roleName, linkedObject);
            }
        }
    }
}
