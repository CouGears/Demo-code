package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import java.util.Locale;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

@TeleOp//(name = "Sensor: REVColorDistance", group = "Sensor")

public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorSide1;
    private DcMotor motorSide2;
    //private DigitalChannel digitalTouch;
    private Blinker expansion_Hub_2;
    private ColorSensor sensorColorRange;
    private Servo servo1;
    private Servo servo2;
    private Servo servoClaw1;
    private Servo servoClaw2;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;
    
    
    @Override
    public void runOpMode() {
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorSide1 = hardwareMap.get(DcMotor.class, "motorSide1");
        motorSide2 = hardwareMap.get(DcMotor.class, "motorSide2");
        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColorRange");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servoClaw1 = hardwareMap.get(Servo.class, "servoClaw1");
        servoClaw2 = hardwareMap.get(Servo.class, "servoClaw2");
        // set digital channel to input mode.
        //digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        //final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        double tgtPower = 0;
        double tgtPower2 = 0;
        double tgtPowerArmBase = 0;
        double tgtPowerArmTop = 0;
        double servoPos1 = 1;
        double servoPos2 = 0.5;
        double servoPosClaw1 = 1;
        double servoPosClaw2 = 0.5;
        int x = 0;
        int times = 0;
        
        // Controller operated
        while (opModeIsActive()) {
            
            // Wheel movements
            
            if(gamepad1.a){
                x = 0;
            }
            if(gamepad1.b){
                x = 1;
            }
            if(gamepad1.x){
                x = 2;
            }
            if(gamepad1.y){
                x = 3;
            }
            
            if(x == 0){ 
                tgtPower = ((-this.gamepad1.left_stick_y)/2)+((-this.gamepad1.right_stick_x)/2);
                tgtPower2 = -((-this.gamepad1.left_stick_y)/2)+((-this.gamepad1.right_stick_x)/2);
            }
            if(x == 1){ 
                tgtPower = ((-this.gamepad1.left_stick_y)/5)+((-this.gamepad1.right_stick_x)/5);
                tgtPower2 = -((-this.gamepad1.left_stick_y)/5)+((-this.gamepad1.right_stick_x)/5);
            }
            if(x == 2){ 
                tgtPower = ((-this.gamepad1.left_stick_y)/10)+((-this.gamepad1.right_stick_x)/10);
                tgtPower2 = -((-this.gamepad1.left_stick_y)/10)+((-this.gamepad1.right_stick_x)/10);
            }
            if(x == 3){ 
                tgtPower = ((-this.gamepad1.left_stick_y)/3)+((-this.gamepad1.right_stick_x)/3);
                tgtPower2 = -((-this.gamepad1.left_stick_y)/3)+((-this.gamepad1.right_stick_x)/3);
            }
            
            if (tgtPower > .5) {
                tgtPower = .5;
            }
            else if (tgtPower < -.5) {
                tgtPower = -.5;
            }
            if (tgtPower2 > .5) {
                tgtPower2 = .5;
            }
            else if (tgtPower2 < -.5) {
                tgtPower2 = -.5;
            }
            motorRight.setPower(tgtPower);
            motorLeft.setPower(tgtPower2);
            
            //Arm
            
            if(gamepad2.right_bumper){
                servoPos1 = 1;
            }
            else if(gamepad2.left_bumper){
                servoPos1 = 0.65;
            }
            else {
                
            }
            servo1.setPosition(servoPos1);
            
            if(gamepad2.x){
                servoPosClaw1 = 1;
            }
            else if(gamepad2.y){
                servoPosClaw1 = 0.5;
            }
            else {
                
            }
            servoClaw1.setPosition(servoPosClaw1);
            
            if(gamepad2.a){
                servoPosClaw2 = 1;
            }
            else if(gamepad2.b){
                servoPosClaw2 = 0.5;
            }
            else {
                
            }
            servoClaw2.setPosition(servoPosClaw2);
            
            motorSide1.setPower(this.gamepad2.left_stick_y/4);
            motorSide2.setPower(this.gamepad2.left_stick_y/4);
            
            // Color sensor
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);
            
            // Screen updates
            telemetry.addData("Servo Position", servo1.getPosition());
            telemetry.addData("Motor R Power", motorRight.getPower());
            telemetry.addData("Motor L Power", motorLeft.getPower());
            telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("Hue", hsvValues[0]);
            
            // is button pressed?
            /*if (digitalTouch.getState() == false) {
                // button is pressed.
                telemetry.addData("Button", "PRESSED");
            } 
            else {
                // button is not pressed.
                telemetry.addData("Button", "NOT PRESSED");
            }
            telemetry.addData("Status", "Running");*/
            
            /*relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });*/

            telemetry.update();
        }
        /*relativeLayout.post(new Runnable() {
            public void run() {
                //relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });*/
    }
}
