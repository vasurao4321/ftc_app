package org.firstinspires.ftc.team8923_2017;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Main robot code center, hold all the information needed to run the robot
 */

public abstract class Master extends LinearOpMode
{
    // Declare motors here
    DcMotor motorFL = null;
    DcMotor motorFR = null;
    DcMotor motorBL = null;
    DcMotor motorBR = null;

    // Declare servos here

    // Declare any neccessary sensors here
    BNO055IMU imu;

    void InitHardware()
    {
        // Motors here
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");

        // Servos here

        // Sensors here
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    // 45 denotes the angle at which the motors are mounted in referece to the chassis frame
    void driveOmni45(double driveAngle, double drivePower, double turnPower)
    {
        // Calculate out x and y directions of drive power, where y is forward (0 degrees) and x is right (-90 degrees)
        double x = drivePower * -Math.sin(Math.toRadians(driveAngle));
        double y = drivePower * Math.cos(Math.toRadians(driveAngle));

        /*
         * to explain:
         * each wheel omni wheel exerts a force in one direction like tank but differs in the fact that they have rollers mounted perpendicular
         * to the wheels outer edge so they can passively roll at 90 degrees to the wheel's facing. This means that with the wheels mounted at 45
         * degrees to the chassis frame and assuming the left hand rule for the motors, each wheel's power needs to be 90 degrees out of phase
         * from the previous wheel on the unit circle starting with positive y and x for FL and going clockwise around the unit circle and robot
         * from there
         */

        double powerFL = y + x + turnPower;
        double powerFR = -y + x + turnPower;
        double powerBL = y - x + turnPower;
        double powerBR = -y - x + turnPower;

        motorFL.setPower(powerFL);
        motorFR.setPower(powerFR);
        motorBL.setPower(powerBL);
        motorBR.setPower(powerBR);
    }

    // Used for calculating distances between 2 points
    double calculateDistance(double deltaX, double deltaY)
    {
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }
}