package bejeweled_sim2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardUI {
	
	JPanel panel_ui;
	final int MATCH_DELAY = 250;
	private ActionListener al_fall;
	private ActionListener al_fill;	
	private ActionListener al_rand;
	JButton btn_fall;
	JButton btn_fill;
	JButton btn_rand;
	
	BoardUI() {
		initPanel();
	}
	
	public void setALFall(ActionListener al) {
		al_fall = al;
		btn_fall.removeAll();
		btn_fall.addActionListener(al_fall);
	}
	public void setALFill(ActionListener al) {
		al_fill = al;
		btn_fill.removeAll();
		btn_fill.addActionListener(al_fill);
	}
	public void setALRand(ActionListener al) {
		al_rand = al;
		btn_rand.removeAll();
		btn_rand.addActionListener(al_rand);
	}
	
	// Initialize ActionListeners
	public void initActionListeners() {
		al_fall = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				throw new UnsupportedOperationException(
						"AL not implemented");
			}
		};
		al_fill = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				throw new UnsupportedOperationException(
						"AL not implemented");
			}
		};
		al_rand = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				throw new UnsupportedOperationException(
						"AL not implemented");
			}
		};
	}
	// Initialize other UI
	public void initPanel() {
		panel_ui = new JPanel() ;
		initButton();
		panel_ui.add(btn_fall);
		panel_ui.add(btn_fill);
		panel_ui.add(btn_rand);
	}
	
	// Initialize Buttons
	public void initButton() {
		btn_fall = new JButton("Fall");
		btn_fill = new JButton("Fill");
		btn_rand = new JButton("Randomize");
	}
	
	
}
