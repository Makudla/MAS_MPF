package Models;

import Models.Base.ObjectPlusPlus;
import Models.Base.TicketType;

import java.util.ArrayList;

public class Attendee extends ObjectPlusPlus {
    private static String roleNamePerson = "person";
    private static String roleNameTicket = "ticket";
    private static String roleNameAttendee = "specializationAttendee";

    private static int attIDcounter = 1;
    private String attendeeID = "att";
    private ArrayList<Ticket> tickets;
    private boolean marketingInfoAgreement;

    public Attendee(boolean marketingInfoAgreement, Person person){
        super();
        this.tickets = new ArrayList<>();
        this.setMarketingInfoAgreement(marketingInfoAgreement);
        this.addLink(roleNamePerson,roleNameAttendee,person);
    }
    public boolean areTicketsInitialized(){
        return this.tickets != null;
    }

    public void buyTicket(TicketType ticketType){
        Ticket ticket = new Ticket(ticketType, this);
        setTicket(ticket);
    }


    public void setTicket(Ticket ticket) {
        if (!tickets.contains(ticket)) {
            tickets.add(ticket);
            ticket.addLink(roleNameTicket,roleNameAttendee,ticket);
        }
    }

    public void removeTicket(Ticket ticket) throws Exception {
        if (tickets.contains(ticket) && ticket != null){
            tickets.remove(ticket);
            this.deleteLink(roleNameTicket,ticket);
            ticket.removeAttendee();
        } else {
            throw new IllegalArgumentException("Biletu nie ma na liście lub jest null");
        }
    }

    public boolean getMarketingInfoAgreement() {
        return marketingInfoAgreement;
    }


    public void setMarketingInfoAgreement(boolean marketingInfoAgreement) {
        this.marketingInfoAgreement = marketingInfoAgreement;
    }

    public ArrayList<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public String toString(){
        return "Zgoda na informacje marketingowe: " + marketingInfoAgreement;
    }
}

