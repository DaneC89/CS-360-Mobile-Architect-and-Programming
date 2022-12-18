package com.example.eventtrackerapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class EventEdit extends AppCompatActivity {

    private EditText eventNameEdit;
    private TextView eventDateEdit;
    int hours, mins;
    Button eventTimeEdit;
    EventDBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidget();
        eventTimeEdit = findViewById(R.id.eventTimeEdit);
        eventDateEdit.setText(String.format("Date: %s", CalenderUtils.formattedDate(CalenderUtils.selectedDate)));

    }

    // Creates scrollable widget that selects time.
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMins) -> {
            hours = selectedHour;
            mins = selectedMins;
            eventTimeEdit.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, mins));

        };
        // making sure clock is digital
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hours, mins, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }

    private void initWidget() {
        eventNameEdit = findViewById(R.id.eventNameEdit);
        eventTimeEdit = findViewById(R.id.eventTimeEdit);
        eventDateEdit = findViewById(R.id.eventDateEdit);
    }

    // Save event to day with Event name and time
    public void saveEventAction(View view) {
        String eventName = eventNameEdit.getText().toString() + "        " + eventTimeEdit.getText().toString();
        Event newEvent = new Event(eventName, CalenderUtils.selectedDate, eventTimeEdit);
        Event.eventList.add(newEvent);
        finish();
    }
}