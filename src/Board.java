import java.util.*;

public class Board {
	int s = 0;
	short array[][];
	Board (int size){
		s = size-1;
		array=new short[size][size];
	}	
	public static Board init(Board board) {
		Stack<Integer> visited = new Stack<Integer>(); 
		int r_num = (int)(Math.random() * (board.s+1));
		int c_num = (int)(Math.random() * (board.s+1));
		board.array[r_num][c_num]=1;
		visited.push(r_num*(board.s+1)+c_num);
		while(!visited.empty()) {
			int rc=visited.pop();
			int r = rc/(board.s+1);
			int c = rc%(board.s+1);
			if(c<board.s  && board.array[r][c+1]==0 ) {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r][c+1]=2;
				}else {
					board.array[r][c+1]=1;
					visited.push(rc+1);
				}	
			}else if(c>0  && board.array[r][c-1]==0){
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r][c-1]=2;
				}else {
					board.array[r][c-1]=1;
					visited.push(rc-1);
				}
			}else if(r<board.s && board.array[r+1][c]==0) {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r+1][c]=2;
				}else {
					board.array[r+1][c]=1;
					visited.push(rc+1+board.s);
				}
			}else if(r>0 && board.array[r-1][c]==0) {
				visited.push(rc);
				double ran = Math.random();
				if (ran<0.3) {
					board.array[r-1][c]=2;
				}else {
					board.array[r-1][c]=1;
					visited.push(rc-1-board.s);
				}
			}
		}
		return board;
	}	  
	public static String[][] toString (Board b){
		int size = b.s+1;
		String [][] data = new String[size][size];
		for (int r=0;r<size;r++) {
			for(int c=0;c<size;c++) {
				data[r][c] = String.valueOf(b.array[r][c]);
			}
		}
		return data;
		
	}
} 
