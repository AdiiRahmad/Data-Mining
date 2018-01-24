/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Connection.DbConnection;
//import Controller.ControllerEvaluation;
//import Controller.ControllerTestting;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author RIDWAN
 */
public class ModelEvaluation {
    
    public ArrayList<String> tahunAjaran;
    //public ControllerEvaluation controllerEvaluation;
    public DbConnection dbConnection;
    
    public String query;
    public ArrayList<String> tableContent;
    public Connection connect;
    public Statement stm;
    public ResultSet rs;
    public PreparedStatement pstmt;

    public String nis;
    public String nama;
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
    
    public ArrayList<String> getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(ArrayList<String> tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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


   

    public void selectTahunAjaran() {
        try {
            //controllerEvaluation = new ControllerEvaluation();
            tahunAjaran = new ArrayList<>();
            dbConnection = new DbConnection();
            connect = dbConnection.connect();
            stm = connect.createStatement();
//            query = "SELECT * FROM tahun";
            query = "SELECT tahun_ajaran FROM siswa GROUP BY tahun_ajaran";
            rs = stm.executeQuery(query);
            while (rs.next()) {
                tahunAjaran.add(rs.getString("tahun_ajaran"));
                //setTahunAjaran(tahunAjaran);
                /*Remark!!*/
            }
//            rs.close();
//            stm.close();
//            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void showDataSetModel(String tempArrayList) throws SQLException {

        /*Tanpa setter method*/
        //controllerEvaluation = new ControllerEvaluation();
        dbConnection = new DbConnection();
        connect = dbConnection.connect();

        //stm = connect.createStatement();
        query = "SELECT * FROM siswa WHERE tahun_ajaran = ?";
        pstmt = connect.prepareStatement(query);
        pstmt.setString(1, tempArrayList.toString());
        rs = pstmt.executeQuery();
        while (rs.next()) {
            setNis(rs.getString("nis"));
            setNama(rs.getString("nama"));
            setJenisKelamin(rs.getString("jenis_kelamin"));
            setMtk1(rs.getString("mtk1"));
            setBindo1(rs.getString("bindo1"));
            setBing1(rs.getString("bing1"));
            setIpa1(rs.getString("ipa1"));
            setMtk2(rs.getString("mtk2"));
            setBindo2(rs.getString("bindo2"));
            setBing2(rs.getString("bing2"));
            setIpa2(rs.getString("Ipa2"));
            setHasilTO(rs.getString("hasilto"));
            setHasilUN(rs.getString("hasilun"));
            break;
        }
    }

}
    

