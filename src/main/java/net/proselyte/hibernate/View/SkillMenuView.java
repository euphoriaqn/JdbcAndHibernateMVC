package net.proselyte.hibernate.View;

import java.util.Scanner;

public class SkillMenuView{
    Scanner sc = new Scanner(System.in);

    public String skillMenu() {

       System.out.println("What do you want to do with skill? \n"+"1.Create new Skill\n"
       +"2.Read Skill\n" + "3.Update Skill\n"+"4.Delete Skill\n"+"5.Show all Skills\n"
       +"6.Back to Main Menu");
        return (sc.nextLine());

        }
    public String createUpdSkillMenu(){
        System.out.println("usage: <skill_name>");
        return sc.nextLine();

    }
    public String enterSkillIDMessage(int enterNumber){
        if (enterNumber == 0)
            System.out.println("Enter skills id which should be read");
        else if (enterNumber == 1)
            System.out.println("Enter skills id which should be update");
        else if (enterNumber == 2)
            System.out.println("Enter skills id which should be delete");
        return sc.nextLine();
    }
    public void printSkill(String skillName){
        System.out.println("Skill name is " + skillName);
    }
    public void showAllSk(Long id, String skillName){
        System.out.println(id + "   " + skillName);
    }
    public String showInfo(int infNumber){
        if (infNumber == 0)
            System.out.println("Enter Developer's id");
        if (infNumber == 1)
            System.out.println("Enter Skill's id");
        if (infNumber == 2) {
            System.out.println("id   |   Skill_NAME");
            return null;
        }
        return sc.nextLine();
    }
    public void printError(int errNumber){
        if (errNumber == 0)
            System.out.println("Warning! usage: <developer_first_name> <developer_last_name> <developer_salary>");
        else if (errNumber == 2)
            System.out.println("Warning! Enter in long format");
        else if (errNumber == 3)
            System.out.println("Warning! There is no such skill in the database");
        else if (errNumber == 4)
            System.out.println("Warning! There is no skills in the database");
        else if (errNumber == 5)
            System.out.println("Warning! There is no such developer/skill in database");
    }

}
