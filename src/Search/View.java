package Search;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

import javax.swing.*;

public class View extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int GRID_SIZE = 10;
	private Field theField;
	MouseHandler mouseHandler = new MouseHandler();
	MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
	private int commond = 0;
	
	public View(Field field) {
		theField = field;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    addMouseListener(mouseHandler);
	    addMouseMotionListener(mouseMotionHandler);
	}
	public void setCommond(int com) {
		commond = com;
	}
	public void clearCommond() {
		commond = 0;
	}
	public void setSrcCommond() {
		commond = 1;//起点
	}
	public void setDestCommond() {
		commond = 2;//设定起点
	}
	public void setWallCommond() {
		commond = 3;//墙
	}
	public void setPlainCommond() {
		commond = 4;//平地
	}
	public void setRiverCommond() {
		commond = 5;//河流
	}
	public void setDesertCommond() {
		commond = 6;//沙漠
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(theField.getWidth() * GRID_SIZE + 1, theField.getHeight() * GRID_SIZE + 1);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int row = 0; row < theField.getHeight(); row++) {
			for (int col = 0; col < theField.getWidth(); col++) {
				Point cell = theField.get(row, col);
				cell.draw(g, col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE);
			}
		}
	}
	/*获取对用位置的point*/
	private Point getPoint(Point2D p) {
        int col = (int) p.getX()/GRID_SIZE;
        int row = (int) p.getY()/GRID_SIZE;
        Point cell = theField.get(row,col);
        return cell;
	}
	
	private class MouseHandler extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			Point point = getPoint(e.getPoint());
			switch(commond) {
			case 1:
				theField.setSrc(point);
				//System.out.println(theField.src)
				break;
			case 2:
				theField.setDest(point);
				break;
			case 3:
				point.setWall();
				break;
			case 4:
				point.setPlain();
				break;
			case 5:
				point.setRiver();
				break;
			case 6:
				point.setDesert();
				break;
			}
	        repaint();
	    }
	}
	
	private class MouseMotionHandler implements MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent e) {
			Point point = getPoint(e.getPoint());
			switch(commond) {
			case 3:
				point.setWall();
				break;
			case 4:
				point.setPlain();
				break;
			case 5:
				point.setRiver();
				break;
			case 6:
				point.setDesert();
				break;
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
		}
		
	}
}
