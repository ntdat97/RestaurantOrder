package controller;
import java.sql.Connection;
import java.sql.DriverManager;

public class mysqlconnect {
    public Connection getConnection(){
    Connection conn = null;
    try{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/menu","root", "");
        if (conn != null) {System.out.println("Ket noi thanh cong");
        }
    } catch (Exception ex){
        System.out.println("Ket khong thanh cong");
        ex.printStackTrace();
    }
    return conn;
}
}