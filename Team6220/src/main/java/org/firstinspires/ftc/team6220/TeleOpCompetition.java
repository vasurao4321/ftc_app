package org.firstinspires.ftc.team6220;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    Competition configuration for driving robot.
    Pilot controls:


    Co-pilot controls:



*/
@TeleOp(name="TeleOpCompetition", group="6220")
public class TeleOpCompetition extends MasterTeleOp
{
    ElapsedTime timer = new ElapsedTime();

    //CodeReview: Define an enum for reading/writing the elements of your lastBtn array instead of using magic numbers in your code.
    //temporary tap trigger variable
    //                                   a      b      x      y
    boolean lastBtn[] = new boolean[]{false,false,false,false};

    @Override
    public void runOpMode() throws InterruptedException
    {
        initializeHardware();

        waitForStart();

        while (opModeIsActive())
        {
            driveRobotWithJoysticks(-gamepad1.left_stick_x/2,    //local x motion power; reversed
                                     gamepad1.left_stick_y,     //local y motion power
                                     gamepad1.right_stick_x);    //rotation power; reversed

            updateLocationUsingEncoders(); //CodeReview: what is this getting you? Seems like it reads data that you never use again.


            if (gamepad2.x && !lastBtn[2])
            {
                motorToggler.toggleMotor();
            }

            if (gamepad2.b && !lastBtn[1])
            {
                motorTogglerReverse.toggleMotor();
            }
            lastBtn[0] = gamepad2.a;
            lastBtn[1] = gamepad2.b;
            lastBtn[2] = gamepad2.x;
            lastBtn[3] = gamepad2.y;

            idle();
        }
    }
}
