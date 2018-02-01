package net.proselyte.hibernate.controller;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.View.ProjectMenuView;
import net.proselyte.hibernate.dao.ProjectDAO;
import net.proselyte.hibernate.model.POJO.Project;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProjectControllerMVC {
    ProjectMenuView theView = new ProjectMenuView();
    ProjectDAO theModel;
    public ProjectControllerMVC(ProjectDAO model){
        this.theModel = model;
    }
    public void startPrjMenu() throws SQLException {
        while (true) {
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(theView.projMenu());
            }
            catch (NumberFormatException e){
                theView.printError(2);
                continue;
            }
            FetchConfig fetchConfig = new FetchConfig();
            switch(menuChoice) {
                //create project
                case 1:
                    String[] parts = theView.createUpdDevMenu().split(" ");
                    if (parts.length != 2) {
                        theView.printError(0);
                        continue;
                    }
                    BigDecimal cost;
                    try {
                        cost = new BigDecimal(parts[1]);
                    } catch (NumberFormatException e) {
                        theView.printError(1);
                        break;
                    }
                    Project project = new Project();
                    project.setName(parts[0]);
                    project.setCost(cost);
                    theModel.save(project);
                    break;
                //read project
                case 2:
                    Long idPrj;
                    try {
                        idPrj = Long.parseLong(theView.enterProjIDMessage(0));
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    fetchConfig.setDevelopers(true);
                    try {
                        Project project1 = theModel.getById(idPrj, fetchConfig);
                        String prjName = project1.getName();
                        BigDecimal prjCost = project1.getCost();
                        String developers = project1.getDevelopers().toString();
                        theView.printPrj(prjName, prjCost, developers);
                    }catch (NullPointerException e){
                        theView.printError(3);
                    }catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //update
                case 3:
                    while (true) {
                        Long idPrjUp;
                        try {
                            idPrjUp = Long.parseLong(theView.enterProjIDMessage(1));
                        }
                        catch (NumberFormatException e){
                            theView.printError(2);
                            continue;
                        }
                        Project projectUp = theModel.getById(idPrjUp, fetchConfig);
                        String[] partsUp = theView.createUpdDevMenu().split(" ");
                        if (partsUp.length != 2) {
                            theView.printError(0);
                            continue;
                        }
                        BigDecimal costUp;
                        try {
                            costUp = new BigDecimal(partsUp[1]);
                        } catch (NumberFormatException e) {
                            theView.printError(1);
                            break;
                        }
                        projectUp.setName(partsUp[0]);
                        projectUp.setCost(costUp);
                        projectUp.setId(idPrjUp);
                        theModel.save(projectUp);
                        break;
                    }
                    break;
                //delete project
                case 4:
                    Long idPrjDel;
                    try {
                        idPrjDel = Long.parseLong(theView.enterProjIDMessage(2));
                        theModel.remove(idPrjDel);
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //show all projects
                case 5:
                    List<Project> projectList = theModel.getAll();

                    if (projectList.size() == 0) {
                        theView.printError(4);
                        continue;
                    }
                    theView.showInfo(2);
                    for (Project value: projectList) {
                        theView.showAllPrj(value.getId(), value.getName(), value.getCost());
                    }
                    break;
                //add developers
                case 6:
                    while (true) {
                        Long idDevel, idProjDev;
                        try {
                            idProjDev = Long.parseLong(theView.showInfo(0));
                            idDevel = Long.parseLong(theView.showInfo(1));
                            theModel.addDeveloperToProj(idProjDev, idDevel);
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
