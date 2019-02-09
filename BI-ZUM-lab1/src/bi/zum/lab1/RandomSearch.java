package bi.zum.lab1;

import cz.cvut.fit.zum.api.AbstractAlgorithm;
import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.UninformedSearch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 * WARNINIG: this is very stupid algorithm!!!
 *
 * Should serve only as an example of UninformedSearch usage
 *
 * @author Tomas Barton
 */
@ServiceProvider(service = AbstractAlgorithm.class, position = 100)
public class RandomSearch extends AbstractAlgorithm implements UninformedSearch {

    private LinkedList<NodeWrapper> opened;
    private HashSet<NodeWrapper> closed;
    private NodeWrapper prev;
    private List<Node> path;

    @Override
    public String getName() {
        return "random search";
    }

    @Override
    public List<Node> findPath(Node startNode) {
        opened = new LinkedList<NodeWrapper>();
        closed = new HashSet<NodeWrapper>();
        NodeWrapper startNodeWrapper = new NodeWrapper(startNode, null);
        NodeWrapper current;
        path = null;
        
        opened.add(startNodeWrapper);
        
        while (!opened.isEmpty()) {
            current = random(opened);

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

    /**
     * Select random node from list
     *
     * @param list
     * @return Node
     */
    private NodeWrapper random(List<NodeWrapper> list) {
        int min = 0;
        int max = list.size();

        if (max == 1) {
            return list.remove(0);
        }
        int num = min + (int) (Math.random() * ((max - min)));

        //we want to remove explored nodes
        return list.remove(num);
    }
}
