package models;

import java.util.Date;

public class Employee {
    private int id;
    private int projectId;
    private Date DateFrom;
    private Date DateTo;

    public Employee(int id, int projectId, Date dateFrom, Date dateTo) {
        this.id = id;
        this.projectId = projectId;
        DateFrom = dateFrom;
        DateTo = dateTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        DateFrom = dateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

    public void setDateTo(Date dateTo) {
        DateTo = dateTo;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", DateFrom=" + DateFrom +
                ", DateTo=" + DateTo +
                '}';
    }
}
