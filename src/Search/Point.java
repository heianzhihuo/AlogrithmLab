package Search;

import java.awt.Color;
import java.awt.Graphics;


public class Point implements Comparable<Point>{
	
	int x = 0,y = 0;
	double heuristic = 0;
	double curCost = Double.MAX_VALUE;
	double totalCost = 0;
	int cost = 1;//0表示障碍，1表示普通，2表示River，4表示Desert,3表示起点和终点
	int type = 0;//0表示未访问，1表示在open_set，2表示在close_set,3表示在path中,4表示起点终点
	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean equals(Object p0) {
		// TODO Auto-generated method stub
		if(p0.getClass()!=Point.class)
			return false;
		Point p = (Point) p0;
		if(x!=p.x || y!=p.y)
			return false;
		return true;
	}
	@Override
	public int compareTo(Point p) {
		// TODO Auto-generated method stub
		double d = this.heuristic-p.heuristic;
		if(Math.abs(d)<1e-6)
			return 0;
		if(d<0)
			return -1;
		return 1;
	}
	public double distance(Point p) {
		double dx = Math.abs(x-p.x),dy = Math.abs(y-p.y);
		return dx + dy + (Math.sqrt(2)-2)*Math.min(dx, dy);
	}
	public void setHeuristic(double h) {
		heuristic = h;
	}
	public void setNonVisited() {
		type = 0;
	}
	public void setOpened() {
		type = 1;
	}
	public boolean isOpened() {
		return type==1;
	}
	public void setClosed() {
		type = 2;
	}
	public boolean isClosed() {
		return type==2;
	}
	public void setPath() {
		type = 3;
	}
	public void setSrcAndDest() {
		type = 4;
	}
	public void setWall() {
		cost = 0;
	}
	public void setPlain() {
		cost = 1;
	}
	public void setRiver() {
		cost = 2;
	}
	public void setDesert() {
		cost = 4;
	}
	
	
	static Color colorTable[] = 
		{Color.black,Color.white,Color.blue,Color.red,Color.yellow,Color.green,Color.gray,Color.orange};
	//0表示禁止，1正常，2溪流，3起点和终点，4沙漠，5当前扩展
	//灰色框表示未访问，黑色框表示open，绿色框表示close,orange表示path
	public void draw(Graphics g, int x, int y, int size) {
		
		switch(cost) {
		case 0:
			//不可通过
			g.setColor(Color.black);
			break;
		case 1:
			//正常道路
			g.setColor(Color.white);
			break;
		case 2:
			//溪流
			g.setColor(Color.blue);
			break;
		case 4:
			//沙漠
			g.setColor(Color.yellow);
			break;
		default:
			g.setColor(Color.white);
		}
    	g.fillRect(x, y, size, size);
    	g.setColor(Color.gray);
    	g.drawRect(x, y, size, size);
    	switch(type) {
		case 1:
			//
			g.setColor(Color.gray);
			g.fillOval(x, y, size, size);
			break;
		case 2:
			g.setColor(Color.green);
			g.fillOval(x, y, size, size);
			break;
		case 3:
			g.setColor(Color.orange);
			g.fillOval(x, y, size, size);
			break;
		case 4:
			g.setColor(Color.red);
			g.fillOval(x, y, size, size);
			break;
		default:
			g.setColor(Color.gray);
		}
		
    }
}
