/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Connection.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RIDWAN
 */

public class ModelTesting {
    
     public String query;
    public ArrayList<String> tahunAjaran = new ArrayList<>();

    public ArrayList<String> getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(ArrayList<String> tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    Connection connect;
    Statement stm;
    ResultSet rs;
    DbConnection dbConnection = new DbConnection();

    public void showTahunAjaran() {
        try {
            //controllerTesting = new ControllerTesting();
            connect = dbConnection.connect();
            stm = connect.createStatement();
            query = "SELECT * FROM tahun";
            System.out.println("Model.ModelTesting.showTahunAjaran()");
            rs = stm.executeQuery(query);
            while (rs.next()) {
                tahunAjaran.add(rs.getString("tahun_ajaran"));
                /*Remark!!*/
                System.out.println(tahunAjaran);
                System.out.println("Sudah sampe sini belom?");
            }
//            rs.close();
//            stm.close();
//            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
    

