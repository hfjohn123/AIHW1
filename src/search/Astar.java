package search;
import java.util.*;

import javax.swing.JOptionPane;

import GUI.Board;
public class Astar {
		public static void ComputePath () {
			
			
		}
		public static Board main(Board board) {
			int counter = 0;
			while (board.start != board.goal) {
				counter++;
				Node start = new Node(board.start,counter,0);
				Node goal = new Node(board.goal,counter,Integer.MAX_VALUE);
				PriorityQueue<Node> Open = new PriorityQueue<Node>();
				Stack<Node> Close = new Stack<Node>(); 
				Open.add(start);
				ComputePath();
				if(Open.isEmpty()) {
					JOptionPane.showMessageDialog(null,"No path found!");
					return board;
				}
				
			}
			JOptionPane.showMessageDialog(null,"Got it!");
		}
}
