package com.example.rodri.todo.ui.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rodri.todo.R;
import com.example.rodri.todo.database.CategoryTaskDataSource;
import com.example.rodri.todo.task.Task;
import com.example.rodri.todo.utils.DateAndTimeUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by rodri on 4/16/2016.
 */
public class TaskNotificationAdapter extends ArrayAdapter<Task> {

    private Activity activity;
    private LayoutInflater inflater = null;
    private ArrayList<Task> tasks;
    private long selectedHour;
    private long selectedMinute;
    private CategoryTaskDataSource dataSource;

    public TaskNotificationAdapter (Activity activity, int textResourceViewId, ArrayList<Task> tasks) {
        super (activity, textResourceViewId, tasks);
        try {
            this.activity = activity;
            this.tasks = tasks;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    public class ViewHolder {
        private TextView displayTaskName;
        private TextView displayDueDate;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_item, null);

            holder.displayTaskName = (TextView) v.findViewById(R.id.txtTaskName);
            holder.displayDueDate = (TextView) v.findViewById(R.id.txtDueDate);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayTaskName.setText(tasks.get(position).getTaskName());
        final String date = DateAndTimeUtil.convertDateInMillisToString(Long.parseLong(tasks.get(position).getDueDate()));
        holder.displayDueDate.setText(String.valueOf(date));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR);
                int currentMinute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;

                timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;


                        /**  set Alarm notification */
                        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

                        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                        notificationIntent.addCategory("android.intent.category.DEFAULT");
                        notificationIntent.putExtra("TASK_ID", tasks.get(position).getId());

                        PendingIntent broadcast = PendingIntent.getBroadcast(activity, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        long taskTimeInMillis = Long.parseLong(tasks.get(position).getDueDate());
                        Calendar cal = new GregorianCalendar();
                        cal.setTimeInMillis(taskTimeInMillis);
                        cal.add(Calendar.HOUR, (int) selectedHour);
                        cal.add(Calendar.MINUTE, (int) selectedMinute);
                        System.out.println("hour: " + (int) selectedHour);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

                        try {
                            dataSource = new CategoryTaskDataSource(activity);
                            dataSource.open();

                            dataSource.createAlarm(tasks.get(position).getDueDate(),
                                    DateAndTimeUtil.convertDateInMillisToString(cal.getTimeInMillis()), tasks.get(position).getId());

                            dataSource.close();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(activity, "Notification set to " + DateAndTimeUtil.convertDateInMillisToString(cal.getTimeInMillis()), Toast.LENGTH_LONG).show();
                        activity.finish();


                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();


            }
        });

        return v;
    }

}
