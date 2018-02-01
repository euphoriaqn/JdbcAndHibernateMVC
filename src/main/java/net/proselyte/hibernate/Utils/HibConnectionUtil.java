package net.proselyte.hibernate.Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibConnectionUtil {

    private static HibConnectionUtil connection;


    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private HibConnectionUtil(){}
    public static HibConnectionUtil getConnection(){
        if (connection==null){
            printInfo();
            connection = new HibConnectionUtil();
        }
        return connection;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void printInfo(){
        System.out.println("FIRST CONFIGURATION OF SESSION FACTORY. CREATING TABLES. PLEASE WAIT");
    }
}


