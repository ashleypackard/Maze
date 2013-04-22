/**
 * 
 * @author Mike Kucharski & Ashley Packard
 * 
 */

package maze;

public interface VertexInterface <T> {
	// mark as visited
	public void visit();
	// mark as unvisited
	public void unVisit();
	// test if visited
	public boolean isVisited();
	// return label data
	public T getLabel();
	// return predecessor data
	public T getPredecessor();
	// set predecessor data
	public void setPredecessor(T label);
	// returns true if has neighbors
	public boolean hasNeighbors();
}
