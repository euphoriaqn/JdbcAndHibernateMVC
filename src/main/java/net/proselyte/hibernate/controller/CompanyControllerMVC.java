package net.proselyte.hibernate.controller;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.View.CompanyMenuView;
import net.proselyte.hibernate.dao.CompanyDAO;
import net.proselyte.hibernate.model.POJO.Company;

import java.sql.SQLException;
import java.util.List;

public class CompanyControllerMVC {
    CompanyMenuView theView = new CompanyMenuView();
    CompanyDAO theModel;
    public CompanyControllerMVC(CompanyDAO model){
        this.theModel = model;
    }

    public void startCompMenu() throws SQLException {
        while (true) {
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(theView.compMenu());
            }
            catch (NumberFormatException e){
                theView.printError(2);
                continue;
            }
            FetchConfig fetchConfig = new FetchConfig();
            switch(menuChoice) {
                //create company
                case 1:
                    String name = theView.createUpdCompMenu();
                    Company company = new Company();
                    company.setCompanyName(name);
                    theModel.save(company);
                    break;
                //read company
                case 2:
                    Long idComp;
                    try {
                        idComp = Long.parseLong(theView.enterCompIDMessage(0));
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    fetchConfig.setProjects(true);
                    try {
                        Company company1 = theModel.getById(idComp, fetchConfig);
                        Long id = company1.getId();
                        String compName = company1.getCompanyName();
                        String projects = company1.getProjects().toString();
                        theView.printComp(id, compName, projects);
                    }catch (NullPointerException e){
                        theView.printError(3);
                    } catch (IllegalArgumentException e){
                        theView.printError(3);
                    }
                    break;
                //update company
                case 3:
                    while (true) {
                        Long idCompUp;
                        try {
                            idCompUp = Long.parseLong(theView.enterCompIDMessage(1));
                        }
                        catch (NumberFormatException e){
                            theView.printError(2);
                            continue;
                        }
                        String partsUp = theView.createUpdCompMenu();
                        Company companyUp = theModel.getById(idCompUp, fetchConfig);
                        companyUp.setCompanyName(partsUp);
                        companyUp.setId(idCompUp);
                        theModel.save(companyUp);
                        break;
                    }
                    break;
                //delete company
                case 4:
                    Long idCusDel;
                    try {
                        idCusDel = Long.parseLong(theView.enterCompIDMessage(2));
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
                //show all companies
                case 5:
                    List<Company> companyList = theModel.getAll();
                    if (companyList.size() == 0) {
                        theView.printError(4);
                        continue;
                    }
                    theView.showInfo(2);
                    for (Company value: companyList) {
                        theView.showAllComp(value.getId(), value.getCompanyName());
                    }
                    break;
                //add projects
                case 6:
                    while (true) {
                        Long idCompAdd, idProj;
                        try {
                            idCompAdd = Long.parseLong(theView.showInfo(0));
                            idProj = Long.parseLong(theView.showInfo(1));
                            theModel.addProjectsToComp(idCompAdd, idProj);
                        }
                        catch (NumberFormatException e) {
                            theView.printError(2);
                            continue;
                        }
                        catch (NullPointerException e){
                            theView.printError(5);
                        }
                        catch (IllegalArgumentException e){
                            theView.printError(5);
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
