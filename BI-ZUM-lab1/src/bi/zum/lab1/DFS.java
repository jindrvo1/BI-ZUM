package bi.zum.lab1;

import cz.cvut.fit.zum.api.AbstractAlgorithm;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.UninformedSearch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.openide.util.lookup.ServiceProvider;
import bi.zum.lab1.NodeWrapper;

/**
 * Depth-first search
 *
 * @see http://en.wikipedia.org/wiki/Depth-first_search
 */
@ServiceProvider(service = AbstractAlgorithm.class, position = 10)
public class DFS extends AbstractAlgorithm implements UninformedSearch {

    private Stack<NodeWrapper> opened;
    private HashSet<NodeWrapper> closed;
    private NodeWrapper prev;
    private List<Node> path;

    @Override
    public String getName() {
        return "DFS";
    }

    @Override
    public List<Node> findPath(Node startNode) {
        opened = new Stack<NodeWrapper>();
        closed = new HashSet<NodeWrapper>();
        NodeWrapper startNodeWrapper = new NodeWrapper(startNode, null);
        NodeWrapper current;
        path = null;
        
        opened.add(startNodeWrapper);
        
        while (!opened.isEmpty()) {
            current = opened.pop();

            if (current.isTarget()) {
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
