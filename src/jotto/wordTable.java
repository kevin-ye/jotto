package jotto;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

public class wordTable extends JPanel {
	private JTable table;
	private Word selected;
	private JScrollPane scrollPane;
	
	public wordTable() {
		super(new BorderLayout());
		selected = null;
		table = new JTable();
		table.setModel(new CustomTableModel());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    scrollPane = new JScrollPane(table);
	    //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		table.setFillsViewportHeight(true);
		this.add(scrollPane, BorderLayout.CENTER);
		CustomTableModel model = (CustomTableModel) table.getModel();
		TableColumn nc = new TableColumn(model.getColumnCount());
		nc.setHeaderValue("Word");
		table.addColumn(nc);
		model.addColumn("Word");
		nc = new TableColumn(model.getColumnCount());
		nc.setHeaderValue("Difficulty");
		table.addColumn(nc);
		model.addColumn("Difficulty");
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
	
	public void load(IWordPredicate itest) {
		MainWindow _main = MainWindow.getInstance();
		String[] difficulty = {"Easy", "Normal", "Hard"};
		Word[] wlist = _main.getWordList().getWords(itest);
		for (int i = 0; i < Array.getLength(wlist); i++) {
			CustomTableModel model = (CustomTableModel) table.getModel();
			Word current = wlist[i];
			model.addRow(new Object[]{current.getWord(), difficulty[current.getDifficulty()]});
		}
		scrollPane.revalidate();
		repaint();
	}
	
	public Word getSelected() {
		if ((table.getSelectedRow() >= 0) && (table.getSelectedRow() < table.getRowCount())) {
			CustomTableModel model = (CustomTableModel) table.getModel();
			String w = (String) model.getValueAt(table.getSelectedRow(), 0);
			if (table.getColumnCount() == 1) {
				selected = new Word(w, 0);
			} else {
				String diff = (String) model.getValueAt(table.getSelectedRow(), 1);
				int d = 0;
				if (diff.equals("Easy")) {
					d = 0;
				} else if (diff.equals("Normal")) {
					d = 1;
				} else {
					d = 2;
				}
				selected = new Word(w, d);
			}
		} else {
			selected = null;
		}
		return selected;
	}
	
	public void clear() {
		CustomTableModel model = (CustomTableModel) table.getModel();
		model.clearData();
		scrollPane.revalidate();
		repaint();
	}
	
	public void removeColumn(int index) {
		table.removeColumn(table.getColumnModel().getColumn(index));
		scrollPane.revalidate();
		repaint();
	}
	
	public void addMouseListenerTotable(MouseListener lis) {
		table.addMouseListener(lis);
	}
}
