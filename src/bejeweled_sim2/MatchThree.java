package bejeweled_sim2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class MatchThree {
	
	ArrayList<ArrayList<Cell>> cells;
	JFrame frame;
	JPanel panel_frame;
	Board board;
	BoardUI ui;
	static Dimension DIM_BOARD  = new Dimension(800, 800);
	static Dimension DIM_UI     = new Dimension(DIM_BOARD.width, 200);
	static Dimension DIM_WINDOW = new Dimension(DIM_BOARD.width,
											    DIM_BOARD.height + DIM_UI.height);
	
	final int DELAY_FALL = 0;
	final int DELAY_FILL = 0;
	final int DELAY_RAND = 0;
	final int DELAY_MATCH = 500;
	
	ActionListener al_fall;
	ActionListener al_fill;	
	ActionListener al_rand;
	ActionListener al_mtch;
	
	MatchThree(int rows, int cols) {
		board = new Board(rows, cols);
		ui = new BoardUI();

		initFrame();
		initActionListeners();
		initPanels();
		
		// Refresh Frame
		frame.revalidate();
	}
	
	// Initialize Frame
	public void initFrame() {
		frame = new JFrame("Board");
		frame.setSize(DIM_WINDOW);
		frame.getContentPane().setBackground(new Color(65,65,65));
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
	}
	// Initialize ActionListeners
	public void initActionListeners() {
		al_fall = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add Swing worker to update board on fall
				SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() throws Exception {
						board.fallCellsAll(DELAY_FALL);
						Thread.sleep(DELAY_MATCH);
						return null;
					}
					@Override
					protected void done() { super.done(); }
				};
				worker.execute();
			}
		};
		al_fill = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add Swing worker to update board on fall
				SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() {
						board.fillCells(DELAY_FILL);
						return null;
					}
					@Override
					protected void done() {	}
				};
				worker.execute();
			}
		};
		al_rand = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add Swing worker to update board on fall
				SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() {
						board.randomizeCells(DELAY_FILL);
						return null;
					}
					@Override
					protected void done() { super.done(); }
				};
				worker.execute();
			}
		};
		al_mtch = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add Swing worker to update board on fall
				SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() {
						board.markCells(3);
						System.out.println(board.clearMarkedCells());
						return null;
					}
					@Override
					protected void done() { super.done(); }
				};
				worker.execute();
			}
		};

		ui.setALFall(al_fall);
		ui.setALFill(al_fill);
		ui.setALRand(al_rand);
		ui.setALMatch(al_mtch);
	}
	// Initialize Panels and add to Frame
	public void initPanels() {
		panel_frame = new JPanel() {
			private static final long serialVersionUID = -7838137756337687463L;
			@Override
			public Dimension getPreferredSize() { return DIM_WINDOW; }
			
		};
		
		panel_frame.add(board.panel_board);
		panel_frame.add(ui.panel_ui);
		frame.add(panel_frame);
	}
	
	public static void main(String[] args) {
		new MatchThree(100, 100);
	}
	
	
}
