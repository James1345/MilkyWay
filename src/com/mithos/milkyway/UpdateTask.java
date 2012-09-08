package com.mithos.milkyway;

import java.util.concurrent.Callable;
import static java.lang.Math.*;

public class UpdateTask implements Callable<Void> {

	protected StructParticle[] bufferA, bufferB, swapBuffer;
	protected int element;
	
	protected static double G = 1;
	protected static double Step = 1;
	
	public UpdateTask(StructParticle[] bufferA, StructParticle[] bufferB, int element){
		this.bufferA = bufferA;
		this.bufferB = bufferB;
		this.element = element;
		swapBuffer = null;
	}
	
	@Override
	public Void call() throws Exception {

		StructParticle current = bufferA[element];
		StructParticle current2 = bufferB[element];
		double a0 = 0, a1 = 0, a2 = 0;
		
		for(StructParticle compare : bufferA){
			if(compare==current) continue;
			
            //calculate gravity
            
            //square of the distance between the two bodies.
            double r2 = 0;
            // direction vector from this to body.
            double d0,d1,d2;
            
            // fill values
            // Processor intensive - Written for efficiency over readability.
            // Direction not normalised - see below.
            r2 += pow((d0 = compare.x0 - current.x0), 2);
            r2 += pow((d1 = compare.x1 - current.x1), 2);
            r2 += pow((d2 = compare.x2 - current.x2), 2);
            
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
            double a = (G * compare.mass)/pow(r2 = sqrt(r2), 3);
            //update acceleration vector
            a0 += d0*a;
            a1 += d1*a;
            a2 += d2*a;
		}
		
		// Increment by correct step.
        double halfStep = scalb(Step, -1); // Efficient (bitwise) divide by 2
        current2.v0 += a0*halfStep;
        current2.x0 += current.v0*Step;
        current2.v0 += a0*halfStep;
        
        current2.v1 += a1*halfStep;
        current2.x1 += current.v1*Step;
        current2.v1 += a1*halfStep;
        
        current2.v2 += a2*halfStep;
        current2.x2 += current.v2*Step;
        current2.v2 += a2*halfStep;
		
		swapBuffer = bufferA;
		bufferA = bufferB;
		bufferB = swapBuffer;
		return null;
	}

}
