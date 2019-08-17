/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyConection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conection {
    public static Connection myConnection()
    {
        Connection conn = null;
             try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
             conn =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","mamta");
            if(!conn.isClosed())
                System.out.println("successfully established");
            else
                System.out.println("not established");
        }
        catch(Exception ex) 
        {
            System.out.println("Error: unable to load driver class!"+ex.getMessage());
        }
        return conn;
   
    }
}
