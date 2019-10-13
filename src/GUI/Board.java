package GUI;
import java.util.*;

import search.Node;

public class Board {
	public int s = 0;
	public int start=0;
	public int goal = 0;
	public Node array[][];
	public int counter =0;
	public int space = 0;
	public int length = 0;
	Board (int size){
		s = size-1;
		array=new Node[size][size];
		for(int r=0;r<size;r++) {
			for(int c=0;c<size;c++) {
				array[r][c]=new Node(r*size+c);
			}
		}
	}	
	Board (String[][] str,int s,int start,int goal){
		this.s=s;
		this.start=start;
		this.goal=goal;
		array = new Node[s+1][s+1];
		for(int r=0;r<s+1;r++) {
			for(int c=0;c<s+1;c++) {
				array[r][c]=new Node(str[r][c],r*(s+1)+c);
			}
		}
	}	
	public void init() {
		Stack<Integer> visited = new Stack<Integer>(); 
		int r_num = (int)(Math.random() * (s+1));
		int c_num = (int)(Math.random() * (s+1));
		array[r_num][c_num].type = " ";
		visited.push(r_num*(s+1)+c_num);
		while(!visited.empty()) {
			int rc=visited.pop();
			int r = rc/(s+1);
			int c = rc%(s+1);
			if(c<s  && array[r][c+1].type=="" ) {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					array[r][c+1].type="b";
				}else {
					array[r][c+1].type=" ";
					visited.push(rc+1);
				}	
			}else if(c>0  && array[r][c-1].type==""){
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					array[r][c-1].type="b";
				}else {
					array[r][c-1].type=" ";
					visited.push(rc-1);
				}
			}else if(r<s && array[r+1][c].type=="") {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					array[r+1][c].type="b";
				}else {
					array[r+1][c].type=" ";
					visited.push(rc+1+s);
				}
			}else if(r>0 && array[r-1][c].type=="") {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					array[r-1][c].type="b";
				}else {
					array[r-1][c].type=" ";
					visited.push(rc-1-s);
				}
			}
		}
		do{
			r_num = (int)(Math.random() * (s+1));
			c_num = (int)(Math.random() * (s+1));
		}while(array[r_num][c_num].type.equals("b"));
		start=r_num*(s+1)+c_num;
		array[r_num][c_num].type = "r";
		do{
			r_num = (int)(Math.random() * (s+1));
			c_num = (int)(Math.random() * (s+1));
		}while(array[r_num][c_num].type.equals("b"));
		goal=r_num*(s+1)+c_num;
		if (goal==start) {
			array[r_num][c_num].type = "SG";
		}else {
			array[r_num][c_num].type = "G";
		}
	
	}	 
	public void find_h() {
		int r_num = goal/(s+1);
		int c_num = goal %(s+1);
		for (int r=0;r<s+1;r++) {
			for (int c=0;c<s+1;c++) {
				array[r][c].h_cost = Math.abs(r-r_num)+Math.abs(c-c_num);
				array[r][c].findNei(this);
			}
		}
		int r_s = start/(s+1);
		int c_s = start%(s+1);
		array[r_s][c_s].vis_findNei(this);
	}
	public void find_bh() {
		int r_num = start/(s+1);
		int c_num = start %(s+1);
		for (int r=0;r<s+1;r++) {
			for (int c=0;c<s+1;c++) {
				array[r][c].h_cost = Math.abs(r-r_num)+Math.abs(c-c_num);
				array[r][c].findNei(this);
			}
		}
		array[r_num][c_num].vis_findNei(this);
	}
	public void blocked(Node x) {
		x.Nei = new ArrayList<Node>();
		int r = x.cord /(s+1);
		int c = x.cord%(s+1);
		if(c>0) {
			array[r][c-1].Nei.remove(x);
		}
		if(c<s) {
			array[r][c+1].Nei.remove(x);
		}
		if(r<s) {
			array[r+1][c].Nei.remove(x);
		}
		if(r>0) {
			array[r-1][c].Nei.remove(x);
		}
	}
	public static String[][] toString(Board board){
		String[][] res = new String[board.s+1][board.s+1];
		for (int r=0;r<board.s+1;r++) {
			for(int c=0;c<board.s+1;c++) {
				res[r][c]=board.array[r][c].type;
			}
		}
		return res;
	}
	public static Board clone(Board board) {
		Board res = new Board(Board.toString(board),board.s,board.start,board.goal);
		return res;
	}
} 
