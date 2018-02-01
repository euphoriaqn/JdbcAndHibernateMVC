package net.proselyte.hibernate.View;
import java.util.Scanner;

public class CompanyMenuView {

    Scanner sc = new Scanner(System.in);

    public String compMenu() {

        System.out.println("What do you want to do with Company? \n"+"1.Create new Company\n"+"2.Read Company\n" +
                "3.Update Company\n"+"4.Delete Company\n"+"5.Show all Companies\n"
                +"6.Add Project to Company\n"+"7.Back to Main Menu");

        return (sc.nextLine());

    }
    public String createUpdCompMenu(){
        System.out.println("usage: <company_name>");
        return sc.nextLine();

    }
    public String enterCompIDMessage(int enterNumber){
        if (enterNumber == 0)
            System.out.println("Enter company id which should be read");
        else if (enterNumber == 1)
            System.out.println("Enter company id which should be update");
        else if (enterNumber == 2)
            System.out.println("Enter company id which should be delete");
        return sc.nextLine();
    }

    public void printComp(Long id, String compName, String projects){
        System.out.println("Company id is " + id + " , Company name: " + compName + "\n"
                + " , Projects of this company " + projects);
    }
    public void showAllComp(Long id, String compName){
        System.out.println(id + "   " + compName + "\n");
    }
    public String showInfo(int infNumber){
        if (infNumber == 0)
            System.out.println("Enter Company id");
        if (infNumber == 1)
            System.out.println("Enter Project id");
        if (infNumber == 2) {
            System.out.println("Company ID  |  Company Name");
            return null;
        }
        return sc.nextLine();
    }
    public void printError(int errNumber){
        if (errNumber == 0)
            System.out.println("usage: <first_name> <last_name>");
        else if (errNumber == 2)
            System.out.println("Warning! Enter in long format");
        else if (errNumber == 3)
            System.out.println("Warning! There is no such company in the database");
        else if (errNumber == 4)
            System.out.println("Warning! There is no companies in the database");
        else if (errNumber == 5)
            System.out.println("Warning! There is no such company/project in database");
    }
}
