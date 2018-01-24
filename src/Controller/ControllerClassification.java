/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Connection.DbConnection;
import Model.ModelClassification;
import Form.FormClassification;
import Form.FormProgressbar;
import com.toedter.calendar.JYearChooser;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author RIDWAN
 */
public class ControllerClassification {
    
    public void chooseFile(ActionEvent evt, JTextField txtFileDirectory, JTextField txtNumberOfK, JLabel labelJumlahData, JButton buttonProses, JTable tablePreview) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Excel File", "xls", "xlsx");
            fileChooser.setFileFilter(fileNameExtensionFilter);

            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                txtFileDirectory.setText(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println("Good, File Chooser runing well!");
                if (txtFileDirectory.getText().endsWith(".xls") || txtFileDirectory.getText().endsWith(".xlsx")) {
                    showOnTable(evt, txtFileDirectory, tablePreview);
                    labelJumlahData.setText(tablePreview.getRowCount() + " Data");
                    txtNumberOfK.setEnabled(true);
                    txtNumberOfK.requestFocus();
                    buttonProses.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "File dataset harus file spreadsheet dengan ekstensi *xls atau *.xlsx!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/fail.png"));
                    txtFileDirectory.setText("");
                    chooseFile(evt, txtFileDirectory, txtNumberOfK, labelJumlahData, buttonProses, tablePreview);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOnTable(ActionEvent evt, JTextField txtFileDirectory, JTable tablePreview) {
        try {
            if (txtFileDirectory.getText().endsWith(".xls")) {
                System.out.println("This File .XLS");
                showXLS(txtFileDirectory, tablePreview);
            } else if (txtFileDirectory.getText().endsWith(".xlsx")) {
                System.out.println("This File .XLSX");
                showXLSX(txtFileDirectory, tablePreview);
            } else {
                System.out.println("You must be choosing one of Excel file.");
            }
        } catch (Exception e) {
        }
    }

    private void showXLS(JTextField txtFileDirectory, JTable tablePreview) throws FileNotFoundException, IOException {
        DefaultTableModel tableModel = new DefaultTableModel();
        File fileName = new File(txtFileDirectory.getText());
        FileInputStream inputStream = new FileInputStream(fileName);
        HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workBook.getSheetAt(0);

        int rowValue = sheet.getLastRowNum() + 1;
        int colValue = sheet.getRow(0).getLastCellNum();
        String[][] data = new String[rowValue][colValue];
        String[] colName = new String[colValue];
        for (int i = 0; i < rowValue; i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < colValue; j++) {
                HSSFCell cell = row.getCell(j);
                int type = cell.getCellType();
                Object returnCellValue = null;
                if (type == 0) {
                    returnCellValue = cell.getNumericCellValue();
                } else if (type == 1) {
                    returnCellValue = cell.getStringCellValue();
                }

                data[i][j] = returnCellValue.toString();
            }
        }

        for (int i = 0; i < colValue; i++) {
            colName[i] = data[0][i];
        }

