package Models;

import Models.Base.EmployeeRole;
import Models.Base.ObjectPlusPlus;

public class Person extends ObjectPlusPlus {
    //private static List<Person> personExtent = new ArrayList<>();//ekstensja
    private static String roleNameEmployee = "specializationEmployee";
    private static String roleNameExhibitor = "specializationExhibitor";
    private static String roleNameAttendee = "specializationAttendee";
    private static String roleNameAddress = "address";
    private static String roleNamePerson = "person";

    private String firstName;
    private String lastName;
    private String email;
    private String telephone;

    private Address address;


    // Konstruktor dla pracownika
    public Person(String firstName, String lastName, String telephone, EmployeeRole role) throws Exception {
        super();
        if (!(lastName.isEmpty() || firstName.isEmpty() || telephone == null)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.telephone = telephone;
            addEmployee(role);
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane - wartości nie mogą być puste");
        }
    }
    public void addEmployee(EmployeeRole role) throws Exception {
        Employee e = new Employee(role, this);  // Inicjalizacja Employee z rolą
        this.addPart(roleNameEmployee, roleNamePerson, e);
    }


    // Konstruktor dla uczestnika
    public Person(String firstName, String lastName, String telephone, boolean marketingInfoAgreement) throws Exception {
        super();
        if (!(lastName.isEmpty() || firstName.isEmpty() || telephone == null)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.telephone = telephone;
            addAttendee(marketingInfoAgreement);
            //personExtent.add(this);
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane - wartości nie mogą być puste");
        }
    }
    public void addAttendee(boolean marketingInfoAgreement) throws Exception {
        Attendee a = new Attendee(marketingInfoAgreement,this);
        this.addPart(roleNameEmployee, roleNamePerson, a);
    }

    // Konstruktor dla wystawcy
    public Person(String firstName, String lastName, String telephone, int lunchTicketsNumber) throws Exception {
        super();
        if (!(lastName.isEmpty() || firstName.isEmpty() || telephone == null)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.telephone = telephone;
            addExhibitor(lunchTicketsNumber);
            //personExtent.add(this);
        } else {
            throw new IllegalArgumentException("Nieprawidłowe dane - wartości nie mogą być puste");
        }
    }

    public void addExhibitor(int lunchTicketsNumber) throws Exception {
        Exhibitor e = new Exhibitor(lunchTicketsNumber, this);  // Inicjalizacja AttendeeDetails
        this.addPart(roleNameEmployee, roleNamePerson, e);
    }

//    public static List<Person> getPersonExtent(){
//        return new ArrayList<>(personExtent);
//    }

    public String getContact() {
        if (getEmail() != null) {
            return "Email: " + getEmail() + ", Tel: " + getTelephone();
        } else {
            return "Tel: " + getTelephone();
        }
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public boolean hasEmployeeRole() throws Exception {
        try {
            // Pobranie powiązanych obiektów przez rolę roleNameEmployee
            ObjectPlusPlus[] obj = this.getLinks(roleNameEmployee);

            // Sprawdzenie, czy znaleziono powiązanie
            if (obj.length > 0 && obj[0] instanceof Employee) {
                // Jeśli pierwszy obiekt w tablicy jest typu Employee, zwróć wynik isRole()
                return ((Employee) obj[0]).isRole();
            } else {
                // Jeśli nie znaleziono powiązania lub pierwszy obiekt nie jest Employee
                throw new Exception("The object is not linked to an Employee!");
            }
        } catch (Exception e) {
            // Obsługa błędu związana z brakiem powiązań lub innym problemem
            throw new Exception("Error checking employee role: " + e.getMessage());
        }
    }

    public boolean hasAttendeeRole() throws Exception {
        // get an employee object
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameAttendee);
            return ((Attendee) obj[0]).areTicketsInitialized();
        } catch (Exception e) {
            // Probably this is an exception telling that this is not an employee
            // (we should introduce different exception classes)
            throw new Exception("The object is not an Attendee!");
        }
    }
    public boolean hasExhibitorRole() throws Exception {
        // get an exhibitor object
        try {
            ObjectPlusPlus[] obj = this.getLinks(roleNameExhibitor);
            return ((Exhibitor) obj[0]).getLunchTicketNumber() > 0;
        } catch (Exception e) {
            // Probably this is an exception telling that this is not an employee
            // (we should introduce different exception classes)
            throw new Exception("The object is not an Exhibitor!");
        }
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(String street, int building, int apartment, String zipCode, String city) {
        Address address = new Address(street,building,apartment,zipCode,city);
        this.address = address;
        this.addLink(roleNameAddress, roleNamePerson,address);
    }
    public void setAddress(String street, int building, String zipCode, String city) {
        Address address = new Address(street,building,zipCode,city);
        this.address = address;
        this.addLink(roleNameAddress, roleNamePerson,address);
    }

    public String toString() {
        StringBuilder info = new StringBuilder(this.getFirstName() + " " + getLastName() + " " + getContact());

        String[] roles = {roleNameEmployee, roleNameExhibitor, roleNameAttendee};
        for (String role : roles) {
            try {
                ObjectPlusPlus[] linkedObjects = this.getLinks(role);
                if (linkedObjects.length > 0) {
                    info.append(" ").append(linkedObjects[0].toString());
                }
            } catch (Exception e) {
                // Ignoring exception as we want to continue with the next role
            }
        }

        return info.toString();
    }
}

