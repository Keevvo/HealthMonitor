package kevinmaiani.lam2020.healthmonitor.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.Models.User;

@Dao
public interface ReportDao {

    @Insert
    void insert(Report report);

    @Query("SELECT * from Report")
    List<Report> getAllReports();

    @Query("SELECT * FROM Report WHERE userId =:userId AND creationDate =:creationDate")
    List<Report> findReportForUser(int userId, Date creationDate);

    @Query("SELECT * FROM Report WHERE userId =:userId")
    List<Report> findReporyByUserId(int userId);
    @Update
    void update(Report report);

    @Delete
    void delete(Report report);
}
