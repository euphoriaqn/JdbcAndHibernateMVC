package net.proselyte.hibernate.controller;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.View.CustomerMenuView;
import net.proselyte.hibernate.dao.CustomerDAO;
import net.proselyte.hibernate.model.POJO.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerControllerMVC {
    CustomerMenuView theView = new CustomerMenuView();
    CustomerDAO theModel;

    public CustomerControllerMVC(CustomerDAO model){
        this.theModel = model;
    }

    public void startCustMenu() throws SQLException {
        while (true) {
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(theView.custMenu());
            }
            catch (NumberFormatException e){
                theView.printError(2);
                continue;
            }
            FetchConfig fetchConfig = new FetchConfig();
            switch(menuChoice) {
                //create customer
                case 1:
                    String[] parts = theView.createUpdCusMenu().split(" ");
                    if (parts.length != 2) {
                        theView.printError(0);
                        continue;
                    }
                    Customer customer = new Customer();
                    customer.setFirstName(parts[0]);
                    customer.setLastName(parts[1]);
                    theModel.save(customer);
                    break;
                //read customer
                case 2:
                    Long idCus;
                    try {
                        idCus = Long.parseLong(theView.enterCustIDMessage(0));
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    fetchConfig.setProjects(true);
                    try {
                        Customer customer1 = theModel.getById(idCus, fetchConfig);
                        String firstNCust = customer1.getFirstName();
                        String lastNCust = customer1.getLastName();
                        String projects = customer1.getProjects().toString();
                        Long id = customer1.getId();
                        theView.printCus(id, firstNCust, lastNCust, projects);
                    }catch (NullPointerException e){
                        theView.printError(3);
                    }catch (IllegalArgumentException e){
                        theView.printError(3);
                    }
                    break;
                //update customer
                case 3:
                    while (true) {
                        Long idCustUp;
                        try {
                            idCustUp = Long.parseLong(theView.enterCustIDMessage(1));
                        }
                        catch (NumberFormatException e){
                            theView.printError(2);
                            continue;
                        }
                        String[] partsUp = theView.createUpdCusMenu().split(" ");
                        if (partsUp.length != 2) {
                            theView.printError(0);
                            continue;
                        }
                        Customer projectUp = theModel.getById(idCustUp, fetchConfig);
                        projectUp.setFirstName(partsUp[0]);
                        projectUp.setLastName(partsUp[1]);
                        projectUp.setId(idCustUp);
                        theModel.save(projectUp);
                        break;
                    }
                    break;
                //delete customer
                case 4:
                    Long idCusDel;
                    try {
                        idCusDel = Long.parseLong(theView.enterCustIDMessage(2));
                        theModel.remove(idCusDel);
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //show all customers
                case 5:
                    List<Customer> customerList = theModel.getAll();

                    if (customerList.size() == 0) {
                        theView.printError(4);
                        continue;
                    }
                    theView.showInfo(2);
                    for (Customer value: customerList) {
                        theView.showAllCus(value.getId(), value.getFirstName(), value.getLastName());
                    }
                    break;
                //add projects
                case 6:
                    while (true) {
                        Long idCust, idProj;
                        try {
                            idCust = Long.parseLong(theView.showInfo(0));
                            idProj = Long.parseLong(theView.showInfo(1));
                            theModel.addProjToCust(idCust, idProj);
                        }
                        catch (NumberFormatException e) {
                            theView.printError(2);
                            continue;
                        }
                        catch (NullPointerException e){
                            theView.printError(3);
                        }
                        catch (IllegalArgumentException e){
                            theView.printError(3);
                        }catch (SQLException e){
                            theView.printError(6);
                        }

                        break;
                    }


                    break;
                //back to main menu
                case 7:
                    return;
            }

        }
    }
}
