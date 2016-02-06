package jotto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class SelectWordWindow extends JDialog {
	private Boolean hiding;
	private WordList wordList;
	private wordTable wTable;
	private MainWindow _main;
	private JPanel panel_buttom;
	private JButton ok_buttom;
	private JButton cancel_buttom;
	private JPanel content;
	private SelectWordWindow _instance;
	
	
	private Word seleceted;
	
	private void set_ui() {
		content = new JPanel();
		content.setLayout(new BorderLayout());
		
		wTable = new wordTable();
		content.add(wTable, BorderLayout.CENTER);
		
		panel_buttom = new JPanel();
		panel_buttom.setLayout(new FlowLayout(FlowLayout.CENTER));
		ok_buttom = new JButton("Ok");
		ok_buttom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				seleceted = wTable.getSelected();
				_instance.dispatchEvent(new WindowEvent(_instance, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel_buttom.add(ok_buttom);
		cancel_buttom = new JButton("Cancel");
		cancel_buttom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				seleceted = null;
				_instance.dispatchEvent(new WindowEvent(_instance, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel_buttom.add(cancel_buttom);
		content.add(panel_buttom, BorderLayout.SOUTH);
		
		this.setContentPane(content);
		this.setMinimumSize(new Dimension(100, 100));
		this.setSize(300, 300);
		this.setLocation(500, 500);
		this.set_middle();
	}
	
	public SelectWordWindow(MainWindow _m) {
		// constructor
		super();
		_instance = this;
		_main = _m;
		set_ui();
		hide_window();
	}
	
	public boolean get_hiding() {
		return hiding;
	}
	
	public void set_middle() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		this.setLocation((int)(width - this.getWidth()) / 2 , (int)(height - this.getHeight()) / 2);
		
	}
	
	public void show_window(){
		wTable.clear();
		wTable.load(new IWordPredicate() {
			
			@Override
			public boolean isOK(Word w) {
				return true;
			}
		});
		seleceted = null;
		this.setModal(true);
		this.setVisible(true);
		hiding = false;
	}
	
	public void hide_window() {
		this.setVisible(false);
		hiding = true;
	}
	
	public void setwordList(WordList list) {
		wordList = list;
	}
	
	public Word getSelected() {
		return seleceted;
	}
}
