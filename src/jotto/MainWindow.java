package jotto;

import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
	
	private boolean hiding = true;
	private JPanel contents;
	private JPanel panel_top;
	private JPanel panel_buttom;
	private JPanel NewGamePanel;
	private JPanel RunningGamePanel;
	private JComboBox diffComboBox;
	private JButton newGame;
	private JButton giveup_button;
	private JButton select_button;
	private JMenuBar mainMenuBar;
	
	private WordPanel wordpanel;
	private WordLog wordlog;
	private InputPanel inputPanel;
	
	private static MainWindow _instance = null;
	
	private GameModel model;
	private Boolean game_start;
	private WordList game_WordList;
	private Word currentWord;
	private String player_word;
	private int diff = 1;
	
	
	private void set_ui() {
		
		this.setTitle("Jotto");
		
		model = new GameModel();
		
		contents = new JPanel();
		this.setContentPane(contents);
		contents.setLayout(new BorderLayout()); 
		
		panel_top = new JPanel();
		contents.add(panel_top, BorderLayout.CENTER);
		panel_top.setLayout(new BorderLayout());
		
		wordpanel = new WordPanel();
		panel_top.add(wordpanel, BorderLayout.NORTH);
		
		inputPanel = new InputPanel();
		panel_top.add(inputPanel, BorderLayout.CENTER);
		
		wordlog = new WordLog();
		panel_top.add(wordlog, BorderLayout.WEST);
		
		panel_top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		RunningGamePanel = new JPanel();
		RunningGamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		giveup_button = new JButton("Give up!");
		giveup_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] optStrings = {"Yes, I give up!", "No, I want to try more!"};
				if (JOptionPane.showOptionDialog(_instance, "Do you really want to give up?", "Question",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optStrings, optStrings[1]) == 0) {
					gameEnd(false);
				}
			}
		});
		RunningGamePanel.add(giveup_button);
		
		NewGamePanel = new JPanel();
		NewGamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		String[] diffList = {"Easy", "Normal", "Hard", "Any Difficulty"};
		diffComboBox = new JComboBox(diffList);
		diffComboBox.setSelectedIndex(1);
		diffComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox sender = (JComboBox)e.getSource();
				diff = sender.getSelectedIndex();
				if ((diff <= 0) || (diff >= 4)) {
					diff = 1;
				}
			}
		});
		NewGamePanel.add(diffComboBox);
		newGame = new JButton("Random Word");
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startnew(true);
			}
		});
		NewGamePanel.add(newGame);
		select_button = new JButton("Choose a word");
		select_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectWordWindow swin = new SelectWordWindow(_instance);
				swin.show_window();
				Word selected = swin.getSelected();
				if (selected != null) {
					currentWord = selected;
					startnew(false);
				}
			}
		});
		NewGamePanel.add(select_button);
		
		NewGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
		RunningGamePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
		
		panel_buttom = NewGamePanel;
		contents.add(panel_buttom, BorderLayout.SOUTH);
		
		mainMenuBar = new JMenuBar();
		JMenu menu = new JMenu("Help");
		JMenuItem item1 = new JMenuItem("How To: Start a game");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instructionDialog newDialog = new instructionDialog(0);
				newDialog.showDialog();
			}
		});
		mainMenuBar.add(menu);
		JMenuItem item2 = new JMenuItem("How To: Play a game");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instructionDialog newDialog = new instructionDialog(1);
				newDialog.showDialog();
			}
		});
		menu.add(item1);
		menu.add(item2);
		
		this.setJMenuBar(mainMenuBar);
		
		model.addView(inputPanel);
		model.addView(wordlog);
		model.addView(wordpanel);
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(100, 100));
		this.setSize(700, 500);
		this.setLocation(500, 500);
		this.set_middle();
		this.reload("");
	}
	
	public void reload(String filename) {
		if (filename.length() == 0) {
			// load defalut
			game_WordList = new WordList("words.txt");
		} else {
			game_WordList = new WordList(filename);
		}
	}
	
	public void gameEnd(Boolean win) {
		int nguess = model.get_nguess();
		if (win) {
			JOptionPane.showMessageDialog(this, "Congratulations! You won!\nTotal number of guesses: " + String.valueOf(nguess) + " .");
		} else {
			JOptionPane.showMessageDialog(this, "The answer is " + currentWord.getWord());
		}
		game_start = false;
		currentWord = null;
		nguess = 0;
		model.reset();
		contents.remove(panel_buttom);
		panel_buttom = NewGamePanel;
		contents.add(panel_buttom, BorderLayout.SOUTH);
		contents.validate();
		contents.repaint();
	}
	
	public void startnew(Boolean getRan) {
		game_start = true;
		model.reset();
		if (getRan) {
			if (diff != 3) {
				currentWord = game_WordList.randomWord(diff);
			} else {
				currentWord = game_WordList.randomWord();
			}
		}
		model.updateAllViews();
		contents.remove(panel_buttom);
		panel_buttom = RunningGamePanel;
		contents.add(panel_buttom, BorderLayout.SOUTH);
		contents.validate();
		contents.repaint();
	}
	
	public Boolean validWord(String w) {
		Boolean re_inDict = game_WordList.contains(w);
		Boolean re_notused = wordlog.validWord(w);
		return (re_inDict) && (re_notused);
	}
	
	public String getCurrentWord() {
		return currentWord.getWord();
	}
	
	public WordList getWordList() {
		return game_WordList;
	}
	
	public boolean get_hiding() {
		return hiding;
	}
	
	public GameModel getGameModel() {
		return model;
	}
	
	public void set_middle() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		this.setLocation((int)(width - this.getWidth()) / 2 , (int)(height - this.getHeight()) / 2);
		
	}
	
	public void show_window(){
		this.setVisible(true);
		hiding = false;
	}
	
	public void hide_window() {
		this.setVisible(false);
		hiding = true;
	}
	
	
	public MainWindow() {
		_instance = this;
		this.set_ui();
	}
	
	public static MainWindow getInstance() {
		return _instance;
	}
}
