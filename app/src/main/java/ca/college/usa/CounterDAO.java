package ca.college.usa;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao // Annotation indicating this interface is a Data Access Object (DAO) in Room.
public interface CounterDAO {

    @Insert // Annotation indicating this method should perform an insert operation in the database.
    long InsertCounter(CounterScore counter);

    @Query("SELECT * FROM CounterScore WHERE counter = (SELECT MAX(counter) FROM CounterScore) LIMIT 1")
    CounterScore getHighestScoreWithDate();// Method to retrieve the highest score from the CounterScore table along with its date.

    @Query("SELECT * FROM CounterScore WHERE counter = (SELECT MIN(counter) FROM CounterScore) LIMIT 1")
    CounterScore getLowestScoreWithDate();

    @Query("SELECT * FROM CounterScore ORDER BY date DESC LIMIT 1")
    CounterScore getMostRecentScore();
}
