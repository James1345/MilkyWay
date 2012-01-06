#include "milkyway_nativePP_Universe.h"
#include "nativepp.h"
#include <jni.h>

#include <stdlib.h>
#include <math.h>

/* Global Variables*/
#define G 1 /* Universal Gravitational Constant */

#define BODY_COUNT 200 /* Temporary Static definition */
body* bodies[BODY_COUNT]; /* array of body pointers */
int body_count = BODY_COUNT; /* Number of massive bodies */
double time_step = 0.01; /* Time per loop */

int done = 0; /* Boolean to check if done, checked each loop. If done, free resources */

/* Create a new body and return the pointer to it. */
body * new_body(double mass, double x0, double x1, double x2, double v0, double v1, double v2)
{
    body * b = malloc(sizeof(body)); 
    b->mass = mass;
    b->x0 = x0;
    b->x1 = x1;
    b->x2 = x2;
    b->v0 = v0;
    b->v1 = v1;
    b->v2 = v2;
    b->a0 = b->a1 = b->a2 = 0;
    return b;
}

void update(body * b)
{
    b->a0 = 0;
    b->a1 = 0;
    b->a2 = 0;
    int count = 0;
    while(count++ < body_count)
    {
        body * that = bodies[count];
        if(b == that) continue;
        
        /* square of the distance between the two bodies. */
        double r2 = 0;
        /* direction vector from this to body. */
        double d0,d1,d2;

        /* 
         * fill values
         * Processor intensive - Written for efficiency over readability.
         * Direction not normalised - see below.
         */
        r2 += pow((d0 = (that->x0) - b->x0), 2);
        r2 += pow((d1 = (that->x1) - b->x1), 2);
        r2 += pow((d2 = (that->x2) - b->x2), 2);

        /* acceleration magnitude
         * Using F=ma and F = GMm/r^2 to calculate a
         *
         * In the next step, this value will be multiplied by the unit
         * vector of direction towards the body in question.
         * Rather than calculate the unit vector directly (which requires
         * each value in the direction vector to be divided by the magnitude
         * of the vector (which is the root of the already calculated r2)); 
         * we simply divide the a variable by a further factor of r.
         */
        double a = (G * that->mass)/pow((r2 = sqrt(r2)), 3);

        /* update acceleration vector */
        b->a0 += d0*a;
        b->a1 += d1*a;
        b->a2 += d2*a;
    }
    
}

void step(body * b, double time)
{
    b->v0 += (b->a0)*ldexp(time, -1); /* ldexp is equivalent to divide by 2 (but faster operation) */
    b->x0 += (b->v0)*time;
    b->v0 += (b->a0)*ldexp(time, -1);
    
    b->v1 += (b->a1)*ldexp(time, -1); 
    b->x1 += (b->v1)*time;
    b->v1 += (b->a1)*ldexp(time, -1);
    
    b->v2 += (b->a2)*ldexp(time, -1); 
    b->x2 += (b->v2)*time;
    b->v2 += (b->a2)*ldexp(time, -1);
}

/* JNI Methods */

/*
 * Class:     milkyway_nativePP_Universe
 * Method:    getPositions
 * Signature: ()[[D
 */
JNIEXPORT jobjectArray JNICALL Java_milkyway_nativePP_Universe_getPositions(JNIEnv * env, jobject obj)
{
    
}

/*
 * Class:     milkyway_nativePP_Universe
 * Method:    run0
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_milkyway_nativePP_Universe_run0(JNIEnv * env, jobject obj)
{
    /* Setup */
    int count = 0;
    while(count++ < body_count)
    {
        bodies[count] = new_body(); /* TODO Fill in values for bodies */
    }
    
    while(!done)
    {
        count = 0;
        while(count++ < body_count)
        {
            update(bodies[count]);
        }
        count = 0;
        while(count++ < body_count)
        {
            step(bodies[count], time_step);
        }
    }
    
    /* Free resources when done*/
    int count = 0;
    while(count++ < body_count)
    {
        free(bodies[count]);
    }
    
}

/*
 * Class:     milkyway_nativePP_Universe
 * Method:    stop0
 * Signature: ()V
 */
/* Set done to 1, called from the AWT event thread to signify the program should exit.*/
JNIEXPORT void JNICALL Java_milkyway_nativePP_Universe_stop0 (JNIEnv * env, jobject obj)
{
    done = 1;
}