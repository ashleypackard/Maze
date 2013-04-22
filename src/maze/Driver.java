/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package maze;

import java.util.Queue;

public class Driver {
	public static void main(String[] args) {
		
		// create a new graph
		Graph ourGraph = new Graph("vertices.txt");
		
		// populate vertices from text file if available
		if(ourGraph.populateVertices()){
			// print out graph's adjacency list
			System.out.println(ourGraph);
			
			// print out a depth first traversal through graph
			System.out.print("DepthFirstTrav: ");
			Queue<Vertex<Character>> mike = ourGraph.depthFirstTraversal(ourGraph.getOriginVertex());
			while(!mike.isEmpty())
				System.out.print(mike.remove().getLabel() + " ");

			// print out a breadth first traversal through graph
			System.out.println("\nBreadthFirstTrav for shortest path: " + ourGraph.breadthFirstTraversal('A', 'J'));
		}else{
			System.out.println("Could not populate vertices :(");
		}
		
	}
}
