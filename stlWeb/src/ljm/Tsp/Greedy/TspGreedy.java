package ljm.Tsp.Greedy;

import com.ljm.getDistance.*;
public class TspGreedy {

	private int cityNum;// 城市数量
	private int[][] distance;// 城市距离矩阵
	private int[] col;// 走过为0，未走过为1
	private int[] row;// 同上
	private int[] path;
	private String [] goals;
	private int distanceCount;
	
	public TspGreedy (String [] points,int [][]distance) {
		goals=points;
		cityNum = goals.length;
		this.distance=new int [cityNum][cityNum];
		this.distance=distance;
	}
	
	private void init() {
		path=new int[cityNum+1];
		col = new int[cityNum];
		col[0] = 0;
		for (int i = 1; i < cityNum; i++)
			col[i] = 1;
		row = new int[cityNum];
		for (int j = 0; j < cityNum; j++)
			row[j] = 1;
	}

	private void solve() {
		int[] temp = new int[cityNum];
		int s = 0;// 计算距离
		int i = 0;// 当前城市
		int j = 0;// 下一个城市
		int n=1;
		path[0]=0;
		// 默认从0开始		
		while (row[i] == 1) {
			// 复制一行
			for (int k = 0; k < cityNum; k++) {
				temp[k] = distance[i][k];
				// System.out.print(temp[k]+" ");
			}
			// System.out.println();
			// 选择下一个节点，要求不是已经走过，并且与i不同
			j = selectmin(temp);
			// 找出下一节点
			row[i] = 0;// 行置0，表示已经选过                 
			col[j] = 0;// 列0，表示已经走过
			path[n]=j;
			// System.out.println(i + "==>" + j);
			// System.out.println(distance[i][j]);
			s = s + distance[i][j];
			i = j;// 当前节点指向下一节点
			n++;
		}
		distanceCount=s;
	}

	public int selectmin(int[] p) {
		int j = 0, m = p[0], k = -1;
		// 寻找第一个可用节点，注意最后一次寻找没有可用节点
		while (col[j] == 0) {
			j++;
			// System.out.print(j+" ");
			if (j >= cityNum) {
				// 没有可用节点，说明已结束，最后一次为 *-->0
				m = p[0];
				return 0;
			} else {
				m = p[j];
			}
		}
		// 从可用节点J开始往后扫描，找出距离最小节点
		for (; j < cityNum; j++) {
			if (col[j] == 1) {
				if (m >= p[j]) {
					m = p[j];
					k = j;
				}
			}
		}
		return k;
	}

	public void printinit() {
		System.out.print("路径为:");
		System.out.print(goals[0]);
		for (int i = 1; i <= cityNum; i++) {
			System.out.print("->"+goals[path[i]]);
		}
		System.out.println("");
		System.out.println("print end....");
	}
	
	public int [] tspTest() {
//		System.out.println("Start....");
//		String [] goals={"华南师范大学（石牌校区）","正佳广场","暨南大学","百脑汇","华南农业大学"};
//		TspGreedy ts = new TspGreedy(goals);
		init();
		solve();
//		printinit();
		return path;
	}
	public int getDiasance() {
		return distanceCount;
	}
}
