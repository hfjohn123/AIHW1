package GUI;
import java.util.*;

public class Board {
	public int s = 0;
	public int start=0;
	public int goal = 0;
	public String array[][];
	
	Board (int size){
		s = size-1;
		array=new String[size][size];
		for(int r=0;r<size;r++) {
			for(int c=0;c<size;c++) {
				array[r][c]="";
			}
		}
	}	
	Board (String[][] array,int s,int start,int goal){
		this.s=s;
		this.array=array;
		this.start=start;
		this.goal=goal;
	}	
	public static Board init(Board board) {
		Stack<Integer> visited = new Stack<Integer>(); 
		int r_num = (int)(Math.random() * (board.s+1));
		int c_num = (int)(Math.random() * (board.s+1));
		board.array[r_num][c_num]=" ";
		visited.push(r_num*(board.s+1)+c_num);
		while(!visited.empty()) {
			int rc=visited.pop();
			int r = rc/(board.s+1);
			int c = rc%(board.s+1);
			if(c<board.s  && board.array[r][c+1]=="" ) {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r][c+1]="b";
				}else {
					board.array[r][c+1]=" ";
					visited.push(rc+1);
				}	
			}else if(c>0  && board.array[r][c-1]==""){
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r][c-1]="b";
				}else {
					board.array[r][c-1]=" ";
					visited.push(rc-1);
				}
			}else if(r<board.s && board.array[r+1][c]=="") {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r+1][c]="b";
				}else {
					board.array[r+1][c]=" ";
					visited.push(rc+1+board.s);
				}
			}else if(r>0 && board.array[r-1][c]=="") {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r-1][c]="b";
				}else {
					board.array[r-1][c]=" ";
					visited.push(rc-1-board.s);
				}
			}
		}
		do{
			r_num = (int)(Math.random() * (board.s+1));
			c_num = (int)(Math.random() * (board.s+1));
		}while(board.array[r_num][c_num].equals("b"));
		board.array[r_num][c_num]="S";
		board.start=r_num*(board.s+1)+c_num;
		do{
			r_num = (int)(Math.random() * (board.s+1));
			c_num = (int)(Math.random() * (board.s+1));
		}while(board.array[r_num][c_num].equals("b"));
		if(board.array[r_num][c_num].equals("S")) {
			board.array[r_num][c_num]="SG";
			board.start=r_num*(board.s+1)+c_num;
			board.goal=r_num*(board.s+1)+c_num;
		}else {
			board.array[r_num][c_num]="G";
			board.goal=r_num*(board.s+1)+c_num;
		}
		return board;
	}	 
} 
