/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ModelDialogDataset;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RIDWAN
 */
public class ControllerDialogDataset {

    public void showListDataset(JTable tableViewDataset) {

        DefaultTableModel tableModel = (DefaultTableModel) tableViewDataset.getModel();
        ModelDialogDataset modelDialogDataset = new ModelDialogDataset();
        modelDialogDataset.selectDataset();
        for (int i = 0; i < modelDialogDataset.getDataSet().size(); i++) {
            tableModel.addRow(modelDialogDataset.getDataSet().get(i));
        }
        tableViewDataset.setModel(tableModel);

    }

    public int countDataset(JTable tableViewDataset) {
        int rowCount = tableViewDataset.getRowCount();
        int dataCount = 0;
        for (int i = 0; i < rowCount; i++) {
            dataCount = dataCount + Integer.parseInt(tableViewDataset.getValueAt(i, 1).toString());

        }
        return dataCount;

    }
}
