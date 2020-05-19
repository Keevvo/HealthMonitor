package kevinmaiani.lam2020.healthmonitor.Models;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import kevinmaiani.lam2020.healthmonitor.Converters.Converters;

@Entity(tableName = "report",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE),
                indices = {@Index("creationDate"), @Index("userId")})
public class Report {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @TypeConverters({Converters.class})
    @ColumnInfo(name = "creationDate")
    private Date creationDate;

    @ColumnInfo(name = "bodyTemperatureLevel")
    private int bodyTemperatureLevel;

    @ColumnInfo(name = "bodyTemperature")
    private int bodyTemperature;

    @ColumnInfo(name = "bloodPressureLevel")
    private int bloodPressureLevel; //pass ENUM (create CLASS Level.java)

    @ColumnInfo(name = "bloodPressure")
    private int bloodPressure;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "userId")
    private int userId;

    public Report() {
    }

    public Report(@NonNull Date createDate, int bloodPressureLevel, int bloodPressure, int bodyTemperature, int bodyTemperatureLevel, String note, int userId) {//        this.creationDate = creationDate;
        this.creationDate = createDate;
        this.bloodPressureLevel = bloodPressureLevel;
        this.bloodPressure = bloodPressure;
        this.bodyTemperature = bodyTemperature;
        this.bodyTemperatureLevel = bodyTemperatureLevel;
        note = note != null ? note : "";
        this.note = note;
        this.userId = userId;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(@NonNull Date creationDate) {
        this.creationDate = (Date) creationDate.clone();
    }

    public int getBloodPressureLevel() {
        return bloodPressureLevel;
    }

    public void setBloodPressureLevel(int bloodPressureLevel) {
        this.bloodPressureLevel = bloodPressureLevel;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(int bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public int getBodyTemperatureLevel() {
        return bodyTemperatureLevel;
    }

    public void setBodyTemperatureLevel(int bodyTemperatureLevel) {
        this.bodyTemperatureLevel = bodyTemperatureLevel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
