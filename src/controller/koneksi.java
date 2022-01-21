/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi {
    private static Connection koneksi;
    public static Connection getKoneksi(){
        if(koneksi == null){
            try {
                String url;
                url = "jdbc:mysql://localhost:3306/sekolah_xiirpl3";
                String username = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url,username,password);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error Connection");
            }
        } return koneksi;     
     
    }
    
    static Object getConnection (){
            throw new UnsupportedOperationException("Not Yet Implemented");
    }
}

