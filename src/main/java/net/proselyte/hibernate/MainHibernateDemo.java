package net.proselyte.hibernate;
import net.proselyte.hibernate.controller.*;
import net.proselyte.hibernate.model.HibernateDaoImpl.*;
import net.proselyte.hibernate.model.JdbcDaoImpl.*;

import java.sql.SQLException;
import java.util.Scanner;

public class MainHibernateDemo {
    public static void main(String[] args) throws SQLException {

 //      SessionFactory sessionFactory = HibConnectionUtil.getConnection().getSessionFactory();

        Scanner sc = new Scanner(System.in);
        while (true) {
            int choice, choiceMethod;
            while (true){
                System.out.println("Select _JDBC_ OR _HIBERNATE_");
                System.out.println("1 - JDBC   |   2 - Hibernate   |   0 - Exit");
                try {
                    choiceMethod = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e){
                    System.out.println("Wrong format!!! Use Integer");
                    continue;
                }

                if ((choiceMethod !=1) & (choiceMethod !=2) & (choiceMethod != 0)){
                    System.out.println("Wrong value!!!");
                    continue;
                }
                if (choiceMethod == 0){
                    System.out.println("Program was shutdown BY USER");
                    return;
                }
                break;
            }
            while (true){
            if (choiceMethod == 1)
                System.out.println("MAIN MENU JDBC");
            else
                System.out.println("MAIN MENU HIBERNATE");
            System.out.println("Select the table:  \n"+"1.Developer\n"+"2.Project\n"+"3.Skill\n"
                    +"4.Company\n"+"5.Customer\n"+"6.Exit");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 6)
                break;

            switch(choice) {
                case 1:
                    System.err.println("DEVELOPER MENU");
                    DeveloperControllerMVC controllerMVC = null;
                    if (choiceMethod == 1)
                        controllerMVC = new DeveloperControllerMVC(new JdbcDeveloperDAOImpl());
                    if (choiceMethod == 2)
                        controllerMVC = new DeveloperControllerMVC(new HibernateDeveloperDAOImpl());
                    controllerMVC.startDevMenu();
                    break;
                case 2:
                    System.err.println("PROJECT MENU");
                    ProjectControllerMVC controllerMVC1 = null;
                    if (choiceMethod == 1)
                        controllerMVC1 = new ProjectControllerMVC(new JdbcProjectDAOImpl());
                    if (choiceMethod == 2)
                        controllerMVC1 = new ProjectControllerMVC(new HibernateProjectDAOImpl());
                    controllerMVC1.startPrjMenu();
                    break;
                case 3:
                    System.err.println("SKILL MENU");
                    SkillControllerMVC controllerMVC2 = null;
                    if (choiceMethod == 1)
                        controllerMVC2 = new SkillControllerMVC(new JdbcSkillDAOImpl());
                    else if (choiceMethod == 2)
                        controllerMVC2 = new SkillControllerMVC(new HibernateSkillDAOImpl());
                    controllerMVC2.startSkillMenu();
                    break;
                case 4:
                    System.err.println("COMPANY MENU");
                    CompanyControllerMVC controllerMVC3 = null;
                    if (choiceMethod == 1)
                        controllerMVC3 = new CompanyControllerMVC(new JdbcCompanyDAOImpl());
                    else if (choiceMethod == 2)
                        controllerMVC3 = new CompanyControllerMVC(new HibernateCompanyDAOImpl());
                    controllerMVC3.startCompMenu();
                    break;
                case 5:
                    System.err.println("CUSTOMER MENU");
                    CustomerControllerMVC controllerMVC4 = null;
                    if (choiceMethod ==1)
                        controllerMVC4 = new CustomerControllerMVC(new JdbcCustomerDAOImpl());
                    if (choiceMethod ==2)
                        controllerMVC4 = new CustomerControllerMVC(new HibernateCustomerDAOImpl());
                    controllerMVC4.startCustMenu();
                    break;
                default:
                    System.err.println("Wrong value. try again!");
                    break;
            }
            }

        }

    }
}
