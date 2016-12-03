package org.firstinspires.ftc.team417;
import android.graphics.Bitmap;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Image;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.Locale;

abstract public class MasterOpMode extends LinearOpMode
{
    DcMotor motorFrontLeft = null;
    DcMotor motorBackLeft = null;
    DcMotor motorFrontRight = null;
    DcMotor motorBackRight = null;
    DcMotor motorLift = null;
    BNO055IMU imu;
    Orientation angles;
    DeviceInterfaceModule dim;                  // Device Object
    DigitalChannel liftSwitch;                // Device Object



    private ElapsedTime runtime = new ElapsedTime();

    // Declare constants
    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder, this was changed when we switched to 40:1 motors
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    // static final double DRIVE_GEAR_REDUCTION = 0.25;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 6.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double COUNTS_PER_MM = COUNTS_PER_INCH / 25.4;
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;


    public void initializeHardware()
    {
        // Initialize motors to be the hardware motors
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorLift = hardwareMap.dcMotor.get("motorLift");

        // get a reference to a Modern Robotics DIM, and IO channels.
        //dim = hardwareMap.get(DeviceInterfaceModule.class, "dim");   //  Use generic form of device mapping
        liftSwitch  = hardwareMap.get(DigitalChannel.class, "liftSwitch");     //  Use generic form of device mapping
        liftSwitch.setMode(DigitalChannelController.Mode.INPUT);          // Set the direction of each channel

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorLift.setPower(0);

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        //CodeReview: have you run the calibration sample opmode and created this file? You should ensure this file exists
        //            if you are going to reference it here. Otherwise you should not set this variable (it's not required).
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //CodeReview: I believe it's optional to have an accelerationIntegrationAlgorithm and you don't need to specify it here.
        //            This particular one doesn't seem to be providing your robot any benefit other than adding to logs.


        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //CodeReview: just curious why you are getting the heading 3 times during your initialization routine. Doesn't seem necessary.
        float angle;
        for (int i = 0; i < 3; i++) {
            sleep(100);
            angle = imu.getAngularOrientation().firstAngle;
        }

    }

    //CodeReview: it's good to provide a comment explaining each method. In this case, you're normalizing the angle to be
    //            between -180..180, which is good to document. Hmm, might be better to rename this method "normalizeAngle".
    public double adjustAngles(double angle)
    {
        while(angle > 180)
            angle -= 360;
        while(angle < -180)
            angle += 360;
        return angle;
    }


    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
