/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;
import java.util.Scanner;
/**
 *
 * @author Devil
 */
public class LibraryManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        
        while(true){
            System.out.println("<-----------WELCOME TO LIBRARY MANAGEMENT SYSTEM--------------->");
            System.out.println("Enter the number corresponding to your role.");
            System.out.println("Enter 1 if Admin");
            System.out.println("Enter 2 if Student");
            System.out.println("Enter 3 to Exit");
            int input=in.nextInt();
            if(input==1){
                Admin.adminServices();
            }
            else if(input==2){
                Student.studentServices();
            }
            else if(input==3){
                System.out.println("Exiting...");
                break;
            }
            else{
                System.out.println("Wrong Input!");
            }
        }
    }
    
}
