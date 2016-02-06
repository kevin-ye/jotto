package jotto;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements ICustomView {
	
	private ArrayList<JLabel> chars;
	private Boolean charlist[];
	
	public WordPanel() {
		super();
		this.setLayout(new GridLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		refresh();
	}
	
	public void refresh() {
		chars = new ArrayList<JLabel>();
		charlist = new Boolean[26];
		for (int i = 0; i < 26; i++) {
			JLabel current_char = new JLabel();
			current_char.setText(" " + String.valueOf((char)('A' + i)));
			current_char.addMouseListener(new MouseListener() {
				
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
					if (e.getButton() == 1) {
						// left click
						JLabel current = (JLabel)e.getSource();
						char c = current.getText().charAt(1);
						if (!MainWindow.getInstance().getGameModel().is_important(c)) {
							highlight_green(current.getText().charAt(1));
							MainWindow.getInstance().getGameModel().set_important(c);
						} else {
							Boolean temp = charlist[Character.toUpperCase(c) - 'A'];
							MainWindow.getInstance().getGameModel().set_clear(c);
							charlist[Character.toUpperCase(c) - 'A'] = temp;
							highlight_clear(c);
						}
					} else if (e.getButton() == 3) {
						// right click
						JLabel current = (JLabel)e.getSource();
						char c = current.getText().charAt(1);
						if (!MainWindow.getInstance().getGameModel().is_ignore(c)) {
							highlight_gray(current.getText().charAt(1));
							MainWindow.getInstance().getGameModel().set_ignore(c);
						} else {
							Boolean temp = charlist[Character.toUpperCase(c) - 'A'];
							MainWindow.getInstance().getGameModel().set_clear(c);
							charlist[Character.toUpperCase(c) - 'A'] = temp;
							highlight_clear(c);
						}
					}
				}
			});
			chars.add(current_char);
			charlist[i] = false;
			this.add(current_char);
		}
	}
	
	public void highlight_gray(char c) {
		int i = (int)(Character.toUpperCase(c) - 'A');
		JLabel modify = chars.get(i);
		//charlist[i] = true;
		modify.setBackground(Color.GRAY);
		modify.setOpaque(true);
		modify.repaint();
	}
	
	public void highlight_clear(char c) {
		int i = (int)(Character.toUpperCase(c) - 'A');
		JLabel modify = chars.get(i);
		if (!charlist[i]) {
			modify.setOpaque(false);
		} else {
			modify.setBackground(Color.YELLOW);
			modify.setOpaque(true);
		}
		modify.repaint();
	}
	
	public void highlight_green(char c) {
		int i = (int)(Character.toUpperCase(c) - 'A');
		JLabel modify = chars.get(i);
		//charlist[i] = true;
		modify.setBackground(Color.green);
		modify.setOpaque(true);
		modify.repaint();
	}
	
	public void highlight_yellow(char c) {
		int i = (int)(Character.toUpperCase(c) - 'A');
		JLabel modify = chars.get(i);
		charlist[i] = true;
		modify.setBackground(Color.YELLOW);
		modify.setOpaque(true);
		modify.repaint();
	}
	
	public void clear() {
		for (int i = 0; i < 26; i++) {
			charlist[i] = false;
			highlight_clear((char)('A' + i));
		}
	}
	
	public void updateView(String inputString) {
		if (inputString == null) {
			return;
		}
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);
			highlight_yellow(c);
		}
		repaint();
	}
}
