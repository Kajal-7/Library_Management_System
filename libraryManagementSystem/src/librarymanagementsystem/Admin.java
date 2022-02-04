/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
/**
 *
 * @author Devil
 */
public class Admin {
    public static void adminServices(){
         Scanner in = new Scanner(System.in);
          Connection conn=Connectdb.connectDB(); 
          String sql;
          String bid,bname,bauth,stuid,stuname;
        while(true){
            try{
                 System.out.println("Enter any number corresponding to the options:");
                    System.out.println("1. Add book");
                    System.out.println("2. Delete Book");
                    System.out.println("3. Edit Book info");
                    System.out.println("4. Add Student");
                    System.out.println("5. Issue Book");
                    System.out.println("6. Return Book");
                    System.out.println("7. View History of Books issued to a student");
                    System.out.println("8: Exit admin menu");
                    
                    
                    int input=in.nextInt();
                    if(input==1){
                        
                        System.out.println(" Enter book ID");
                        bid= in.next();
                        System.out.println(" Enter book name");
                        bname= in.next();
                        System.out.println(" Enter book author");
                        bauth= in.next();
                        PreparedStatement stmt=conn.prepareStatement("insert into books values(?,?,?,?,?)");
                        stmt.setString(1,bid);//1 specifies the first parameter in the query
                        stmt.setString(2,bname);
                        stmt.setString(3,bauth);
                        stmt.setInt(4,0);
                        stmt.setString(5,"NULL");
                        stmt.executeUpdate();
                        stmt.close();
                        System.out.println("Book with book id: "+bid+", book name: "+bname+", book author: "+bauth+" has been added.");
                    }
                    else if(input==2){
                        System.out.println(" Enter book ID");
                        bid= in.next();

                        PreparedStatement stmt=conn.prepareStatement("DELETE FROM books WHERE book_id=?");
                        stmt.setString(1,bid);//1 specifies the first parameter in the query

                        stmt.executeUpdate();
                        stmt.close();
                        System.out.println("Book with book id: "+bid+" has been removed.");
                    }
                    else if(input==3){
                        System.out.println("Enter the book id whose information you want to edit.");
                        bid=in.next();
                        System.out.println(" Enter the new value for book name");
                        bname =in.next();
                        System.out.println(" Enter the new value for book author");
                        bauth =in.next();
                        
                        PreparedStatement stmt=conn.prepareStatement("UPDATE books SET book_name = ?, book_author = ? WHERE book_id = ?;");
                        //1 specifies the first parameter in the query
                        stmt.setString(1,bname);
                        stmt.setString(2,bauth);
                        stmt.setString(3,bid);
                        stmt.executeUpdate();
                        stmt.close();
                        System.out.println("Book inforamtion updated.");
                    }
                    else if(input==4){
                        String count;
                        count="0";
                        System.out.println("Enter student id");
                        stuid=in.next();
                        System.out.println("Enter student name");
                        stuname=in.next();
                        
                        PreparedStatement stmt=conn.prepareStatement("Insert into students values (?,?,?)");
                         stmt.setString(1,stuid);//1 specifies the first parameter in the query
                        stmt.setString(2,stuname);
                        stmt.setString(3,count);
                         stmt.executeUpdate();
                         stmt.close();
                         System.out.println("Student added successfully.");
                    }
                    else if(input==5){
                        System.out.println("Enter the student id");
                        stuid=in.next();
                        System.out.println(" Enter Book's ID");
                        bid=in.next();
                        PreparedStatement stmt;
                        sql="Select issue_status FROM books WHERE book_id=?";
                         stmt = conn.prepareStatement(sql);
                         stmt.setString(1, bid);
                          ResultSet rs=stmt.executeQuery();
                          if(rs.next()){
                               int status  = rs.getInt("issue_status");
                               if(status==1){
                                   System.out.println("Book already issued");
                                   continue;
                               }
                               else{
                                  
                                   sql="select countOfBooks from students where student_id=?";
                               stmt=conn.prepareStatement(sql);
                               stmt.setString(1, stuid);
                               rs=stmt.executeQuery();
                               rs.next();
                               int count=rs.getInt("countOfBooks");
                               if(count>3){
                                   System.out.println("Already four books are issued.No more books!");
                                   break;
                               }                            
                                //update status of book
                                String temp = "1";
                               sql="update books set issue_status='"+temp+"' where book_id='"+bid+"'";
                               stmt=conn.prepareStatement(sql);
                               stmt.execute();
                               //update issued by
                                sql="update books set issuedby='"+stuid+"' where book_id='"+bid+"'";
                               stmt=conn.prepareStatement(sql);
                               stmt.execute();
                               
                               //update count of books
                               count+=1;
                               sql="update students set countOfBooks='"+count+"' where student_id='"+stuid+"'";
                               stmt=conn.prepareStatement(sql);
                               stmt.execute();
                               //insert this process in issuelist
                               //Getting the current Date value
                                LocalDate currentDate = LocalDate.now();
                               sql="Insert into issuelist (student_id,book_id,issue_date,fine) Values ('"+stuid+"','"+bid+"','"+currentDate+"',0)";
                               stmt=conn.prepareStatement(sql);
                               stmt.execute();
                               
                                System.out.println("Issued Successfully!\n");
                               }
                          }
                          else{
                              System.out.println("Wrong book id.");
                          }
                           rs.close();
                          stmt.close();                                                
                    }
                    else if(input==6){
                        System.out.println("Enter the student id");
                        stuid=in.next();
                        System.out.println(" Enter Book's ID");
                         bid= in.next();
                         System.out.println("Enter issuelist id");
                         String transid=in.next();
                         
                         sql="Select issuedby FROM books WHERE book_id=?";
                         PreparedStatement stmt = conn.prepareStatement(sql);
                         stmt.setString(1, bid);
                         ResultSet rs=stmt.executeQuery();
                         if(!rs.next()){
                              System.out.println("Invalid book id");
                              continue;
                         }
                         String issued_id = rs.getString("issuedby");
                              
                              if(!issued_id.equals(stuid)){
                                  System.out.println("This book is not issued by "+stuid+" .");
                              }
                              else{
                                 // LocalDate currentDate = LocalDate.now();
                                 Date currentDate=new Date();
                                    sql="Select issue_date FROM issuelist WHERE id=?";
                                    stmt = conn.prepareStatement(sql);
                                    stmt.setString(1, transid);
                                    rs=stmt.executeQuery();
                                   
                                    int fine_generated=0;
                                    if(rs.next()){
                                        Date fg=rs.getDate("issue_date");
                                         long time_difference = fg.getTime() - currentDate.getTime();  
                                          long days_difference = (time_difference / (1000*60*60*24))-15; 
                                                                         
                                      if(days_difference>0)
                                      {
                                          fine_generated+=(days_difference*5);
                                      }
                           
                                    }else{
                                        System.out.println("Wrong issuelist id");
                                        break;
                                    }
                                  //update status and issued by in books table
                                  String temp="0";
                                  sql="Update books SET issue_status='"+temp+"',issuedby= null where book_id= '"+bid+"'";
                                  stmt=conn.prepareStatement(sql);
                                  stmt.execute(); 
                                   // update count of books issued
                                  sql="select countOfBooks from students where student_id=?";
                                   stmt=conn.prepareStatement(sql);
                                   stmt.setString(1, stuid);
                                   rs=stmt.executeQuery();
                                   rs.next();
                                   int count=rs.getInt("countOfBooks");count=count-1;
                                   sql="update students set countOfBooks='"+count+"' where student_id='"+stuid+"'";
                                   stmt=conn.prepareStatement(sql);
                                   stmt.execute();
                                   
                                    sql="update issuelist set fine='"+fine_generated+"' where id='"+transid+"'";
                                   stmt=conn.prepareStatement(sql);
                                   stmt.execute();
                                   
                                   System.out.println("Book of student with student id "+stuid+" has returned the book with book id "+bid+" with fine of "+fine_generated+".");
                              }
                              rs.close();
                              stmt.close();
                                                  
                    }
                    else if(input==7){
                        System.out.println("Enter the student id");
                        stuid=in.next();
                        System.out.println("Books issued by student whose roll no is "+stuid+" are:");
                        sql="Select book_id,issue_date from issuelist where student_id= ?";
                        PreparedStatement stmt=conn.prepareStatement(sql);                         
                         stmt.setString(1, stuid);
                         ResultSet rs=stmt.executeQuery();
                         while(rs.next()){
                                                       
                            String bkid  = rs.getString("book_id");
                            String issuedate = rs.getString("issue_date");
                           
                            //Display values
                            System.out.print("BookID: " + bkid);
                            System.out.println(", Issue Date " + issuedate);
                       
                        }
                        rs.close();
                        stmt.close();
                    }else if(input==8){
                        System.out.println("Exiting the admin menu...");
                        break;
                    }
                    else{
                        System.out.println("Wrong input!");
                    }
                    
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
