package com.example.gandh.inclass07;

/**
 * Created by gandh on 2/27/2017.
 */

public class Note
{

    public   long id;
   public   String subject, priority, status;


    public Note(String subject, String priority, String status) {
        this.subject = subject;
        this.priority = priority;
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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