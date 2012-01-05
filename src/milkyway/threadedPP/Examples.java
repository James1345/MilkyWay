package milkyway.threadedPP;

import static java.lang.Math.*;
/**
 * A class to hold example setups for the main thread.
 * @author james
 */
public class Examples {
     public static void binaryStar(){
            Universe.init(1, 0.01);
            Universe.get().add(new MassiveBody(50, 10, new double[]{400,200, 0}, new double[]{0.1,0, 0}));
            Universe.get().add(new MassiveBody(50, 10, new double[]{400,600, 0}, new double[]{-0.1,0, 0}));
     }
     
     public static void fourStar(){
            Universe.init(1, 0.01);
            Universe.get().add(new MassiveBody(100, 10, new double[]{400,200, 0}, new double[]{0.5,0, 0}));
            Universe.get().add(new MassiveBody(100, 10, new double[]{400,600, 0}, new double[]{0,-0.5, 0}));
            Universe.get().add(new MassiveBody(100, 10, new double[]{800,200, 0}, new double[]{0,0.5, 0}));
            Universe.get().add(new MassiveBody(100, 10, new double[]{800,600, 0}, new double[]{-0.5,0, 0}));
     }
     
     /**
      * Create a system with a central star with a very large mass, and a given number of
      * smaller particles orbiting it (roughly models a spinning gas cloud).
      * 
      * The particles are placed in a 2D circle with circular motion
      * @param particles The number of particles to use (works best with 200 - 300 particles)
      */
     public static void circle(int particles){
         
         Universe.init(1, 0.01);
         Universe.get().add(new MassiveBody(1e6, 25, new double[]{700,350,0}, new double[]{0,0, 0}));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(4*PI);
             Universe.get().add(
                     new MassiveBody(
                             10, 
                             5,
                             new double[]{random()*100 -50 + 700 + 150*cos(theta), random()*100 -50 + 350 + 150*sin(theta), 0}, 
                             new double[]{80*sin(theta),80*-cos(theta), 0}));
         }
     }
     
     /**
      * Create a system with a central star with a very large mass, and a given number of
      * smaller particles orbiting it circularly.
      * 
      * The particles have been moved from {@link #circle(int)} to show a 3D (ish) effect
      * 
      * The particles are placed in a 2D circle with circular motion
      * @param particles The number of particles to use (works best with 200 - 300 particles)
      */
     public static void zCircle(int particles){
         
         Universe.init(1, 0.01);
         Universe.get().add(new MassiveBody(1e6, 25, new double[]{700,350,0}, new double[]{0,0, 0}));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(4*PI);
             Universe.get().add(
                     new MassiveBody(
                             10, 
                             5,
                             new double[]{random()*100 -50 + 700 + 150*cos(theta),350, random()*100 -50 + 150*sin(theta)}, 
                             new double[]{80*sin(theta), 0, 80*-cos(theta)}));
         }
     }
     
     /**
      * Uses 200 particles
      */
     public static void sphere(){
         int particles = 200;
         Universe.init(1, 0.01);
         Universe.get().add(new MassiveBody(5e5, 25, new double[]{700,350,0}, new double[]{0,0, 0}));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(8*PI);
             double phi = (j*particles)/(4*PI);
             Universe.get().add(
                     new MassiveBody(
                             10, 
                             5,
                             new double[]{random()*300 -150 + 700 + 150*cos(theta)*sin(phi), random()*300 -150 + 350 + 150*sin(theta)*cos(phi), random()*300 - 150 + 150*cos(theta)}, 
                             new double[]{80*sin(theta)*-cos(phi), 80*-cos(theta)*sin(phi), 40*sin(theta)}));
         }
     }
     
     public static void massive(){
         Universe.init(1, 1);
         for(int i = 0; i < 600; i++){
            double size = random()*100;
            Universe.get().add(new MassiveBody(size > 90 ? 50 : 10, size > 90 ? 10 : 5, new double[]{1300*random(),700*random(), 1000*random()}, new double[]{0,0, 0})); 
            //Universe.get().add(new MassiveBody(10 + size/2, 5 + size/10, new double[]{1300*random(),700*random()}, new double[]{0,0}));
         }
     }
}
