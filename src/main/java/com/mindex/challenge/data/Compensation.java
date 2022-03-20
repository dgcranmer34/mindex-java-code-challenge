package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private double   salary;
    private String   effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getSalary() {return salary; }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

}
