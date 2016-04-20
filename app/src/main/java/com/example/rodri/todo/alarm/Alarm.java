package com.example.rodri.todo.alarm;

/**
 * Created by rodri on 4/18/2016.
 */
public class Alarm implements Cloneable {

    private long id;
    private long date;
    private long alarmTime;
    private long taskId;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
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
