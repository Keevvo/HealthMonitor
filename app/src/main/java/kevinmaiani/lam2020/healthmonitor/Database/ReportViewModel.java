package kevinmaiani.lam2020.healthmonitor.Database;

import android.app.Application;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kevinmaiani.lam2020.healthmonitor.Models.Report;

public class ReportViewModel  extends AndroidViewModel {

    private ReportDao reportDao;
    private LiveData<List<Report>> allReports;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        reportDao = ApplicationDatabase.getDatabase(application).reportDao();
        allReports = reportDao.getAllReports();
    }

    public List<Report> findReportForUser(int userId, Date creationDate) {return reportDao.findReportForUser(userId, creationDate);}
    public List<Report> findReporyByUserId(int userId) {return reportDao.findReporyByUserId(userId);}
    public LiveData<List<Report>> findMaxPriorityReports(int userId) {return reportDao.findMaxPriorityReports(userId);}
    public void insert(Report report) { reportDao.insert(report);}
    public void update(Report report) { reportDao.update(report);}
    public void delete(Report report) { reportDao.delete(report);}
    public void deleteAllReports() { reportDao.deleteAllReports();}
    public LiveData<List<Report>> getAllReports() {
        return allReports;
    }
}