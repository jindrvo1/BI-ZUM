package bi.zum.lab3;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.api.ga.AbstractIndividual;
import cz.cvut.fit.zum.data.Edge;
import cz.cvut.fit.zum.data.StateSpace;
import cz.cvut.fit.zum.util.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Your Name
 */
public class Individual extends AbstractIndividual {

    private double fitness = Double.NaN;
    private AbstractEvolution evolution;
    
    
    // @TODO Declare your genotype
    private ArrayList<Boolean> genotype = new ArrayList<>();
    

    /**
     * Creates a new individual
     * 
     * @param evolution The evolution object
     * @param randomInit <code>true</code> if the individial should be
     * initialized randomly (we do wish to initialize if we copy the individual)
     */
    public Individual(AbstractEvolution evolution, boolean randomInit) {
        this.evolution = evolution;
        
        if (randomInit) {
            for ( int i = 0; i < StateSpace.nodesCount(); i++ ) {
                genotype.add( Math.random() >= 0.5 );
            }
        }
    }

    @Override
    public boolean isNodeSelected(int j) {      
        return this.genotype.get(j);
    }

    /**
     * Evaluate the value of the fitness function for the individual. After
     * the fitness is computed, the <code>getFitness</code> may be called
     * repeatedly, saving computation time.
     */
    @Override
    public void computeFitness() {      
        this.repair();
        
        int numberOfNotSelected = 0;
        
        for ( int i = 0; i < StateSpace.nodesCount(); i++ ){
            if ( !isNodeSelected(i) ) {
                numberOfNotSelected++;
            }
        }
        
        this.fitness = numberOfNotSelected;
    }

    /**
     * Only return the computed fitness value
     *
     * @return value of fitness fucntion
     */
    @Override
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Does random changes in the individual's genotype, taking mutation
     * probability into account.
     * 
     * @param mutationRate Probability of a bit being inverted, i.e. a node
     * being added to/removed from the vertex cover.
     */
    @Override
    public void mutate(double mutationRate) {
        for ( int i = 0; i < StateSpace.nodesCount(); i++ ) {
            if ( Math.random() < mutationRate ) {
                this.genotype.set(i, !this.genotype.get(i));
            }
        }
    }
    
    /**
     * Crosses the current individual over with other individual given as a
     * parameter, yielding a pair of offsprings.
     * 
     * @param other The other individual to be crossed over with
     * @return A couple of offspring individuals
     */
    @Override
    public Pair crossover(AbstractIndividual other) {
        Pair<Individual,Individual> result = new Pair();
        
        Random r = new Random();
        int numberOfChanges = r.nextInt(StateSpace.nodesCount());
        HashSet<Integer> changePoints = new HashSet<>();
        
        while ( changePoints.size() != numberOfChanges ) {
            changePoints.add(r.nextInt());
        }
        
        Boolean changePoint = true;
        
        Individual a = this.deepCopy();
        Individual b = this.deepCopy();
        ArrayList<Boolean> genotypeA = new ArrayList<>();
        ArrayList<Boolean> genotypeB = new ArrayList<>();
        Individual individualOther = (Individual) other;
        
        for ( int i = 0; i < StateSpace.nodesCount(); i++ ) {
            if ( changePoints.contains(i) ) {
                changePoint = !changePoint;
            }
            
            if ( changePoint ) {
                genotypeA.add(this.genotype.get(i));
                genotypeB.add(individualOther.genotype.get(i));
            } else {
                genotypeA.add(individualOther.genotype.get(i));
                genotypeB.add(this.genotype.get(i));
            }
        }
        
        a.genotype = genotypeA;
        b.genotype = genotypeB;
        
        result.a = a;
        result.b = b;
        
        return result;
    }
    
    /**
    * Repairs the genotype to make it valid, i.e. ensures all the edges
    * are in the vertex cover.
    */
    private void repair() {
        /* We iterate over all the edges */
        for(Edge e : StateSpace.getEdges()) {
            if ( !( isNodeSelected(e.getFromId()) || isNodeSelected(e.getToId()) ) ) {
                if ( Math.random() < 0.5 ) {
                    this.genotype.set(e.getFromId(), true);
                } else {
                    this.genotype.set(e.getToId(), true);
                }
            }
        }
    }
    
    /**
     * When you are changing an individual (eg. at crossover) you probably don't
     * want to affect the old one (you don't want to destruct it). So you have
     * to implement "deep copy" of this object.
     *
     * @return identical individual
     */
    @Override
    public Individual deepCopy() {
        Individual newOne = new Individual(evolution, false);

        // TODO: at least you should copy your representation of search-space state

        // for primitive types int, double, ...
        // newOne.val = this.val;

        // for objects (String, ...)
        // for your own objects you have to implement clone (override original inherited from Objcet)
        // newOne.infoObj = thi.infoObj.clone();

        // for arrays and collections (ArrayList, int[], Node[]...)
        /*
         // new array of the same length
         newOne.pole = new MyObjects[this.pole.length];		
         // clone all items
         for (int i = 0; i < this.pole.length; i++) {
         newOne.pole[i] = this.pole[i].clone(); // object
         // in case of array of primitive types - direct assign
         //newOne.pole[i] = this.pole[i]; 
         }
         // for collections -> make new instance and clone in for/foreach cycle all members from old to new
         */
        
        newOne.genotype = new ArrayList(this.genotype);               
        newOne.fitness = this.fitness;
               
        return newOne;
    }

    /**
     * Return a string representation of the individual.
     *
     * @return The string representing this object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        
        /* TODO: implement own string representation, such as a comma-separated
         * list of indices of nodes in the vertex cover
         */
        
        
        sb.append(super.toString());

        return sb.toString();
    }    
    
}
