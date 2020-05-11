package kevinmaiani.lam2020.healthmonitor.Database;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.Models.User;


public class UserReportDetails {

    public UserReportDetails() {

    }

    @Embedded
    public User user;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = Report.class)
    private List<Report> reportList;

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }
}
