package net.proselyte.hibernate.View;

import java.math.BigDecimal;
import java.util.Scanner;

public class DeveloperMenuView {
    Scanner sc = new Scanner(System.in);

    public String devMenu() {


        System.out.println("What do you want to do with developer? \n" + "1.Create new Developer\n" + "2.Read Developer\n" +
                "3.Update Developer\n" + "4.Delete Developer\n" + "5.Show all developers\n"
                + "6.Add skills to developer\n" + "7.Back to Main Menu");

            return (sc.nextLine());

    }
    public String createUpdDevMenu(){
        System.out.println("usage: <developer_first_name> <developer_last_name> <developer_salary>");
        return sc.nextLine();

    }
    public String readDevMenu(){
        System.out.println("Enter developers id which should be read");
        return sc.nextLine();
    }
    public String updateDevMenu(){
        System.out.println("Enter developers id which should be update");
        return sc.nextLine();
    }
    public String deleteDevMenu(){
        System.out.println("Enter developers id which should be delete");
        return sc.nextLine();
    }
    public void printDev(String FirstName, String LastName, BigDecimal Salary, String Skills, String Projects){
        System.out.println("Developer name is " + FirstName + " , surname is " + LastName
                + " , salary is " + Salary + " , skills: " + Skills
                + " , projects: " + Projects);
    }
    public void showAllDev(Long id, String firstName, String lastName, BigDecimal salary){
        System.out.println(id + "   " + firstName + "   " + lastName
                + "   " + salary + "\n");
    }
    public String showInfo(int infNumber){
        if (infNumber == 0)
            System.out.println("Enter Developer's id");
        if (infNumber == 1)
            System.out.println("Enter Skill's id");
        if (infNumber == 2) {
            System.out.println("Developer ID | First name | Last name | Salary");
            return null;
        }
        return sc.nextLine();
    }
    public void printError(int errNumber){
        if (errNumber == 0)
            System.out.println("Warning! usage: <developer_first_name> <developer_last_name> <developer_salary>");
        else if (errNumber == 1)
            System.out.println("Warning! Enter <developer_salary> in BigDecimal format");
        else if (errNumber == 2)
            System.out.println("Warning! Enter in long format");
        else if (errNumber == 3)
            System.out.println("Warning! There is no such developer in the database");
        else if (errNumber == 4)
            System.out.println("Warning! There is no developers in the database");
        else if (errNumber == 5)
            System.out.println("Warning! There is no such developer/skill in database");
    }

}
