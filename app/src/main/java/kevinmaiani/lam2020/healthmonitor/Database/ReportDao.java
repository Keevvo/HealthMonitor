package kevinmaiani.lam2020.healthmonitor.Database;

import java.util.Date;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import kevinmaiani.lam2020.healthmonitor.Models.Report;

@Dao
public interface ReportDao {

    @Insert
    void insert(Report report);

    @Query("SELECT * from Report")
    LiveData<List<Report>> getAllReports();

    @Query("SELECT * FROM Report WHERE userId =:userId AND creationDate =:creationDate")
    List<Report> findReportForUser(int userId, Date creationDate);

    @Query("SELECT * FROM Report WHERE userId =:userId")
    List<Report> findReporyByUserId(int userId);

    @Query("SELECT * FROM Report WHERE userId =:userId AND bloodPressureLevel = 5 AND bodyTemperatureLevel = 5")
    LiveData<List<Report>> findMaxPriorityReports(int userId);

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("DELETE FROM Report")
    void deleteAllReports();
}
