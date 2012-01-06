package milkyway;

import java.util.*;

/**
 * An abstract Base for all package Universe objects to base on.
 * 
 * This class specifies some requirements of a Universe (e.g. G) and some
 * global numbers (DIM).
 * 
 * @author james
 */
public abstract class BaseUniverse implements Runnable{
    
    // Global number of Dimensions of the universe
    public static final int DIM = 3;
    
    // value of G;
    public final double G; // 6.67e-11;
    
    // Ammount of time to step per cycle
    public final double Step;
    
    // All of the bodies in this universe
    protected List<BaseBody> bodies = new ArrayList<>();
    
    /**
     * Create a new Universe.
     * @param G the value of the universal gravitational constant in this universe.
     * @param step The amount of time (in seconds) each time round the update loop.
     */
    public BaseUniverse(double G, double step){
        this.Step = step;
        this.G = G;
    }
    
    /**
     * Add a body to the list of bodies held by this universe.
     * @param body The body to add to the universe.
     */
    public void addBody(BaseBody body){
        bodies.add(body);
    }
    
    /**
     * Retrieve the list of bodies from the universe.
     *
     * @return The list of bodies in this universe.
     */
    public List<BaseBody> getBodies(){
        return bodies;
    }
    
    /**
     * This method is called by the draw thread when it is time to exit
     * It should free up resources and exit gracefully.
     */
    public abstract void done();
    
}
