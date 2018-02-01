package net.proselyte.hibernate.controller;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.View.DeveloperMenuView;
import net.proselyte.hibernate.dao.DeveloperDAO;
import net.proselyte.hibernate.model.POJO.Developer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class DeveloperControllerMVC {
    DeveloperMenuView theView = new DeveloperMenuView();
    DeveloperDAO theModel;
    public DeveloperControllerMVC(DeveloperDAO model){
        this.theModel = model;
    }
    public void startDevMenu() throws SQLException {
        while (true) {
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(theView.devMenu());
            }
            catch (NumberFormatException e){
                theView.printError(2);
                continue;
            }
            FetchConfig fetchConfig = new FetchConfig();
            switch(menuChoice) {
                //create developer
                case 1:
                    String[] parts = theView.createUpdDevMenu().split(" ");
                    if (parts.length != 3) {
                       theView.printError(0);
                       continue;
                    }
                    BigDecimal salary;
                    try {
                        salary = new BigDecimal(parts[2]);
                    } catch (NumberFormatException e) {
                        theView.printError(1);
                        break;
                    }
                    Developer developer = new Developer();
                    developer.setFirstName(parts[0]);
                    developer.setLastName(parts[1]);
                    developer.setSalary(salary);
                    theModel.save(developer);
                    break;
                //read developer
                case 2:
                    Long idDev;
                    try {
                        idDev = Long.parseLong(theView.readDevMenu());
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    fetchConfig.setSkills(true);
                    fetchConfig.setProjects(true);
                    try {
                        Developer developer1 = theModel.getById(idDev, fetchConfig);
                        String firstName = developer1.getFirstName();
                        String lastName = developer1.getLastName();
                        BigDecimal devSalary = developer1.getSalary();
                        String skills = developer1.getSkills().toString();
                        String projects = developer1.getProjects().toString();
                        theView.printDev(firstName, lastName, devSalary, skills, projects);
                    }catch (NullPointerException e){
                        theView.printError(3);
                    }catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //update
                case 3:
                    while (true) {
                        Long idDevUp;
                        try {
                            idDevUp = Long.parseLong(theView.updateDevMenu());
                        }
                        catch (NumberFormatException e){
                            theView.printError(2);
                            continue;
                        }
                        Developer developerUp = theModel.getById(idDevUp, fetchConfig);
                        String[] partsUp = theView.createUpdDevMenu().split(" ");
                        if (partsUp.length != 3) {
                            theView.printError(0);
                            continue;
                        }
                        developerUp.setFirstName(partsUp[0]);
                        developerUp.setLastName(partsUp[1]);
                        developerUp.setId(idDevUp);
                        try {
                            BigDecimal salaryUp = new BigDecimal(partsUp[2]);
                            developerUp.setSalary(salaryUp);
                        } catch (NumberFormatException e) {
                            theView.printError(1);
                            continue;
                        }
                        theModel.save(developerUp);
                        break;
                    }
                    break;
                //delete developer
                case 4:
                    Long idDevDel;
                    try {
                        idDevDel = Long.parseLong(theView.deleteDevMenu());
                        theModel.remove(idDevDel);
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //show all developers
                case 5:
                    List<Developer> developerList = theModel.getAll();

                    if (developerList.size() == 0) {
                        theView.printError(4);
                        continue;
                    }
                    theView.showInfo(2);
                    for (Developer value: developerList) {
                        theView.showAllDev(value.getId(), value.getFirstName(), value.getLastName(), value.getSalary());
                    }
                    break;
                //add skills
                case 6:
                    while (true) {
                        Long idSkill, idDevSk;
                        try {
                            idDevSk = Long.parseLong(theView.showInfo(0));
                            idSkill = Long.parseLong(theView.showInfo(1));
                            theModel.addSkillToDev(idSkill, idDevSk);
                        }
                        catch (NumberFormatException e) {
                            theView.printError(2);
                            continue;
                        }
                        catch (NullPointerException e){

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
