package org.usfirst.frc3467.subsystems.DriveBase;

import java.lang.Math;
class DriveMath{
 
  private  double zAccelMax;
  private  double aNetMax;
  private  double xVelMax;
  private  double yVelMax;
  private  double zVelMax;
  private  double robotWidth;
  public  double yEncFt = 3812/10;
  private  double xEncFt = 18*2*(3812.0/10)*1859.96/3911.40/32;//11160/12417;
  
  private  double loopT;
  
  private  double xLast;
  private  double yLast;
  private  double zLast;
  private  double xTgt;
  private  double yTgt;
  private  double zTgt;
  
  private  double tAbs;
  
  private  double stepScaleRange;
  private  boolean setStepScale;
  
  private  double linearScaleFactor;
  private  boolean runLinearScale;
  
  private  double rotationScaleFactor;
  
  private  double xOut;
  private  double yOut;
  private  double zOut;
  
  //constructors
  //form: Max x Accel, Max y Accel, Max z Accel, Max x Vel, Max y Vel, Max z Vel, Loop Period, Robot width
  public DriveMath(double zAc, double xV, double yV, double zV, double lT, double wd)
  {
    
    zAccelMax = zAc;
    
    xLast = 0;
    yLast = 0;
    zLast = 0;
    
    loopT = lT;
    tAbs = 0;
    
    xVelMax = xV;
    yVelMax = yV;
    zVelMax = zV;
    
    setStepScale = false;
    
    robotWidth = wd;
    
    aNetMax = 1;
  }
  
  public DriveMath()
  {
    
    xLast = 0;
    yLast = 0;
    
    loopT = .05;
    tAbs = 0;
    
    xVelMax = 0;
    yVelMax = 0;
    
    setStepScale = false;
    
    robotWidth = 1;
    aNetMax = 1;
    
  }
  
  //Linear Scaling Code
  
  public  void enableLinearScale(){
    if(!runLinearScale)
      runLinearScale = true;
    
  }
  
  public  void disableLinearScale(){
    if(runLinearScale)
      runLinearScale = false;
  }
  
  public  void setScaleFactor(double n){
    linearScaleFactor = n;
  }
  
  public  void setRotationFactor(double n){
    rotationScaleFactor = n;
  }
  
  private  void linearScale(){
    if(runLinearScale){
      if(setStepScale && Math.abs(xTgt)>stepScaleRange && Math.abs(yTgt)>stepScaleRange);
      else{
        xTgt = xTgt*linearScaleFactor;
        yTgt = yTgt*linearScaleFactor;
      }
    }
  }
  
  
  //Step Scaling Code
  public  void setStepScaleRange(double range){
    stepScaleRange = range;
  }
  
  public  void enableStepScale(){
    setStepScale = true;
  }
  
  public  void disableStepScale(){
    setStepScale = false;
  }
  
  private  void stepScale(){
    
    if(setStepScale){
      double ratio = xTgt/yTgt;
      
      if(Math.abs(xTgt)>stepScaleRange && Math.abs(yTgt)>stepScaleRange){
        if(xTgt>=yTgt && xTgt >0){
          xTgt = xVelMax;
          yTgt = xTgt/ratio;
          //System.out.println("Case1");
        }
        else if(xTgt<=yTgt && xTgt<0){
          xTgt = -1*xVelMax;
          yTgt = xTgt/ratio;
          //System.out.println("Case2 "+ratio+" "+xTgt+" "+yTgt);
        }
        else if(yTgt<xTgt && yTgt<0){
          yTgt = -1*yVelMax;
          xTgt = yTgt*ratio;
          //System.out.println("Case3");
        } 
        else {
          yTgt = yVelMax;
          xTgt = yTgt*ratio;
          //System.out.println("Case4");
        }
      }
    }
  }
  
  
  //main scaling method
  public  void set(double xTarget, double yTarget, double zTarget)
  {
    
    xTgt = xTarget;
    yTgt = yTarget;
    zTgt = zTarget;
    
    linearScale();
    //stepScale();
    
    double ratio = xTgt/yTgt;
    if(yTgt == 0)
      ratio = 0;
    
    //Acceleration limiting
    double aNet = Math.sqrt(Math.pow(accelCalc(xTgt-xLast),2)+Math.pow(accelCalc(yTgt-yLast),2));
    
    if(aNet*aNet>aNetMax*aNetMax){
      xTgt = xLast+(accelCalc(xTgt-xLast)*aNetMax/aNet)*loopT;
      yTgt = yLast+(accelCalc(yTgt-yLast)*aNetMax/aNet)*loopT;
    }
    
    //rotation scale
    zTgt = zTgt*rotationScaleFactor;
    
    //rotation limiting, Angular Acceleration
    
    if(accelCalc(zTgt-zLast)>zAccelMax){
      zTgt = zLast+zAccelMax*loopT;
    }
    else if(accelCalc(zTgt-zLast)<-1*zAccelMax){
      zTgt = zLast - zAccelMax*loopT;
    }
    
    if(zTgt< -1*zVelMax)
      zTgt = -1*zVelMax;
    else if(zTgt>zVelMax)
      zTgt = zVelMax;
    
    //rotation limiting, Linear Acceleration
    double turningFactor = Math.abs(zTgt*robotWidth/2);
    
    //velocity limiting
    if(xTgt > xVelMax){
     xTgt = xVelMax;
     if(ratio == 0)
       yTgt = 0;
     else
    	 yTgt = xTgt/ratio;
    }
    if(xTgt< -1*xVelMax){
     xTgt = -1*xVelMax;
     yTgt = xTgt/ratio;
     if(ratio == 0)
       yTgt = 0;
    }
    if(yTgt > yVelMax){
      yTgt = yVelMax;
      xTgt = yTgt*ratio;
    }
    if(yTgt < -1*yVelMax){
      yTgt = -1*yVelMax;
      xTgt = yTgt*ratio;
    }
    
    if(turningFactor != 0){
      if(yTgt+turningFactor>yVelMax)
        yTgt = yVelMax-turningFactor-2;
      else if(yTgt-turningFactor < -1*yVelMax)
        yTgt = -1*yVelMax+turningFactor+2;
      if(ratio != 0)
        xTgt = yTgt*ratio;
      System.out.println(turningFactor+"    "+yTgt);
    }
    
    //resetting
    tAbs = tAbs+loopT;
    xLast = xTgt;
    yLast = yTgt;
    zLast = zTgt;
    
    ftToEnc();
    
  }
  
  private  double accelCalc(double dN){
    double accel = (int)(dN*1000000/loopT)/1000000.0;
    return accel;
  }
  
  public  void setNetAccel(double in){
    aNetMax = in;
  }
  
  private  void ftToEnc(){
    yOut = yLast*yEncFt;
    xOut = xLast*xEncFt;
    zOut = zLast;
  }
  
  //returns
  public  double getXVal(){
    return xOut;
  }
  
  public  double getYVal(){
    return yOut;
  }
  
  public  double getZVal(){
    return zOut;
  }
  
  public  double getTAbs(){
    return tAbs;
  }
  
  public String toString(){
    return tAbs+"\t"+xLast+"\t"+yLast+"\t"+zLast;
  }
  
}