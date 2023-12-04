package ca.college.usa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CounterScore {
    protected int counter;
    protected String date;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    protected Long id;

    public CounterScore(int counter, String date) {
        this.counter = counter;
        this.date = date;
    }

    public int getCounter() {
        return counter;
    }

    public String getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

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
