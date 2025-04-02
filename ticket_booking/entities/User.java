package org.ticket_booking.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;
    private String userId;

    public User(String name, String password, String hashedPassword, List<Ticket> ticketsBooked, String userId){
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }

    public User(){}

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {

        return ticketsBooked;
    }

    public boolean removeTicket(String ticketId) {
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return false;
        }

        // Check tickets before removal
        System.out.println("Checking tickets for cancellation...");
        boolean removed = this.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(ticketId));

        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been removed.");
        } else {
            System.out.println("No ticket found with ID " + ticketId);
        }

        return removed;
    }



    public String getUserId(){
        return userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

   public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = (ticketsBooked != null) ? ticketsBooked : new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        if (ticket != null) {
            this.ticketsBooked.add(ticket);
        }
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void printTickets(){
        for (int i = 0; i<ticketsBooked.size(); i++){
            System.out.println(ticketsBooked.get(i).getTicketInfo());
            }
        if(ticketsBooked.isEmpty()){
            System.out.println("no tickets");
        }
    }


}
