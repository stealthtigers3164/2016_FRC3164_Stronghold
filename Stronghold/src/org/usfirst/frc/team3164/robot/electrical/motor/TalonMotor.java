package org.usfirst.frc.team3164.robot.electrical.motor;

import org.usfirst.frc.team3164.robot.comms.Watchcat;

import edu.wpi.first.wpilibj.Talon;

public class TalonMotor implements BasicMotor {
    private int pwmLoc;
    private Talon m;
    private double power;
    private boolean dead;
    
    /**
     * Make a new Motor Object
     * @param pwmLoc PWM location of the motor
     */
    public TalonMotor(int pwmLoc) {
        this.pwmLoc = pwmLoc;
        this.m = new Talon(this.pwmLoc);
        this.power = 0;
        Watchcat.registerMotor(this);
    }
    
    /**
     * Make a new Motor Object
     * @param pwmLoc PWM location of the motor
     * @param reversed true if the motor should be reversed.
     */
    public TalonMotor(final int pwmLoc, boolean reversed) {
    	this(pwmLoc);
    	this.m.setInverted(reversed);
    }
    
    /**
     * Please Please Please Please don't mess with this unless you MUST! D:
     * @return The Jaguar controller
     */
    public Jaguar getJag() {
    	return m;
    }
    
    //*****************
    //****Overridden***
    //*****************
    
    /**
     * Set the power of the motor
     * @param pwr power of the motor, -1.0 to 1.0
     */
    @Override
    public void setPower(double pwr) {
    	if(dead) return;
        this.power = pwr;
        m.set(power);
        
    }
    
    /**
     * Set the scaled power of the motor
     * @param pwr power of the motor, -100 to 100
     */
    @Override
    public void setScaledPower(int pwr) {
    	if(dead) return;
        this.power = pwr/100.0;
        m.set(this.power);
    }
    
    /**
     * Adds power for incrementing power purposes
     * @param pwr power to increase/decrease by.
     */
    @Override
    public void addPower(double pwr) {
    	if(dead) return;
        this.power += pwr;
        this.checkPower();
        m.set(this.power);
    }
    
    /**
     * Stop the motor.
     */
    @Override
    public void stop() {
    	if(dead)
    		return;
        m.set(0);
    }
    
    @Override
    public void reverse() {
    	m.setInverted(!m.getInverted());
    }
    
    @Override
    @Deprecated //Unfinished
    public void slowStop() {
    	if(dead)
    		return;
        //TODO Use a thread calling back here to stop slowly
    }
    
    //*****************
    //*****Private*****
    //*****************
    
    private void checkPower() {
    	if(this.power>1.0) {
        	this.power = 1.0;
        }
        if(this.power<-1.0) {
        	this.power = -1.0;
        }
    }

	@Override
	public int getLoc() {
		return pwmLoc;
	}

	@Override
	public void setDead(boolean shouldBeDead) {
		if(shouldBeDead)
			this.power = 0;
		this.dead = shouldBeDead;
	}
	
	public double getPower() {
		return this.power;
	}
}