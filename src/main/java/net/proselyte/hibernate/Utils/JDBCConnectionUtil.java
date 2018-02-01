package net.proselyte.hibernate.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionUtil {

        private static JDBCConnectionUtil connectionUtil;


        private final String DRIVER = "com.mysql.jdbc.Driver";
        private final String URL = "jdbc:mysql://localhost:3306/hibernate_homework?serverTimezone=UTC";
        private final String USER = "root";
        private final String PASS = "admex281289iiLL";
        private JDBCConnectionUtil(){
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASS);
        }


        public static JDBCConnectionUtil getInstance(){
            if (connectionUtil == null){
                connectionUtil = new JDBCConnectionUtil();
            }
            return connectionUtil;
        }
    }

