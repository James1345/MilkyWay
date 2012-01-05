/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.grid;

import milkyway.BaseBody;
import milkyway.BaseUniverse;
import static java.lang.Math.*;

/**
 * A massive body in a grid gravity system.
 * 
 * As well as the normal BaseBody properties, this Body also contains a reference
 * to the Cluster it is currently assigned to.
 * 
 * @author james
 */
public class MassiveBody extends BaseBody{
    
    protected Cluster cluster;
    
    public MassiveBody
        (double mass, double radius, 
        double x0, double x1, double x2,
        double v0, double v1, double v2, BaseUniverse universe){
        
        super(mass, radius, x0, x1, x2, v0, v1, v2, universe);
    }
    
    /**
     * Set the cluster to which this Body belongs
     * @param c the cluster to set this to.
     */
    public void setCluster(Cluster c){
        this.cluster = c;
    }
    
    /**
     * Update the current acceleration based on the bodies in this cluster
     * and the clusters in the universe
     */
    @Override
    public void update(){
        
        for(MassiveBody body : cluster.bodies){
            if(body==this) continue;
            //calculate gravity
            
            //square of the distance between the two bodies.
            double r2 = 0;
            // direction vector from this to body.
            double d0,d1,d2;
            
            // fill values
            // Processr intensive - Written for efficiency over readability.
            // Direction not normalised - see below.
            r2 += pow((d0 = body.x0 - x0), 2);
            r2 += pow((d1 = body.x1 - x1), 2);
            r2 += pow((d2 = body.x2 - x2), 2);
            
            // acceleration magnitude
            // Using F=ma and F = GMm/r^2 to calculate a
            /*
             * In the next step, this value will be multiplied by the unit
             * vector of direction towards the body in question.
             * Rather than calculate the unit vector directly (which requires
             * each value in the direction vector to be divided by the magnitude
             * of the vector (which is the root of the already calculated r2)); 
             * we simply divide the a variable by a further factor of r.
             */
            double a = (universe.G * body.mass)/pow(r2 = sqrt(r2), 3);
            
            
            // Check for collisions and this having the greater mass
            /*
            if(((radius + body.radius) > r2) && (mass > body.mass)){
                
                // conserve momentum; add mass and radius
                for(int i = 0; i < dim; i++)
                    v[i] = (v[i]*mass + body.v[i]*body.mass)/(mass += body.mass);
                
                radius += pow(body.radius, 1.0/3);
                
                
                // "delete" body - cheat so as not to break threading
                body.mass = 0;
                body.radius = 0;
            }
            */
            
            //update acceleration vector
            a0 += d0*a;
            a1 += d1*a;
            a2 += d2*a;
            
        }
        
        for(Cluster cluster : ((Universe)universe).getClusters()){
            if(cluster == this.cluster) continue;
            //calculate gravity
            //square of the distance between the two bodies.
            double r2 = 0;
            // direction vector from this to body.
            double d0,d1,d2;
            
            // fill values
            // Processr intensive - Written for efficiency over readability.
            // Direction not normalised - see below.
            r2 += pow((d0 = cluster.x0 - x0), 2);
            r2 += pow((d1 = cluster.x1 - x1), 2);
            r2 += pow((d2 = cluster.x2 - x2), 2);
            
            // acceleration magnitude
            // Using F=ma and F = GMm/r^2 to calculate a
            /*
             * In the next step, this value will be multiplied by the unit
             * vector of direction towards the body in question.
             * Rather than calculate the unit vector directly (which requires
             * each value in the direction vector to be divided by the magnitude
             * of the vector (which is the root of the already calculated r2)); 
             * we simply divide the a variable by a further factor of r.
             */
            double a = (universe.G * cluster.mass)/pow(r2 = sqrt(r2), 3);
            
            
            // Check for collisions and this having the greater mass
            /*
            if(((radius + body.radius) > r2) && (mass > body.mass)){
                
                // conserve momentum; add mass and radius
                for(int i = 0; i < dim; i++)
                    v[i] = (v[i]*mass + body.v[i]*body.mass)/(mass += body.mass);
                
                radius += pow(body.radius, 1.0/3);
                
                
                // "delete" body - cheat so as not to break threading
                body.mass = 0;
                body.radius = 0;
            }
            */
            
            //update acceleration vector
            a0 += d0*a;
            a1 += d1*a;
            a2 += d2*a;
        }
        
    }
    
    /**
     * Step the particle.
     */
    @Override
    public void step(){
        double timeStep = universe.Step;
        x0 = (v0 = a0*timeStep)*timeStep;
        x1 = (v1 = a1*timeStep)*timeStep;
        x2 = (v2 = a2*timeStep)*timeStep;
    }
    
}
