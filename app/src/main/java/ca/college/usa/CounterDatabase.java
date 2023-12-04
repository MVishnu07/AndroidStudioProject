package ca.college.usa;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities ={CounterScore.class}, version = 1, exportSchema = false)
public abstract class CounterDatabase extends RoomDatabase {

    public abstract CounterDAO cDAO();
    private static volatile CounterDatabase INSTANCE;

    static CounterDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CounterDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CounterDatabase.class, "counter_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
