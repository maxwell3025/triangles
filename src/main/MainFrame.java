package main;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainFrame(int width, int height) {
		MainPanel panel = new MainPanel(width, height);
		add(panel);
		panel.init();
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setVisible(true);
	}

}
