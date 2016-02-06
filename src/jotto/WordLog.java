package jotto;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;



public class WordLog extends JPanel implements ICustomView {
	private JTable log_table;
	private WordList used_list = new WordList();
	private JScrollPane scrollPane;
	
	public WordLog() {
		super(new BorderLayout());
		log_table = new JTable();
		log_table.setModel(new CustomTableModel());
		scrollPane = new JScrollPane(log_table);
		log_table.setFillsViewportHeight(true);
		this.add(scrollPane, BorderLayout.CENTER);
		//String[] col= {"Guess#", "Words", "Exact", "Partial"};
		CustomTableModel model = (CustomTableModel) log_table.getModel();
		TableColumn nc = new TableColumn(model.getColumnCount());
		nc.setHeaderValue("Guess#");
		log_table.addColumn(nc);
		model.addColumn("Guess#");
		nc = new TableColumn(model.getColumnCount());
		nc.setHeaderValue("Words");
		log_table.addColumn(nc);
		model.addColumn("Words");
		nc = new TableColumn(model.getColumnCount());
		nc.setHeaderValue("Exact");
		log_table.addColumn(nc);
		model.addColumn("Exact");
		nc = new TableColumn(model.getColumnCount());
		nc.setHeaderValue("Partial");
		log_table.addColumn(nc);
		model.addColumn("Partial");
		log_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		log_table.getColumn("Guess#").setMaxWidth(100);
		log_table.getColumn("Exact").setMaxWidth(100);
		log_table.getColumn("Partial").setMaxWidth(100);
		this.setBorder(BorderFactory.createTitledBorder("Log"));
	}
	
	public void updateView(String w) {
		if (w != null) {
			MainWindow _main = MainWindow.getInstance();
			CustomTableModel model = (CustomTableModel) log_table.getModel();
			model.addRow(new Object[]{model.getRowCount() + 1, w, getExcat(w), getPartial(w)});
			used_list.add(new Word(w, 0));
			String currentWord = _main.getCurrentWord();
			if (currentWord.equals(w)) {
				_main.gameEnd(true);
			}
		}
		scrollPane.revalidate();
		log_table.revalidate();
		log_table.repaint();
		repaint();
	}
	
	public Boolean validWord(String w) {
		Boolean flag = true;
		flag = (used_list.contains(w));
		return !flag;
	}
	
	@Override
	public void clear() {
		used_list = new WordList();
		CustomTableModel model = (CustomTableModel) log_table.getModel();
		model.clearData();
		scrollPane.revalidate();
		repaint();
	}
	
	public int getExcat(String w) {
		int count = 0;
		String currentWord = MainWindow.getInstance().getCurrentWord();
		for (int i = 0; i < Math.min(currentWord.length(), w.length()); i++) {
			if (currentWord.charAt(i) == w.charAt(i)) {
				count++;
			}
		}
		
		return count;
	}
	
	public int getPartial(String w) {
		int count = 0;
		Boolean checklist_current[] = new Boolean[26];
		Boolean checklist_checking[] = new Boolean[26];
		for (int i = 0; i < 26; i++) {
			checklist_checking[i] = false;
			checklist_current[i] = false;
		}
		String currentWord = MainWindow.getInstance().getCurrentWord();
		for (int i = 0; i < currentWord.length(); i++) {
			checklist_current[Character.toUpperCase(currentWord.charAt(i)) - 'A'] = true;
		}
		for (int i = 0; i < w.length(); i++) {
			if (checklist_current[Character.toUpperCase(w.charAt(i)) - 'A']) {
				count++;
			}
		}
		
		return count;
	}

}
