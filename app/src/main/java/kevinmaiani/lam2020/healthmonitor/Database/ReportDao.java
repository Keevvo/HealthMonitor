package kevinmaiani.lam2020.healthmonitor.Database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.Models.User;

@Dao
public interface ReportDao {

    @Insert
    void insert(Report report);

    @Query("SELECT * from Report")
    List<Report> getAllReports();

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);
}
