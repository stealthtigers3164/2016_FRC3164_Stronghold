
package org.usfirst.frc.team3164.robot;

import org.usfirst.frc.team3164.robot.comms.watchcat;
import org.usfirst.frc.team3164.robot.input.gamepad;
import org.usfirst.frc.team3164.robot.movement.driveTrain;
import org.usfirst.frc.team3164.robot.electrical.electricalConfig;
import org.usfirst.frc.team3164.robot.electrical.motor.jaguarMotor;
import org.usfirst.frc.team3164.robot.vission.Camera;
/*
 * *************
 * Daniel: Correct vission to vision
 * Add the new files
 * *************
 */


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {	
    public static Robot instance;//Has this always been there????
	/*
     * NOTE: TEMP
     */
	final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;

    final String driveTank = "Tank Drive";
    final String driveForza = "Forza Drive";
    final String driveNone = "No Drive";
    String driveSelected;
    SendableChooser chooserDT;

    
    private driveTrain drive;
    private gamepad gamePad1;
    private gamepad gamePad2;

    //private AnalogInput sensorRange;
    
    //private Camera microsoftCamera;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//TEMP
    	chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        //END Temp
        
        
        watchcat.init();//Not sure if should go here
        
        
        //////////////		Drivetrain		//////////////
        drive = new driveTrain(
        			new jaguarMotor(electricalConfig.wheel_frontLeft_pwm, electricalConfig.wheel_frontLeft_rev),
        			new jaguarMotor(electricalConfig.wheel_frontRight_pwm, electricalConfig.wheel_frontRight_rev),
        			new jaguarMotor(electricalConfig.wheel_backLeft_pwm, electricalConfig.wheel_backLeft_rev),
        			new jaguarMotor(electricalConfig.wheel_backRight_pwm, electricalConfig.wheel_backRight_rev));
        drive.setScaleFactor(0.7);//Overridden by smart dashboard
        
        
        //////////////		Gamepad		//////////////
        gamePad1 = new gamepad(0);
        gamePad2 = new gamepad(1);

        
        gamePad1.sticks.setDeadzones();
        gamePad2.sticks.setDeadzones();

        //////////////		Driving		//////////////				DONT KEEP FOR COMP
        chooserDT = new SendableChooser();
        chooserDT.addDefault("Tank Drive", driveTank);
        chooserDT.addObject("Forza Drive", driveForza);
        chooserDT.addObject("No Drive", driveNone);
        SmartDashboard.putData("Drivetrain", chooserDT);

        SmartDashboard.putNumber("Driving Scale Factor", 0.7);
        SmartDashboard.putNumber("Turning Scale Factor", 0.5);
                
        //////////////		Sensors		//////////////
        //sensorRange = new AnalogInput(electricalConfig.analog_ultrasonic_port);
        
        
        //DONT KEEP
        CameraServer.getInstance().setQuality(50);
        CameraServer.getInstance().startAutomaticCapture("cam0");//Move to electical config
        //TestCamera = new CameraServer();
        //microsoftCamera = new Camera();
        instance = this;//What does this do and why?
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	} 
    }
    public void teleopInit() {
    	SmartDashboard.putString("Mode", "Teleop");
    	
    	SmartDashboard.putNumber("buttonPort", 1);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	driveSelected = (String) chooserDT.getSelected();
    	drive.setScaleFactor(SmartDashboard.getNumber("Driving Scale Factor"));
    	drive.setScaleFactor(SmartDashboard.getNumber("Turning Scale Factor"), true);
    	
    	//Testing
    	SmartDashboard.putBoolean("ImageTest", CameraServer.getInstance().isAutoCaptureStarted());
    	//SmartDashboard.putNumber("analog", sensorRange.getAverageVoltage());
    	
    	
    	//Remove switch for comp, wastes cycles
    	switch(driveSelected) {
    		case driveNone:
    			drive.tankDrive(0, 0);
    			break;
	    	case driveForza:
	        	drive.forzaDrive(gamePad1.sticks.LEFT_X.getScaled(), gamePad1.trigger.getAxis());
	        	break;
	    	case driveTank:
	    	default:
	        	drive.tankDrive(gamePad1.sticks.LEFT_Y.getScaled(), gamePad1.sticks.RIGHT_Y.getScaled());
	            break;
    	}
    
    	SmartDashboard.putString("isXbox", gamePad1.jstick.getName());
    	SmartDashboard.putBoolean("ButtonPressed", gamePad1.jstick.getRawButton((int)SmartDashboard.getNumber("buttonPort")));
    	
    	
    	watchcat.feed();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit() {
    	SmartDashboard.putString("Mode", "Test");
    	
    }
    
    public void testPeriodic() {
    	
    }
    
}
