package search;

import java.util.ArrayList;

import GUI.Board;

public class Node implements Comparable<Node>{
	public int  h_cost,s_cost;
	public double g_cost;
	public String type;
	public int cord;
	public Node pre;
	ArrayList<Node> Nei = new ArrayList<Node>();
	public Node(int cord){
		g_cost = 0;
		h_cost =0;
		s_cost = 0;
		type  = "";
		this.cord=cord;
	}
	public Node(String type, int cord) {
		this.cord=cord;
		this.type = type;
	}
	public void set_cost (int s_cost,double g_cost){
		this.s_cost=s_cost;
		this.g_cost=g_cost;
	}
	
	public int compareTo(Node o) {
		if(this.g_cost+this.h_cost < o.g_cost+o.h_cost) {
			return -1;
		}else if(this.g_cost+this.h_cost > o.g_cost+o.h_cost) {
			return 1;
		}else {
			return 0;
		}
	}
	public void findNei (Board board){
		int r = cord / (board.s+1);
		int c = cord%(board.s+1);
		if (c>0 && board.array[r][c-1].type!="b") {
			Nei.add(board.array[r][c-1]);
		}
		if(c<board.s  && board.array[r][c+1].type!="b" ) {
			Nei.add(board.array[r][c+1]);
		}
		if(r<board.s && board.array[r+1][c].type!="b") {
			Nei.add(board.array[r+1][c]);
		}
		if(r>0 && board.array[r-1][c].type!="b") {
			Nei.add(board.array[r-1][c]);
		}
	}
}
