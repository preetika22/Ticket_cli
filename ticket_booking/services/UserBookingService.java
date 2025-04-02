package org.ticket_booking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.ticket_booking.entities.Ticket;
import org.ticket_booking.entities.Train;
import org.ticket_booking.entities.User;
import org.ticket_booking.utils.UserServiceUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class UserBookingService {
    private User user;
    public List<Ticket> ticketList = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userList = new ArrayList<>();
    private static final String USER_PATH = "C:/Users/Preetika/fullstack/IRCTC/app/src/main/java/org/ticket_booking/localDB/users.json";


    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUser();
    }

    public UserBookingService() throws IOException {
        loadUser();
    }


    public void loadUser() throws IOException {
        try {
            File users = new File(USER_PATH);
            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});

        } catch (Exception e) {
            System.out.println("Invalid Attempt to load users: " + e.getMessage());
        }
    }

    public boolean login(User user) {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtils.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public boolean signUp(User user) {
        try {
            userList.add(user);
            saveUserList();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public  void saveUserList() throws IOException{
        try{
            objectMapper.writeValue(new File(USER_PATH), userList);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void fetchBookings(){
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtils.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
    }

    public void createNewTicket(String start, String destination, String dateOfTravel, Train train) throws IOException {
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtils.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        System.out.println(userFetched.get().getName());
        if (userFetched.isPresent()){
            Ticket newTicket = new Ticket(UUID.randomUUID().toString(), userFetched.get().getUserId(), start, destination, dateOfTravel, train);
            userFetched.get().addTicket(newTicket);
        }
        saveUserList();
        System.out.println("Ticket saved");
    }



    public Boolean cancelBooking(String ticketId) throws IOException {

        Optional<User> userFetched = userList.stream().filter(user1 ->
                user1.getName().equals(user.getName()) &&
                        UserServiceUtils.checkPassword(user.getPassword(), user1.getHashedPassword())
        ).findFirst();

        if (userFetched.isPresent()) {
            User realUser = userFetched.get(); // Get the actual user object from the list
            if (realUser.removeTicket(ticketId)) {
                saveUserList();  // Save updated user data to JSON
                return Boolean.TRUE;
            }
        }

        System.out.println("Ticket cancellation failed. Ticket not found.");
        return Boolean.FALSE;
    }


    public List<Train> getTrains(String start, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(start, destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeat();
    }



    public boolean bookSeat(Train train, int row, int seat){
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeat();
            if(row >= 0 && row < seats.size() && seat >=0 && seat < seats.get(row).size()){
                if(seats.get(row).get(seat) == 0){
                    seats.get(row).set(seat, 1);
                    train.setSeat(seats);
                    trainService.updateTrain(train);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        } catch (Exception e){
            return  Boolean.FALSE;
        }
    }

}
