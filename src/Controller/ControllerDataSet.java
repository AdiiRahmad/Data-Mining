/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.ModelDataset;

import com.toedter.calendar.JYearChooser;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RIDWAN
 */
public class ControllerDataSet {
    
    public ModelDataset modelDataSet = new ModelDataset();
    boolean yes;

    public String nis;
    public String nama;
    public String jenisKelamin;
    public String mtk1;
    public String bindo1;
    public String bing1;
    public String ipa1;
    public String mtk2;
    public String bindo2;
    public String bing2;
    public String ipa2;
    public String hasilto;
    public String hasilun;
    public String tahunAjaran;
    



    public void importToDatabase(int rowValue, int columnValue, String[][] data, JYearChooser thnAjaran1, JYearChooser thnAjaran2, DefaultTableModel tableModel, int countOutlier, JLabel lableAllData) throws SQLException {
        tahunAjaran = Integer.toString(thnAjaran1.getYear()) + "/" + Integer.toString(thnAjaran2.getYear());
        modelDataSet.setTahunAjaran(tahunAjaran);
        boolean cekTahun = modelDataSet.cekTahunAjaran();
        if (cekTahun == true) {
            JOptionPane.showMessageDialog(null, "Tahun Ajaran " + thnAjaran1.getYear() + "/" + thnAjaran2.getYear() + " sudah ada, periksa Tahun Ajaran!", "Peringatan", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/fail.png"));
        } else {
            int rowData = tableModel.getRowCount();
            for (int i = 1; i < rowValue; i++) {
                yes = false;
                int j = 0;
                while (j < columnValue) {
                    nis = data[i][j];
                    nama = data[i][j + 1];
                    jenisKelamin = data[i][j + 2];
                    mtk1 = data[i][j + 3];
                    bindo1 = data[i][j + 4];
                    bing1 = data[i][j + 5];
                    ipa1 = data[i][j + 6];
                    mtk2 = data[i][j + 7];
                    bindo2 = data[i][j + 8];
                    bing2 = data[i][j + 9];
                    ipa2 = data[i][j + 10];
                    hasilto = data[i][j + 11];
                    hasilun = data[i][j + 12];
                    /*Pre-Processing*/
                    cleansingData(tableModel, i);
                    break;
                }
                if (yes == false) {
                    modelDataSet.setNis(nis);
                    modelDataSet.setNama(nama);
                    modelDataSet.setJenisKelamin(jenisKelamin);
                    modelDataSet.setMtk1(mtk1);
                    modelDataSet.setBindo1(bindo1);
                    modelDataSet.setBing1(bing1);
                    modelDataSet.setIpa1(ipa1);
                    modelDataSet.setMtk2(mtk2);
                    modelDataSet.setBindo2(bindo2);
                    modelDataSet.setBing2(bing2);
                    modelDataSet.setIpa2(ipa2);
                    modelDataSet.setHasilTO(hasilto);
                    modelDataSet.setHasilUN(hasilun);
                    
                    modelDataSet.saveDataNilai();

                }
            }

//            modelDataSet.setJumlahData(((rowValue - 1) - countOutlier));
//            modelDataSet.saveTahunAjaran();
            lableAllData.setText(countTotalData() + " Data");
            JOptionPane.showMessageDialog(null, "Berhasil Meng-Import Dataset.\n\n"
                    + "Informasi :\n"
                    + "- " + ((rowValue - 1) - countOutlier) + " Data\n"
                    + "- " + countOutlier + " Outlier", "Sukses", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/success.png"));

        }

    }

    public void cleansingData(DefaultTableModel tableModel, int i) {
        double tempMtk1;
        double tempBindo1;
        double tempBing1;
        double tempIpa1;
        double tempMtk2;
        double tempBindo2;
        double tempBing2;
        double tempIpa2;
        tempMtk1 = Double.parseDouble(mtk1);
        tempBindo1 = Double.parseDouble(bindo1);
        tempBing1 = Double.parseDouble(bing1);
        tempIpa1 = Double.parseDouble(ipa1);
        tempMtk2 = Double.parseDouble(mtk2);
        tempBindo2 = Double.parseDouble(bindo2);
        tempBing2 = Double.parseDouble(bing2);
        tempIpa2 = Double.parseDouble(ipa2);
        if (tempMtk1 == 0.0 || tempBindo1 == 0.0 || tempBing1 == 0.0 || tempIpa1 == 0.0  || tempMtk2 == 0.0 || tempBindo2 == 0.0 || tempBing2 == 0.0 || tempIpa2 == 0.0) {
            //tableModel.removeRow(i);
            yes = true;
        }
        //return yes;
    }

    public void checkAnomaliData() {
         double tempMtk1;
        double tempBindo1;
        double tempBing1;
        double tempIpa1;
        double tempMtk2;
        double tempBindo2;
        double tempBing2;
        double tempIpa2;
        tempMtk1 = Double.parseDouble(mtk1);
        tempBindo1 = Double.parseDouble(bindo1);
        tempBing1 = Double.parseDouble(bing1);
        tempIpa1 = Double.parseDouble(ipa1);
        tempMtk2 = Double.parseDouble(mtk2);
        tempBindo2 = Double.parseDouble(bindo2);
        tempBing2 = Double.parseDouble(bing2);
        tempIpa2 = Double.parseDouble(ipa2);
        if (tempMtk1 == 0.0 || tempBindo1 == 0.0 || tempBing1 == 0.0 || tempIpa1 == 0.0  || tempMtk2 == 0.0 || tempBindo2 == 0.0 || tempBing2 == 0.0 || tempIpa2 == 0.0) {
//            tableModel.removeRow(i);
            yes = true;
        }
    }

    public int countTotalData() {
        int totalData;
        modelDataSet = new ModelDataset();
        totalData = modelDataSet.selectTotalData();
        return totalData;
    }

}
    

