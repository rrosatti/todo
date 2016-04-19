package com.example.rodri.todo.alarm;

/**
 * Created by rodri on 4/18/2016.
 */
public class Alarm implements Cloneable {

    private long id;
    private String date;
    private String alarmTime;
    private long taskId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public Alarm clone() {
        try {
            final Alarm result = (Alarm) super.clone();
            return result;
        } catch (final CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
