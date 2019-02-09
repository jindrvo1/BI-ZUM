package bi.zum.lab1;

import cz.cvut.fit.zum.api.AbstractAlgorithm;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.UninformedSearch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.openide.util.lookup.ServiceProvider;

/**
 * Breadth-first search
 *
 *
 * @see http://en.wikipedia.org/wiki/Breadth-first_search
 *
 * 1 procedure BFS(G,v): 2 create a queue Q 3 enqueue v onto Q 4 mark v 5 while
 * Q is not empty: 6 t ← Q.dequeue() 7 if t is what we are looking for: 8 return
 * t 9 for all edges e in G.adjacentEdges(t) do 12 u ← G.adjacentVertex(t,e) 13
 * if u is not marked: 14 mark u 15 enqueue u onto Q 16 return none
 *
 *
 */
@ServiceProvider(service = AbstractAlgorithm.class, position = 5)
public class BFS extends AbstractAlgorithm implements UninformedSearch {

    private LinkedList<NodeWrapper> opened;
    private HashSet<NodeWrapper> closed;
    private NodeWrapper prev;
    private List<Node> path;

    @Override
    public String getName() {
        return "BFS";
    }

    /**
     * When implementing uninformed search the only way how to find out whether
     * you reached your destination is calling node.isTarget() on each Node you
     * find. When you've checked a Node you should add it to closed list, so
     * that you'll avoid infinite loops in your program.
     *
     * @param startNode starting Node is represented by red color on the
     * @return List of Nodes which represents the shortest path from targetNode
     * (which you have to find first) to startNode
     */
    @Override
    public List<Node> findPath(Node startNode) {
        long startTime, endTime, totalTime;
        startTime = System.currentTimeMillis();
           
        opened = new LinkedList<NodeWrapper>();
        closed = new HashSet<NodeWrapper>();
        NodeWrapper startNodeWrapper = new NodeWrapper(startNode, null);
        NodeWrapper current;
        path = null;
        
        opened.add(startNodeWrapper);
        
        while (!opened.isEmpty()) {
            current = opened.removeFirst();

            if (current.isTarget()) {
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                System.out.println("time = "+totalTime+" ms");
                return buildPath(startNodeWrapper, current);
            }

            for (Node y : current.expand()) {                
                NodeWrapper tmp = new NodeWrapper(y, null);
                if ( !opened.contains( tmp ) && !closed.contains( tmp ) ) {
                    opened.add( new NodeWrapper(y, current) );
                }                               
            }
            closed.add(current);

        }
        
        return path;
    }
    
    private List<Node> buildPath(NodeWrapper start, NodeWrapper target) {
        path = new ArrayList<Node>();
        path.add( target.getNode() );
        NodeWrapper current = target;
        
        while ( current.getPrev() != null ) {
            path.add( current.getNode() );
            current = current.getPrev();
        }
        
        path.add( current.getNode() );
        
        return path;
    }
}