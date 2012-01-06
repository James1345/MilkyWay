package milkyway.threadedPP;

import milkyway.BaseBody;
import static java.lang.Math.*;


/**
 * Represents a Massive Body in the universe.
 * 
 * This class represents a massive body in the universe.
 * As it was made before BaseBody, there is some duplication of effort, 
 * as it was made to match the specification of that class while still
 * using its own methods.
 * 
 * @author james
 */
public class MassiveBody extends BaseBody {
    
    
    public MassiveBody(double mass, double radius,double x0, double x1, double x2, double v0, double v1, double v2, Universe u){
        super(mass,radius,x0,x1,x2,v0,v1,v2, u);
    }
    
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
    @Override
    public void update(){
        a0 = 0;
        a1 = 0;
        a2 = 0;
        for(BaseBody bbody : universe.getBodies()){
            MassiveBody body = (MassiveBody)bbody;
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
        
            // Check for collisions
            // Uses position in list to determine which one absorbs the other
            // It doesn't matter which but one has to be judged somehow.
            if(((radius + body.radius) > r2) && (universe.getBodies().indexOf(this) < universe.getBodies().indexOf(body))){
                
                // conserve momentum; add mass and radius
                double totalMass = mass + body.mass;
                
                v0 = (v0*mass + body.v0*body.mass)/(totalMass);
                v1 = (v1*mass + body.v1*body.mass)/(totalMass);
                v2 = (v2*mass + body.v2*body.mass)/(totalMass);
                
                radius = cbrt(pow(body.radius, 3) + pow(radius, 3));
                mass = totalMass;
                
                // "delete" body - cheat so as not to break threading
                body.mass = 0;
                body.radius = 0;
            }
            //update acceleration vector
            a0 += d0*a;
            a1 += d1*a;
            a2 += d2*a;
        }
    }
    
    @Override
    public void step(){
        // Increment by correct step.
        double halfStep = scalb(universe.Step, -1); // Efficient (bitwise) divide by 2
        v0 += a0*halfStep; 
        x0 += v0*universe.Step;
        v0 += a0*halfStep;
        
        v1 += a1*halfStep; 
        x1 += v1*universe.Step;
        v1 += a1*halfStep;
        
        v2 += a2*halfStep; 
        x2 += v2*universe.Step;
        v2 += a2*halfStep;
        
    }
}
