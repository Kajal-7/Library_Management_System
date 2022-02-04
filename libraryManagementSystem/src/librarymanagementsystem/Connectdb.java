/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;
import java.sql.*;
/**
 *
 * @author Devil
 */
public class Connectdb {
    public static Connection connectDB(){
         try{
             
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb","root","iiit127_kk");
        System.out.println("connection established");
        return conn;
        }
        catch(Exception e){
            System.out.println("fail");
            System.out.println(e);}
         return null;
    }
}
