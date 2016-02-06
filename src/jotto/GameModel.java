package jotto;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GameModel {
	private int nguess = 0;
	private String player_input;
	private Boolean[] ignore = new Boolean[26];
	private Boolean[] important = new Boolean[26];
	
	private ArrayList<ICustomView> views = new ArrayList<ICustomView>();
	
	public GameModel() {
		for (int i = 0; i < 26; i++) {
			ignore[i] = false;
			important[i] = false;
		}
	}
	
	public void set_player_input(String w) {
		nguess++;
		player_input = w;
		updateAllViews();
		player_input = null;
	}
	
	public void addView(ICustomView iv) {
		views.add(iv);
	}
	
	public void updateAllViews() {
		for (int i = 0; i < views.size(); i++) {
			ICustomView v = views.get(i);
			v.updateView(player_input);
		}
	}
	
	public void reset() {
		player_input = null;
		nguess = 0;
		for (int i = 0; i < views.size(); i++) {
			ICustomView v = views.get(i);
			v.clear();
		}
	}
	
	public void set_ignore(char c) {
		ignore[Character.toUpperCase(c) - 'A'] = true;
		updateAllViews();
	}
	
	public void set_important(char c) {
		important[Character.toUpperCase(c) - 'A'] = true;
		updateAllViews();
	}
	
	public void set_clear(char c) {
		ignore[Character.toUpperCase(c) - 'A'] = false;
		important[Character.toUpperCase(c) - 'A'] = false;
		updateAllViews();
	}
	
	public Boolean is_ignore(char c) {
		return ignore[Character.toUpperCase(c) - 'A'];
	}
	
	public Boolean is_important(char c) {
		return important[Character.toUpperCase(c) - 'A'];
	}
	
	public int get_nguess() {
		return nguess;
	}
}
