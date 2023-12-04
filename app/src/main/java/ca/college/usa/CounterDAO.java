package ca.college.usa;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CounterDAO {
    @Insert
    void InsertCounter(int counter);

    @Insert
    void InsertDate(String date);

    @Query("SELECT MAX(counter) FROM CounterScore")
    int SelectLargerst();

    @Query("SELECT MIN(counter) FROM CounterScore")
    int SelectSmallest();

    @Query("SELECT MAX(id) FROM CounterScore")
    int SelectLatest();
}
