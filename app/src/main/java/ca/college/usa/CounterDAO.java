package ca.college.usa;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CounterDAO {

    @Insert
    long InsertCounter(CounterScore counter);

    @Query("SELECT * FROM CounterScore WHERE counter = (SELECT MAX(counter) FROM CounterScore) LIMIT 1")
    CounterScore getHighestScoreWithDate();

    @Query("SELECT * FROM CounterScore WHERE counter = (SELECT MIN(counter) FROM CounterScore) LIMIT 1")
    CounterScore getLowestScoreWithDate();

    @Query("SELECT * FROM CounterScore ORDER BY date DESC LIMIT 1")
    CounterScore getMostRecentScore();
}
