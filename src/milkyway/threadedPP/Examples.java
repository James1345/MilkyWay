package milkyway.threadedPP;

import static java.lang.Math.*;
/**
 * A class to hold example setups for the main thread.
 * @author james
 */
public class Examples {
     public static void binaryStar(){
            Universe.init(2, 1, 0.01);
            Universe.get().add(new MassiveBody(50, 10, new double[]{400,200}, new double[]{0.1,0}));
            Universe.get().add(new MassiveBody(50, 10, new double[]{400,600}, new double[]{-0.1,0}));
     }
     
     public static void fourStar(){
            Universe.init(2, 1, 0.01);
            Universe.get().add(new MassiveBody(100, 10, new double[]{400,200}, new double[]{0.5,0}));
            Universe.get().add(new MassiveBody(100, 10, new double[]{400,600}, new double[]{0,-0.5}));
            Universe.get().add(new MassiveBody(100, 10, new double[]{800,200}, new double[]{0,0.5}));
            Universe.get().add(new MassiveBody(100, 10, new double[]{800,600}, new double[]{-0.5,0}));
     }
     
     /**
      * Create a system with a central star with a very large mass, and a given number of
      * smaller particles orbiting it (roughly models a spinning gas cloud).
      * @param particles The number of particles to use
      */
     public static void circle(int particles){
         
         Universe.init(2, 1, 0.01);
         Universe.get().add(new MassiveBody(1e6, 25, new double[]{700,350}, new double[]{0,0}));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(4*PI);
             Universe.get().add(
                     new MassiveBody(
                             10, 
                             5,
                             new double[]{random()*200 -100 + 700 + 200*cos(theta), random()*200 -100 + 350 + 200*sin(theta)}, 
                             new double[]{80*sin(theta),80*-cos(theta)}));
         }
     }
     
     public static void massive(){
         Universe.init(2, 1, 1);
         for(int i = 0; i < 600; i++){
            double size = random()*100;
            Universe.get().add(new MassiveBody(size > 90 ? 50 : 10, size > 90 ? 10 : 5, new double[]{1300*random(),700*random()}, new double[]{0,0})); 
            //Universe.get().add(new MassiveBody(10 + size/2, 5 + size/10, new double[]{1300*random(),700*random()}, new double[]{0,0}));
         }
     }
}
