package jotto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel{
	private ArrayList<String> columns = new ArrayList<String>();
	private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>(); 
	
	public void clearData() {
		data.clear();
	}
	
	public void addColumn(String col) {
		if (columns == null) {
			columns = new ArrayList<String>();
		}
		columns.add(col);
		//repaint();
	}
	
	public void addRow(Object[] rowData) {
		ArrayList<Object> newRow = new ArrayList<Object>();
		data.add(newRow);
		for (int i = 0; i < Array.getLength(rowData); i++) {
			if (rowData[i] != null) {
				newRow.add(rowData[i]);
			}
		}
		//repaint();
	}
	
	@Override
	public String getColumnName(int column) {
		if ((column >=0) && (column < getColumnCount())) {
			return columns.get(column);
		} else {
			return "";
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		return false;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if ((rowIndex >=0) && (rowIndex < getRowCount()) && (columnIndex >=0) && (columnIndex < getColumnCount())) {
			return data.get(rowIndex).get(columnIndex);
		} else {
			return null;
		}
	}
}
