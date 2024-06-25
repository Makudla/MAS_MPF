package Models;
import Models.Base.ObjectPlusPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Address extends ObjectPlusPlus {

    private String street;
    private int building;
    private int apartment;
    private String zipCode;
    private String city;

    private List<Person> locators;


    public Address(String street, int building, String zipCode, String city) {
        super();
        if (!(street.isEmpty() || building < 1 || city.isEmpty()) && isZipCodeValidFormat(zipCode)){
            setCity(city);
            setStreet(street);
            setZipCode(zipCode);
            setBuilding(building);
        }else throw new IllegalArgumentException("Dane są niepoprawne!");
    }
    public Address(String street, int building,int apartment, String zipCode, String city) {
        super();
        if (!(street.isEmpty() || building < 1 || apartment <0 || city.isEmpty()) && isZipCodeValidFormat(zipCode)){
            setCity(city);
            setStreet(street);
            setZipCode(zipCode);
            setBuilding(building);
            setApartment(apartment);
        }else throw new IllegalArgumentException("Dane są niepoprawne!");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static boolean isZipCodeValidFormat(String input) {
        // Wyrażenie regularne sprawdzające format 00-000
        String regex = "^\\d{2}-\\d{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public List<Person> getLocators() {
        return new ArrayList<>(locators);
    }

    public void setLocator(Person person) {
        if(person != null){
            this.locators.add(person);
            if(person.getAddress() == null){
                person.setAddress(getStreet(),getBuilding(),getApartment(),getZipCode(),getCity());
            }
        }else throw new IllegalArgumentException("Osoba ma wartość null!");
    }

    public String toString() {
        return String.format("Adres: %s %d/%d, %s %s",
                street, building, apartment, zipCode, city);
    }
}

