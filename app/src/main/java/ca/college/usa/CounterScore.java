package ca.college.usa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CounterScore {
    public int getCounter() {
        return counter;
    }

    public String getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    @ColumnInfo(name="counter")
    protected int counter;

    @ColumnInfo(name="date")
    protected String date;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    protected Long id;


    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
