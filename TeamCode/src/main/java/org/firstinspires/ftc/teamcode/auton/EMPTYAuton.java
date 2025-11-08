package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.action.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.action.loader.SingleBallLoader;

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
