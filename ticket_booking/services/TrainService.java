package org.ticket_booking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.ticket_booking.entities.Train;
import org.ticket_booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {

    private List<Train> trainList = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TRAIN_DB_PATH = "C:/Users/Preetika/fullstack/IRCTC/app/src/main/java/org/ticket_booking/localDB/trains.json";

    TrainService() throws IOException {
        File trains = new File(TRAIN_DB_PATH);
        loadTrains();
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }


    public void loadTrains() throws IOException {
        try {
            File trainFile = new File(TRAIN_DB_PATH);

            // Deserialize the JSON data into a List<Train>
            trainList = objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {});

        } catch (IOException e) {
            System.out.println("Error loading train data: " + e.getMessage());
            throw e; // Rethrow exception if data cannot be loaded
        }
    }

    public List<Train> searchTrains(String start, String destination){
        if (trainList.isEmpty()) {
            System.out.println("No trains available!");
            return new ArrayList<>();
        }

        System.out.println("Searching for trains from " + start + " to " + destination);

        List<Train> foundTrains = trainList.stream()
                .filter(train -> {
                    return validTrain(train, start, destination);
                })
                .collect(Collectors.toList());

        if (foundTrains.isEmpty()) {
            System.out.println("No valid trains found!");
        }

        return foundTrains;
    }

    //void addTrain(Train)

    public  void saveTrain() throws IOException{
        try{
            objectMapper.writeValue(new File(TRAIN_DB_PATH), trainList);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addTrain(Train newTrain) throws IOException {
        Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId()))
                .findFirst();
        if(existingTrain.isPresent()){
            updateTrain(newTrain);
        }else {
            trainList.add(newTrain);
            saveTrain();
        }
    }

    public void updateTrain(Train updatedTrain) throws IOException {
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId())).findFirst();
        if(index.isPresent()){
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrain();
        }else{
            addTrain(updatedTrain);
        }
    }

    public boolean validTrain(Train train,String start, String destination){
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(start.trim().toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.trim().toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex != destinationIndex;
    }
}
