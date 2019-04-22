package Search;

import java.util.PriorityQueue;

/**
 * @author WenWei
 * @date 2019年4月16日
 * @time 下午8:49:48
 */
public class Field {
	private int width;
	private int height;
	private Point[][] field;
	private Point src=null,dest=null;
	private PriorityQueue<Point> open_set = new PriorityQueue<>();
	
	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		field = new Point[height][width];
	}
	
	public void setSrc(Point p) {
		clearSrc();
		src = p;
		src.setSrcAndDest();
	}
	public void clearSrc() {
		if(src!=null) {
			src.setNonVisited();
			src = null;
		}
	}
	public void setDest(Point p) {
		clearDest();
		dest = p;
		dest.setSrcAndDest();
	}
	public void clearDest() {
		if(dest!=null) {
			dest.setNonVisited();
			dest = null;
		}
	}
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Point place(int row, int col, Point o) {
		Point ret = field[row][col];
		field[row][col] = o;
		return ret;
	}

	public Point get(int row, int col) {
		return field[row][col];
	}
	
	private double getHeuristic(Point p) {
		return p.distance(dest);
	}
	private boolean isValid(int x,int y) {
		if(x<0 || x>=width || y<0 || y>=height)
			return false;
		return true;
	}
	
	public void BuildPath() {
		
	}
	public Point SelectNext() {
		if(open_set.isEmpty()) {
			System.out.println("没有可行解！！！");
			return null;
		}
		Point p = open_set.poll();
		p.setClosed();
		return p;
	}
	
	public int Start() {
		if(src==null || dest==null)
			return -1;
		src.setHeuristic(0);
		open_set.add(src);
		src.setOpened();
		return 0;
	}

	public void ExtendPoint(Point p) {
		if(p.equals(dest)) {
			BuildPath();
			return;
		}
		ProcessPoint(p.x+1, p.y, p);
		ProcessPoint(p.x+1, p.y-1, p);
		ProcessPoint(p.x+1, p.y+1, p);
		ProcessPoint(p.x, p.y-1, p);
		ProcessPoint(p.x, p.y+1, p);
		ProcessPoint(p.x-1, p.y-1, p);
		ProcessPoint(p.x-1, p.y+1, p);
		ProcessPoint(p.x-1, p.y, p);
	}
	
	public void ProcessPoint(int x,int y,Point parent) {
		if(!isValid(x, y))
			return;
		Point p = get(x, y);
		if(p.isClosed())
			return;
		if(p.isOpened()) 
			open_set.remove(p);
		p.setHeuristic(getHeuristic(p)+p.distance(src));
		p.setOpened();
		open_set.add(p);
	}
	
}
