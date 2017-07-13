package com.example.gandh.inclass10;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gandh on 2/27/2017.
 */

public class Note
{

    public   long id;
   public   String name, priority, status,time,key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Note(String subject, String priority, String status) {
        this.name = subject;
        this.priority = priority;
        this.status = status;
        //this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("priority", priority);
        result.put("status", status);
        result.put("time",time);

        return result;
    }


    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", subject='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ",zzztatus='" + status + '\'' +
                ", time='" + time + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return name;
    }

    public void setSubject(String subject) {
        this.name = subject;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Note() {
    }
}