        tableModel = new DefaultTableModel(data, colName);
        tablePreview.setModel(tableModel);
        tableModel.removeRow(0);
    }

    private void showXLSX(JTextField txtFileDirectory, JTable tablePreview) throws FileNotFoundException, IOException {
        DefaultTableModel tableModel = new DefaultTableModel();
        File fileName = new File(txtFileDirectory.getText());
        FileInputStream inputStream = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rowValue = sheet.getLastRowNum() + 1;
        int colValue = sheet.getRow(0).getLastCellNum();
        String[][] data = new String[rowValue][colValue];
        String[] colName = new String[colValue];
        for (int i = 0; i < rowValue; i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < colValue; j++) {
                XSSFCell cell = row.getCell(j);
                int type = cell.getCellType();
                Object returnCellValue = null;
                if (type == 0) {
                    returnCellValue = cell.getNumericCellValue();
                } else if (type == 1) {
                    returnCellValue = cell.getStringCellValue();
                }

                data[i][j] = returnCellValue.toString();
            }
        }

        for (int i = 0; i < colValue; i++) {
            colName[i] = data[0][i];
        }

        tableModel = new DefaultTableModel(data, colName);
        tablePreview.setModel(tableModel);
        tableModel.removeRow(0);
    }

    public String[] processMining(JTextField textNumberOfK, JTable tablePreview, JLabel labelPesanError, JTable tableResult, JLabel labelSiswaLULUS, JLabel labelSiswaTIDAK, JLabel labelKeterangan, JYearChooser jYearChooser1, JYearChooser jYearChooser2, JTabbedPane jTabbedPane1) {

        String numberValidate = textNumberOfK.getText();
        ModelClassification modelClassification = new ModelClassification();
        int rowCountModel = modelClassification.getRowCount();
        int rowCountData = tablePreview.getRowCount();
        System.out.println("Row Count Data : " + rowCountData);
        System.out.println("Row Count Model : " + rowCountModel);
        String[] knnValue = null;

        /*Validasi Nilai Number of Nearest Neighbor*/
        if (Pattern.matches("[0-9]+", numberValidate) == false && numberValidate.length() > 0) {
            labelPesanError.setText("Number of Nearest Neighbor tidak valid");
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak valid!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/fail.png"));
            textNumberOfK.requestFocus();
        } else if (numberValidate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh kosong!", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/fail.png"));
            labelPesanError.setText("Number of Nearest Neighbor tidak boleh kosong");
            textNumberOfK.requestFocus();
        } else if (Integer.parseInt(numberValidate) >= rowCountModel) {
            labelPesanError.setText("Number of Nearest Neighbor tidak boleh lebih dari " + rowCountModel + "");
            JOptionPane.showMessageDialog(null, "Number of Nearest Neighbor tidak boleh lebih dari " + rowCountModel + " !", "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/fail.png"));
            textNumberOfK.requestFocus();
        } else {
            int confirm = 0;
            confirm = JOptionPane.showOptionDialog(null, "Yakin ingin memproses data?", "Proses Klasifikasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.OK_OPTION) {
                int kValue = Integer.parseInt(textNumberOfK.getText());
                String[][] modelValue = getModelValue(rowCountModel);
                double[][] dataValue = getDataValue(rowCountData, tablePreview);
                knnValue = getKNNValue(rowCountData, rowCountModel, modelValue, dataValue, kValue);
                showClassificationResult(tableResult, tablePreview, knnValue, rowCountData, labelSiswaLULUS, labelSiswaTIDAK, labelKeterangan, jYearChooser1, jYearChooser2, kValue);
                jTabbedPane1.setSelectedIndex(1);
            }

        }
        return knnValue;
    }

    private String[][] getModelValue(int rowCountModel) {
        ModelClassification modelClassification = new ModelClassification();
        modelClassification.getData(rowCountModel);
        String[][] returnValue = new String[rowCountModel][4];
        String Mtk1;
        String Bindo1;
        String Bing1;
        String Ipa1;
        String Mtk2;
        String Bindo2;
        String Bing2;
        String Ipa2;
        String HasilTO;
        double transHasilTO = 0;
        String HasilUN;
        double tempMaxUNIPA = 0;
        double meanTO1;
        double meanTO2;
        try {
            DbConnection dbConnection = new DbConnection();
            Connection connect = dbConnection.connect();
            Statement stm = connect.createStatement();
            String query = "SELECT * FROM siswa";
            ResultSet rs = stm.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                Mtk1 = rs.getString("mtk1");
                Bindo1 = rs.getString("bindo1");
                Bing1 = rs.getString("bing1");
                Ipa1 = rs.getString("ipa1");
                Mtk2 = rs.getString("mtk2");
                Bindo2 = rs.getString("bindo2");
                Bing2 = rs.getString("bing2");
                Ipa2 = rs.getString("ipa2");
                HasilTO = rs.getString("hasilto");
                HasilUN = rs.getString("hasilun");
                if (rs.getString("hasilto").equals("LULUS")) {
                    transHasilTO = 1;
                } else if (rs.getString("hasilto").equals("TIDAK")) {
                    transHasilTO = 0;
                }
                returnValue[i][0] = String.valueOf((Double.parseDouble(Mtk1) + Double.parseDouble(Bindo1) + Double.parseDouble(Bing1) + Double.parseDouble(Ipa1)) / 4);
                returnValue[i][1] = String.valueOf((Double.parseDouble(Mtk2) + Double.parseDouble(Bindo2) + Double.parseDouble(Bing2) + Double.parseDouble(Ipa2)) / 4);
                returnValue[i][2] = String.valueOf(transHasilTO);
                returnValue[i][3] = HasilUN;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private double[][] getDataValue(int rowCountData, JTable tablePreview) {
        double[][] returnValue = new double[rowCountData][3];
        int transHasilTO = 0;
        for (int i = 0; i < rowCountData; i++) {
            if (tablePreview.getValueAt(i, 11).equals("LULUS")) {
                transHasilTO = 1;
            } else if (tablePreview.getValueAt(i, 11).equals("TIDAK")) {
                transHasilTO = 0;
            }
            returnValue[i][0] = (Double.parseDouble(tablePreview.getValueAt(i, 3).toString()) + Double.parseDouble(tablePreview.getValueAt(i, 4).toString()) + Double.parseDouble(tablePreview.getValueAt(i, 5).toString()) + Double.parseDouble(tablePreview.getValueAt(i, 6).toString())) / 4;
            returnValue[i][1] = (Double.parseDouble(tablePreview.getValueAt(i, 7).toString()) + Double.parseDouble(tablePreview.getValueAt(i, 8).toString()) + Double.parseDouble(tablePreview.getValueAt(i, 9).toString()) + Double.parseDouble(tablePreview.getValueAt(i, 10).toString())) / 4;
            returnValue[i][2] = transHasilTO;
        }
        return returnValue;
    }

    private String[] getKNNValue(int rowCountData, int rowCountModel, String[][] modelValue, double[][] dataValue, int kValue) {
        DecimalFormat df = new DecimalFormat("00.0000000000");
        double[][] squaredRoot = new double[rowCountData][rowCountModel];
        String[][] initClass = new String[rowCountData][rowCountModel];
        double[] sortSquared = new double[rowCountModel];
        String[] sortHasilUN = new String[rowCountModel];
        String[] nearestNeighbor = new String[kValue];
        String[] majorClass = new String[rowCountData];
        int onRow = 0;
        for (int i = 0; i < rowCountData; i++) {
            System.out.println("=======================================================================");
            System.out.println("Data Uji ke-\tData Model ke-\tEuclidean Distance\t  Class Target");
            System.out.println("=======================================================================");
            for (int j = 0; j < rowCountModel; j++) {
                double meanTO1 = Math.pow(Math.abs(Double.parseDouble(modelValue[j][0]) - dataValue[i][0]), 2);
                double meanTO2 = Math.pow(Math.abs(Double.parseDouble(modelValue[j][1]) - dataValue[i][1]), 2);
                double HasilTO = Math.pow(Math.abs(Double.parseDouble(modelValue[j][2]) - dataValue[i][2]), 2);
                double sqrt = Math.sqrt(meanTO1 + meanTO2 + HasilTO);
                squaredRoot[i][j] = sqrt;
                initClass[i][j] = modelValue[j][3];

                System.out.print(i + "\t\t" + j + "\t\t" + df.format(squaredRoot[i][j]) + "\t\t\t" + initClass[i][j]);
                System.out.println();
//                System.out.println("akar MeanUN ke-" + i + " '" + meanUN + "'");
//                System.out.println("akar MeanUN ke-" + i + " '" + meanPT + "'");
//                System.out.println("akar MeanUN ke-" + i + " '" + minat + "'");
            }
            System.out.println("=======================================================================");
            System.out.println("Proses Selection Sorting...");
            selectionSorting(onRow, rowCountModel, initClass, squaredRoot);
            System.out.println("=======================================================================");
            System.out.println("Euclidean Distance\tClass Target");
            System.out.println("=======================================================================");

            for (int k = 0; k < rowCountModel; k++) {
                sortSquared[k] = squaredRoot[onRow][k];
                sortHasilUN[k] = initClass[onRow][k];
                System.out.print(df.format(sortSquared[k]) + "\t\t   ");
                System.out.println(sortHasilUN[k]);
            }
            System.out.println("=======================================================================");
            nearestNeighbor = getNearestNeighbor(kValue, sortHasilUN);
            majorClass[i] = getMajorClass(nearestNeighbor);
//            System.out.println("Data ke-'" + i + "' kelasnya, " + majorClass[i]);
            onRow++;
        }

        return majorClass;
    }

    private void selectionSorting(int onRow, int rowCountModel, String[][] initClass, double[][] squaredRoot) {
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

    private String[] getNearestNeighbor(int kValue, String[] sortHasilUN) {
        String[] returnvalue = new String[kValue];
        int i = 0;
        System.out.println("Get " + kValue + " Nearest Neighbor :");
        while (i < kValue) {
            returnvalue[i] = sortHasilUN[i];
            System.out.println(returnvalue[i]);
            i++;
        }
        return returnvalue;
    }

    private String getMajorClass(String[] nearestNeighbor) {
        String returnValue;
        int countLULUS = 0;
        int countTIDAK = 0;
        for (int i = 0; i < nearestNeighbor.length; i++) {
            if (nearestNeighbor[i].equals("LULUS")) {
                countLULUS = countLULUS + 1;
            } else if (nearestNeighbor[i].equals("TIDAK")) {
                countTIDAK = countLULUS + 1;
            }
        }
        if (countLULUS >= countTIDAK) {
            returnValue = "LULUS";
        } else {
            returnValue = "TIDAK";
        }
        System.out.println("Jumlah Class Target LULUS = " + countLULUS);
        System.out.println("Jumlah Class Target TIDAK = " + countTIDAK);
        System.out.println("Kelulusan Final : " + returnValue + "\n");
        return returnValue;
    }

    public void showClassificationResult(JTable tableResult, JTable tablePreview, String[] knnValue, int rowCountData, JLabel labelSiswaLULUS, JLabel labelSiswaTIDAK, JLabel labelKeterangan, JYearChooser jYearChooser1, JYearChooser jYearChooser2, int kValue) {
        DefaultTableModel tableModelResult = new DefaultTableModel();
        tableModelResult = (DefaultTableModel) tableResult.getModel();
        int jumlahSiswaLULUS = 0;
        int jumlahSiswaTIDAK = 0;

        for (int i = 0; i < rowCountData; i++) {
            if (knnValue[i].equals("LULUS")) {
                jumlahSiswaLULUS = jumlahSiswaLULUS + 1;
            } else if (knnValue[i].equalsIgnoreCase("TIDAK")) {
                jumlahSiswaTIDAK = jumlahSiswaTIDAK + 1;
            }

            String nis = tablePreview.getValueAt(i, 0).toString();
            String nama = tablePreview.getValueAt(i, 1).toString();
            String jenKel = tablePreview.getValueAt(i, 2).toString();
            String Mtk1 = tablePreview.getValueAt(i, 3).toString();
            String Bindo1 = tablePreview.getValueAt(i, 4).toString();
            String Bing1 = tablePreview.getValueAt(i, 5).toString();
            String Ipa1 = tablePreview.getValueAt(i, 6).toString();
            String Mtk2 = tablePreview.getValueAt(i, 7).toString();
            String Bindo2 = tablePreview.getValueAt(i, 8).toString();
            String Bing2 = tablePreview.getValueAt(i, 9).toString();
            String Ipa2 = tablePreview.getValueAt(i, 10).toString();
            String HasilTO = tablePreview.getValueAt(i, 11).toString();
            String HasilUN = knnValue[i];
            Object[] resultData = {nis, nama, jenKel, Mtk1, Bindo1, Bing1, Ipa1, Mtk2, Bindo2, Bing2, Ipa2, HasilTO, HasilUN};
            tableModelResult.addRow(resultData);
        }
        tableResult.setModel(tableModelResult);
        labelSiswaLULUS.setText(jumlahSiswaLULUS + "");
        labelSiswaTIDAK.setText(jumlahSiswaTIDAK + "");
        labelKeterangan.setText("Hasil Klasifikasi Kelulusan UN Siswa Pada Tahun Ajaran " + jYearChooser1.getYear() + "/" + jYearChooser2.getYear() + ", dengan paramater K = " + kValue + " adalah sebagai berikut ");
    }

    public void saveResultFile(JYearChooser thnAjaran1, JYearChooser thnAjaran2, JTable tableResult) throws IOException {
        JFileChooser dirChooser = new JFileChooser();
        dirChooser.setDialogTitle("Save as Excel File");
        String thnAjaran = Integer.toString(thnAjaran1.getYear()) + "-" + Integer.toString(thnAjaran2.getYear());
        String generateFileName = "Data Hasil Kelulusan UN Siswa Tahun Ajaran " + thnAjaran + ".xlsx";
        dirChooser.setSelectedFile(new File(generateFileName));
        int userSelection = dirChooser.showSaveDialog(null);
        int rowCountData = tableResult.getRowCount();
        if (userSelection == dirChooser.APPROVE_OPTION) {
            File fileToSave = dirChooser.getSelectedFile();
            convertToExcel(tableResult, fileToSave);
            JOptionPane.showMessageDialog(null, "Hasil klasifikasi kelulusan berhasil disimpan", "Penyimpanan Berhasil", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Gambar/success.png"));
        }
    }

    public void convertToExcel(JTable tableResult, File fileToSave) throws FileNotFoundException, IOException {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet();
        XSSFRow row;
        int rowCountData = tableResult.getRowCount();

        Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
        data.put(1, new Object[]{"NIS", "NAMA", "JK", "MTK1", "BINDO1", "BING1", "IPA1", "MTK2", "BINDO2", "BING2", "IPA2", "HASILTO", "HASILUN"});
        for (int i = 0; i < rowCountData; i++) {
//            System.out.println(tableResult.getValueAt(i, 1));
            data.put((i + 2), new Object[]{tableResult.getValueAt(i, 0), tableResult.getValueAt(i, 1), tableResult.getValueAt(i, 2),
                tableResult.getValueAt(i, 3), tableResult.getValueAt(i, 4), tableResult.getValueAt(i, 5),
                tableResult.getValueAt(i, 6), tableResult.getValueAt(i, 7), tableResult.getValueAt(i, 8), tableResult.getValueAt(i, 9), tableResult.getValueAt(i, 10), tableResult.getValueAt(i, 11), tableResult.getValueAt(i, 12)});
        }

        //System.out.println("KeySet " + data.keySet());
        Set<Integer> keyID = data.keySet();
        int rowID = 0;
        for (Integer key : keyID) {
            row = sheet.createRow(rowID);
            Object[] tempData = data.get(key);
            rowID++;
            int cellID = 0;
            for (Object obj : tempData) {
                Cell cell = row.createCell(cellID);
                cell.setCellValue(obj.toString());
                cellID++;
            }
        }
        FileOutputStream out = new FileOutputStream(fileToSave);
        workBook.write(out);
        out.close();
        System.out.println(fileToSave + " Berhasil disimpan");
    }

    public void validasiNumberofNearest(java.awt.event.KeyEvent evt, JTextField textNumberOfK, JLabel labelPesanError) {
        ModelClassification modelClassification = new ModelClassification();
        String numberValidate = textNumberOfK.getText();
        int modelRow = modelClassification.getRowCount();
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

    public void showChart(JLabel jumlahSiswaLULUS, JLabel jumlahSiswaTIDAK, JLabel labelKeterangan) {
        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        barChartData.setValue(Integer.parseInt(jumlahSiswaLULUS.getText()), "LULUS", "LULUS");
        barChartData.setValue(Integer.parseInt(jumlahSiswaTIDAK.getText()), "TIDAK", "TIDAK");
        JFreeChart barchart = ChartFactory.createBarChart3D("Grafik Jumlah Siswa Yang Lulus dan Tidak, Tahun Ajaran " + labelKeterangan.getText().substring(53, 62) + "", "Hasil", "Jumlah Siswa", barChartData, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plotBarChart = barchart.getCategoryPlot();
        ChartFrame chartFrame = new ChartFrame("Grafik Jumlah Siswa Yang Lulus dan Tidak", barchart, true);
        chartFrame.setVisible(true);
        chartFrame.setSize(700, 500);
        chartFrame.setLocationRelativeTo(null);
        plotBarChart.setRangeGridlinePaint(java.awt.Color.black);
        ChartPanel chartPanel = new ChartPanel(barchart);
    }
}

    

