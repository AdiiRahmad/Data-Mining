/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Connection.DbConnection;
import Model.ModelEvaluation;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author RIDWAN
 */
public class ControllerEvaluation {
    
    ModelEvaluation modelEvaluation;
    DefaultTableModel tableModel1;
    DefaultTableModel tableModel2;
    DefaultTableModel tableModelDataSet1;
    DefaultTableModel tableModelDataSet2;
    String query;
    String HasilUN;
    DbConnection dbConnection;
    Connection connect;
    Statement stm;
    ResultSet rs;
    PreparedStatement pstmt;
    private DecimalFormat df = new DecimalFormat("#.##");

    /*Method untuk menampilkan tahun ajaran*/
    public void showTahunAjaran(JTable tableTahunModel, JTable tableTahunTesting) {
        modelEvaluation = new ModelEvaluation();
        try {
            tableModel1 = (DefaultTableModel) tableTahunModel.getModel();
            tableModel2 = (DefaultTableModel) tableTahunTesting.getModel();
            modelEvaluation.selectTahunAjaran();
            for (int i = 0; i < modelEvaluation.getTahunAjaran().size(); i++) {
                tableModel1.addRow(new Object[]{
                    modelEvaluation.getTahunAjaran().get(i), false
                });
                tableModel2.addRow(new Object[]{
                    modelEvaluation.getTahunAjaran().get(i), false
                });
            }
            tableTahunModel.setModel(tableModel1);
            tableTahunTesting.setModel(tableModel2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Method untuk menampilkan dataset model*/
    public void showDataSetModel(JTable tableDataSetModel, JTable tableTahunModel, JLabel totalDataModel) throws SQLException {
        int row;
        int transHasilTO;
        tableModelDataSet1 = (DefaultTableModel) tableDataSetModel.getModel();
        row = tableModelDataSet1.getRowCount();
        for (int i = 0; i < row; i++) {
            tableModelDataSet1.removeRow(0);
        }
        row = tableTahunModel.getRowCount();
        boolean checkList;
        for (int i = 0; i < row; i++) {
            checkList = Boolean.valueOf("" + tableTahunModel.getValueAt(i, 1));
            if (checkList == true) {
                dbConnection = new DbConnection();
                connect = dbConnection.connect();
                query = "SELECT * FROM siswa WHERE tahun_ajaran = ?";
                pstmt = connect.prepareStatement(query);
                pstmt.setString(1, tableTahunModel.getValueAt(i, 0).toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String nis = rs.getString("nis");
                    String nama = rs.getString("nama");
                    String jenisKelamin = rs.getString("jenis_kelamin");
                    String Mtk1 = rs.getString("mtk1");
                    String Bindo1 = rs.getString("bindo1");
                    String Bing1 = rs.getString("bing1");
                    String Ipa1 = rs.getString("ipa1");
                    double meanTO1 = (Double.parseDouble(Mtk1) + Double.parseDouble(Bindo1) + Double.parseDouble(Bing1) + Double.parseDouble(Ipa1)) / 4;
                    String Mtk2 = rs.getString("mtk2");
                    String Bindo2 = rs.getString("bindo2");
                    String Bing2 = rs.getString("bing2");
                    String Ipa2 = rs.getString("ipa2");
                    double meanTO2 = (Double.parseDouble(Mtk2) + Double.parseDouble(Bindo2) + Double.parseDouble(Bing2) + Double.parseDouble(Ipa2)) / 4;
                    String HasilTO = rs.getString("hasilto");
                    if (HasilTO.equals("LULUS")) {
                        transHasilTO = 1;
                    } else {
                        transHasilTO = 0;
                    }
                    String HasilUN = rs.getString("hasilun");
                    Object tableContent[] = {nis, Mtk1, Bindo1, Bing1, Ipa1, meanTO1, Mtk2, Bindo2, Bing2, Ipa2, meanTO2, HasilTO, transHasilTO, HasilUN};
                    tableModelDataSet1.addRow(tableContent);
                }
            }
            tableDataSetModel.setModel(tableModelDataSet1);
            totalDataModel.setText(tableDataSetModel.getRowCount() + " Data");
        }
    }

    /*Method untuk menampilkan dataset uji*/
    public void showDataSetTest(JTable tableDataSetTesting, JTable tableTahunTesting, JLabel labelTotalDataModel) throws SQLException {
        int row;
        int transHasilTO;
        //modelEvaluation = new ModelEvaluation();
        tableModelDataSet2 = (DefaultTableModel) tableDataSetTesting.getModel();
        //tempArray = new ArrayList<String>();
        row = tableModelDataSet2.getRowCount();
        for (int i = 0; i < row; i++) {
            tableModelDataSet2.removeRow(0);
        }
        row = tableTahunTesting.getRowCount();
        boolean checkList;
        for (int i = 0; i < row; i++) {
            checkList = Boolean.valueOf("" + tableTahunTesting.getValueAt(i, 1));
            if (checkList == true) {
                dbConnection = new DbConnection();
                connect = dbConnection.connect();
                query = "SELECT * FROM siswa WHERE tahun_ajaran = ?";
                pstmt = connect.prepareStatement(query);
                pstmt.setString(1, tableTahunTesting.getValueAt(i, 0).toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String nis = rs.getString("nis");
                    String nama = rs.getString("nama");
                    String jenisKelamin = rs.getString("jenis_kelamin");
                    String Mtk1 = rs.getString("mtk1");
                    String Bindo1 = rs.getString("bindo1");
                    String Bing1 = rs.getString("bing1");
                    String Ipa1 = rs.getString("ipa1");
                    double meanTO1 = (Double.parseDouble(Mtk1) + Double.parseDouble(Bindo1) + Double.parseDouble(Bing1) + Double.parseDouble(Ipa1)) / 4;
                    String Mtk2 = rs.getString("mtk2");
                    String Bindo2 = rs.getString("bindo2");
                    String Bing2 = rs.getString("bing2");
                    String Ipa2 = rs.getString("ipa2");
                    double meanTO2 = (Double.parseDouble(Mtk2) + Double.parseDouble(Bindo2) + Double.parseDouble(Bing2) + Double.parseDouble(Ipa2)) / 4;
                    String HasilTO = rs.getString("hasilto");
                    if (HasilTO.equals("LULUS")) {
                        transHasilTO = 1;
                    } else {
                        transHasilTO = 0;
                    }
                    HasilUN = rs.getString("hasilun");
                    Object tableContent[] = {nis, Mtk1, Bindo1, Bing1, Ipa1, meanTO1, Mtk2, Bindo2, Bing2, Ipa2, meanTO2, HasilTO, transHasilTO
                    };

                    tableModelDataSet2.addRow(tableContent);
                }
            }
            tableDataSetTesting.setModel(tableModelDataSet2);
            labelTotalDataModel.setText(tableDataSetTesting.getRowCount() + " Data");
        }
    }

    /*Method untuk mengambil nilai dataset model*/
    private String[][] getModelValue(int rowCountModel, JTable tableDataSetModel) {
        String[][] returnValue = new String[rowCountModel][4];
        //System.out.println(tableModelDataSet1.getValueAt(0, 2));
        // System.out.println("Ini Data Set Model");
        for (int i = 0; i < rowCountModel; i++) {
            returnValue[i][0] = tableDataSetModel.getValueAt(i, 5).toString();
            returnValue[i][1] = tableDataSetModel.getValueAt(i, 10).toString();
            returnValue[i][2] = tableDataSetModel.getValueAt(i, 12).toString();
            returnValue[i][3] = tableDataSetModel.getValueAt(i, 13).toString();
        }
        /// System.out.println("======================================");
        return returnValue;
    }

    /*Method untuk mengambil nilai dataset uji*/
    private double[][] getTestvalue(int rowCountTest, JTable tableDataSetTesting) {
        double[][] returnValue = new double[rowCountTest][3];
        //System.out.println("Ini Data Set Testing");
        for (int i = 0; i < rowCountTest; i++) {
            returnValue[i][0] = Double.parseDouble(tableDataSetTesting.getValueAt(i, 5).toString());
            returnValue[i][1] = Double.parseDouble(tableDataSetTesting.getValueAt(i, 10).toString());
            returnValue[i][2] = Double.parseDouble(tableDataSetTesting.getValueAt(i, 12).toString());
            
        }
        //  System.out.println("======================================");
        return returnValue;
    }

    /*Method untuk melakukan proses mining+klasifikasi+pengujian+evaluasi*/
    public void proccessMining(JTable tableDataSetModel, JTable tableDataSetTesting, JTextField txtNumberOfK,
            JLabel labelPesanError, JTabbedPane jTabbedPane1, JTable tableResult, JTable tableConfMatrix,
            JTable tableTahunTesting, JLabel totalAccuracy, JPanel panelChart, JPanel panelChart1,
            JPanel panelChart2, JRadioButton singleTesting, JRadioButton multiTesting, JTextArea txtArea) throws SQLException {
        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        modelEvaluation = new ModelEvaluation();
        int rowCountModel = tableDataSetModel.getRowCount();
        int rowCountTest = tableDataSetTesting.getRowCount();
        int[] tempK;
        double[][] tempEval;
        double[][] evalValue;
        boolean valid = false;

        /*Validasi Dataset Model dan Dataset Uji*/
        if (rowCountModel == 0) {
            JOptionPane.showMessageDialog(null, "Pilih dataset model terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
            txtNumberOfK.requestFocus();
        } else if (rowCountTest == 0) {
            JOptionPane.showMessageDialog(null, "Pilih dataset uji terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
            txtNumberOfK.requestFocus();
        } else {
            valid = true;
        }
        /*Validasi Dataset Model dan Dataset Uji*/

        if (valid == true) {
            if (multiTesting.isSelected()) {
                String iterasi = JOptionPane.showInputDialog("Input Jumlah Iterasi Pengujian :");
                boolean validMulti = false;

                if (iterasi != null) {

                    /*Validasi Jumlah Iterasi*/
                    if (Pattern.matches("[0-9]+", iterasi) == false && iterasi.length() > 0) {
                        JOptionPane.showMessageDialog(null, "Nilai iterasi tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    } else if (iterasi.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nilai iterasi tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    } else if (iterasi.length() == 9) {
                        JOptionPane.showMessageDialog(null, "Nilai iterasi terlalu panjang!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    } else if (rowCountTest > rowCountModel) {

                        JOptionPane.showMessageDialog(null, "Data Uji tidak boleh lebih besar daripada data Model!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    } else {
                        validMulti = true;
                        System.out.println("valiMulti = " + validMulti + " Kok");
                    }
                    /*Validasi Jumlah Iterasi*/
                }

                if (validMulti == true) {
                    tempK = new int[Integer.parseInt(iterasi)];
                    evalValue = new double[3][tempK.length];
                    for (int i = 0; i < Integer.parseInt(iterasi); i++) {
                        validMulti = false;
                        String k = JOptionPane.showInputDialog("Input Nilai Nearest Neighbor (k) ke " + (i + 1) + " :");
                        if (k != null) {
                            /*Validasi Nilai K Tiap Iterasi*/
                            if (Pattern.matches("[0-9]+", k) == false && k.length() > 0) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                            } else if (k.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                            } else if (k.length() == 9) {
                                JOptionPane.showMessageDialog(null, "Nilai nearest neighbor (k) terlalu panjang!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                            } else {
                                validMulti = true;
                            }
                            /*Validasi Nilai K Tiap Iterasi*/
                        }

                        if (validMulti == true) {
                            tempK[i] = Integer.parseInt(k);
                            System.out.println(tempK[i]);
                        } else {
                            break;
                        }
                    }

                    if (validMulti == true) {
                        for (int i = 0; i < tempK.length; i++) {
                            int kValue = tempK[i];
                            String[][] modelValue = getModelValue(rowCountModel, tableDataSetModel);
                            double[][] testValue = getTestvalue(rowCountTest, tableDataSetTesting);
                            String[] knnValue = getKNNValue(rowCountModel, rowCountTest, modelValue, testValue, kValue);
                            tempEval = evaluationModel(tableResult, tableConfMatrix, totalAccuracy, tableTahunTesting, tableDataSetTesting, knnValue, i, tempK, panelChart);
                            //Menampung nilai Accuracy
                            evalValue[0][i] = tempEval[0][i];
                            //Menampung nilai Recall
                            evalValue[1][i] = tempEval[1][i];
                            //Menampung nilai Precision
                            evalValue[2][i] = tempEval[2][i];
                            jTabbedPane1.setSelectedIndex(1);
                            txtArea.append("Tingkat Keberhasilan Sistem dengan Nilai Number of Nearest Neighbor (K) = " + tempK[i] + "\n");
                            txtArea.append("Akurasi\t\t: " + evalValue[0][i] * 100 + " %\n");
                            txtArea.append("Recall\t\t: " + evalValue[1][i] * 100 + " %\n");
                            txtArea.append("Precision\t: " + evalValue[2][i] * 100 + " %\n");
                            txtArea.append("=============================================================================\n");
                        }
                        showChart(tempK, evalValue, panelChart, panelChart1, panelChart2);
                    }
                }
            } else if (singleTesting.isSelected()) {
                boolean validSingle = false;
                String k = txtNumberOfK.getText();
                int nilaiK = 0;
                evalValue = new double[3][1];

                /*Validasi Nilai Number of Nearest Neighbor*/
                if (Pattern.matches("[0-9]+", k) == false && k.length() > 0) {
                    labelPesanError.setText("Number of Nearest Neighbor tidak valid");
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    txtNumberOfK.requestFocus();
                } else if (k.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    labelPesanError.setText("Number of Nearest Neighbor tidak boleh kosong");
                    txtNumberOfK.requestFocus();
                } else if (rowCountModel == 0 && Integer.parseInt(k) >= rowCountModel) {
                    JOptionPane.showMessageDialog(null, "Pilih dataset model terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    txtNumberOfK.requestFocus();
                } else if (rowCountTest == 0 && Integer.parseInt(k) >= rowCountTest) {
                    JOptionPane.showMessageDialog(null, "Pilih dataset uji terlebih dahulu!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    txtNumberOfK.requestFocus();
                } else if (Integer.parseInt(k) >= rowCountModel) {
                    JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh lebih dari " + rowCountModel + " !", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Gambar/fail.png")));
                    txtNumberOfK.requestFocus();
                } else {
                    validSingle = true;
                    nilaiK = Integer.parseInt(k);
                }
                /*Validasi Nilai Number of Nearest Neighbor*/

                if (validSingle == true) {
                    int confirm;
                    int i = 0;
                    confirm = JOptionPane.showOptionDialog(null, "Yakin ingin memproses data?", "Proses Klasifikasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == JOptionPane.OK_OPTION) {

                        int kValue = Integer.parseInt(txtNumberOfK.getText());
                        String[][] modelValue = getModelValue(rowCountModel, tableDataSetModel);
                        double[][] testValue = getTestvalue(rowCountTest, tableDataSetTesting);
                        String[] knnValue = getKNNValue(rowCountModel, rowCountTest, modelValue, testValue, kValue);
                        tempEval = evaluationModel(tableResult, tableConfMatrix, totalAccuracy, tableTahunTesting, tableDataSetTesting, knnValue, nilaiK, panelChart);
                        evalValue[0][i] = tempEval[0][0];
                        evalValue[1][i] = tempEval[1][0];
                        evalValue[2][i] = tempEval[2][0];
                        jTabbedPane1.setSelectedIndex(1);
                    }
                    System.out.println("Controller.ControllerEvaluation.proccessMining()OKOKOK");
                    showChart(nilaiK, evalValue, panelChart, panelChart1, panelChart2);
                    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                }
            }
        }

    }

    /*Proses Utama Algoritma K-Nearest Neighbor*/
    private String[] getKNNValue(int rowCountModel, int rowCountTest, String[][] modelValue, double[][] testValue, int kValue) {
        double[][] squaredRoot = new double[rowCountTest][rowCountModel];
        String[][] initClass = new String[rowCountTest][rowCountModel];
        double[] sortSquared = new double[rowCountModel];
        String[] sortHasilUN = new String[rowCountModel];
        String[] nearestNeighbor = new String[kValue];
        String[] majorClass = new String[rowCountTest];
        System.out.println("rowCountModel " + rowCountModel);
        int onRow = 0;
        for (int i = 0; i < rowCountTest; i++) {
            for (int j = 0; j < rowCountModel; j++) {
                double meanTO1 = Math.pow(Math.abs(Double.parseDouble(modelValue[j][0]) - testValue[i][0]), 2);
                double meanTO2 = Math.pow(Math.abs(Double.parseDouble(modelValue[j][1]) - testValue[i][1]), 2);
                double HasilTO = Math.pow(Math.abs(Double.parseDouble(modelValue[j][2]) - testValue[i][2]), 2);
                double sqrt = Math.sqrt(meanTO1 + meanTO2 + HasilTO);
                squaredRoot[i][j] = sqrt;
                initClass[i][j] = modelValue[j][3];
//                System.out.println("Jarak antara data ke-" + i + " dan ke-" + j + " adalah " + squaredRoot[i][j]);
            }
//            System.out.println("===================================================================================================================================================================");
            selectionSorting(rowCountModel, squaredRoot, initClass, onRow);
//            System.out.println("Sorting...");
            for (int k = 0; k < rowCountModel; k++) {
                sortSquared[k] = squaredRoot[onRow][k];
                sortHasilUN[k] = initClass[onRow][k];
//                System.out.println("jarak setelah di sorting " + sortSquared[k]);
//                System.out.println("hasilUN setelah di sorting " + sortHasilUN[k]);
                //System.out.println(sortValue[k] + "'\t\t'" + sortHasilUN[k]);
            }
            onRow++;
            nearestNeighbor = getNearestNeighbor(kValue, sortHasilUN);
            majorClass[i] = votingMajorClass(nearestNeighbor);
//            System.out.println("Kesimpulannya, Data Ke'" + i + "' kelasnya adalah : " + majorClass[i]);
        }
        return majorClass;
    }

    /*Sub-Proses dari KNN, mensorting secara ASC hasil perhitungan euclidean dist*/
    private void selectionSorting(int rowCountModel, double[][] squaredRoot, String[][] initClass, int onRow) {
        for (int i = 0; i < rowCountModel; i++) {
            double tempMin = squaredRoot[onRow][i];
            String tempHasilUN = initClass[onRow][i];
            for (int j = i; j < rowCountModel; j++) {
                if (squaredRoot[onRow][j] <= squaredRoot[onRow][i]) {
                    /*Sorting Nilai Euclidean*/
                    squaredRoot[onRow][i] = squaredRoot[onRow][j];
                    squaredRoot[onRow][j] = tempMin;
                    tempMin = squaredRoot[onRow][i];
                    /*Sorting HasilUN*/
                    initClass[onRow][i] = initClass[onRow][j];
                    initClass[onRow][j] = tempHasilUN;
                    tempHasilUN = initClass[onRow][i];
                }
            }
        }
    }

    /*Sub-Proses dari KNN, mengambil k nilai(tetangga terdekat)*/
    private String[] getNearestNeighbor(int kValue, String[] sortHasilUN) {
        String[] returnValue = new String[kValue];
        for (int i = 0; i < kValue; i++) {
            returnValue[i] = sortHasilUN[i];
        }
//        System.out.println("Jumlah '" + kValue + "' Tetangga Terdekat adalah:\n");
        int b = 0;
        while (b < kValue) {
//            System.out.println(returnValue[b]);
            b++;
        }
        return returnValue;
    }

    /*Sub-Proses dari KNN, memperoleh/memvoting kelas terbanyak*/
    private String votingMajorClass(String[] nearestNeighbor) {
        String returnValue;
        int countLULUS = 0;
        int countTIDAK = 0;
        for (int i = 0; i < nearestNeighbor.length; i++) {
            if (nearestNeighbor[i].equals("LULUS")) {
                countLULUS = countLULUS + 1;
            } else if (nearestNeighbor[i].equals("TIDAK")) {
                countTIDAK = countTIDAK + 1;
            }
        }

        if (countLULUS >= countTIDAK) {
            returnValue = "LULUS";
        } else {
            returnValue = "TIDAK";
        }

        return returnValue;
    }

    /*Method untuk memperoleh kelas/target dari dataset uji untuk dibandingkan dengan hasil klasifikasi*/
    private String[] getHasilTest(JTable tableTahunTesting, JTable tableDataSetTesting) throws SQLException {
        String[] returnValue = new String[tableDataSetTesting.getRowCount()];
        int row;
        row = tableTahunTesting.getRowCount();
        boolean checkList;
        int j = 0;
        for (int i = 0; i < row; i++) {
            checkList = Boolean.valueOf("" + tableTahunTesting.getValueAt(i, 1));
            if (checkList == true) {
                dbConnection = new DbConnection();
                connect = dbConnection.connect();
                query = "SELECT * FROM siswa WHERE tahun_ajaran = ?";
                pstmt = connect.prepareStatement(query);
                pstmt.setString(1, tableTahunTesting.getValueAt(i, 0).toString());
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    returnValue[j] = rs.getString("hasilun");
                    j++;
                }
            }
        }
        return returnValue;
    }

    /*Method untuk mendapatkan nilai hasil evaluasi. Recall, Precision, dan Accuracy*/
    private double[][] evaluationModel(JTable tableResult, JTable tableConfMatrix, JLabel totalAccuracy, JTable tableTahunTesting, JTable tableDataSetTesting, String[] knnValue, int i, int[] tempK, JPanel panelChart) throws SQLException {
        int actLULUS = 0;
        int actTIDAK = 0;
        int trueLULUS = 0;
        int falseLULUS = 0;
        int trueTIDAK = 0;
        int falseTIDAK = 0;

        double recLULUS;
        double recTIDAK;
        double preLULUS;
        double preTIDAK;
        double accuracy;

        DefaultTableModel tableModelRes = new DefaultTableModel();
        tableModelRes = (DefaultTableModel) tableResult.getModel();
        String[] tempHasilUN = getHasilTest(tableTahunTesting, tableDataSetTesting);

        for (int j = 0; j < tableDataSetTesting.getRowCount(); j++) {
            String nis = tableDataSetTesting.getValueAt(j, 0).toString();
            String HasilUN = tempHasilUN[j];
            String classified = knnValue[j];
            Object[] tableContent = {nis, HasilUN, classified};
            tableModelRes.addRow(tableContent);
            tableResult.setModel(tableModelRes);
        }

        /*Hitung Jumlah Data Actual LULUS dan TIDAK*/
        for (int j = 0; j < tempHasilUN.length; j++) {
            if (tempHasilUN[j].equals("LULUS")) {
                actLULUS = actLULUS + 1;
            } else if (tempHasilUN[j].equals("TIDAK")) {
                actTIDAK = actTIDAK + 1;
            }
        }

        /*Hitung Jumlah Data Classified LULUS dan TIDAK*/
        for (int j = 0; j < knnValue.length; j++) {
            if (tableResult.getValueAt(j, 1).equals("LULUS")) {
                if (tableResult.getValueAt(j, 1).equals(tableResult.getValueAt(j, 2))) {
                    trueLULUS = trueLULUS + 1;
                } else {
                    falseTIDAK = falseTIDAK + 1;
                }
            } else if (tableResult.getValueAt(j, 1).equals("TIDAK")) {
                if (tableResult.getValueAt(j, 1).equals(tableResult.getValueAt(j, 2))) {
                    trueTIDAK = trueTIDAK + 1;
                } else {
                    falseLULUS = falseLULUS + 1;
                }
            }
        }


        /*Hitung Nilai Recall, Precision, dan Accuracy*/
        preLULUS = (double) trueLULUS / (trueLULUS + falseLULUS);
        preTIDAK = (double) trueTIDAK / (trueTIDAK + falseTIDAK);
        recLULUS = (double) trueLULUS / (trueLULUS + falseTIDAK);
        recTIDAK = (double) trueTIDAK / (trueTIDAK + falseLULUS);
        accuracy = (double) (trueLULUS + trueTIDAK) / (trueLULUS + trueTIDAK + falseLULUS + falseTIDAK);

        /*Tampung Nilai Recall, Precision, dan Accuracy*/
        double[][] tempEval = new double[3][tempK.length];
        tempEval[0][i] = accuracy;
        tempEval[1][i] = recLULUS;
        tempEval[2][i] = preLULUS;


        /*Set Nilai TF, TN, FP, FN ke Tabel Confusion Matrix*/
        tableConfMatrix.setValueAt("Actual LULUS", 0, 0);
        tableConfMatrix.setValueAt("Actual TIDAK", 1, 0);
        tableConfMatrix.setValueAt("Class Precision", 2, 0);
        tableConfMatrix.setValueAt(trueLULUS, 0, 1);
        tableConfMatrix.setValueAt(falseTIDAK, 0, 2);
        tableConfMatrix.setValueAt(falseLULUS, 1, 1);
        tableConfMatrix.setValueAt(trueTIDAK, 1, 2);

        /*Set Nilai Recall, Precision, dan Accuracy ke Tabel Confusion Matrix*/
        if (Double.isNaN(preLULUS)) {
            tableConfMatrix.setValueAt("NaN" + " %", 2, 1);
        } else {
            tableConfMatrix.setValueAt(df.format(preLULUS * 100) + " %", 2, 1);
        }
        if (Double.isNaN(preTIDAK)) {
            tableConfMatrix.setValueAt("NaN" + " %", 2, 2);
        } else {
            tableConfMatrix.setValueAt(df.format(preTIDAK * 100) + " %", 2, 2);
        }
        if (Double.isNaN(recLULUS)) {
            tableConfMatrix.setValueAt("NaN" + " %", 0, 3);
        } else {
            tableConfMatrix.setValueAt(df.format(recLULUS * 100) + " %", 0, 3);
        }
        if (Double.isNaN(recTIDAK)) {
            tableConfMatrix.setValueAt("NaN" + " %", 1, 3);
        } else {
            tableConfMatrix.setValueAt(df.format(recTIDAK * 100) + " %", 1, 3);
        }
        if (Double.isNaN(accuracy)) {
            totalAccuracy.setText("Overall Accuracy is " + "NaN" + " %");
        } else {
            totalAccuracy.setText("Overall Accuracy is " + df.format(accuracy * 100) + " %");
        }

        tableModelRes.setRowCount(0);

        return tempEval;
    }

    /*Method untuk mendapatkan nilai hasil evaluasi. Recall, Precision, dan Accuracy Single Testing*/
    private double[][] evaluationModel(JTable tableResult, JTable tableConfMatrix, JLabel totalAccuracy, JTable tableTahunTesting, JTable tableDataSetTesting, String[] knnValue, int nilaiK, JPanel panelChart) throws SQLException {
        int actLULUS = 0;
        int actTIDAK = 0;
        int trueLULUS = 0;
        int falseLULUS = 0;
        int trueTIDAK = 0;
        int falseTIDAK = 0;
        int classLULUS = 0;
        int classTIDAK = 0;

        double recLULUS;
        double recTIDAK;
        double preLULUS;
        double preTIDAK;
        double accuracy;

        DefaultTableModel tableModelConf = (DefaultTableModel) tableConfMatrix.getModel();
        DefaultTableModel tableModelRes = (DefaultTableModel) tableResult.getModel();
        String[] tempHasilUN = getHasilTest(tableTahunTesting, tableDataSetTesting);

        if (tableModelRes.getRowCount() != 0) {
            tableModelRes.setRowCount(0);
        }

        for (int j = 0; j < tableDataSetTesting.getRowCount(); j++) {
            String nis = tableDataSetTesting.getValueAt(j, 0).toString();
            String HasilUN = tempHasilUN[j];
            String classified = knnValue[j];
            Object[] tableContent = {nis, HasilUN, classified};
            tableModelRes.addRow(tableContent);
            tableResult.setModel(tableModelRes);
        }

        /*Hitung Jumlah Data Actual LULUS dan TIDAK*/
        for (int j = 0; j < tempHasilUN.length; j++) {
            if (tempHasilUN[j].equals("LULUS")) {
                actLULUS = actLULUS + 1;
            } else if (tempHasilUN[j].equals("TIDAK")) {
                actTIDAK = actTIDAK + 1;
            }
        }

        /*Hitung Jumlah Data Classified LULUS dan TIDAK*/
        for (int j = 0; j < knnValue.length; j++) {
            if (tableResult.getValueAt(j, 1).equals("LULUS")) {
                if (tableResult.getValueAt(j, 1).equals(tableResult.getValueAt(j, 2))) {
                    trueLULUS = trueLULUS + 1;
                } else {
                    falseTIDAK = falseTIDAK + 1;
                }
            } else if (tableResult.getValueAt(j, 1).equals("TIDAK")) {
                if (tableResult.getValueAt(j, 1).equals(tableResult.getValueAt(j, 2))) {
                    trueTIDAK = trueTIDAK + 1;
                } else {
                    falseLULUS = falseLULUS + 1;
                }
            }
        }
//        System.out.println("trueLULUS =" + trueLULUS);
//        System.out.println("falseLULUS =" + falseLULUS);
//        System.out.println("falseTIDAK =" + falseTIDAK);
//        System.out.println("trueTIDAK =" + trueTIDAK);

        /*Hitung Nilai Recall, Precision, dan Accuracy*/
        preLULUS = (double) trueLULUS / (trueLULUS + falseLULUS);
        preTIDAK = (double) trueTIDAK / (trueTIDAK + falseTIDAK);
        recLULUS = (double) trueLULUS / (trueLULUS + falseTIDAK);
        recTIDAK = (double) trueTIDAK / (trueTIDAK + falseLULUS);
        accuracy = (double) (trueLULUS + trueTIDAK) / (trueLULUS + trueTIDAK + falseLULUS + falseTIDAK);

        /*Tampung Nilai Recall, Precision, dan Accuracy*/
        double[][] tempEval = new double[3][1];
        tempEval[0][0] = accuracy;
        tempEval[1][0] = recLULUS;
        tempEval[2][0] = preLULUS;


        /*Set Nilai TF, TN, FP, FN ke Tabel Confusion Matrix*/
        tableModelConf.setValueAt("Actual LULUS", 0, 0);
        tableModelConf.setValueAt("Actual TIDAK", 1, 0);
        tableModelConf.setValueAt("Class Precision", 2, 0);
        tableModelConf.setValueAt(trueLULUS, 0, 1);
        tableModelConf.setValueAt(falseTIDAK, 0, 2);
        tableModelConf.setValueAt(falseLULUS, 1, 1);
        tableModelConf.setValueAt(trueTIDAK, 1, 2);

        /*Set Nilai Recall, Precision, dan Accuracy ke Tabel Confusion Matrix*/
        if (Double.isNaN(preLULUS)) {
            tableModelConf.setValueAt("NaN" + " %", 2, 1);
        } else {
            tableModelConf.setValueAt(df.format(preLULUS * 100) + " %", 2, 1);
        }
        if (Double.isNaN(preTIDAK)) {
            tableModelConf.setValueAt("NaN" + " %", 2, 2);
        } else {
            tableModelConf.setValueAt(df.format(preTIDAK * 100) + " %", 2, 2);
        }
        if (Double.isNaN(recLULUS)) {
            tableModelConf.setValueAt("NaN" + " %", 0, 3);
        } else {
            tableModelConf.setValueAt(df.format(recLULUS * 100) + " %", 0, 3);
        }
        if (Double.isNaN(recTIDAK)) {
            tableModelConf.setValueAt("NaN" + " %", 1, 3);
        } else {
            tableModelConf.setValueAt(df.format(recTIDAK * 100) + " %", 1, 3);
        }
        if (Double.isNaN(accuracy)) {
            totalAccuracy.setText("Overall Accuracy is " + "NaN" + " %");
        } else {
            totalAccuracy.setText("Overall Accuracy is " + df.format(accuracy * 100) + " %");
        }
//        System.out.println("Recall LULUS = " + recLULUS);
//        System.out.println("Recall LULUS =" + recTIDAK);
//        System.out.println("Precision LULUS  = " + preLULUS);
//        System.out.println("Precision TIDAK  = " + preTIDAK);
//        System.out.println("Overall Accuracy  = " + accuracy);
        return tempEval;
    }

    private void showChart(int[] tempK, double[][] evalValue, JPanel panelChart, JPanel panelChart1, JPanel panelChart2) {
        final XYSeries accuracy = new XYSeries("Accuracy");
        final XYSeries recall = new XYSeries("Recall");
        final XYSeries precision = new XYSeries("Precision");
        final XYSeriesCollection accColect = new XYSeriesCollection();

        System.out.println("tempk panjangnya " + tempK.length);
        for (int i = 0; i < tempK.length; i++) {
            accuracy.add(tempK[i], evalValue[0][i]);
            recall.add(tempK[i], evalValue[1][i]);
            precision.add(tempK[i], evalValue[2][i]);
            System.out.println("Akurasi K ke-" + tempK[i] + "= " + evalValue[0][i]);
        }
        accColect.addSeries(accuracy);
        accColect.addSeries(recall);
        accColect.addSeries(precision);
        JFreeChart xyLineChart = ChartFactory.createXYLineChart("Grafik Hasil Pengujian Multi Testing", "Number of Nearest Neighbor", "Persentase", accColect, PlotOrientation.VERTICAL, true, true, false);
        final XYPlot xyPlot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer xyRender = new XYLineAndShapeRenderer();
        xyRender.setSeriesPaint(0, Color.RED);
        xyRender.setSeriesPaint(1, Color.GREEN);
        xyRender.setSeriesPaint(2, Color.BLUE);
        xyRender.setSeriesStroke(0, new BasicStroke(4.0f));
        xyRender.setSeriesStroke(1, new BasicStroke(3.0f));
        xyRender.setSeriesStroke(2, new BasicStroke(2.0f));
        xyPlot.setRenderer(xyRender);
        ChartPanel cp = new ChartPanel(xyLineChart);
        panelChart.removeAll();
        panelChart.add(cp);
        panelChart.validate();

    }

    private void showChart(int nilaiK, double[][] evalValue, JPanel panelChart, JPanel panelChart1, JPanel panelChart2) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(evalValue[0][0], "Kriteria", "Accuracy");
        dataset.addValue(evalValue[1][0], "Kriteria", "Recall");
        dataset.addValue(evalValue[2][0], "Kriteria", "Precision");

        JFreeChart lineChart = ChartFactory.createLineChart("Grafik Hasil Pengujian Single Test", "Kriteria", "Persentase", dataset, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot linePlot = lineChart.getCategoryPlot();
        LineAndShapeRenderer lineRender = new LineAndShapeRenderer();
        lineRender.setBaseShapesVisible(true);
        lineRender.setDrawOutlines(true);
        lineRender.setUseFillPaint(true);
        lineRender.setBaseFillPaint(Color.white);
        lineRender.setSeriesStroke(0, new BasicStroke(1.0f));
        lineRender.setSeriesOutlineStroke(0, new BasicStroke(5.0f));
        lineRender.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        linePlot.setRenderer(lineRender);

        ChartPanel cp = new ChartPanel(lineChart);
        panelChart.removeAll();
        panelChart.add(cp);
        panelChart.validate();

    }

    public void validasiNumberofNearest(KeyEvent evt, JTextField txtNumberOfK, JLabel labelPesanError, JTable tableDataSetModel) {
        String numberValidate = txtNumberOfK.getText();
        int modelRow = tableDataSetModel.getRowCount();
        if (Pattern.matches("[0-9]+", numberValidate) == false && numberValidate.length() > 0) {
            evt.consume();
            labelPesanError.setText("Number of Nearest Neighbor tidak valid");
        } else if (numberValidate.length() == 9) {
            evt.consume();
            labelPesanError.setText("Number of Nearest Neighbor terlalu panjang");
        } else {
            labelPesanError.setText("");
        }
    }
}

