package org.firstinspires.ftc.teamcode.auton.thirdbot;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.PIDFlyWheel;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loader.TripleBallQuadLoader;

@Autonomous(name = "Robot Test")
public class RobotTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(0,0,deg(0));
        //MecanumDrive drive = new MecanumDrive(hardwareMap,startingPos);
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        TripleBallQuadLoader loader = new TripleBallQuadLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap, telemetry);
//        Action runLauncher = new ParallelAction(
//                launcher.revLauncher(250)
//        );
        Action runIntake = new ParallelAction(
                intake.startActiveIntake()
        );
        Action stopIntake = new ParallelAction(
                intake.stopActiveIntake()
        );

        Action runLoader = new ParallelAction(
                loader.start()
        );

        waitForStart();

        Actions.runBlocking(new SequentialAction(
                runIntake,
                runLoader,
                new SleepAction(30),
                stopIntake
        ));

    }
}
