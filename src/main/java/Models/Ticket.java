package Models;

import Models.Base.ObjectPlusPlus;
import Models.Base.TicketType;

public class Ticket extends ObjectPlusPlus {
    private static String roleNameAttendee = "specializationAttendee";
    private static String roleNameTicket = "ticket";

    private static int ticketIDCounter = 1;
    private String ticketID = "tic";
    private TicketType type;
    private Attendee attendee;

    public Ticket(TicketType ticketType, Attendee attendee){
        this.type = ticketType;
        this.addLink(roleNameAttendee,roleNameTicket,attendee);
        this.ticketID += ticketIDCounter++;
    }

    public double getPrice(){
        double price = 0;
        switch (type){
            case ULGOWY -> price = 100;
            case DARMOWY -> price = 0;
            case PREMIUM -> price = 300;
            case NORMALNY -> price = 200;
        }
        return price;
    }


    public String getTicketID() {
        return ticketID;
    }

    public TicketType getType() {
        return type;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void removeAttendee() throws Exception {
        if(this.attendee != null){
            Attendee temp = this.attendee;
            this.attendee = null;
            this.deleteLink(roleNameAttendee,temp);
            temp.removeTicket(this);
        }
    }

    public String toString(){
        return "NrBiletu: " + getTicketID() + ", cena: " + getPrice() + " PLN";
    }
}
