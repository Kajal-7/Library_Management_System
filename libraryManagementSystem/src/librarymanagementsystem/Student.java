/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;
import java.util.Scanner;
import java.sql.*;


/**
 *
 * @author Devil
 */
public class Student {
    public static void studentServices(){
         Scanner in = new Scanner(System.in);
         Connection conn=Connectdb.connectDB();
         ResultSet rs;
         PreparedStatement st;
         String sql;
         String stuid;
         //<------------Student Credentials Matching-------->
         System.out.println("Enter student id");
         stuid=in.next();
         try{
             sql="Select student_id FROM students Where student_id=?";
             st=conn.prepareStatement(sql);
             st.setString(1, stuid);
             rs=st.executeQuery();
             if(rs.next()){
                 System.out.println("Student Menu");
             }else{
                 System.out.println("Wrong Student Id");
                 return;
             }  
             rs.close();
             st.close();
         }catch(Exception e){
             System.out.println(e);
         }
         while(true){
             
               try{
                   System.out.println("Select any number corresponding to the options:");
                    System.out.println("1. Search Book");
                    System.out.println("2. Check history of books issued");
                    System.out.println("3. Check books currently issued (Self-status).");
                    System.out.println("4. Exit");
                    int input=in.nextInt();
                    if(input==1){
                         System.out.println(" Enter Book's ID");
                         String bid= in.next();
                         sql="Select book_name,book_author,issue_status FROM books WHERE book_id=?";
                         st = conn.prepareStatement(sql);
                         st.setString(1, bid);
                         rs=st.executeQuery();
                         if(rs.next()){
                             int status  = rs.getInt("issue_status");
                             String author = rs.getString("book_author");
                             String book = rs.getString("book_name");

                            //Display values
                            System.out.print("Book ID: " + bid);
                            System.out.print(", Book Name: " + book);
                            System.out.print(", Book author: " + author);
                            if(status == 1)
                              System.out.println(", Book already issued! \n");
                            else{
                                System.out.println(", Book is available!");                                                                                                                                                      
                            }     
                         }
                         else{
                             System.out.println("Invalid book id");
                         }
                         
                    }
                    else if(input==2){
                        System.out.println("Books issued by student whose roll no is "+stuid+" are:");
                        sql="Select book_id,issue_date from issuelist where student_id= ?";
                         st=conn.prepareStatement(sql);
                         st.setString(1, stuid);
                         rs=st.executeQuery();
                         while(rs.next()){
                            //Retrieve by column name
                            
                            String bkid  = rs.getString("book_id");
                            String issuedate = rs.getString("issue_date");
                           
                            //Display values
                            System.out.print("BookID: " + bkid);
                            System.out.println(", Issue Date " + issuedate);
                       
                        }
                        
                    }
                    else if(input==3){
                         sql="Select book_id,book_name,book_author from books where issuedby= ? and issue_status=?";
                         st=conn.prepareStatement(sql);
                         st.setString(1,stuid);
                         st.setString(2,"1");
                         rs=st.executeQuery();
                         System.out.println("Self- status: Currently books issued by student whose id is "+stuid+"are: ");
                         while(rs.next()){
                             String bkid=rs.getString("book_id");
                             String bkname=rs.getString("book_name");
                             String bkauth=rs.getString("book_author");
                             System.out.print("Book ID: " + bkid);
                            System.out.print(", Book Name: " + bkname);
                            System.out.println(", Book author: " + bkauth);
                         }
                    }
                    else if(input==4){
//                         rs.close();
//                    st.close();
//                    conn.close();
                        System.out.println("Exiting student menu...");
                        break;
                    }
                    else{
                         System.out.println("Wrong Input");
                         continue;
                    }
                   
               }
               catch(Exception e){
                   System.out.println(e);}
               
             
         }
             
    }
}
