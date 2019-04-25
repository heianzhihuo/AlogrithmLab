
import java.io.*;
import java.util.*;


/**
 * @author WenWei
 * @date 2019年4月10日
 * @time 下午7:29:10
 */
public class ConvexHull {
	
	public static void savePointInFile(Point[] points,String fileName) {
		try {
			File file =new File(fileName);
			if(file.exists()==false) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			for(Point p:points) 
				ps.println(String.valueOf(p.x)+","+String.valueOf(p.y));
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConvexHull hull = new ConvexHull();
		Point p1 = hull.new Point(0, 0),p2 = hull.new Point(100, 100);
		
		int n = 500;
		Point[] points = hull.randomPoints(n, p1, p2);
//		System.out.println("随机生成的点集为：");
//		for(Point p:points)
//			System.out.println(p.x+","+p.y);
		
		HashSet<Point> convex1 = hull.GramhamScan(points);
		System.out.println("凸包顶点数:"+convex1.size());
//		for(Point p:convex1)
//			System.out.println(p.x+","+p.y);
		
		HashSet<Point> convex2 = hull.BruteForce(points);
		System.out.println("暴力计算的凸包顶点数:"+convex2.size());
//		for(Point p:convex2)
//			System.out.println(p.x+","+p.y);
		
		HashSet<Point> convex3 = hull.DivideConquer(points);
		System.out.println("分治计算的凸包顶点数:"+convex3.size());
//		for(Point p:convex3)
//			System.out.println(p.x+","+p.y);
		
		savePointInFile(points,"points.txt");
		
		Point[] con1 = convex1.toArray(new Point[convex1.size()]);
		Arrays.sort(con1,1,con1.length,hull.new comparator(con1[0]));
		savePointInFile(con1,"points1.txt");
		
		Point[] con2 = convex2.toArray(new Point[convex2.size()]);
		Arrays.sort(con2,1,con2.length,hull.new comparator(con2[0]));
		savePointInFile(con2,"points2.txt");
		
		Point[] con3 = convex3.toArray(new Point[convex3.size()]);
		Arrays.sort(con3,1,con3.length,hull.new comparator(con3[0]));
		savePointInFile(con3,"points3.txt");
		
		
		int num[] = {100,200,500,800,1000,1500,2000,2500,3000};
		long starTime,endTime;
		System.out.print("\tGramhamScan\tBruteForce\tDivide\n");
		for(int m:num) {
			System.out.print(m+"\t");
			
			points = hull.randomPoints(m, p1, p2);
			
			starTime = System.nanoTime();
			convex1 = hull.GramhamScan(points);
			endTime = System.nanoTime();
			System.out.print((endTime-starTime)*1.0/1000000+"ms\t");
			
			starTime = System.nanoTime();
			convex2 = hull.BruteForce(points);
			endTime = System.nanoTime();
			System.out.print((endTime-starTime)*1.0/1000000+"ms\t");
			
			starTime = System.nanoTime();
			convex3 = hull.DivideConquer(points);
			endTime = System.nanoTime();
			System.out.print((endTime-starTime)*1.0/1000000+"ms\t");
			
			if ( convex1.equals(convex2) && convex1.equals(convex3))
				System.out.println("凸包相同.顶点数："+convex1.size());
			else
				System.out.println("凸包不相同！！！顶点数："+convex1.size()+","+convex2.size()+","+convex3.size());
			
			//System.out.println("点数：\t"+convex1.length+"\t\t"
			//+convex2.length+"\t\t"+convex3.length);
		}
		
	}
	
	/*点类*/
	class Point{
		double x,y;
		public Point(double x,double y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			int a = (int)x;
			int b = (int)y;
			return a*101+b;
		}
		@Override
		public boolean equals(Object p) {
			if(p.getClass()!=this.getClass())
				return false;
			Point p0 = (Point) p;
			Point p1 = sub(this, p0);
			if(sign(p1.x)==0 && sign(p1.y)==0)
				return true;
			return false;
		}
	}
	/*随机生成以点p1和点p2为对角线的矩形范围内的一个点*/
	Point randomPoint(Point p1,Point p2) {
		double x0 = p1.x<p2.y?p1.x:p2.x;
		double y0 = p1.y<p2.y?p1.y:p2.y;
		double length = Math.abs(p2.y-p1.y)+1;
		double width = Math.abs(p2.x-p1.x)+1;
		return new Point(x0+Math.random()*width,y0
				+Math.random()*length);
	}
	/*随机生成n个以点p1和点p2为对角线的矩形范围内的点**/
	Point[] randomPoints(int n,Point p1,Point p2) {
		Point points[] = new Point[n];
		for(int i=0;i<n;i++) 
			points[i] = randomPoint(p1, p2);
		return points;
	}
	/*返回x的符号*/
	int sign(double x) {
		if(Math.abs(x)<1e-8) return 0;
		if(x<0) return -1;
		return 1;
	}
	/*p1-p2*/
	Point sub(Point p1,Point p2) {
		return new Point(p1.x-p2.x, p1.y-p2.y);
	}
	/*p1和p2的距离*/
	double distance(Point p1,Point p2) {
		return (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y);
	}
	
	/* p1和p2的叉积
	 * p1xp2>0，则p1在p2的顺时针方向
	 * p1xp2<0，则p1在p2的逆时针方向
	 * p1xp2=0，则p1和p2共线，可能同向也可能反向*/
	double crossProduct(Point p1,Point p2) {
		return p1.x*p2.y-p1.y*p2.x;
	}
	/*第一个点，即最下方的点*/
//	private Point firstPoint = null;
	/*比较器：相对于firstPoint极角的排序*/
	
	class comparator implements Comparator<Point>{
		Point firstPoint = null;
		public comparator(Point p) {
			firstPoint = p;
		}
		@Override
		public int compare(Point p1, Point p2) {
			if(firstPoint==null)
				return 0;
			double tmp = crossProduct(sub(p1,firstPoint),sub(p2,firstPoint));
			double d = distance(p1,firstPoint)-distance(p2,firstPoint);
			int a = -sign(tmp),b = -sign(d);
			return a!=0?a:b;
		}
	}
	
//	private Comparator<Point> pcomparator = new Comparator<Point>() {
////		Point firstPoint = null;
//		@Override
//		public int compare(Point p1, Point p2) {
//			if(firstPoint==null)
//				return 0;
//			double tmp = crossProduct(sub(p1,firstPoint),sub(p2,firstPoint));
//			double d = distance(p1,firstPoint)-distance(p2,firstPoint);
//			int a = -sign(tmp),b = -sign(d);
//			return a!=0?a:b;
//		}
//	};
	/*Gramham扫描算法*/
	private HashSet<Point> GramhamScan(Point[] S){
		if(S.length<=3) 
			return new HashSet<>(Arrays.asList(S));
		int i,k=0;
		Point firstPoint = S[0];
		for(i=0;i<S.length-1;i++) {
			Point p = S[i];
			if(xycomparator.compare(firstPoint, p)>0) {
				firstPoint = p;
				k = i;
			}
		}
		S[k] = S[0];
		S[0] = firstPoint;
		comparator pcomparator = new comparator(S[0]);
		
		Arrays.sort(S,1,S.length,pcomparator);
		Stack<Point> stack = new Stack<>();
		stack.add(S[0]);
		stack.add(S[1]);
		for(i=2;i<S.length;i++) {
			while(stack.size()>1) {
				Point p1 = sub(stack.peek(),stack.get(stack.size()-2));
				Point p2 = sub(S[i], stack.get(stack.size()-2));
				if(sign(crossProduct(p1, p2))<=0)
					stack.pop();
				else break;
			}
			stack.add(S[i]);
		}
		return new HashSet<Point>(stack);
	}
	
	/*暴力搜索法*/
	private HashSet<Point> BruteForce(Point[] S){
		List<Point> convex = new ArrayList<>();
		if(S.length<=3) 
			return new HashSet<>(Arrays.asList(S));
					//Arrays.copyOf(S,S.length);
		int i,j,k,z;
		for(z=0;z<S.length;z++) {
			boolean flag = true;
			for(i=0;i<S.length-2&&flag;i++)
				for(j=i+1;j<S.length-1&&flag;j++)
					for(k=j+1;k<S.length&&flag;k++) 
						if(i!=z && j!=z && k!=z
						&& isPointInTriangle(S[z],S[i],S[j],S[k])) {
							flag = false;
						}
			if(flag)
				convex.add(S[z]);
		}
		return new HashSet<>(convex);
	}
	/*分治算法*/
	private HashSet<Point> DivideConquer(Point[] S){
		Arrays.sort(S,xycomparator);
		Point result[] = DivideConquer(S, 0, S.length-1);
		return new HashSet<>(Arrays.asList(result));
	}
	private Point[] DivideConquer(Point[] S,int low,int high){
		if(high-low<=2)
			return Arrays.copyOfRange(S, low,high+1);
		int mid = RandPartition(S, low, high);
		Point []left = DivideConquer(S,low,mid);
		Point []right = DivideConquer(S,mid+1,high);
		Point merge[] = new Point[left.length+right.length];
		System.arraycopy(left, 0, merge, 0, left.length);
		System.arraycopy(right, 0, merge, left.length, right.length);
		HashSet<Point> mergeRes = GramhamScan(merge);
		return mergeRes.toArray(new Point[mergeRes.size()]);
	}
	/*随机划分*/
	private int RandPartition(Point[] S,int low,int high) {
		int i = (int)(Math.random()*(high-low+1))+low;
		swap(S, high, i);
		Point x = S[high];
		i = low-1;
		for(int j=low;j<=high-1;j++) 
			if(xycomparator.compare(S[j],x)<=0) {
				i++;
				swap(S, i, j);
			}
		swap(S, i+1, high);
		return i+1;
	}
	/*交换两个为止的数据*/
	private void swap(Point[] S,int i,int j) {
		if(i==j)
			return;
		Point tmp = S[i];
		S[i] = S[j];
		S[j] = tmp; 
	}
	/*比较器：x和y的相对大小*/
	private Comparator<Point> xycomparator = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {
			Point p = sub(o1, o2);
			int a = sign(p.x),b = sign(p.y);
			return b!=0?b:a;
		}
	};
	/*判断点p0是否在p1，p2，p3三角形内*/
	private boolean isPointInTriangle(Point p0,Point p1,Point p2,Point p3) {
		Point pp1 = sub(p1, p0);
		Point pp2 = sub(p2, p0);
		Point pp3 = sub(p3, p0);
		int t1 = sign(crossProduct(pp1, pp2));
		int t2 = sign(crossProduct(pp2, pp3));
		int t3 = sign(crossProduct(pp3, pp1));
		if((t1>0 && t2>0 && t3>0) || (t1<0 && t2<0 && t3<0))
			return true;
		if(t1==0) {
			double d0 = distance(p1, p2);
			double d1 = distance(p0, p1);
			double d2 = distance(p0, p2);
			int a = sign(d0-d1),b = sign(d0-d2);
			if(a>0 && b>0)
				return true;
		}
		if(t2==0) {
			double d0 = distance(p2, p3);
			double d1 = distance(p0, p2);
			double d2 = distance(p0, p3);
			int a = sign(d0-d1),b = sign(d0-d2);
			if(a>0 && b>0)
				return true;
		}
		if(t3==0) {
			double d0 = distance(p3, p1);
			double d1 = distance(p0, p3);
			double d2 = distance(p0, p1);
			int a = sign(d0-d1),b = sign(d0-d2);
			if(a>0 && b>0)
				return true;
		}
		return false;
	}
	
}
