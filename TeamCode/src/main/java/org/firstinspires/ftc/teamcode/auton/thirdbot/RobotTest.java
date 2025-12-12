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
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loader.TripleBallQuadLoader;

@Autonomous(name = "Robot Test")
public class RobotTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        TripleBallQuadLoader loader = new TripleBallQuadLoader(hardwareMap);

        Action runLauncher = new ParallelAction(
                launcher.revLauncher(250)
        );

        Action runLoader = new ParallelAction(
                loader.start()
        );

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                runLoader,
                new SleepAction(2),
                runLauncher
        ));

    }
}
