package search;
import java.util.*;
import GUI.Board;
public class Astar {
		public static Board Foward (Board board) {
			
			return board;
			
		}
		public static void main(Board board) {
			int counter = 0;
			while (board.start != board.goal) {
				counter++;
				Node goal = new Node(board.goal,counter,Integer.MAX_VALUE);
				PriorityQueue<Node> minHeap = new PriorityQueue<Node>();
				Stack<Node> visited = new Stack<Node>(); 
				
			}
		}
}
