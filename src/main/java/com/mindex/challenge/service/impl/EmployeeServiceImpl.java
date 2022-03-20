package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }


    //Calculate the number of direct and indirect reports for a specific employee
    @Override
    public ReportingStructure getNumberOfReportsByEmployeeId(String id) {
        LOG.debug("Getting number of reports for id [{}]", id);

        //Check if the employeeId is valid and throw an exception if its not
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure rs = new ReportingStructure();
        rs.setEmployee(employee);
        rs.setNumberOfReports(getNumberOfReports(employee));
        return rs;

    }

    // Iterate over the employee hierarchy to get the number of employees
    // who report to this employee, either directly or indirectly
    int getNumberOfReports(Employee employee) {
        int reportCount = 0;

        if (employee.getDirectReports() != null) {
            reportCount += employee.getDirectReports().size();
            for (Employee directReport : employee.getDirectReports()) {
                Employee emp = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
                reportCount += getNumberOfReports(emp);
            }
            return reportCount;
        }

        return reportCount;

    }

}
