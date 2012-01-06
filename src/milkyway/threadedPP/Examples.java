package milkyway.threadedPP;

import static java.lang.Math.*;
/**
 * A class to hold example setups for the main thread.
 * @author james
 */
public class Examples {
     public static Universe binaryStar(){
            Universe u = new Universe(1, 0.001);
            u.add(new MassiveBody(50, 10, 400,200, 0, 0.1,0, 0, u));
            u.add(new MassiveBody(50, 10, 400,600, 0, -0.1,0, 0, u));
            return u;
     }
     
     public static Universe fourStar(){
            Universe u = new Universe(1, 0.001);
            u.add(new MassiveBody(100, 10, 400,200, 0, 0.5,0, 0, u));
            u.add(new MassiveBody(100, 10, 400,600, 0, 0,-0.5, 0, u));
            u.add(new MassiveBody(100, 10, 800,200, 0, 0,0.5, 0, u));
            u.add(new MassiveBody(100, 10, 800,600, 0, -0.5,0, 0, u));
            return u;
     }
     
     /**
      * Create a system with a central star with a very large mass, and a given number of
      * smaller particles orbiting it (roughly models a spinning gas cloud).
      * 
      * The particles are placed in a 2D circle with circular motion
      * @param particles The number of particles to use (works best with 200 - 300 particles)
      */
     public static Universe circle(int particles){
         
         Universe u = new Universe(1, 0.01);
         u.add(new MassiveBody(1e6, 25, 700,350,0, 0,0, 0, u));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(4*PI);
             u.add(
                     new MassiveBody(
                             10, 
                             5,
                             random()*100 -50 + 700 + 150*cos(theta), random()*100 -50 + 350 + 150*sin(theta), 0,
                             80*sin(theta),80*-cos(theta), 0, u));
         }
         return u;
     }
     
     /**
      * Create a system with a central star with a very large mass, and a given number of
      * smaller particles orbiting it circularly.
      * 
      * The particles have been moved from {@link #circle(int), u to show a 3D (ish) effect
      * 
      * The particles are placed in a 2D circle with circular motion
      * @param particles The number of particles to use (works best with 200 - 300 particles)
      */
     public static Universe zCircle(int particles){
         
         Universe u = new Universe(1, 0.01);
         u.add(new MassiveBody(1e6, 25, 700,350,0, 0,0, 0, u));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(4*PI);
             u.add(
                     new MassiveBody(
                             10, 
                             5,
                             random()*100 -50 + 700 + 150*cos(theta),350, random()*100 -50 + 150*sin(theta), 
                             80*sin(theta), 0, 80*-cos(theta), u));
         }
         return u;
     }
     
     /**
      * Uses 200 particles
      */
     public static Universe sphere(){
         int particles = 200;
         Universe u = new Universe(1, 0.01);
         u.add(new MassiveBody(5e5, 25, 700,350,0, 0,0, 0, u));
         for(int i = 0; i < particles; i++){
             double j = i;
             double theta = (j*particles)/(8*PI);
             double phi = (j*particles)/(4*PI);
             u.add(
                     new MassiveBody(
                             10, 
                             5,
                             random()*300 -150 + 700 + 150*cos(theta)*sin(phi), random()*300 -150 + 350 + 150*sin(theta)*cos(phi), random()*300 - 150 + 150*cos(theta), 
                             80*sin(theta)*-cos(phi), 80*-cos(theta)*sin(phi), 40*sin(theta), u));
         }
         return u;
     }
     
     public static Universe massive(){
         Universe u = new Universe(1, 0.1);
         for(int i = 0; i < 600; i++){
            double size = random()*1000;
            u.add(new MassiveBody(300, 5, 1300*random(),700*random(), 1000*random(), 10*random() - 5,10*random() - 5, 10*random() - 5, u)); 
            //u.add(new MassiveBody(10 + size/2, 5 + size/10, 1300*random(),700*random(), u, 0,0, u));
         }
         return u;
     }
}
