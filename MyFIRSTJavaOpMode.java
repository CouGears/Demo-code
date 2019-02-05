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
    private DcMotor motorSide;
    //private Blinker expansion_Hub_2;
    private ColorSensor sensorColorRange;
    private Servo servoMarker;
    private Servo servoHolder;
    private Servo servo3;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;
    
    
    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");

        motorSide = hardwareMap.get(DcMotor.class, "motorSide");
        //expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColorRange");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        servoMarker = hardwareMap.get(Servo.class, "servoMarker");
        servoHolder = hardwareMap.get(Servo.class, "servoHolder");
        servo3 = hardwareMap.get(Servo.class, "servo3");
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
                tgtPower = ((this.gamepad1.left_stick_y)/2)+((-this.gamepad1.right_stick_x)/2);
                tgtPower2 = -((this.gamepad1.left_stick_y)/2)+((-this.gamepad1.right_stick_x)/2);
            }
            if(x == 1){ 
                tgtPower = ((this.gamepad1.left_stick_y)/5)+((-this.gamepad1.right_stick_x)/5);
                tgtPower2 = -((this.gamepad1.left_stick_y)/5)+((-this.gamepad1.right_stick_x)/5);
            }
            if(x == 2){ 
                tgtPower = ((this.gamepad1.left_stick_y)/10)+((-this.gamepad1.right_stick_x)/10);
                tgtPower2 = -((this.gamepad1.left_stick_y)/10)+((-this.gamepad1.right_stick_x)/10);
            }
            if(x == 3){ 
                tgtPower = ((this.gamepad1.left_stick_y)/3)+((-this.gamepad1.right_stick_x)/3);
                tgtPower2 = -((this.gamepad1.left_stick_y)/3)+((-this.gamepad1.right_stick_x)/3);
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
            
            //Veritical Arm
            motorSide.setPower(-this.gamepad2.left_stick_y/3);
            
            if (this.gamepad2.right_stick_y < 1) {
                servo3.setPosition(0);
            }
            else {
                servo3.setPosition(1);
            }
            
            if (this.gamepad2.a) {
                servoHolder.setPosition(0);
            }
            
            else if (this.gamepad2.b) {
                servoHolder.setPosition(1);
            }
            
            else if (this.gamepad2.x) {
                servoMarker.setPosition(1);
            }
            else if (this.gamepad2.y) {
                servoMarker.setPosition(1);
            }
            
            // Color sensor
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);
            
            // Screen updates
            telemetry.addData("Motor R Power", motorRight.getPower());
            telemetry.addData("Motor L Power", motorLeft.getPower());
            telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
        }
    }
}
