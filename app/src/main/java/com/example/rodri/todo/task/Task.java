package com.example.rodri.todo.task;

/**
 * Created by rodri on 4/9/2016.
 */
public class Task {

    private long id;
    private String taskName;
    private int priority;
    private String dueDate;
    private int setAlarm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getSetAlarm() {
        return setAlarm;
    }

    public void setSetAlarm(int setAlarm) {
        this.setAlarm = setAlarm;
    }

}
