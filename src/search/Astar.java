package search;

import java.util.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JOptionPane;

import GUI.Board;
public class Astar {
	private static void ComputePath (Node goal, Node start, PriorityQueue<Node> Open,Stack<Node> Close,Board board) {
		while (Open.peek()!=null&&goal.g_cost> Open.peek().g_cost+Open.peek().h_cost) {
			Node tem  = Open.remove();
			board.space++;
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
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable trans = new StringSelection(board.counter+"\t"+board.space+"\t"+board.length);
				clipboard.setContents(trans,null);
				System.out.println("Routes_planed: "+board.counter+" Cells_Expended: "+board.space+" Route_length: "+board.length);

				return board;
			}
			do {
				r_s = board.start/(board.s+1);
				c_s = board.start%(board.s+1);
				Node temp = board.array[r_g][c_g];
				while (temp.pre != board.array[r_s][c_s]) {
					if(!temp.type.endsWith(".")) {
					temp.type=temp.type+".";
					}
					temp = temp.pre;
				}
				if (temp.type.startsWith("b")) {
					board.blocked(temp);
					return board;
				}else {
					temp.vis_findNei(board);
					board.start = temp.cord;
					temp.type = "r";
					board.length ++;
				}
			}while(board.start != board.goal);
		}
		JOptionPane.showMessageDialog(null,"You got it!");
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable trans = new StringSelection(board.counter+"\t"+board.space+"\t"+board.length);
		clipboard.setContents(trans,null);
		System.out.println("Routes_planed: "+board.counter+" Cells_Expended: "+board.space+" Route_length: "+board.length);
		return board;
	}
	public static Board BAStar(Board board) {
		while (board.start != board.goal) {
			board.find_bh();
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
				System.out.println("Routes_planed: "+board.counter+" Cells_Expended: "+board.space+" Route_length: "+board.length);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable trans = new StringSelection(board.counter+"\t"+board.space+"\t"+board.length);
				clipboard.setContents(trans,null);
				return board;
			}
			do {
				r_s = board.start/(board.s+1);
				c_s = board.start%(board.s+1);
				Node temp = board.array[r_s][c_s];
				temp = temp.pre;
				if(temp.type.startsWith("b")) {
					board.blocked(temp);
					while(temp!=null) {
						if(!temp.type.endsWith(".")) {
							temp.type=temp.type+".";
							}
							temp = temp.pre;
					}
					return board;
				}else {
					temp.vis_findNei(board);
					board.start = temp.cord;
					temp.type = "r";
					board.length ++;
				}
			}while(board.start != board.goal);
		}
		JOptionPane.showMessageDialog(null,"You got it!");
		System.out.println("Routes_planed: "+board.counter+" Cells_Expended: "+board.space+" Route_length: "+board.length);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable trans = new StringSelection(board.counter+"\t"+board.space+"\t"+board.length);
		clipboard.setContents(trans,null);
		return board;
	}
	public static Board AAStar(Board board,Stack<Node> Close) {
		while (board.start != board.goal) {
			board.counter++;
			int r_s = board.start/(board.s+1);
			int c_s = board.start%(board.s+1);
			int r_g = board.goal/(board.s+1);
			int c_g = board.goal%(board.s+1);
			while(!(Close.isEmpty())) {
				Node x = Close.pop();
				x.h_cost = (int) (board.array[r_g][c_g].g_cost-x.g_cost);
			}
			board.array[r_s][c_s].set_cost(board.counter,0);
			board.array[r_g][c_g].set_cost(board.counter, Double.POSITIVE_INFINITY);
			PriorityQueue<Node> Open = new PriorityQueue<Node>();
			Open.add(board.array[r_s][c_s]);
			ComputePath(board.array[r_g][c_g], board.array[r_s][c_s], Open,Close,board);
			if(Open.isEmpty()) {
				JOptionPane.showMessageDialog(null,"No path found!");
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable trans = new StringSelection(board.counter+"\t"+board.space+"\t"+board.length);
				clipboard.setContents(trans,null);
				System.out.println("Routes_planed: "+board.counter+" Cells_Expended: "+board.space+" Route_length: "+board.length);
				return board;
			}
			do {
				r_s = board.start/(board.s+1);
				c_s = board.start%(board.s+1);
				Node temp = board.array[r_g][c_g];
			while (temp.pre != board.array[r_s][c_s]) {
				if(!temp.type.endsWith(".")) {
				temp.type=temp.type+".";
				}
				temp = temp.pre;
			}
			if (temp.type.startsWith("b")) {
				board.blocked(temp);
				return board;
			}else {
				temp.vis_findNei(board);
				board.start = temp.cord;
				temp.type = "r";
				board.length ++;
			}
			}while(board.start!=board.goal);
		}
		JOptionPane.showMessageDialog(null,"You got it!");
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable trans = new StringSelection(board.counter+"\t"+board.space+"\t"+board.length);
		clipboard.setContents(trans,null);
		System.out.println("Routes_planed: "+board.counter+" Cells_Expended: "+board.space+" Route_length: "+board.length);
		return board;
	}
}
