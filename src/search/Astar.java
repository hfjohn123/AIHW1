package search;

import java.util.*;

import javax.swing.JOptionPane;

import GUI.Board;
public class Astar {
	private static void ComputePath (Node goal, Node start, PriorityQueue<Node> Open,Board board,int counter) {
		while (Open.peek()!=null&&goal.g_cost> Open.peek().g_cost+Open.peek().h_cost) {
			Node tem  = Open.remove();
			for (Node x : tem.Nei) {
				if (x.s_cost < counter) {
					x.set_cost(counter, Double.POSITIVE_INFINITY);
				}
				if(x.g_cost>tem.g_cost+1) {
					x.g_cost = tem.g_cost+1;
					x.pre=tem;
					if(Open.contains(x)) {
						Open.remove(x);
					}
				Open.add(x);
				}
			}
		}
		
	}
	public static void main(Board board) {
		int counter = 0;
		if (board.start != board.goal) {
			counter++;
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			board.array[r_s][c_s].set_cost(counter, 0);
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			board.array[r_g][c_g].set_cost(counter, Double.POSITIVE_INFINITY);
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_s][c_s]);
			ComputePath(board.array[r_g][c_g], board.array[r_s][c_s], Open,board,counter);
			if(Open.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No path found!");
				return;
			}
			Node temp = board.array[r_g][c_g];
			while (temp.pre != board.array[r_s][c_s]) {
				temp = temp.pre;
			}
			board.start = temp.cord;
			temp.type = "r";
		}else {
			JOptionPane.showMessageDialog(null,"You got it!");
		}
		return;
	}
	public static void BAStar(Board board) {
		int counter = 0;
		if (board.start != board.goal) {
			counter++;
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			board.array[r_g][c_g].set_cost(counter, 0);
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			board.array[r_s][c_s].set_cost(counter, Double.POSITIVE_INFINITY);
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_g][c_g]);
			ComputePath(board.array[r_s][c_s], board.array[r_g][c_g], Open,board,counter);
			if(Open.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No path found!");
				return;
			}
			Node temp = board.array[r_s][c_s];
			temp = temp.pre;
			board.start = temp.cord;
			temp.type = "r";
		}else{
			JOptionPane.showMessageDialog(null,"You got it!");
		}
		return;

	}
}
