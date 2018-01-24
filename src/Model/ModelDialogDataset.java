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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RIDWAN
 */
public class ModelDialogDataset {

    public ArrayList<Object[]> dataSet = new ArrayList<Object[]>();
    public String tahunAjaran;
    public int jumlahData;

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public int getJumlahData() {
        return jumlahData;
    }

    public void setJumlahData(int jumlahData) {
        this.jumlahData = jumlahData;
    }

    public ArrayList<Object[]> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<Object[]> dataSet) {
        this.dataSet = dataSet;
    }

    public void selectDataset() {
        try {
            DbConnection dbConnection = new DbConnection();
            Connection connect = dbConnection.connect();
            String query = "SELECT tahun_ajaran, COUNT(nis) as jumlah_data FROM siswa GROUP BY tahun_ajaran";
            Statement stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                tahunAjaran = rs.getString("tahun_ajaran");
                jumlahData = rs.getInt("jumlah_data");
                Object[] tempData = {tahunAjaran, jumlahData};
                dataSet.add(tempData);
            }

        } catch (Exception e) {
            System.out.println("Model.ModelDialogDataset.selectDataset()");
            e.printStackTrace();
        }
    }

}
