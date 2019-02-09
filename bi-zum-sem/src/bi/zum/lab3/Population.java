package bi.zum.lab3;

import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.api.ga.AbstractIndividual;
import cz.cvut.fit.zum.api.ga.AbstractPopulation;
import cz.cvut.fit.zum.data.StateSpace;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Your name
 */
public class Population extends AbstractPopulation {

    public Population(AbstractEvolution evolution, int size) {
        individuals = new Individual[size];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(evolution, true);
            individuals[i].computeFitness();
        }
    }

    /**
     * Method to select individuals from population
     *
     * @param count The number of individuals to be selected
     * @return List of selected individuals
     */
    public List<AbstractIndividual> selectIndividuals(int count) {
        ArrayList<AbstractIndividual> selected = new ArrayList<>();
        
        int sum = 0;
        
        for ( AbstractIndividual ind : this.getIndividuals() ) {
            sum += ind.getFitness();
        }
        
        Random r = new Random();
        
        for ( int i = 0; i < count; i++ ) {
            int sumTmp = r.nextInt(sum);
            for ( int j = 0; j < this.getIndividuals().length; j++ ) {
                sumTmp -= this.getIndividual(j).getFitness();
                
                if ( sumTmp <= 0 ) {
                    selected.add(this.getIndividual(j));
                    break;
                }
            }
        }

        return selected;
    }
}
