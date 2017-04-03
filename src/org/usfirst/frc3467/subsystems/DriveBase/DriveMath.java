package org.usfirst.frc3467.subsystems.DriveBase;

import java.lang.Math;

class DriveMath{
 
  private  double xAccelMax;
  private  double yAccelMax;
  private  double zAccelMax;
  private  double aNetMax;
  private  double xVelMax;
  private  double yVelMax;
  private  double zVelMax;
  private  double robotWidth;
  public  double yEncFt = 12417;
  private  double xEncFt = yEncFt*11160/12417;
  
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
  private double stepScaleFactor;
  
  private  double linearScaleFactor;
  private  boolean runLinearScale;
  
  private  double rotationScaleFactor;
  
  private  double xOut;
  private  double yOut;
  private  double zOut;
  
  //constructors
  //form: Max x Accel, Max y Accel, Max z Accel, Max x Vel, Max y Vel, Max z Vel, Loop Period, Robot width
  public DriveMath(double xAc, double yAc, double zAc, double xV, double yV, double zV, double lT, double wd)
  {
    
    xAccelMax = xAc;
    yAccelMax = yAc;
    zAccelMax = zAc;
    
    xLast = 0;
    yLast = 0;
    zLast = 0;
    
    loopT = lT;
    tAbs = 0;
    
    xVelMax = xV;
    yVelMax = yV;
    zVelMax = zV;
    
    aNetMax = 1;
    
    setStepScale = false;
    
    robotWidth = wd;
  }
  
  public DriveMath()
  {
    xAccelMax = 0;
    yAccelMax = 0;
    
    xLast = 0;
    yLast = 0;
    
    loopT = .001;
    tAbs = 0;
    
    xVelMax = 0;
    yVelMax = 0;
    
    aNetMax = 1;
    
    setStepScale = false;
    
    robotWidth = 1;
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
    if(!setStepScale)
      setStepScale = true;
  }
  
  public  void disableStepScale(){
    if(setStepScale)
      setStepScale = false;
  }
  
  private  void stepScale(){
    
    if(setStepScale){
      double ratio = xTgt/yTgt;
      
      if(Math.sqrt(Math.pow(xTgt/linearScaleFactor,2)+Math.pow(yTgt/linearScaleFactor,2))>stepScaleRange){
         yTgt = stepScaleFactor*yTgt/linearScaleFactor;
         xTgt = stepScaleFactor*xTgt/linearScaleFactor;
      }
    }
  }
  
  public void setStepScaleFactor(double n){
	  stepScaleFactor = n;
  }
  
  //main scaling method
  public  void set(double xTarget, double yTarget, double zTarget)
  {
    
    xTgt = xTarget;
    yTgt = yTarget;
    zTgt = zTarget;
    
    linearScale();
    stepScale();
    
    double xAccelTgt = accelCalc(xTgt - xLast);
    int xAccelSign = (int)(xAccelTgt/Math.abs(xAccelTgt));
    
    double ratio = xTgt/yTgt;
    
    //acceleration limiting
    if(xAccelTgt > xAccelMax || xAccelTgt*-1 >xAccelMax)
    {
      xTgt = xLast+xAccelSign*xAccelMax*loopT;
      yTgt = xTgt/ratio;
    }
    
    double yAccelTgt = accelCalc(yTgt - yLast);
    int yAccelSign = (int)(yAccelTgt/Math.abs(yAccelTgt));
    
    if(yAccelTgt > yAccelMax || yAccelMax*-1 > yAccelMax)
    {
      yTgt = yLast+yAccelSign*yAccelMax*loopT;
      xTgt = yTgt*ratio;
    }
    
    //System.out.println("Case1 "+ratio+" "+xTgt+" "+yTgt);
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
     yTgt = xTgt/ratio;
    }
    if(xTgt< -1*xVelMax){
     xTgt = -1*xVelMax;
     yTgt = xTgt/ratio;
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
        yTgt = yVelMax-turningFactor;
      else if(yTgt+turningFactor < -1*yVelMax)
        yTgt = -1*yVelMax+turningFactor;
      xTgt = yTgt*ratio;
      //System.out.println(turningFactor);
    }
    
    double aNet = Math.sqrt(Math.pow(accelCalc(xTgt-xLast),2)+Math.pow(accelCalc(xTgt-xLast),2));
    
    if(aNet>aNetMax){
      xTgt = xTgt*aNetMax/aNet;
      yTgt = yTgt*aNetMax/aNet;
    }
    
    //resetting
    tAbs = tAbs+loopT;
    xLast = xTgt;
    yLast = yTgt;
    zLast = zTgt;
    
    ftToEnc();
    
  }
  
  public void setNetAccel(double in){
    aNetMax = in;
  }
  
  private  double accelCalc(double dN){
    double accel = (int)(dN*1000000/loopT)/1000000.0;
    return accel;
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