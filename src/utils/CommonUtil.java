package utils;

import models.Employee;
import models.Tuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

public class CommonUtil {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final int ID_POS = 0;
    private static final int PROJECT_ID_POS = 1;
    private static final int DATE_FROM_POS = 2;
    private static final int DATE_TO_POS = 3;

    private Map<Integer, List<Employee>> projectEmployeesMap;
    private Map<Tuple<Employee, Employee>, Long> employeePairsTimeWorkedMap;
    private Tuple<Employee, Employee> longestWorkedEmployees;

    public CommonUtil() {
        projectEmployeesMap = new HashMap<>();
        employeePairsTimeWorkedMap = new HashMap<>();
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

    public Tuple<Employee, Employee> getEmployeePairs() {
        projectEmployeesMap.forEach((projectId, employees) -> {
            processProject(employees);
        });

        boolean[] isFirst = {true};
        employeePairsTimeWorkedMap.forEach((employeeEmployeeTuple, periodWorked) -> {
            if(isFirst[0]) {
                longestWorkedEmployees = employeeEmployeeTuple;
                isFirst[0] = false;
            } else {
                if(employeePairsTimeWorkedMap.get(longestWorkedEmployees) < periodWorked) {
                    longestWorkedEmployees = employeeEmployeeTuple;
                }
            }
        });
        return longestWorkedEmployees;
    }

    private void processProject(List<Employee> employees) {
        if(employees.size()<=1){
            return;
        }
        for (int i = 0; i < employees.size(); i++) {
            for (int j = i+1; j < employees.size(); j++) {
                 long period = overlap(employees.get(i).getDateFrom(), employees.get(i).getDateTo(),
                         employees.get(j).getDateFrom(), employees.get(j).getDateTo());

                Tuple<Employee, Employee> employeeTuple = new Tuple<>(employees.get(i), employees.get(j));

                //accumulate the period worked
                if(employeePairsTimeWorkedMap.containsKey(employeeTuple)){
                    employeePairsTimeWorkedMap.put(employeeTuple, employeePairsTimeWorkedMap.get(employeeTuple)+period);
                }else {
                    employeePairsTimeWorkedMap.put(employeeTuple, period);
                }
            }
        }

    }


    private Date getDate(String dateString) throws ParseException {
        if (dateString.equals("NULL")){
            return new Date();
        }

        return formatter.parse(dateString);
    }

    private static long overlap(Date date1Start, Date date1End,
                                Date date2Start, Date date2End) {
        if (date2Start.getTime() > date1End.getTime() || date1Start.getTime() > date2End.getTime()){
            return 0;
        }

        long start = Math.max(date1Start.getTime(), date2Start.getTime());
        long end = Math.min(date1End.getTime(), date2End.getTime());

        return end-start;
    }
}
