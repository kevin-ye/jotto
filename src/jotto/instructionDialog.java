package jotto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.image.BufferedImage;
import java.io.File;

public class instructionDialog extends JDialog {
	
	class imgPanel extends JPanel {
		private BufferedImage img;
		
		public imgPanel(String filename) {
			super();
			try {
				img = ImageIO.read(new File(filename));
			} catch (Exception e) {
				img = null;
				System.err.println("File not found!");
			}
		}
		
		@Override
		public void paint(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(img, 0, 0, null);
		}
	}
	
	public instructionDialog(int tab) {
		super();
		this.setVisible(false);
		
		JTabbedPane helpPane = new JTabbedPane();
		imgPanel p1 = new imgPanel("ins.jpg");
		helpPane.addTab("How To: Start a game", p1);
		imgPanel p2 = new imgPanel("ins2.jpg");
		helpPane.addTab("How To: Play a game",p2);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(helpPane, BorderLayout.CENTER);
		
		helpPane.setSelectedIndex(tab);
		
		this.setMinimumSize(new Dimension(100, 100));
		this.setTitle("Help/Instruction");
		this.setSize(780, 580);
		this.setLocation(500, 500);
		this.set_middle();
	}
	
	public void set_middle() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		this.setLocation((int)(width - this.getWidth()) / 2 , (int)(height - this.getHeight()) / 2);
		
	}
	
	public void showDialog() {
		this.setVisible(true);
	}
}
