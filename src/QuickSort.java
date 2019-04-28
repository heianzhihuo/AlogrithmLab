import java.util.Arrays;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

/**
 * @author WenWei
 * @date 2019年4月10日
 * @time 下午5:01:53
 */
public class QuickSort {
	/**
	 * 快速排序算法
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuickSort quic = new QuickSort();
		quic.Test();
		//quic.Test1();
	}
	private void Test() {
		//int N[] = {1000000},X[] = {0};
		//int N[] = {1000000},X[] = {100};
		int N[] = {1000,5000,10000},X[] = {0,10,20,30,40,50,60,70,80,90,100};
		//int N[] = {1000,5000,10000,50000,100000,500000,1000000},X[]={0};
		//int N[] = {200000,400000,600000,800000,1000000,1200000,1400000,1600000,1800000,2000000},X[] = {0};
		long starTime,endTime;
		for(int n:N) {
			System.out.println("n="+n);
			for(int x:X) {
				System.out.print("x="+x);
				int A[] = randomArray(n, x);
				try {
					System.out.print("\tC:");
					int C[] = Arrays.copyOf(A, n);
					starTime = System.nanoTime();
					newQuickSort(C, 0, n-1);
					endTime = System.nanoTime();
					System.out.print((endTime-starTime)*1.0/1000000+"ms");
				}catch(StackOverflowError e) {
					System.out.print("StackOverflow");
				}
				try {
					System.out.print("\tB:");
					int B[] = Arrays.copyOf(A, n);
					starTime = System.nanoTime();
					Arrays.sort(B);
					endTime = System.nanoTime();
					System.out.print((endTime-starTime)*1.0/1000000+"ms");
				}catch(StackOverflowError e) {
					System.out.print("StackOverflow");
				}
				try {
					System.out.print("\tA:");
					starTime = System.nanoTime();
					QuickSort(A,0,n-1);
					endTime = System.nanoTime();
					System.out.print((endTime-starTime)*1.0/1000000+"ms");
				}catch(StackOverflowError e) {
					System.out.print("StackOverflow");
				}
				System.out.println();
			}
		}
		
	}
	/*正确性测试*/
	private void Test1() {
		int n = 10;
		int x = 0;
		int A[] = randomArray(n, x);
		int B[] = Arrays.copyOf(A, n);
		long starTime,endTime;
		System.out.println("n="+n+",x="+x);
		
		starTime = System.nanoTime();
		Arrays.sort(B);
		endTime = System.nanoTime();
		System.out.println("B:"+(endTime-starTime)*1.0/1000000+"ms\t");
		for(int i=0;i<n;i++)
			System.out.println(B[i]+"\t");
		System.out.println();
		
		starTime = System.nanoTime();
		QuickSort(A,0,n-1);
		endTime = System.nanoTime();
		System.out.println("A:"+(endTime-starTime)*1.0/1000000+"ms\t");
		for(int i=0;i<n;i++)
			System.out.println(A[i]+"\t");
		System.out.println();
		System.out.println();
	}
	
	private int[] randomArray(int n,int x) {
		int p = n*x/100;
		int i;
		int A[] = new int[n];
		for(i=0;i<p;i++)
			A[i] = Integer.MAX_VALUE/2;
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
	
	private void newQuickSort(int[] A,int p,int r) {
		if(p<r) {
			int q = Rand_Partition(A, p, r);
			int i;
			for(i=q+1;i<=r;i++)
				if(A[i]!=A[q])
					break;
			newQuickSort(A,i,r);
			for(i=q-1;i>=p;i--)
				if(A[i]!=A[q])
					break;
			newQuickSort(A,p,i);
		}
	}
	
	private int Rand_Partition(int[] A,int p,int r) {
		int i = (int)(Math.random()*(r-p+1))+p;
		swap(A, r, i);
		int x = A[r];
		i = p-1;
		for(int j=p;j<=r-1;j++) {
			if(A[j]<=x) {
				i++;
				swap(A, i, j);
			}
		}
		swap(A, i+1, r);	
		return i+1;
	}
	
	private void swap(int[] A,int i,int j) {
		if(i==j)
			return;
		A[i] = A[i]^A[j];
		A[j] = A[i]^A[j];
		A[i] = A[i]^A[j];
	}
}
