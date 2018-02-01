package net.proselyte.hibernate.View;

import java.util.Scanner;

public class CustomerMenuView {

    Scanner sc = new Scanner(System.in);

    public String custMenu() {


        System.out.println("What do you want to do with Customer? \n"+"1.Create new Customer\n"+"2.Read Customer\n" +
                "3.Update Customer\n"+"4.Delete Customer\n"+"5.Show all Customers\n"
                +"6.Add Project to Customer\n"+"7.Back to Main Menu");

        return (sc.nextLine());

    }
    public String createUpdCusMenu(){
        System.out.println("usage: <first_name> <last_name>");
        return sc.nextLine();

    }
    public String enterCustIDMessage(int enterNumber){
        if (enterNumber == 0)
            System.out.println("Enter customers id which should be read");
        else if (enterNumber == 1)
            System.out.println("Enter projects id which should be update");
        else if (enterNumber == 2)
            System.out.println("Enter projects id which should be delete");
        return sc.nextLine();
    }

    public void printCus(Long id, String cusFirstName, String cusLastName, String projects){
        System.out.println("Customer id is " + id + " , Firstname: " + cusFirstName + " , Lastname: "
                + cusLastName + "\n" + "Projects of this customer: " + projects);
    }
    public void showAllCus(Long id, String fName, String lName){
        System.out.println(id + "   " + fName + "   " + lName + "\n");
    }
    public String showInfo(int infNumber){
        if (infNumber == 0)
            System.out.println("Enter Customer's id");
        if (infNumber == 1)
            System.out.println("Enter Project's id");
        if (infNumber == 2) {
            System.out.println("id   |   First Name  |  Last Name");
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
            System.out.println("Warning! There is no such customer in the database");
        else if (errNumber == 4)
            System.out.println("Warning! There is no customers in the database");
        else if (errNumber == 5)
            System.out.println("Warning! There is no such customer/project in database");
        else if (errNumber == 6)
            System.out.println("Error with query to database!!!");
    }
}
