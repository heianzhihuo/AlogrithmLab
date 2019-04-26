import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gnu.glpk.*;


public class CoverSet {
	
	
	/*生成1-n范围内x个随机不重复的数*/
	private Set<Integer> randomSubSet(int n,int x){
		Set<Integer> result = new HashSet<>();
		if(x>n)
			return result;
		int num[] = new int[n];
		int i,k;
		for(i=0;i<n;i++)
			num[i] = i+1;
		for(i=0;i<n;i++) {
			k = (int)(Math.random()*i);
			int tmp = num[k];
			num[k] = num[i];
			num[i] = tmp; 
		}
		for(i=0;i<x;i++)
			result.add(num[i]);
		return result;
	}
	/*生成m个1-n范围内x个不重复的数集合*/
	private Set<Integer>[] randomSubSetFamily(int m,int n,int d){
		@SuppressWarnings("unchecked")
		Set<Integer>[] result = new Set[m];
		Set<Integer> X = new HashSet<>();
		Set<Integer> tmp;
		int i,k;
		for(i=0;i<n;i++)
			X.add(i+1);
		for(i=0;i<m;i++) {
			k = (int)(Math.random()*d+1);
			tmp = randomSubSet(n, k);
			X.removeAll(tmp);
			result[i] = tmp; 
		}
		for(int x:X) {
			k = (int)(Math.random()*m);
			tmp = result[k];
			tmp.add(x);
		}
		return result;
	}
	
	public void Test() {
		
		int m = 30,n = 30;
		int X[] = new int[n];
		Set<Integer> []F = randomSubSetFamily(m, n,20);
		int i,j;
		long startTime,endTime;
		System.out.println("集合X的元素为"+n);
		for(i=0;i<X.length;i++) 
			X[i] = i+1; 
//		System.out.println();
		for(i=0;i<F.length;i++) {
			System.out.print("第"+i+"个子集为：");
			for(int x:F[i])
				System.out.print(x+",");
			System.out.println();
		}
		
		startTime = System.nanoTime();
		List<Integer> S1 = greedySetCover(X,F);
		endTime = System.nanoTime();
		System.out.print("贪心解为："+S1.size());
		System.out.print("\t时间"+(endTime-startTime)*1.0/1000000+"ms\t");
		for(int x:S1) {
			System.out.print(x+",");
		}
		System.out.println();
		startTime = System.nanoTime();
		List<Integer> S2 = SetCoverLP(X, F);
		endTime = System.nanoTime();
		System.out.print("LP解为："+S2.size());
		System.out.print("\t时间"+(endTime-startTime)*1.0/1000000+"ms\t");
		for(int x:S2)
			System.out.print(x+",");
		System.out.println();
		
		System.out.println();
		
		int M[] = {100,1000,5000};
		int N[] = {100,1000,5000};
		for(i=0;i<M.length;i++) {
			m = M[i];
			n = N[i];
			X = new int[n];
			for(j=0;j<n;j++)
				X[j] = j+1; 
			F = randomSubSetFamily(m, n, 60);
			
			System.out.println("|X|="+n+",|F|="+m);
			
			startTime = System.nanoTime();
			S1 = greedySetCover(X,F);
			endTime = System.nanoTime();
			System.out.println("贪心时间："+(endTime-startTime)*1.0/1000000+"ms\t");
			System.out.println("贪心解大小："+S1.size());
			
			startTime = System.nanoTime();
			S2 = SetCoverLP(X, F);
			endTime = System.nanoTime();
			System.out.println("LP时间："+(endTime-startTime)*1.0/1000000+"ms\t");
			System.out.println("LP解大小："+S2.size());
			System.out.println();
		}
		
	}
	
	/*SetCover贪心算法*/
	private List<Integer> greedySetCover(int X[],Set<Integer> F[]){
		List<Integer> result = new ArrayList<>();
		int left = X.length;
		@SuppressWarnings("unchecked")
		Set<Integer> T[] = new Set[F.length];
		int i,k;
		for(i=0;i<F.length;i++) 
			T[i] = new HashSet<>(F[i]);
		while(left>0) {
			k = 0;
			for(i=0;i<T.length;i++) 
				if(T[i].size()>T[k].size())
					k = i;
			if(T[k].size()==0)
				return null;
			left -= T[k].size();
			result.add(k);
			Set<Integer> tmp = T[k];
			for(i=0;i<T.length;i++) {
				if(i!=k)
					T[i].removeAll(tmp);
			}
			tmp.clear();
		}
		return result;
	}
	
