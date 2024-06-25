package Models;

import Models.Base.ObjectPlusPlus;

import java.util.ArrayList;

public class Sponsor extends ObjectPlusPlus {
    private int nip;
    private String nazwa;
    private static String roleNameSponsor = "sponsor";
    private static String roleNameEventSupport = "eventSupport";

    private ArrayList<EventSupport> eventSupports;//kolekcja klas pośredniczących

    public Sponsor(int nip, String nazwa) {
        if (nip <= 0 || nazwa.isEmpty()) {
            throw new IllegalArgumentException("Nieprawidłowe dane - nazwa nie może być pusta, NIP musi być w odpowiednim formacie.");
        }
        this.nip = nip;
        this.nazwa = nazwa;
    }

    public void supportNewEvent(Event event, double amount) {
        try {
            // Utworzenie połączenia z EventSupport
            this.addLink(roleNameEventSupport, roleNameSponsor, new EventSupport(amount, this, event));
        } catch (Exception e) {
            e.printStackTrace(); // Obsługa wyjątku w zależności od potrzeb
        }
    }
    public void addPromotionalMaterialToSupportedEvent(Event event, String material){
        for (EventSupport es : getEventSupports()) {
            if (es.getEvent().equals(event)) {
                es.addPromotionalMaterial(material);
                break;
            }
        }
    }

    public ArrayList<EventSupport> getEventSupports() {
        return new ArrayList<>(eventSupports);
    }

    public String toString(){
        return  nazwa + ", " + "NIP: " + nip;
    }
}

