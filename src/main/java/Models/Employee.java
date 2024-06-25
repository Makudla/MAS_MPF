package Models;


import Models.Base.EmployeeRole;
import Models.Base.ObjectPlusPlus;

import java.util.HashSet;

public class Employee extends ObjectPlusPlus {
    private static String roleNamePerson = "person";
    private static String roleNameEmployee = "specializationEmployee";
    private static String roleNameEvent = "event";

    private static int empIDcounter = 1;
    private String employeeID = "emp";
    private HashSet<EmployeeRole> roles = new HashSet<>();

    public Employee(EmployeeRole role, Person person) {
        super();
        this.setRole(role);
        this.addLink(roleNamePerson,roleNameEmployee,person);
        this.employeeID += empIDcounter++;
    }


    public void setRole(EmployeeRole role){
        if(role != null){
            roles.add(role);
        }else throw new IllegalArgumentException("Role nie może być null!");
    }

    public boolean isRole(){
        return !roles.isEmpty();
    }


    public HashSet<EmployeeRole> getRoles(){
        return new HashSet<>(roles);
    }


    public String getEmployeeID(){
        return employeeID;
    }
//
    public void removeRole(EmployeeRole role){
        roles.remove(role);
    }
//
    public void setEventAssignedTo(Event event){
        if( event != null){
            this.addLink(roleNameEvent,roleNameEmployee,event);
        }else throw new IllegalArgumentException("Event nie może być null!");
    }


    public String toString(){
        return "ID pracownika: " + getEmployeeID();
    }
}

