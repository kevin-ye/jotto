package jotto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel implements ICustomView{
	private wordTable choosingTable;
	private JPanel button_section;
	private JButton select_button;
	private JTextField manulField;
	private WordList used_list = new WordList();
	
	private String input_w;
	
	public InputPanel() {
		super(new BorderLayout());
		choosingTable = new wordTable();
		choosingTable.addMouseListenerTotable(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				//
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				//
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				//
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (choosingTable.getSelected() != null) {
					input_w = null;
					input_w = choosingTable.getSelected().getWord();
					manulField.setText(input_w);
				}
				if ((e.getClickCount() == 2) && (e.getButton() == 1)) {
					// double click
					e.consume();
					e.consume();
					select_button.getActionListeners()[0].actionPerformed(null);
				}
			}
		});
		choosingTable.removeColumn(1);
		this.add(choosingTable, BorderLayout.CENTER);
		
		button_section = new JPanel();
		button_section.setLayout(new BorderLayout());
		button_section.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		manulField = new JTextField();
		button_section.add(manulField, BorderLayout.CENTER);
		
		select_button = new JButton("Guess!");
		select_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				input_w = manulField.getText();
				if (!MainWindow.getInstance().getWordList().contains(input_w)) {
					JOptionPane.showMessageDialog(MainWindow.getInstance(), "Invalid word!\nWord is not in the dictionary.");
				} else if (used_list.contains(input_w)) {
					JOptionPane.showMessageDialog(MainWindow.getInstance(), "Invalid word!\nWord has already been used.");
				} else {
					MainWindow.getInstance().getGameModel().set_player_input(input_w);
					manulField.setText("");
				}
			}
		});
		button_section.add(select_button, BorderLayout.EAST);
		this.add(button_section, BorderLayout.SOUTH);
		this.setBorder(BorderFactory.createTitledBorder("Word List"));
	}
	
	public String getInput() {
		return input_w;
	}

	@Override
	public void updateView(String info) {
		if (info != null) {
			used_list.add(new Word(info, 0));
		}
		choosingTable.clear();
		choosingTable.load(new IWordPredicate() {
			
			@Override
			public boolean isOK(Word w) {
				Boolean flag = true;
				if (used_list.contains(w.getWord())) {
					flag = false;
				}
				for (int i = 0; (i < w.getWord().length() && flag); i++) {
					if (MainWindow.getInstance().getGameModel().is_ignore(w.getWord().charAt(i))) {
						flag = false;
					}
				}
				return flag;
			}
		});
		repaint();
	}
	
	@Override
	public void clear() {
		choosingTable.clear();
		used_list = new WordList();
	}

}
