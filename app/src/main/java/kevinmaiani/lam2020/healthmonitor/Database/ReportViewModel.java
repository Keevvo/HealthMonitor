package kevinmaiani.lam2020.healthmonitor.Database;

import android.app.Application;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.Report;

public class ReportViewModel  extends AndroidViewModel {

    private ReportDao reportDao;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        reportDao = ApplicationDatabase.getDatabase(application).reportDao();
    }

    public List<Report> findReportForUser(int userId, Date creationDate) {return reportDao.findReportForUser(userId, creationDate);}
    public List<Report> findReporyByUserId(int userId) {return reportDao.findReporyByUserId(userId);};
    public void insert(Report report) { reportDao.insert(report);}
    public void update(Report report) { reportDao.update(report);}
    public void delete(Report report) { reportDao.delete(report);}
}
