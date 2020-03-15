package utils;

import models.Employee;
import models.Tuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.BiConsumer;

public class CommonUtil {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final int ID_POS = 0;
    private static final int PROJECT_ID_POS = 1;
    private static final int DATE_FROM_POS = 2;
    private static final int DATE_TO_POS = 3;

    private Map<Integer, List<Employee>> projectEmployeesMap;

    public CommonUtil() {
        projectEmployeesMap = new HashMap<>();
    }

    public void processFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");

                Employee employee = new Employee(
                        Integer.parseInt(parts[ID_POS]),
                        Integer.parseInt(parts[PROJECT_ID_POS]),
                        getDate(parts[DATE_FROM_POS]),
                        getDate(parts[DATE_TO_POS])
                    );
                if (projectEmployeesMap.containsKey(employee.getProjectId())) {
                    projectEmployeesMap.get(employee.getProjectId()).add(employee);
                } else {
                    List<Employee> employeeList = new ArrayList<>();
                    employeeList.add(employee);
                    projectEmployeesMap.put(employee.getProjectId(), employeeList);
                }

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void getEmployeePairs() {
        projectEmployeesMap.forEach((projectId, employees) -> {
            Tuple<Employee, Employee> tuple = getTuple(employees);
            System.out.println(
                    "For project " + projectId + ": " +
                            tuple.getData1() + " and " + tuple.getData2()
            );
        });
    }

    private Tuple<Employee, Employee> getTuple(List<Employee> employees) {
        if(employees.size()<=1){
            return new Tuple<>(employees.get(0), null);
        }
        Employee employee1 = employees.get(0);
        Employee employee2 = employees.get(1);
        long maxPeriod = overlap(employees.get(0).getDateFrom(), employees.get(0).getDateTo(),
                employees.get(1).getDateFrom(), employees.get(1).getDateTo());

        for (int i = 0; i < employees.size(); i++) {
            for (int j = i+1; j < employees.size(); j++) {
                 long period = overlap(employees.get(i).getDateFrom(), employees.get(i).getDateTo(),
                         employees.get(j).getDateFrom(), employees.get(j).getDateTo());

                 if(period > maxPeriod) {
                     maxPeriod = period;
                     employee1 = employees.get(i);
                     employee2 = employees.get(j);
                 }

            }

        }

        return new Tuple<>(employee1, employee2);
    }


    private Date getDate(String dateString) throws ParseException {
        if (dateString.equals("NULL")){
            return new Date();
        }

        return formatter.parse(dateString);
    }

    public static long overlap(Date date1Start, Date date1End,
                                 Date date2Start, Date date2End) {
        long totalRange = Math.max(date1End.getTime(), date2End.getTime()) - Math.min(date1Start.getTime(), date2Start.getTime());
        long sumOfRanges = (date1End.getTime() - date1Start.getTime()) + (date2End.getTime() - date2Start.getTime());
        long overlappingInterval = 0;

        if (sumOfRanges > totalRange) { // means they overlap
            overlappingInterval = Math.min(date1End.getTime(), date2End.getTime()) - Math.max(date1Start.getTime(), date2Start.getTime());
        }

        return overlappingInterval;
    }
}
