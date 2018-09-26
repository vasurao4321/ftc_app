package org.firstinspires.ftc.team8923_2018;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp Competition")

class TeleOpCompetition extends MasterTeleOp
{
    @Override
    public void runOpMode()
    {
        InitHardware();

        waitForStart();

        while (opModeIsActive())
        {
            driveMecanumTeleOp();
        }
    }
}