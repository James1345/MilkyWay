/* 
 * File:   nativepp.h
 * Author: james
 *
 * Created on January 6, 2012, 12:01 AM
 */

#ifndef NATIVEPP_H
#define	NATIVEPP_H

#ifdef	__cplusplus
extern "C" {
#endif

    typedef struct {
        double mass, x0, x1, x2, v0, v1, v2, a0, a1, a2;
    } body;
    
    body * new_body(double mass, double x0, double x1, double x2, double v0, double v1, double v2);
    
    void update(body * b);
    void step(body * b, double time);
    void run0();
    void stop0();


#ifdef	__cplusplus
}
#endif

#endif	/* NATIVEPP_H */

