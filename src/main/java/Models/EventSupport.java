package Models;

import Models.Base.ObjectPlusPlus;

import java.util.ArrayList;

public class EventSupport extends ObjectPlusPlus {
    private static String roleNameSponsor = "sponsor";
    private static String roleNameEventSupport = "eventSupport";
    private static String roleNameEvent = "event";

    private double amount;
    private ArrayList<String> promotionalMaterials;

    private Sponsor sponsor;
    private Event event;

    public EventSupport(double amount, Sponsor sponsor, Event event){
        if (amount <= 0 || sponsor == null || event == null) {
            throw new IllegalArgumentException("Nieprawidłowe dane - kwota musi być większa od zera, sponsor i wydarzenie nie mogą być null.");
        }
        setAmount(amount);
        setSponsor(sponsor);
        this.addLink(roleNameSponsor,roleNameEventSupport,sponsor);
        setEvent(event);
        this.addLink(roleNameEvent,roleNameEventSupport,event);
        this.promotionalMaterials = new ArrayList<>();
    }
    private void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public void deleteSponsor(){
        this.sponsor = null;
    }

    public void deleteEvent(){
        this.event = null;
    }

    private void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    private void setEvent(Event event){
        this.event = event;
    }
    public Event getEvent() {
        return event;
    }

    public void addPromotionalMaterial(String promotionalMaterial) {
        if(!promotionalMaterial.isEmpty()){
            this.promotionalMaterials.add(promotionalMaterial);
        }
    }

    public ArrayList<String> getPromotionalMaterials() {
        return new ArrayList<>(promotionalMaterials);
    }
}

