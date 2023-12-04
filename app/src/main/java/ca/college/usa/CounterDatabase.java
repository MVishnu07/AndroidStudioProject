package ca.college.usa;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Annotation to denote this class as a Room Database with CounterScore as its entity, version 1, and not exporting schema.
@Database(entities ={CounterScore.class}, version = 1, exportSchema = false)
public abstract class CounterDatabase extends RoomDatabase {

    // Abstract method for getting an instance of CounterDAO.
    public abstract CounterDAO cDAO();
    private static volatile CounterDatabase INSTANCE; // Singleton instance of the database class, 'volatile' to ensure atomic access to the variable.

    // Static method to get the singleton instance of the database.
    static CounterDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {// Check if INSTANCE is null.
            synchronized (CounterDatabase.class) {// Synchronize access to the class.
                if (INSTANCE == null) { // Double check if INSTANCE is still null.
                    // Create a new database instance.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CounterDatabase.class, "counter_database")
                            .build();
                }
            }
        }
        return INSTANCE; // Return the database instance.
    }
}