	private int maxFreq(int X[],Set<Integer> F[]) {
		int n = F.length,m = X.length;
		 int count[] = new int[m];
		int maxF = 0;
        for(int i=0;i<m;i++) {
        	for(int j=0;j<n;j++)
        		if(F[j].contains(X[i]))
        			count[i]++;
        	if(maxF<count[i])
        		maxF = count[i];
        }
        return maxF;
	}
	
	private List<Integer> SetCoverLP(int X[],Set<Integer> F[]){
		// Minimize z = x1+x2+...+xn
		//
		// subject to
		// sum{xi| if j in F[i]}>=1  j = 1,2,...,n
		// 0.0 <= xi <= 1   i = 1,2,...,n
		glp_prob lp;
        glp_smcp parm;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int n = F.length,m = X.length;//m rows,n cols
        int ret = 0;
        int maxF = maxFreq(X, F);
        List<Integer> result = new ArrayList<>();
        try {
        	//Create problem
        	lp = GLPK.glp_create_prob();
            //System.out.println("LP Problem created");
            GLPK.glp_set_prob_name(lp, "LPProblem");
            GLPK.glp_set_obj_name(lp, "z");
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MIN);
            
            //Define columns
            GLPK.glp_add_cols(lp, n);//n columns
            for(int i=1;i<=n;i++) {
            	GLPK.glp_set_col_name(lp, i, "x"+(i-1));//set labels
                GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_CV);
                GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_DB, 0, 1);//set range
                GLPK.glp_set_obj_coef(lp, i, 1.);//set objective coef
            }
            
            // Create constraints

            // Allocate memory
            ind = GLPK.new_intArray(n);
            val = GLPK.new_doubleArray(n);
            
            //Create rows
            GLPK.glp_add_rows(lp, m);
            //Set row details
            for(int j=1;j<=m;j++) {
            	GLPK.glp_set_row_name(lp, j, "c"+j);
                GLPK.glp_set_row_bnds(lp, j, GLPKConstants.GLP_LO, 1, 0);
                for(int i=1;i<=n;i++) {
                	GLPK.intArray_setitem(ind, i, i);
                	if(F[i-1].contains(X[j-1]))
                		GLPK.doubleArray_setitem(val, i, 1);
                	else
                		GLPK.doubleArray_setitem(val, i, 0);
                }
                GLPK.glp_set_mat_row(lp, j, n, ind, val);
            }
            
            // Free memory
            //GLPK.delete_intArray(ind);
            //GLPK.delete_doubleArray(val);
            // Solve model
            parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            //ret = GLPK.glp_simplex(lp, parm);
            ret = GLPK.glp_simplex(lp, null);
            //GLPK.glp_intopt(lp, null);
            // Write model to file
            //GLPK.glp_write_lp(lp, null, "CoverSetLP.txt");

            // Retrieve solution
            if (ret == 0) {
//                write_lp_solution(lp);
                for(int i=1;i<=n;i++) {
                	double x = GLPK.glp_get_col_prim(lp, i);
                	if(maxF*x>=1)
                		result.add(i-1);
                }
            } else {
                System.out.println("The problem could not be solved");
            }
        }catch(GlpkException e) {
        	e.printStackTrace();
        }
		return result;
	}
	 
	/*输出可行解*/
	private void write_lp_solution(glp_prob lp) {
        int i;
        int n;
        String name;
        double val;

        name = GLPK.glp_get_obj_name(lp);
        val = GLPK.glp_get_obj_val(lp);
        System.out.print(name);
        System.out.print(" = ");
        System.out.println(val);
        n = GLPK.glp_get_num_cols(lp);
        for (i = 1; i <= n; i++) {
            name = GLPK.glp_get_col_name(lp, i);
            val = GLPK.glp_get_col_prim(lp, i);
            System.out.print(name);
            System.out.print(" = ");
            System.out.println(val);
        }
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CoverSet cs = new CoverSet();
		cs.Test();
	}
	
	

}
