
package org.usfirst.frc.team3164.robot;

import org.usfirst.frc.team3164.robot.comms.Watchcat;
import org.usfirst.frc.team3164.robot.electrical.ElectricalConfig;
import org.usfirst.frc.team3164.robot.electrical.motor.SparkMotor;
import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.movement.DriveTrain;
import org.usfirst.frc.team3164.robot.movement.FlyWheel;
import org.usfirst.frc.team3164.robot.movement.Intake;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;
import org.usfirst.frc.team3164.robot.vision.Camera;
import org.usfirst.frc.team3164.robot.vision.GoalAlign;

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
	private final String defaultAuto = "Default";
    private final String customAuto = "My Auto";
    private String autoSelected;
    private SendableChooser chooser;

    private final String driveTank = "Tank Drive";
    private final String driveForza = "Forza Drive";
    private final String driveNone = "No Drive";
    private String driveSelected;
    private SendableChooser chooserDT;

    private FlyWheel shooter;
    
    private DriveTrain<SparkMotor> drive;
    private Gamepad gamePad1;
    private Gamepad gamePad2;
    private Camera camera;
    
    private ThreadQueue<WorkerThread> queue;
    
    private Intake<SparkMotor> inftake;
    
    //private AnalogInput sensorRange;
    
    //private Camera microsoftCamera;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    //private /*final*/ NetworkTable grip = NetworkTable.getTable("grip");

    public void robotInit() {
    	//TEMP
    	chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        //END Temp
        
        
        Watchcat.init();//Not sure if should go here
        
        //////////////		Gamepad		//////////////
        gamePad1 = new Gamepad(0);
        gamePad2 = new Gamepad(1);
        
        //////////////		Drivetrain		//////////////
        drive = new DriveTrain<SparkMotor>(
        			new SparkMotor(ElectricalConfig.wheel_frontLeft_pwm, ElectricalConfig.wheel_frontLeft_rev),
        			new SparkMotor(ElectricalConfig.wheel_frontRight_pwm, ElectricalConfig.wheel_frontRight_rev),
        			new SparkMotor(ElectricalConfig.wheel_backLeft_pwm, ElectricalConfig.wheel_backLeft_rev),
        			new SparkMotor(ElectricalConfig.wheel_backRight_pwm, ElectricalConfig.wheel_backRight_rev),
        			gamePad1);
        drive.setScaleFactor(0.7);//Overridden by smart dashboard
        
        //intake = new Intake<SparkMotor>(gamePad2,
        	//	new  Motor(ElectricalConfig.intake_motor));
        
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
        
        queue = new ThreadQueue<WorkerThread>();
        
        shooter = new FlyWheel(queue, gamePad1);
        
        //////////////		Sensors		//////////////
        //sensorRange = new AnalogInput(electricalConfig.analog_ultrasonic_port);
        
        
        //DONT KEEP
        //CameraServer.getInstance().setQuality(50);
        //CameraServer.getInstance().startAutomaticCapture("cam0");//Move to electical config
        
        /*try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        camera = new Camera();
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
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    GoalAlign testGoal = new GoalAlign("GoalContours");
    public void autonomousPeriodic() {
    	//for (double area : grip.getNumberArray("GoalContours/area", new double[0])) {
           // System.out.println("Got contour with area=" + area);
        //}
    	/*switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}*/
    	testGoal.updateByLargestArea();
    	SmartDashboard.putNumber("DistanceXfromCenter", testGoal.getHorizontalDistanceFromCenter());
    	
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
    	//SmartDashboard.putBoolean("ImageTest", CameraServer.getInstance().isAutoCaptureStarted());
    	//SmartDashboard.putNumber("analog", sensorRange.getAverageVoltage());
    	
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    	
    	
    	//Remove switch for comp, wastes cycles
    	switch(driveSelected) {
    		case driveNone:
    			drive.setUpdate(false);
    			break;
	    	case driveForza:
	    		if (drive.getDriveType() != DriveTrain.FORZA_DRIVE)
	    			drive.useForzaDrive();
	        	if (!drive.shouldUpdate())
	        		drive.setUpdate(true);
	    		break;
	    	case driveTank:
	    		if (drive.getDriveType() != DriveTrain.TANK_DRIVE)
	    			drive.useTankDrive();
	    		if (!drive.shouldUpdate())
	        		drive.setUpdate(true);
	    	default:
	        	
	            break;
    	}
    	
    	shooter.update(0);	
    	
    	drive.updateMotors();
    	
    	//intake.updateMotors();
    	
    	SmartDashboard.putString("isXbox", gamePad1.jstick.getName());
    	SmartDashboard.putBoolean("ButtonPressed", gamePad1.jstick.getRawButton((int)SmartDashboard.getNumber("buttonPort")));
    	
    	Watchcat.feed();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit() {
    	SmartDashboard.putString("Mode", "Test");
    	
    }
    
    public void testPeriodic() {
    	camera.Update();
    }
    
}
