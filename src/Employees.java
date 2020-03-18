import models.Employee;
import models.Tuple;
import utils.CommonUtil;

import java.util.Scanner;

public class Employees {
    public static void main(String[] args) {
        String filePath = "/Users/rbonev/projects/employees/src/data.txt"; //absolute file path to a txt file
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter an absolute path");
        //filePath = scanner.nextLine();


        CommonUtil commonUtil = new CommonUtil();
        commonUtil.processFile(filePath);
        Tuple<Employee, Employee> employeeTuple = commonUtil.getEmployeePairs();
        System.out.println(employeeTuple.getData1() + " and " + employeeTuple.getData2());
    }
}
