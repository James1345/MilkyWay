package milkyway;

/**
 * A base for any MassiveBody.
 * 
 * The body keeps a record of its properties, and a reference to the
 * Universe that contains it.
 * 
 * @author james
 */
public abstract class BaseBody {
    
    // Position held in the x variables, velocity in v and acceleration in a
    // Held in fields for faster access.
    protected double mass, radius, x0, x1, x2, v0, v1, v2, a0, a1, a2;
    
    protected BaseUniverse universe;
    
    public BaseBody
            (double mass, double radius, 
            double x0, double x1, double x2,
            double v0, double v1, double v2, BaseUniverse universe){
        this.mass = mass;
        this.radius = radius;
        this.x0 = x0;
        this.x1 = x1;
        this.x2 = x2;
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.universe = universe;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getX0() {
        return x0;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }
    
    
    
    /**
     * Update the acceleration of the body.
     * 
     * As each time step will cause a movement, each body should update its
     * acceleration before any updates its position. I.e. every body
     * calls Update, <b>then</b> every body calls step().
     */
    public abstract void update();
    
    /**
     * Update the velocity and position of this body by one timeStep, as defined
     * in the Universe that contains the body.
     */
    public abstract void step();
}
