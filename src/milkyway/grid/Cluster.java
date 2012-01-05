/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.grid;


import java.util.*;
import java.util.logging.*;
import java.util.concurrent.*;
import milkyway.*;

/**
 * A Cluster is a sub section of a universe.
 * 
 * It contains references to the Bodies inside it, and to the universe that
 * contains it.
 * <br />
 * Cluster implements the runnable interface so that it may be run in its own thread.
 * When it is run, each body in it is updated (via the update-step methods) with respect
 * to the other bodies in the cluster, and the other clusters in the universe.
 * This greatly reduces the particle-pair calculations, and there is no need to update-step
 * a cluster as its centre of mass does not change during the update process.
 * 
 * @author james
 */
public class Cluster implements Runnable{
    
    protected Universe universe;
    protected List<MassiveBody> bodies = new ArrayList<>();
    
    // The centre of mass location and total mass of the cluster
    protected double mass, x0, x1, x2;
    
    /**
     * Create a new cluster with a reference to the universe that contains it.
     * It is the job of the universe to allocate Clusters and ensure that they
     * do not overlap.
     * @param universe The universe containing the cluster.
     */
    public Cluster(Universe universe){
        this.universe = universe;
    }
    
    /**
     * Add a body reference to this cluster.
     * @param body The body to add to this cluster.
     */
    public void addBody(MassiveBody body){
        
        // Add body to list
        bodies.add(body);
        body.setCluster(this);
        // Update centre of mass info
        double oldMass = mass;
        double bMass = body.getMass();
        mass += bMass;
        x0 = ((oldMass*x0) + (bMass*body.getX0()))/mass;
        x1 = ((oldMass*x1) + (bMass*body.getX1()))/mass;
        x2 = ((oldMass*x2) + (bMass*body.getX2()))/mass;
    }
    
    /**
     * Clear the memory of this cluster.
     */
    public void clear(){
        for(MassiveBody body : bodies){
            body.setCluster(null);
        }
        bodies.clear();
        mass = 0;
        x0 = 0;
        x1 = 0;
        x2 = 0;
    }
    
    /**
     * Retrieve the list of bodies in this cluster.
     * @return The list of bodies in the cluster.
     */
    public List<MassiveBody> getBodies(){
        return bodies;
    }
    
    /**
     * Update all of the particles in this cluster.
     */
    @Override
    public void run(){
        for(BaseBody body : bodies){
            body.update();
        }
        for(BaseBody body : bodies){
            body.step();
        }
        
        try {
            barrier.await();
        } catch ( InterruptedException | BrokenBarrierException ex ) {
            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * Thread Synchronization Info.
     */
    protected CyclicBarrier barrier = null;
    public void setLatch(CyclicBarrier b){
        barrier = b;
    }
}
