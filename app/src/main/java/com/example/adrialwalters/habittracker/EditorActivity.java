package com.example.adrialwalters.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adrialwalters.habittracker.data.HabitContract.HabitEntry;
import com.example.adrialwalters.habittracker.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity{

    /** EditText field to enter the type of activity */
    private EditText mActivityEditText;

    /** EditText field to enter the amount of time the activity was done */
    private EditText mTimeEditText;

    /** EditText field to enter the day of the week the activity was done */
    private Spinner mDaySpinner;

    /**
     * Day of the week. The possible valid values are in the HabitContract.java file:
     * {@link HabitEntry#DAY_SUNDAY}, {@link HabitEntry#DAY_MONDAY}, {@link HabitEntry#DAY_TUESDAY},
     * {@link HabitEntry#DAY_WEDNESDAY}, {@link HabitEntry#DAY_THURSDAY},
     * {@link HabitEntry#DAY_FRIDAY}, OR {@link HabitEntry#DAY_SATURDAY}.
     */
    private int mDay = HabitEntry.DAY_SUNDAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mActivityEditText = (EditText) findViewById(R.id.edit_activity_type);
        mTimeEditText = (EditText) findViewById(R.id.edit_amount_of_time);
        mDaySpinner = (Spinner) findViewById(R.id.spinner_day_of_week);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDaySpinner.setAdapter(daySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.day_sunday))) {
                        mDay = HabitEntry.DAY_SUNDAY;
                    } else if (selection.equals(getString(R.string.day_monday))) {
                        mDay = HabitEntry.DAY_MONDAY;
                    } else if (selection.equals(getString(R.string.day_tuesday))) {
                        mDay = HabitEntry.DAY_TUESDAY;
                    } else if (selection.equals(getString(R.string.day_wednesday))) {
                        mDay = HabitEntry.DAY_WEDNESDAY;
                    } else if (selection.equals(getString(R.string.day_thursday))) {
                        mDay = HabitEntry.DAY_THURSDAY;
                    } else if (selection.equals(getString(R.string.day_friday))) {
                        mDay = HabitEntry.DAY_FRIDAY;
                    } else {
                        mDay = HabitEntry.DAY_SATURDAY;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = HabitEntry.DAY_SUNDAY;
            }
        });
    }

    /**
     * Get user input from the editor and save new activity into database.
     */
    private void insertHabit() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white spaces
        String activityString = mActivityEditText.getText().toString().trim();
        String timeString = mTimeEditText.getText().toString().trim();
        int time = Integer.parseInt(timeString);

        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column name are the keys,
        // and activity attributes from editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_ACTIVITY_TYPE, activityString);
        values.put(HabitEntry.COLUMN_WEEK_DAY, mDay);
        values.put(HabitEntry.COLUMN_TIME, time);

        // Insert a new row for pt in database, returning the ID of that new row.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not insertion was successful
        if (newRowId == - 1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving activity", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful wand we can display a toast with the row ID.
            Toast.makeText(this, "Activity saved with row id:" + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertHabit();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
