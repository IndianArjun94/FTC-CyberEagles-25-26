package org.firstinspires.ftc.teamcode.auton.thirdbot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.PIDFlyWheel;

@Autonomous(name = "PID Launcher Test")
public class PIDLauncherTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);

        Action runLauncher = new ParallelAction(
                launcher.revLauncher(250),
//                launcher.getRPM(),
                new SequentialAction(
                        new SleepAction(2000),
                        launcher.stopLauncher()
                )
        );

        waitForStart();

        Actions.runBlocking(runLauncher);

    }
}
