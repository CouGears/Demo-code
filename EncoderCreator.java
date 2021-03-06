package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.util.Timer;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Locale;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

@Autonomous

public class EncoderCreator extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorSide;
    //private Blinker expansion_Hub_2;
    private ColorSensor sensorColorRange;
    private Servo servoMarker;
    private Servo servoHolder;
    private Servo servo3;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;
    int currentSide = 0;
    int current = 0;
    
    public void motors (String motor, int distance, double speed) {
        if (motor.equals("arm")) {
            motorSide.setTargetPosition(current + distance);
            motorSide.setPower(speed);
            
            while ((motorSide.getCurrentPosition() != distance + current) && opModeIsActive()) {
            }
            
            current = motorSide.getCurrentPosition();
        }
        
        else if (motor.equals("drive")) {
            motorLeft.setTargetPosition(current + distance);
            motorLeft.setPower(speed);
            motorRight.setTargetPosition(current - distance);
            motorRight.setPower(-speed);
            
            while ((motorLeft.getCurrentPosition() != distance + current) && opModeIsActive()) {
            }
            
            current = motorLeft.getCurrentPosition();
        }
        
        else if (motor.equals("turn")) {
            motorLeft.setTargetPosition(current + distance);
            motorLeft.setPower(speed);
            motorRight.setTargetPosition(current + distance);
            motorRight.setPower(speed);
            
            while ((motorLeft.getCurrentPosition() != distance + current) && opModeIsActive()) {
            }
            
            current = motorLeft.getCurrentPosition();
        }
    }
    
    @Override
    public void runOpMode() {
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorSide = hardwareMap.get(DcMotor.class, "motorSide");
        //expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColorRange");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        servoMarker = hardwareMap.get(Servo.class, "servoMarker");
        servoHolder = hardwareMap.get(Servo.class, "servoHolder");
        servo3 = hardwareMap.get(Servo.class, "servo3");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorSide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        //final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        int times = 0;
        
        //actual autonomous Code
        while (opModeIsActive()) {
            //Have the robot lower itself
            motors("arm", -8500, 1);
            
            //move off of the hook
            motors("drive", 600, .5);
            motors("turn", 200, .5);
            motors("drive", 200, .5);
            motors("turn", 600, .5);
            
            //drive to block
            motors("drive", 3000, .5);
            /*
            motorRight.setPower(.5);
            motorLeft.setPower(.5);
            try {
                Thread.sleep(400);
            }
            catch (InterruptedException e) {}
            motorRight.setPower(-.5);
            motorLeft.setPower(.5);
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {}
            
            motorRight.setPower(0);
            motorLeft.setPower(0);
            
            //sample
            int runs = 0;
            for (int i = 0; i < 2; i++){
                //stop in front of block so we know it works
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {}
                
                Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                        (int) (sensorColor.green() * SCALE_FACTOR),
                        (int) (sensorColor.blue() * SCALE_FACTOR),
                        hsvValues);
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.update();
                
                // If the object is yellow
                if (hsvValues[0] < 65) {
                    //drive forward to push it off
                    motorRight.setPower(-.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {}
                    //stop
                    motorRight.setPower(0);
                    motorLeft.setPower(0);
                    runs = i+1;
                    i = 3;
                    break;
                }
                
                // If the first two objects are white
                else if (i == 1){
                    //back up
                    motorRight.setPower(.5);
                    motorLeft.setPower(-.5);
                    try {
                        Thread.sleep(1000);//change
                    }
                    catch (InterruptedException e) {}
                    
                    //turn 90 degrees
                    motorRight.setPower(.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(1000);//change
                    }
                    catch (InterruptedException e) {}
                    
                    //move forward
                    motorRight.setPower(-.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(1500);
                    }
                    catch (InterruptedException e) {}
                    
                    //drive to the block
                    motorRight.setPower(-.5);
                    motorLeft.setPower(-.5);
                    try {
                        Thread.sleep(1000);//change
                    }
                    catch (InterruptedException e) {}
                    
                    motorRight.setPower(-.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(750);//change
                    }
                    catch (InterruptedException e) {}
                    
                    //push the block off
                    motorRight.setPower(-.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {}
                    
                    //stop
                    motorRight.setPower(0);
                    motorLeft.setPower(0);
                    runs = 3;
                }
                
                // If the object is white
                else {
                    //back up
                    motorRight.setPower(.5);
                    motorLeft.setPower(-.5);
                    try {
                        Thread.sleep(1000);//change
                    }
                    catch (InterruptedException e) {}
                    
                    //turn 90 degrees
                    motorRight.setPower(.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(1000);//change
                    }
                    catch (InterruptedException e) {}
                    
                    //move forward
                    motorRight.setPower(-.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(1500);
                    }
                    catch (InterruptedException e) {}
                    
                    //drive to the block
                    motorRight.setPower(-.5);
                    motorLeft.setPower(-.5);
                    try {
                        Thread.sleep(1000);//change
                    }
                    catch (InterruptedException e) {}
                    
                    motorRight.setPower(-.5);
                    motorLeft.setPower(.5);
                    try {
                        Thread.sleep(750);//change
                    }
                    catch (InterruptedException e) {}
                    
                    //stop
                    motorRight.setPower(0);
                    motorLeft.setPower(0);
                }
                
            }
            */
        }
    }
}

