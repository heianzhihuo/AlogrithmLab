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
		//quic.Test1();
		//quic.Test2();
		//quic.Test3();
		quic.Test4();
		//quic.Test5();
		//quic.Test6();
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
	/*n=1000000随机数测试*/
	private void Test2() {
		int n = 1000000;
		int x = 0;
		int A[] = randomArray(n, x);
		int B[] = Arrays.copyOf(A, n);
		long starTime,endTime;
		System.out.println("n="+n+",x="+x);
		
		starTime = System.nanoTime();
		Arrays.sort(B);
		endTime = System.nanoTime();
		System.out.println("B:"+(endTime-starTime)*1.0/1000000+"ms\t");
		
		starTime = System.nanoTime();
		QuickSort(A,0,n-1);
		endTime = System.nanoTime();
		System.out.println("A:"+(endTime-starTime)*1.0/1000000+"ms\t");
		System.out.println();
		System.out.println();
	}
	/*n=1000000个1测试*/
	private void Test3() {
		int n = 1000000;
		int x = 100;
		int A[] = randomArray(n, x);
		int B[] = Arrays.copyOf(A, n);
		long starTime,endTime;
		System.out.println("n="+n+",x="+x);
		
		starTime = System.nanoTime();
		Arrays.sort(B);
		endTime = System.nanoTime();
		System.out.println("B:"+(endTime-starTime)*1.0/1000000+"ms\t");
		
		starTime = System.nanoTime();
		QuickSort(A,0,n-1);
		endTime = System.nanoTime();
		System.out.println("A:"+(endTime-starTime)*1.0/1000000+"ms\t");
		System.out.println();
		System.out.println();
	}
	/*n=10000 x=0,10,20,30,40,50,60,70,80,90,100测试*/
	private void Test4() {
		int n = 10000;
		int X[] = {0,10,20,30,40,50,60,70,80,90,100};
		System.out.println("n="+n);
		for(int x:X) {
			System.out.print("x="+x);
			int A[] = randomArray(n, x);
			int B[] = Arrays.copyOf(A, n);
			long starTime,endTime;
			
			starTime = System.nanoTime();
			Arrays.sort(B);
			endTime = System.nanoTime();
			System.out.print("\tB:"+(endTime-starTime)*1.0/1000000+"ms\t");
			
			starTime = System.nanoTime();
			QuickSort(A,0,n-1);
			endTime = System.nanoTime();
			System.out.println("\tA:"+(endTime-starTime)*1.0/1000000+"ms\t");
		}
	}
	/*n=1000,5000 x=0,50,60,70,80,90,100测试*/
	private void Test5() {
		int num[] = {1000,5000};
		int X[] = {0,50,60,70,80,90,100};
		for(int n:num) {
			System.out.println("n="+n);
			for(int x:X) {
				System.out.print("x="+x);
				int A[] = randomArray(n, x);
				int B[] = Arrays.copyOf(A, n);
				long starTime,endTime;
				
				starTime = System.nanoTime();
				Arrays.sort(B);
				endTime = System.nanoTime();
				System.out.print("\tB:"+(endTime-starTime)*1.0/1000000+"ms\t");
				
				starTime = System.nanoTime();
				QuickSort(A,0,n-1);
				endTime = System.nanoTime();
				System.out.println("\tA:"+(endTime-starTime)*1.0/1000000+"ms\t");
			}
			System.out.println();
		}
	}
	/*x=0,n=1000,5000,10000,50000,100000,500000,1000000*/
	private void Test6() {
		int num[] = {200000,400000,600000,800000,1000000,1200000,1400000,1600000,1800000,2000000};
		int x = 0;
		System.out.println("x="+x);
		for(int n:num) {
			System.out.print("n="+n);
			int A[] = randomArray(n, x);
			int B[] = Arrays.copyOf(A, n);
			//int B[] = copyofArray(A, n);
			long starTime,endTime;
			
			starTime = System.nanoTime();
			Arrays.sort(B);
			endTime = System.nanoTime();
			System.out.print("\tB:"+(endTime-starTime)*1.0/1000000+"ms\t");
			
			starTime = System.nanoTime();
			QuickSort(A,0,n-1);
			endTime = System.nanoTime();
			System.out.println("\tA:"+(endTime-starTime)*1.0/1000000+"ms\t");
		}
	}
	
	private int[] copyofArray(int[] A,int n) {
		int []B = new int[n];
		for(int i=0;i<n;i++)
			B[i] = A[i];
		return B;
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
