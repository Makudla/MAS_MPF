package Models;

import Models.Base.ObjectPlusPlus;

public class Exhibitor extends ObjectPlusPlus {
    private static String roleNamePerson = "person";
    private static String roleNameExhibitor = "specializationExhibitor";

    private static int exhIDcounter = 1;
    private String exhibitorID = "exh";
    private static double baseParticipationFee = 300;

    private static double vipPackageCost = 150;
    private static double lunchTicketCost = 25;
    private boolean vipPackage;
    private int lunchTicketNumber;

    private Person person;

    public Exhibitor(int lunchTicketNumber, Person person){
        super();
//        this.setPerson(person);
        this.setLunchTicketNumber(lunchTicketNumber);
        this.addLink(roleNamePerson,roleNameExhibitor,person);
        exhibitorID += exhIDcounter++;
    }

//    public void setPerson(Person person) {
//        this.person = person;
//        if(person.getExhibitorDetails() == null){
//            person.setExhibitorDetails(this);
//        }
//    }

    public Person getPerson() {
        return person;
    }

    public static double getBaseParticipationFee() {
        return baseParticipationFee;
    }

    public static void setBaseParticipationFee(double baseParticipationFee) {
        Exhibitor.baseParticipationFee = baseParticipationFee;
    }

    public static double getVipPackageCost() {
        return vipPackageCost;
    }

    public static void setVipPackageCost(double vipPackageCost) {
        Exhibitor.vipPackageCost = vipPackageCost;
    }

    public static double getLunchTicketCost() {
        return lunchTicketCost;
    }

    public static void setLunchTicketCost(double lunchTicketCost) {
        Exhibitor.lunchTicketCost = lunchTicketCost;
    }

    public boolean isVipPackage() {
        return vipPackage;
    }

    public void setVipPackage(boolean vipPackage) {
        this.vipPackage = vipPackage;
    }

    public int getLunchTicketNumber() {
        return lunchTicketNumber;
    }

    public void setLunchTicketNumber(int lunchTicketNumber) {
        if(lunchTicketNumber>0){
            this.lunchTicketNumber += lunchTicketNumber;
        }else throw new IllegalArgumentException("Liczba biletów musi być większa od 0");
    }

    public String toString(){
        return "Indywidualny koszt uczestnictwa: " + getExhibitorOveralFee() + " PLN";
    }

    protected double getExhibitorOveralFee(){

        double exhibitorOveralFee = getBaseParticipationFee()+ getLunchTicketNumber() * getLunchTicketCost();
        if(isVipPackage()){
            exhibitorOveralFee += getVipPackageCost();
        }
        return exhibitorOveralFee;
    }
}

