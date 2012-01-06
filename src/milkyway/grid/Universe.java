/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.grid;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import milkyway.*;

/**
 * A universe for use in a grid based Universe.
 * It is runnable to allow it to calculate in its own thread, separate from
 * the drawing thread.
 * 
 * @author james
 */
public class Universe extends BaseUniverse {
    
    protected List<Cluster> clusters;
    protected boolean done = false;
    
    public Universe(double g, double timeStep){
        super(g, timeStep);
    }
    
    public List<Cluster> getClusters(){
        return clusters;
    }
    
    @Override
    public void run(){
        
        // Using 8 clusters
        clusters = new ArrayList<>(16); // Make initial capacity large enough that array never needs resize
        for(int i = 8; i > 0; i--){
            clusters.add(new Cluster(this)); // Create clusters
        }
        
        // Run loop
        while(!done){
        
            //assign bodies
            for(BaseBody bbody : bodies){
                MassiveBody body = (MassiveBody)bbody;
                int position = 
                        body.getX0() > 0 ? 0 : 0b001 +
                        body.getX1() > 0 ? 0 : 0b010 +
                        body.getX2() > 0 ? 0 : 0b100;
                clusters.get(position).addBody(body);
            }
            
            /*
             * barrier size = number of clusters + 1 (This thread) to keep things synchronized
             */
            CyclicBarrier barrier = new CyclicBarrier(clusters.size() + 1);
            // Calculate positions
            for(Cluster cluster : clusters){
                /* 
                 * each cluster runs once in its own thread.
                 * A compromise between threading and itteration.
                 */
                cluster.setLatch(barrier);
                (new Thread(cluster)).start(); 
            }
            // wait for each cluster to run.
            try {
                barrier.await();
            } catch ( InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(Universe.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // reset each cluster
            for(Cluster cluster : clusters){
                cluster.clear();
            }
        }
        for(Cluster cluster : clusters){
            // TODO stop all clusters here.
        }
        
    }
    
    public void done(){
        done = true;
    }
}
