package org.firstinspires.ftc.teamcode.auton.firstbot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "EMPTY Auton")
public class EMPTYAuton extends OpMode {

    @Override
    public void init() {
        telemetry.addData("sig", "sig");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("sig2", "sig");
        telemetry.update();
    }
}
