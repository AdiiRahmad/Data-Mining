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

/**
 *
 * @author RIDWAN
 */
public class ModelClassification {
    
    public String nama;
    public String nis;
    public String jenisKelamin;
    public String Mtk1;
    public String Bindo1;
    public String Bing1;
    public String Ipa1;
    public String Mtk2;
    public String Bindo2;
    public String Bing2;
    public String Ipa2;
    public String HasilTO;
    public String HasilUN;
  

    /*Hasil Transformasi*/
    public int meanTO1;
    public int meanTO2;
    public int transHasilTO;

    public int getMeanTO() {
        return meanTO1;
    }

    public void setMeanTO1(int meanTO1) {
        this.meanTO1 = meanTO1;
    }

    public int getMeanTO2() {
        return meanTO2;
    }

    public void setMeanTO2(int meanTO2) {
        this.meanTO2 = meanTO2;
    }

    public int getTransHasilTO() {
        return transHasilTO;
    }

    public void setTransHasilTO(int transHasilTO) {
        this.transHasilTO = transHasilTO;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getMtk1() {
        return Mtk1;
    }

    public void setMtk1(String Mtk1) {
        this.Mtk1 = Mtk1;
    }

    public String getBindo1() {
        return Bindo1;
    }

    public void setBindo1(String Bindo1) {
        this.Bindo1 = Bindo1;
    }

    public String getBing1() {
        return Bing1;
    }

    public void setBing1(String Bing1) {
        this.Bing1 = Bing1;
    }

    public String getIpa1() {
        return Ipa1;
    }

    public void setIpa1(String Ipa1) {
        this.Ipa1 = Ipa1;
    }

    public String getMtk2() {
        return Mtk2;
    }

    public void setMtk2(String Mtk2) {
        this.Mtk2 = Mtk2;
    }

    public String getBindo2() {
        return Bindo2;
    }

    public void setBindo2(String Bindo2) {
        this.Bindo2 = Bindo2;
    }

    public String getBing2() {
        return Bing2;
    }

    public void setBing2(String Bing2) {
        this.Bing2 = Bing2;
    }

    public String getIpa2() {
        return Ipa2;
    }

    public void setIpa2(String Ipa2) {
        this.Ipa2 = Ipa2;
    }

    public String getHasilTO() {
        return HasilTO;
    }

    public void setHasilTO(String HasilTO) {
        this.HasilTO = HasilTO;
    }

    public String getHasilUN() {
        return HasilUN;
    }

    public void setHasilUN(String HasilUN) {
        this.HasilUN = HasilUN;
    }
    
    

    public int getRowCount() {
        int rowCount = 0;
        try {
            DbConnection dbConnection = new DbConnection();
            Connection connect = dbConnection.connect();
            Statement stm = connect.createStatement();
            String query = "SELECT COUNT(*) FROM siswa";
            ResultSet rs = stm.executeQuery(query);
            rowCount = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Model.ModelClassification.getRowCount() " + e);
            
            e.printStackTrace();

        }
        return rowCount;
    }

    public String[][] getData(int rowCOuntModel) {
        String[][] data = new String[rowCOuntModel][10];
        try {
            DbConnection dbConnection = new DbConnection();
            Connection connect = dbConnection.connect();
            Statement stm = connect.createStatement();
            String query = "SELECT * FROM siswa";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                setMtk1("mtk1");
                setBindo1("bindo1");
                setBing1("bing1");
                setIpa1("ipa1");
                setMtk2("mtk2");
                setBindo2("bindo2");
                setBing2("bing2");
                setIpa2("ipa2");
                setHasilTO("hasilto");
                break;
            }
        } catch (Exception e) {

        }
        return data;
    }

}
    

