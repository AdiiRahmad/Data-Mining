/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;


import Form.NewJInternalFrame;
import Model.ModelInternalFrame;
import com.toedter.calendar.JYearChooser;

/**
 *
 * @author RIDWAN
 */
public class ControllerInternalFrame {
    
    public NewJInternalFrame internalFrame;
    public ModelInternalFrame modelInternalFrame;
    String nis, nama, nilai;

    public void importToDatabase(int rowValue, int columnValue, String[][] data, JYearChooser thnAjaran1, JYearChooser thnAjaran2) {
        internalFrame = new NewJInternalFrame();
        modelInternalFrame = new ModelInternalFrame();
        System.out.println(data[0][0]);
        String tahunAjaran = Integer.toString(thnAjaran1.getYear()) + "/" + Integer.toString(thnAjaran2.getYear());
        for (int b = 1; b < rowValue; b++) {
            int c = 0;
            while (c < columnValue) {
                nis = data[b][c];
                nama = data[b][c + 1];
                nilai = data[b][c + 2];
                break;
            }
            modelInternalFrame.setNim(nis);
            modelInternalFrame.setNama(nama);
            modelInternalFrame.setNilai(nilai);
            modelInternalFrame.setTahun(tahunAjaran);

            System.out.println(nis + ", " + nama + ", " + nilai + "," + tahunAjaran);
            modelInternalFrame.importToDatabase();
        }

    }

}
