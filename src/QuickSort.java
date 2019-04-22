import java.util.Arrays;
import java.util.Scanner;

/**
 * @author WenWei
 * @date 2019年4月10日
 * @time 下午5:01:53
 */
public class QuickSort {

	/**
	 * @param args
	 * 5000,1000,1000000;
		0,50,60,70,80,100
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuickSort quic = new QuickSort();
		quic.Test();
	}
	
	void Test2() {
		Scanner in = new Scanner(System.in);
		long starTime,endTime;
		while(true) {
			int n = in.nextInt();
			int x = in.nextInt();
			if(n<=0 || x<0)
				break;
			int A[] = randomArray(n, x);
			int B[] = Arrays.copyOf(A, n);
			System.out.println();
			
			starTime = System.nanoTime();
			Arrays.sort(B);
			endTime = System.nanoTime();
			System.out.print("B:"+(endTime-starTime)*1.0/1000000+"ms\t");
			
			starTime = System.nanoTime();
			QuickSort(A,0,n-1);
			endTime = System.nanoTime();
			System.out.print("A:"+(endTime-starTime)*1.0/1000000+"ms\t");
			System.out.println();
		}
		in.close();
	}
	
	private void Test() {
		int num[] = {1000,5000};
		int per[] = {0,50,60,70,80,90,100};
		long starTime,endTime;
		System.out.print("\t");
		for(int j=0;j<per.length;j++)
			System.out.print(per[j]+"%\t\t\t\t");
		System.out.println();
		for(int i=0;i<num.length;i++) {
			System.out.print(num[i]+"\t");
			for(int j=0;j<per.length;j++) {
				int A[] = randomArray(num[i], per[j]);
				int B[] = Arrays.copyOf(A, num[i]);
				starTime = System.nanoTime();
				Arrays.sort(B);
				endTime = System.nanoTime();
				System.out.print("B:"+(endTime-starTime)*1.0/1000000+"ms\t");
				
				starTime = System.nanoTime();
				QuickSort(A,0,num[i]-1);
				endTime = System.nanoTime();
				System.out.print("A:"+(endTime-starTime)*1.0/1000000+"ms\t");
			}
			System.out.println();
		}
		int n = 1000000;
		int x = 0;
		int A[] = randomArray(n, x);
		int B[] = Arrays.copyOf(A, n);
		System.out.print("n="+n+",x="+x+":\t");
		
		starTime = System.nanoTime();
		Arrays.sort(B);
		endTime = System.nanoTime();
		System.out.print("B:"+(endTime-starTime)*1.0/1000000+"ms, ");
		
		starTime = System.nanoTime();
		QuickSort(A,0,n-1);
		endTime = System.nanoTime();
		System.out.print("A:"+(endTime-starTime)*1.0/1000000+"ms");
		System.out.println();
		
		x = 100;
		A = randomArray(n, x);
		B = Arrays.copyOf(A, n);
		System.out.print("n="+n+",x="+x+":");
		
		starTime = System.nanoTime();
		Arrays.sort(B);
		endTime = System.nanoTime();
		System.out.print("B:"+(endTime-starTime)*1.0/1000000+"ms, ");
		
		starTime = System.nanoTime();
		QuickSort(A,0,n-1);
		endTime = System.nanoTime();
		System.out.print("A:"+(endTime-starTime)*1.0/1000000+"ms");
		System.out.println();
	}
	
	private int[] randomArray(int n,int x) {
		int p = n*x/100;
		int i;
		int A[] = new int[n];
		for(i=0;i<p;i++)
			A[i] = 1;
		for(;i<n;i++)
			A[i] = (int)(Math.random()*Integer.MAX_VALUE); 
		return A;
	}
	
	private void QuickSort(int[] A,int p,int r) {
		if(p<r) {
			int q = Rand_Partition(A, p, r);
			QuickSort(A, p, q-1);
			QuickSort(A, q+1,r);
		}
	}
	
	private int Rand_Partition(int[] A,int p,int r) {
		int i = (int)(Math.random()*(r-p+1))+p;
		exchange(A, r, i);
		int x = A[r];
		i = p-1;
		for(int j=p;j<=r-1;j++) {
			if(A[j]<=x) {
				i++;
				exchange(A, i, j);
			}
		}
		exchange(A, i+1, r);	
		return i+1;
	}
	
	private void exchange(int[] A,int i,int j) {
		if(i==j)
			return;
		A[i] = A[i]^A[j];
		A[j] = A[i]^A[j];
		A[i] = A[i]^A[j];
	}
}
