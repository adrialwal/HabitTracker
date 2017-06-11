package com.example.adrialwalters.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by adrialwalters on 6/10/17.
 */
/**
 * API Contract for the Habit Tracker app.
 */
public class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {}

    /**
     * Inner class that defines constant values for the habit database table.
     * Each entry in the table represents a single habit.
     */
    public static final class HabitEntry implements BaseColumns {

        /** Name of database table for activities */
        public final static String TABLE_NAME = "activities";

        /**
         * Unique ID number for the activity (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Type of activity.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ACTIVITY_TYPE ="type of activity";

        /**
         * Day of the week.
         *
         * The only possible values are {@link HabitEntry#DAY_SUNDAY},
         * {@link HabitEntry#DAY_MONDAY}, {@link HabitEntry#DAY_TUESDAY},
         * {@link HabitEntry#DAY_WEDNESDAY}, {@link HabitEntry#DAY_THURSDAY},
         * {@link HabitEntry#DAY_FRIDAY}, OR {@link HabitEntry#DAY_SATURDAY}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_WEEK_DAY = "day of week";

        /**
         * Amount of time.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_TIME = "amount of time";

        /**
         * Possible values for the day of the week.
         */
        public static final int DAY_SUNDAY = 0;
        public static final int DAY_MONDAY = 1;
        public static final int DAY_TUESDAY = 2;
        public static final int DAY_WEDNESDAY = 3;
        public static final int DAY_THURSDAY = 4;
        public static final int DAY_FRIDAY = 5;
        public static final int DAY_SATURDAY = 6;

    }

}

