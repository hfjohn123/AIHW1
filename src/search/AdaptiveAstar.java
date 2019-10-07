package search;

import java.util.ArrayList;
import java.util.PriorityQueue;

import GUI.Board;

public class AdaptiveAstar {
	private void re_Init(Node x, ArrayList<Integer> chan,int counter) {
		if (x.s_cost != counter && x.s_cost!=0) {
			if (x.g_cost+x.h_cost<p_cost[x.s_cost-1]) {
				x.h_cost = (int) (p_cost[x.s_cost-1]-x.g_cost);
			}
			x.h_cost = x.h_cost-chan.get(counter-1)-chan.get(x.s_cost-1);
		}
	}
	private void ComputePath() {
		
	}
	public void Main(Board board) {
		int counter = 1;
		ArrayList<Integer> chan = new ArrayList<Integer>();
		chan.add(0);
		ArrayList<Double> p = new ArrayList<Double>();
		while (board.start!=board.goal) {
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			re_Init(board.array[r_s][c_s],chan,counter);
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			re_Init(board.array[r_g][c_g],chan,counter);
			board.array[r_s][c_s].g_cost = 0;
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_s][c_s]);
			ComputePath();
			if (Open.isEmpty()) {
				p.add(Double.POSITIVE_INFINITY);
			}else {
				p.add(board.array[r_g][c_g].g_cost);
			}
			
			chan.add(chan.get(counter));
			counter++;
		}
	}
}
