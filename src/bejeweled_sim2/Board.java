package bejeweled_sim2;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;
import bejeweled_sim2.Cell.COLOR;

public class Board {
	
	ArrayList<ArrayList<Cell>> cells;
	JPanel panel_board;
	final int ROWS;
	final int COLS;
	final int MATCH_DELAY = 250;
	final boolean DEBUG = false;
	
	Board(final int rows, final int cols) {
		ROWS = rows;
		COLS = cols;

		initUI();
	}
	
	// Helper Functions
	public Cell getCell(int row, int col) {
		return cells.get(row).get(col);
	}
	public COLOR getColor(int row, int col) {
		return cells.get(row).get(col).getColor();
	}
	public void setColor(int row, int col, COLOR color) {
		cells.get(row).get(col).setColor(color);
	}
	public void markCell(int row, int col) {
		getCell(row, col).mark();
	}
	public void unmarkCell(int row, int col) {
		getCell(row, col).unmark();
	}

	// Initialize Panels and add to Frame
	public void initUI(boolean randomize) {
		panel_board = new JPanel() {
			private static final long serialVersionUID = 3711142179468433750L;
			@Override
			public Dimension getPreferredSize() {
				return MatchThree.DIM_BOARD;
			}
		};
		panel_board.setLayout(new GridLayout(ROWS, COLS));
		initCells();
		if (randomize) randomizeCells();
	}
	public void initUI() {
		initUI(true);
	}
	// Initialize Cells and add to Frame
 	public void initCells() {
		cells = new ArrayList<ArrayList<Cell>>();
		for (int r = 0; r < ROWS; r++) {
			ArrayList<Cell> cell_row = new ArrayList<Cell>();
			for (int c = 0; c < COLS; c++) {
				Cell cell = new Cell();
				cell_row.add(cell);
				panel_board.add(cell);
			}
			cells.add(cell_row);
		}
	}

	// Randomize colors in Cells
	public void randomizeCells() {
		Random rand = new Random();
		// rand.setSeed(10000);
		for (ArrayList<Cell> r : cells) {
			for (Cell c : r) {
				c.setColor(rand.nextInt(Cell.COLOR.COUNT));
			}
		}
	}
	
	// Fill Empty Cells
	public void fillCells() {
		Random rand = new Random();
		for (ArrayList<Cell> r : cells) {
			for (Cell c : r) {
				if (c.getColor() == Cell.COLOR.EMPTY)
					// -1 to avoid empty
					c.setColor(rand.nextInt(Cell.COLOR.COUNT - 1));
			}
		}
	}
	// Fall Cells down once
	// Optimize limit is to hasten the search by decreasing the number of
	// rows checked
	public boolean fallCells(int optimize_limit) {
		assert optimize_limit >= 0: "Optimizing cannot be less than 0";
		
		// The flag indicates if there is fall movement
		// If it's false, then no cells have fallen
		boolean flag = false;
		for (int r = 0; r < cells.size() - 1 - optimize_limit; r++) {
			for (int c = 0; c < cells.get(0).size(); c++) {
				if (getColor(r,   c) == COLOR.EMPTY) continue;
				if (getColor(r+1, c) == COLOR.EMPTY) {
					setColor(r+1, c, getColor(r, c));
					setColor(r,   c, COLOR.EMPTY);
					flag = true;
				}
			}
		}
		return flag;
	}
	public boolean fallCells() {
		return fallCells(0);
	}
	
	
	public void markCells(int match_length) {
		markCellsRight(match_length);
		markCellsDown(match_length);
	}
	private void markCellsRight(int match_length) {
		// Match right
		for (int r = 0; r < ROWS - match_length + 1; r++) {
			for (int c = 0; c < COLS; c++) {
				boolean match = true;
				COLOR color = getColor(r, c);
				if (color == COLOR.EMPTY) continue;
				
				// Check if all cells are of same COLOR
				for (int i = 1; i < match_length; i++) {
					match &= (color == getColor(r+i, c));
				}
				// If true, then mark all
				if (match) {
					for (int i = 0; i < match_length; i++) {
						markCell(r+i, c);
					}
				}
			}
		}
	}
	private void markCellsDown(int match_length) {
		// Match down
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS - match_length + 1; c++) {
				boolean match = true;
				COLOR color = getColor(r, c);
				if (color == COLOR.EMPTY) continue;
				
				// Check if all cells are of same COLOR
				for (int i = 1; i < match_length; i++) {
					match &= (color == getColor(r, c+i));
				}
				// If true, then mark all
				if (match) {
					for (int i = 0; i < match_length; i++) {
						markCell(r, c+i);
					}
				}
			}
		}
	}
	
	// This method clears all the marked cells and returns a dict
	// of matches
	public Map<COLOR, Integer> clearMarkedCells() {
		Map<COLOR, Integer> cell_map = 
				new HashMap<Cell.COLOR, Integer>();
		for (COLOR c : Cell.COLOR.values()) cell_map.put(c, 0);
		
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				if (getCell(r, c).isMarked()) {
					cell_map.replace(getColor(r, c),
							cell_map.get(getColor(r, c)) +
							clearMarkedCellsRec(r, c));
				}
			}
		}
		return cell_map;
	}
	private int clearMarkedCellsRec(int r, int c) {
		COLOR color = getColor(r, c);
		unmarkCell(r, c);
		getCell(r, c).setColor(COLOR.EMPTY);
		int sum = 1;
		if (r != ROWS - 1 && getColor(r + 1, c) == color &&
			getCell(r + 1, c).isMarked()) { // Down
			// if (DEBUG) System.out.print("Down ");
			sum += clearMarkedCellsRec(r + 1, c);
		}
		if (r != 0 && getColor(r - 1, c) == color &&
			getCell(r - 1, c).isMarked()) { // Up
			// if (DEBUG) System.out.print("Up ");
			sum += clearMarkedCellsRec(r - 1, c);
		}
		if (c != COLS - 1 && getColor(r, c + 1) == color &&
			getCell(r, c + 1).isMarked()) { // Right
			// if (DEBUG) System.out.print("Right ");
			sum += clearMarkedCellsRec(r, c + 1);
		}
		if (c != 0 && getColor(r, c - 1) == color &&
			getCell(r, c - 1).isMarked()) { // Left
			// if (DEBUG) System.out.print("Left ");
			sum += clearMarkedCellsRec(r, c - 1);
		} 
		return sum;
	}
	
	
}
