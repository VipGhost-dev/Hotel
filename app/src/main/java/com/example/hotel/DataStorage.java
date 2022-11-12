package com.example.hotel;

public class DataStorage {

    private Integer ID;
    private Integer Room;
    private Integer Count_Peoples;
    private String Status;

    public DataStorage(Integer ID,Integer Room, Integer Count_Peoples, String Status){
        this.ID = ID;
        this.Room = Room;
        this.Count_Peoples = Count_Peoples;
        this.Status = Status;
    }

    public DataStorage(Integer Room, Integer Count_Peoples, String Status){
        this.Room = Room;
        this.Count_Peoples = Count_Peoples;
        this.Status = Status;
    }

    public Integer getID(){
        return ID;
    }

    public void setID(Integer ID){
        this.ID = ID;
    }

    public Integer getRoom(){
        return Room;
    }

    public void setRoom(Integer Room){
        this.Room = Room;
    }

    public Integer getCount_Peoples(){
        return Count_Peoples;
    }

    public void setCount_Peoples(Integer Count_Peoples){
        this.Count_Peoples = Count_Peoples;
    }

    public String getStatus(){
        return Status;
    }

    public void setStatus(String Status){
        this.Status = Status;
    }
}
