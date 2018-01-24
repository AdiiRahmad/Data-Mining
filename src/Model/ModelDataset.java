/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author RIDWAN
 */
public class ModelDataset {
    
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
    public String tahunAjaran;
    public int jumlahData;
//    private boolean cekTahun;
    private String query;

    public Connection connection;
    public DbConnection dbConnection = new DbConnection();
    public PreparedStatement preparedStatement;
    public Statement statement;
    public ResultSet resultSet;

    public int getJumlahData() {
        return jumlahData;
    }

    public void setJumlahData(int jumlahData) {
        this.jumlahData = jumlahData;
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

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public void saveTahunAjaran() {
        String tempTahun;
        boolean cekTahun = false;
        try {
            connection = dbConnection.connect();
            statement = connection.createStatement();
            query = "SELECT tahun_ajaran FROM siswa GROUP BY tahun_ajaran";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempTahun = resultSet.getString("tahun_ajaran");
                if (tempTahun.equals(tahunAjaran)) {
                    cekTahun = true;
                    break;
                }
            }
//            resultSet.close();
//            statement.close();
//            connection.close();

//            if (cekTahun == false) {
//                query = "INSERT INTO tahun(tahun_ajaran, jumlah_data)"
//                        + "VALUES (?, ?)";
//                preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setString(1, getTahunAjaran());
//                preparedStatement.setInt(2, getJumlahData());
//                preparedStatement.executeUpdate();
//            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Model.ModelDataSet.saveTahunAjaran()\n " + e);
        }
    }

    public void saveDataNilai() {
        try {
            connection = dbConnection.connect();
            query = "INSERT INTO siswa(tahun_ajaran, nis, nama, jenis_kelamin, mtk1, bindo1, bing1, ipa1, mtk2, bindo2, bing2, ipa2, hasilto, hasilun) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getTahunAjaran());
            preparedStatement.setString(2, getNis());
            preparedStatement.setString(3, getNama());
            preparedStatement.setString(4, getJenisKelamin());
            preparedStatement.setString(5, getMtk1());
            preparedStatement.setString(6, getBindo1());
            preparedStatement.setString(7, getBing1());
            preparedStatement.setString(8, getIpa1());
            preparedStatement.setString(9, getMtk2());
            preparedStatement.setString(10, getBindo2());
            preparedStatement.setString(11, getBing2());
            preparedStatement.setString(12, getIpa2());
            preparedStatement.setString(13, getHasilTO());
            preparedStatement.setString(14, getHasilUN());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Model.ModelDataSet.saveDataNilai()\n " + e);
        }
    }

    public int selectTotalData() {
        int totalData = 0;
        try {
            dbConnection = new DbConnection();
            connection = dbConnection.connect();
            query = "SELECT COUNT(*) AS total_data FROM siswa";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                totalData = resultSet.getInt("total_data");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
        return totalData;
    }

    public boolean cekTahunAjaran() throws SQLException {
        String tempTahun;
        boolean cekTahun = false;
        try {
            connection = dbConnection.connect();
            statement = connection.createStatement();
            query = "SELECT tahun_ajaran FROM siswa GROUP BY tahun_ajaran";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempTahun = resultSet.getString("tahun_ajaran");
                if (tempTahun.equals(tahunAjaran)) {
                    cekTahun = true;
                    break;
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Model.ModelDataSet.saveTahunAjaran()\n " + e);
        }
        return cekTahun;
    }

}

