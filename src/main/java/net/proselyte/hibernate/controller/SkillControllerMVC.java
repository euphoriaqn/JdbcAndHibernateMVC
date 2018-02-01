package net.proselyte.hibernate.controller;

import net.proselyte.hibernate.Utils.FetchConfig;
import net.proselyte.hibernate.View.SkillMenuView;
import net.proselyte.hibernate.dao.SkillDAO;
import net.proselyte.hibernate.model.POJO.Skill;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SkillControllerMVC {
    SkillMenuView theView = new SkillMenuView();
    SkillDAO theModel;
    public SkillControllerMVC(SkillDAO model){
        this.theModel = model;
    }

    Scanner sc = new Scanner(System.in);
    public void startSkillMenu() throws SQLException {
        while (true) {
            int menuChoice;
            try {
                menuChoice = Integer.parseInt(theView.skillMenu());
            }
            catch (NumberFormatException e){
                theView.printError(2);
                continue;
            }
            FetchConfig fetchConfig = new FetchConfig();
            switch(menuChoice) {
                //create skill
                case 1:
                    String skillName = theView.createUpdSkillMenu();
                    Skill skill = new Skill();
                    skill.setSkillName(skillName);
                    theModel.save(skill);
                    break;
                //read skill
                case 2:
                    Long idSkill;
                    try {
                        idSkill = Long.parseLong(theView.enterSkillIDMessage(0));
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    fetchConfig.setDevelopers(true);
                    try {
                        Skill skill1 = theModel.getById(idSkill, fetchConfig);
                        theView.printSkill(skill1.getSkillName());
                    }catch (NullPointerException e){
                        theView.printError(3);
                    }catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //update
                case 3:
                    while (true) {
                        Long idSkUp;
                        try {
                            idSkUp = Long.parseLong(theView.enterSkillIDMessage(1));
                        }
                        catch (NumberFormatException e){
                            theView.printError(2);
                            continue;
                        }
                        try {
                        Skill skill1 = theModel.getById(idSkUp, fetchConfig);
                        String skillUp = theView.createUpdSkillMenu();
                        skill1.setSkillName(skillUp);
                        skill1.setId(idSkUp);
                        theModel.save(skill1);
                        } catch (NumberFormatException e) {
                            theView.printError(2);
                            continue;
                        }
                        break;
                    }
                    break;
                //delete developer
                case 4:
                    Long idSkilDel;
                    try {
                        idSkilDel = Long.parseLong(theView.enterSkillIDMessage(2));
                        theModel.remove(idSkilDel);
                    }
                    catch (NumberFormatException e){
                        theView.printError(2);
                        continue;
                    }
                    catch (IllegalArgumentException e){
                        theView.printError(3);
                    }

                    break;
                //show all skills
                case 5:
                    List<Skill> skillList = theModel.getAll();

                    if (skillList.size() == 0) {
                        theView.printError(4);
                        continue;
                    }
                    theView.showInfo(2);
                    for (Skill value: skillList) {
                        theView.showAllSk(value.getId(), value.getSkillName());
                    }
                    break;
                //back to main menu
                case 6:
                    return;
            }

        }
    }
}
