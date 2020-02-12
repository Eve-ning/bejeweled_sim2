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
	
	ActionListener ac_fall;
	ActionListener ac_fill;	
	ActionListener ac_randomize;
	
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
		ac_fall = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add Swing worker to update board on fall
				SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() throws Exception {
						int op_fall = 0;
						while (board.fallCells(op_fall)) { op_fall++; }
						board.markCells(3);
//						Thread.sleep(MATCH_DELAY);
						System.out.println(board.clearMarkedCells());
						return null;
					}
					@Override
					protected void done() { 

						// fillCells();
						// randomizeCells();
						super.done();
						}
				};
				worker.execute();
			}
		};
		ac_fill = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				board.fillCells();
			}
		};
		ac_randomize = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				board.randomizeCells();
			}
		};

		ui.setALFall(ac_fall);
		ui.setALFill(ac_fill);
		ui.setALRand(ac_randomize);
	}
	// Initialize Panels and add to Frame
	public void initPanels() {
		panel_frame = new JPanel() {
			private static final long serialVersionUID = -7838137756337687463L;
			@Override
			public Dimension getPreferredSize() {
				return DIM_WINDOW;
			}
		};
		
		panel_frame.add(board.panel_board);
		panel_frame.add(ui.panel_ui);
		frame.add(panel_frame);
	}
	
	public static void main(String[] args) {
		new MatchThree(150, 150);
	}
	
	
}
