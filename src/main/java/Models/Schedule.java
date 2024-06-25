package Models;

import Models.Base.ObjectPlusPlus;

import java.time.LocalDate;

public class Schedule extends ObjectPlusPlus {
    private static String roleNameEvent = "event";
    private static String roleNameSchedule = "schedule";

    private LocalDate date;

    public Schedule(Event event, LocalDate date){
        this.setDate(date);
        this.addLink(roleNameEvent,roleNameSchedule,event);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
