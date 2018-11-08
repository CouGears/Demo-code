@ -1,100 +0,0 @@
package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically 
 * call your Autonomous and OperatorControl methods at the right time as controlled
 * by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 */
public class Robot extends SampleRobot 
{
    RobotDrive myRobot;
    Joystick stick;

    /**
     * Constructor. Called once when this class is loaded.
     */
    public Robot() 
    {
        System.out.println("Robot.constructor()");

        myRobot = new RobotDrive(0, 1);  // 2 motors on PWM ports 0 & 1.
        myRobot.setExpiration(0.1);      // need to see motor input at least every 
                                         // 10th of a second or stop motors.
        stick = new Joystick(0);         // joystick on usb port 0.
    }
    
    /**
     * Called once after class load complete.
     * Use to perform any needed initializations.
     * Very similar to the constructor.
     */
    public void robotInit() 
    {
        System.out.println("Robot.robotInit()");
    }

    /**
     * Executes a simple autonomous program.
     * Called by the driver station or field control system at the
     * start of the autonomous period.
     */
    public void autonomous() 
    {
        System.out.println("Robot.autonomous()");

        myRobot.setSafetyEnabled(false);  // motor safety off due to the fact
                                          // we want the motor to run 2 sec
                                          // with no other input.
        myRobot.drive(-0.5, 0.0);         // drive forwards half speed
        Timer.delay(2.0);                 //    for 2 seconds.
        myRobot.drive(0.0, 0.0);          // stop robot.
    }

    /**
     * Runs the motors with arcade steering, input from joystick.
     * Called by the driver station or field control system at the
     * start of the operator control (teleop) period.
     */
    public void operatorControl() 
    {
        System.out.println("Robot.operatorControl()");

        myRobot.setSafetyEnabled(true);   // motor safety back on.
    
        while (isOperatorControl() && isEnabled()) 
        {
            myRobot.arcadeDrive(stick);   // drive with arcade style (use right stick).
            Timer.delay(0.020);           // wait for a joystick update.
        }
    }
    
    /**
     * Called whenever the robot is disabled by the DS or field control.
     * Use to perform any needed resets or changes when switching between modes.
     */
    public void disabled()
    {
        System.out.println("Robot.disabled()");
    }

    /**
     * Runs during test mode
     */
    public void test() 
    {
        System.out.println("Robot.test()");
    }
}