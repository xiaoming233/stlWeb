package ljm.Tsp.Ant;

import com.ljm.getDistance.*;

public class TspAnt {

	private Ant[] ants; // 蚂蚁
	private int antNum; // 蚂蚁数量
	private int cityNum; // 城市数量
	private int MAX_GEN; // 运行代数
	private float[][] pheromone; // 信息素矩阵
	private int[][] distance; // 距离矩阵
	private int bestLength; // 最佳长度
	private int[] bestTour; // 最佳路径

	private String [] result;
	// 三个参数
	private float alpha;
	private float beta;
	private float rho;
	/**
	 * @param n 城市数量
	 * @param m 蚂蚁数量
	 * @param g 迭代步数
	 * @param a 控制信息素浓度重要程度系数
	 * @param b 控制路径开销重要程度系数
	 * @param r （1-r）为信息素挥发系数
	 */
	public  TspAnt(String []result,int [][]distance) {
		this.result=result;
		cityNum = result.length;
		this.distance=new int [cityNum ][cityNum ];
		this.distance=distance;
		
	}

	private void init( int m, int g, float a, float b, float r) {
		
		antNum = m;
		ants = new Ant[antNum];
		MAX_GEN = g;
		alpha = a;
		beta = b;
		rho = r;

		
		// 初始化信息素矩阵
		pheromone = new float[cityNum][cityNum];
		for (int i = 0; i < cityNum; i++) {
			for (int j = 0; j < cityNum; j++) {
				pheromone[i][j] = 0.1f; // 初始化为0.1
			}
		}
		bestLength = Integer.MAX_VALUE;
		bestTour = new int[cityNum + 1];

		for (int i = 0; i < antNum; i++) {
			ants[i] = new Ant(cityNum);
			ants[i].init(distance, alpha, beta);
		}
	}

	public void solve() {
		// 迭代MAX_GEN次
		for (int g = 0; g < MAX_GEN; g++) {
			// antNum只蚂蚁
			for (int i = 0; i < antNum; i++) {
				// i这只蚂蚁走cityNum步，完整一个TSP
				for (int j = 1; j < cityNum; j++) {
					ants[i].selectNextCity(pheromone);
				}
				// 把这只蚂蚁起始城市加入其禁忌表中
				// 禁忌表最终形式：起始城市,城市1,城市2...城市n,起始城市
				ants[i].getTabu().add(ants[i].getFirstCity());
				// 查看这只蚂蚁行走路径距离是否比当前距离优秀
				if (ants[i].getTourLength() < bestLength) {
					// 比当前优秀则拷贝优秀TSP路径
					bestLength = ants[i].getTourLength();
					for (int k = 0; k < cityNum+1; k++) {
						bestTour[k] = ants[i].getTabu().get(k).intValue();
					}
				}
				// 更新这只蚂蚁的信息数变化矩阵，对称矩阵
				for (int j = 0; j < cityNum; j++) {
					ants[i].getDelta()[ants[i].getTabu().get(j).intValue()][ants[i].getTabu().get(j + 1)
							.intValue()] = (float) (1. / ants[i].getTourLength());
					ants[i].getDelta()[ants[i].getTabu().get(j + 1).intValue()][ants[i].getTabu().get(j)
							.intValue()] = (float) (1. / ants[i].getTourLength());
				}
			}
			// 更新信息素
			updatePheromone();
			// 重新初始化蚂蚁
			for (int i = 0; i < antNum; i++) {
				ants[i].init(distance, alpha, beta);
			}
		}
		//System.out.println("蚁群算法总距离为:" + bestLength);
		// 打印最佳结果
//		printOptimal();
	}

	// 更新信息素
	private void updatePheromone() {
		// 信息素挥发
		for (int i = 0; i < cityNum; i++)
			for (int j = 0; j < cityNum; j++)
				pheromone[i][j] = pheromone[i][j] * (1 - rho);
		// 信息素更新
		for (int i = 0; i < cityNum; i++) {
			for (int j = 0; j < cityNum; j++) {
				for (int k = 0; k < antNum; k++) {
					pheromone[i][j] += ants[k].getDelta()[i][j];
				}
			}
		}
	}

	private void printOptimal() {
		System.out.println("总距离为:" + bestLength);
		System.out.print("路径为: ");
		for (int i = 0; i < cityNum ; i++) {
			System.out.print(result[bestTour[i]]+"->");
		}
		System.out.println(result[bestTour[cityNum]]);
	}
	public  int [] tspTest() {
		// TODO Auto-generated method stub  
		//init( 10, 100, 1.f, 5.f, 0.5f);
		init( 5, 50, 1.2f, 5.f, 0.6f); 
		solve();
		return bestTour;
	}
	public int getDiasance() {
		return bestLength;
	}
	
}
