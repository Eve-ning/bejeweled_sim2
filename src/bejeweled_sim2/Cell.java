package bejeweled_sim2;

import java.awt.Color;

import javax.swing.JButton;

public class Cell extends JButton {

	enum COLOR {
		GREEN,
		RED,
		BLUE,
		YELLOW,
		BROWN,
		PURPLE,
		EMPTY;
		
		final public static int COUNT = 7;
		COLOR(){
			
		}
		
		// Integer Conversion Helpers
		public int asInt() {
			switch (this) {
			case GREEN : return 0;
			case RED   : return 1;
			case BLUE  : return 2;
			case YELLOW: return 3;
			case BROWN : return 4;
			case PURPLE: return 5;
			case EMPTY : return 6;
			default    : return -1;
			}
		}
		public static int asInt(COLOR color) {
			switch (color) {
			case GREEN : return 0;
			case RED   : return 1;
			case BLUE  : return 2;
			case YELLOW: return 3;
			case BROWN : return 4;
			case PURPLE: return 5;
			case EMPTY : return 6;
			default    : return -1;
			}
		}
		public static COLOR fromInt(int x) {
			switch (x) {
			case 0:  return GREEN ;
			case 1:  return RED   ;
			case 2:  return BLUE  ;
			case 3:  return YELLOW;
			case 4:  return BROWN ;
			case 5:  return PURPLE;
			case 6:  return EMPTY ;
			default: return EMPTY ;
			}
		}
	}
	
	public Cell(COLOR color) {
		color_ = color;
		updateColor();
		mark_ = false;
		setBorderPainted(false);
	}
	public Cell() {
		this(COLOR.EMPTY);
	}
	
	public void setColor(COLOR color)  { color_ = color; updateColor(); }
	public void setColor(int color_i)  { color_ = COLOR.fromInt(color_i); updateColor(); }
	public COLOR getColor() { return color_; }
	public int asInt() { return color_.asInt(); }
	
	public boolean isMarked() { return mark_; }
	public void mark() 		  { mark_ = true; updateMark();}
	public void unmark()      { mark_ = false; updateMark();}
	
	private void updateMark() { setText(mark_ ? "X" : ""); }
	
	private void updateColor() {
		switch (color_) {
		case GREEN : setBackground(new Color(66,  179, 41 ));  break;
		case RED   : setBackground(new Color(179, 50,  41 ));  break;
		case BLUE  : setBackground(new Color(41,  59,  179)); break;
		case YELLOW: setBackground(new Color(214, 219, 46 ));  break; 
		case BROWN : setBackground(new Color(135, 86,  27 ));  break;
		case PURPLE: setBackground(new Color(125, 37,  184)); break; 
		case EMPTY : setBackground(new Color(200, 200, 200)); break; 
		}
	}
	
	private boolean mark_;
	private COLOR color_;
	private static final long serialVersionUID = 1L;
}
