package search;

import java.util.*;

import javax.swing.JOptionPane;

import GUI.Board;
public class Astar {
	private static void ComputePath (Node goal, Node start, PriorityQueue<Node> Open,Stack<Node> Close,Board board) {
		while (Open.peek()!=null&&goal.g_cost> Open.peek().g_cost+Open.peek().h_cost) {
			Node tem  = Open.remove();
			if(Close !=null) {
				Close.add(tem);
			}
			for (Node x : tem.Nei) {
				if (x.s_cost < board.counter) {
					x.set_cost(board.counter, Double.POSITIVE_INFINITY);
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
	public static Board main(Board board) {
		while (board.start != board.goal) {
			board.counter++;
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			board.array[r_s][c_s].set_cost(board.counter, 0);
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			board.array[r_g][c_g].set_cost(board.counter, Double.POSITIVE_INFINITY);
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_s][c_s]);
			ComputePath(board.array[r_g][c_g], board.array[r_s][c_s], Open,null,board);
			if(Open.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No path found!");
				return board;
			}
			do {
				r_s = board.start/(board.s+1);
				c_s = board.start%(board.s+1);
				Node temp = board.array[r_g][c_g];
				while (temp.pre != board.array[r_s][c_s]) {
					temp = temp.pre;
				}
				if (temp.type.equals("b")) {
					temp.vis_findNei(board);
					return board;
				}else {
					temp.vis_findNei(board);
					board.start = temp.cord;
					temp.type = "r";
				}
			}while(board.start != board.goal);
		}
		JOptionPane.showMessageDialog(null,"You got it!");
		return board;
	}
	public static Board BAStar(Board board) {
		while (board.start != board.goal) {
			board.counter++;
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			board.array[r_g][c_g].set_cost(board.counter, 0);
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			board.array[r_s][c_s].set_cost(board.counter, Double.POSITIVE_INFINITY);
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_g][c_g]);
			ComputePath(board.array[r_s][c_s], board.array[r_g][c_g],Open,null,board);
			if(Open.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No path found!");
				return board;
			}
			do {
				r_s = board.start/(board.s+1);
				c_s = board.start%(board.s+1);
				Node temp = board.array[r_s][c_s];
				temp = temp.pre;
				if(temp.type.equals("b")) {
					temp.vis_findNei(board);
					return board;
				}else {
					temp.vis_findNei(board);
					board.start = temp.cord;
					temp.type = "r";
				}
			}while(board.start != board.goal);
		}
		JOptionPane.showMessageDialog(null,"You got it!");
		return board;
	}
	public static Board AAStar(Board board,Stack<Node> Close) {
		while (board.start != board.goal) {
			board.counter++;
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			board.array[r_s][c_s].set_cost(board.counter, 0);
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			while(!(Close.isEmpty())) {
				Node x = Close.pop();
				x.h_cost = (int) (board.array[r_g][c_g].g_cost-x.g_cost);
			}
			board.array[r_g][c_g].set_cost(board.counter, Double.POSITIVE_INFINITY);
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_s][c_s]);
			ComputePath(board.array[r_g][c_g], board.array[r_s][c_s], Open,Close,board);
			if(Open.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No path found!");
				return board;
			}
			Node temp = board.array[r_g][c_g];
			while (temp.pre != board.array[r_s][c_s]) {
				temp = temp.pre;
			}
			if (temp.type.equals("b")) {
				temp.vis_findNei(board);
				return board;
			}else {
				temp.vis_findNei(board);
				board.start = temp.cord;
				temp.type = "r";
				return board;
			}
		}
		JOptionPane.showMessageDialog(null,"You got it!");
		return board;

	}
}
