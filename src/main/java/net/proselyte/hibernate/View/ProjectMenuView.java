package net.proselyte.hibernate.View;


import java.math.BigDecimal;
import java.util.Scanner;

public class ProjectMenuView {

    Scanner sc = new Scanner(System.in);

    public String projMenu() {


        System.out.println("What do you want to do with Project? \n"+"1.Create new Project\n"+"2.Read Project\n" +
                "3.Update Project\n"+"4.Delete Project\n"+"5.Show all Projects\n"
                +"6.Add Developer to Project\n"+"7.Back to Main Menu");

        return (sc.nextLine());

    }
    public String createUpdDevMenu(){
        System.out.println("usage: <project_name> <project_cost>");
        return sc.nextLine();

    }
    public String enterProjIDMessage(int enterNumber){
        if (enterNumber == 0)
            System.out.println("Enter projects id which should be read");
        else if (enterNumber == 1)
            System.out.println("Enter projects id which should be update");
        else if (enterNumber == 2)
            System.out.println("Enter projects id which should be delete");
        return sc.nextLine();
    }

    public void printPrj(String prjName, BigDecimal prjCost, String developers){
        System.out.println("Project name is " + prjName + " , cost is " + prjCost + "\n" +
                "developers in this project: " + developers);
    }
    public void showAllPrj(Long id, String prjName, BigDecimal prjCost){
        System.out.println(id + "   " + prjName + "   " + prjCost + "\n");
    }
    public String showInfo(int infNumber){
        if (infNumber == 0)
            System.out.println("Enter Project's id");
        if (infNumber == 1)
            System.out.println("Enter Developer's id");
        if (infNumber == 2) {
            System.out.println("Project ID | Project name | Project cost");
            return null;
        }
        return sc.nextLine();
    }
    public void printError(int errNumber){
        if (errNumber == 0)
            System.out.println("usage: <project_name> <project_cost>");
        else if (errNumber == 1)
            System.out.println("Warning! Enter <project_cost> in BigDecimal format");
        else if (errNumber == 2)
            System.out.println("Warning! Enter in long format");
        else if (errNumber == 3)
            System.out.println("Warning! There is no such project in the database");
        else if (errNumber == 4)
            System.out.println("Warning! There is no projects in the database");
        else if (errNumber == 5)
            System.out.println("Warning! There is no such developer/skill in database");
    }

}
