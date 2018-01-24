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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RIDWAN
 */
public class ModelInternalFrame {
    
    public String tahun;
    public String nim;
    public String nama;
    public String nilai;
    private String query;
    public String hasiltahun;
    boolean validasi = false;
    public Connection connect;
    private PreparedStatement prepStat;
    public DbConnection dbConnection;

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public void importToDatabase() {
        dbConnection = new DbConnection();
        try (Connection conn = dbConnection.connect();
                Statement sta = conn.createStatement();) {
            String query = "SELECT * FROM tahun WHERE tahun_ajaran";
            ResultSet hasil = sta.executeQuery(query);

            while (hasil.next()) {
                hasiltahun = hasil.getString("tahun_ajaran");

                if (hasiltahun.equals(tahun)) {
                    validasi = true;
                    System.out.println("Tahun ajaran sudah ada!");
                    break;
                }

            }
            hasil.close();
            if (validasi == false) {
                System.out.println("Ini dikerjain");
                saveTahunAjaran();
            }

            saveDataSiswa();

        } catch (SQLException ex) {
            Logger.getLogger(ModelInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("errornya disini " + e);
        }
    }

    public void saveTahunAjaran() {
        dbConnection = new DbConnection();
        try (Connection conn = dbConnection.connect();
                Statement sta = conn.createStatement();) {

            String query = "INSERT INTO tahun (tahun_ajaran) VALUES ('" + tahun + "')";
            sta.executeUpdate(query);
            System.out.println("Sukses menyimpan tahun ajaran.");
        } catch (SQLException e) {
            System.out.println("Error savetahunAjaran() " + e);
        }
    }

    public void saveDataSiswa() {
        dbConnection = new DbConnection();
        try (Connection conn = dbConnection.connect();
                Statement stm = conn.createStatement();) {
            String query = "INSERT INTO mahasiswa (tahun_ajaran, nim, nama, nilai) VALUES ('" + tahun + "', '" + nim + "', '" + nama + "', '" + nilai + "')";
            stm.executeUpdate(query);
            System.out.println("Sukses menyimpan data siswa!");

        } catch (Exception e) {
            System.out.println("Error saveDataSiswa() :" + e);
        }
    }

}
    

