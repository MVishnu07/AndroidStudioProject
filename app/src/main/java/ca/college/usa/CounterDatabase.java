package ca.college.usa;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities ={CounterScore.class}, version = 1)
public abstract class CounterDatabase extends RoomDatabase {

    public abstract CounterDAO cDAO();

}
