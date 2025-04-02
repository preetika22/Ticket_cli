package org.ticket_booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Train {

    @JsonProperty("train_id")
    private String trainId;

    @JsonProperty("train_no")
    private String trainNo;
    private List<List<Integer>> seat;
    private Map<String, String> schedule;
    private List<String> stations;

    public Train(){}

    public Train(String trainId, String trainNo,List<List<Integer>> seat, Map<String, String> schedule, List<String> stations){
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seat = seat;
        this.schedule = schedule;
        this.stations = stations;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeat() {
        return seat;
    }

    public void setSeat(List<List<Integer>> seat) {
        this.seat = seat;
    }

    public Map<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, String> schedule) {
        this.schedule = schedule;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }


}
