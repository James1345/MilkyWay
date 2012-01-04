package milkyway;

import java.awt.Color;
import java.util.*;
import static java.lang.Math.*;

/**
 * Represents a Massive Body in the universe.
 * 
 * This class represents a massive body in the universe.
 * 
 * @author james
 */
public class MassiveBody {
    
    // Essential properties
    public final Color color;
    protected double mass = 0;
    protected double radius = 0;
    protected double[] pos = null;
    protected double[] v = null;
    
    protected double[] a = null; // acceleration held as instance var so can be shared by methods
    
    // Values copied from Universe
    protected final Universe universe;
    protected final int dim;
    protected final double step;
    
    public MassiveBody(double mass, double radius, double[] pos, double[] v){
        this.mass = mass + random(); // add some random to masses so that one of the two is always greater
        this.radius = radius;
        this.pos = pos;
        this.v = v;
        this.color = null;
        // get values from Universe
        universe = Universe.get();
        dim = universe.Dimensions;
        step = universe.Step;
        a = new double[dim];
        
        // Check arguments
        if(pos.length != dim || v.length != dim)
            throw new IllegalArgumentException("Illegal vector dimensions!");
    }
    
    public double getRadius(){ return radius; }
    
    public double[] getPos(){ return pos; }
    
    /**
     * Calculates the change in velocity of this body due to the 
     * gravitational force of every other body in the universe, and updates
     * the position of this by one time increment.
     * 
     * This method is run many times. As such, some readability has been sacrificed
     * for efficiency. The 'normal' way of calculating force has been adjusted slightly
     * to achieve the same mathematical result, but performing operations in a 
     * faster order (that is, an order that derives from rearranging equations but is
     * more efficient, rather than the intuitive order from the theory).
     * Explanations for the changes are in the comments.
     */
    public void update(){
        Arrays.fill(a, 0);
        for(MassiveBody body : universe.bodies){
            if(this == body) continue; // do not compare this to itself.
            
            //square of the distance between the two bodies.
            double r2 = 0;
            // direction vector from this to body.
            double dir[] = new double[dim];
            
            //loop to fill values
            // Processr intensive - Written for efficiency over readability.
            // Direction not normalised - see below.
            for(int i = 0; i < dim; i++){
                r2 += pow((dir[i] = body.pos[i] - pos[i]),2);
            }
            
            // acceleration magnitude
            // Using F=ma and F = GMm/r^2 to calculate a
            /*
             * In the next step, this value will be multiplied by the unit
             * vector of direction towards the body in question.
             * Rather than calculate the unit vector directly (which requires
             * each value in the direction vector to be divided by the magnitude
             * of the vector (which is the root of the already calculated r2)); 
             * we simply divide the acc variable by a further factor of r.
             */
            double acc = (universe.G * body.mass)/pow(r2 = sqrt(r2), 3);
            
            
            // Check for collisions and this having the greater mass
            if(((radius + body.radius) > r2) && (mass > body.mass)){
                
                // conserve momentum; add mass and radius
                for(int i = 0; i < dim; i++)
                    v[i] = (v[i]*mass + body.v[i]*body.mass)/(mass += body.mass);
                
                radius += pow(body.radius, 1.0/3);
                
                
                // "delete" body - cheat so as not to break threading
                body.mass = 0;
                body.radius = 0;
            }
            
            // add to acceleration vector 
            for(int i = 0; i < dim; i++){
                a[i] += dir[i]*acc;
            }
        }
    }
     
    public void step(){
        // Increment by correct step.
        for(int i = 0 ; i < dim; i++){
            pos[i] += (v[i] += a[i]*step)*step;
        }
    }
}
