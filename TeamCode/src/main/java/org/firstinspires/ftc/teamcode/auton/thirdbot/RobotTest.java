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
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        TripleBallQuadLoader loader = new TripleBallQuadLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap, telemetry);

        Action runLauncher = launcher.revLauncher(200);

        Action stopLauncher = launcher.stopLauncher();

        Action runIntake = intake.startActiveIntake();

        Action stopIntake = intake.stopActiveIntake();

        Action runLoader = loader.start();

        Action stopLoader = loader.stop();

        waitForStart();

        Actions.runBlocking(
                        new SequentialAction(
                                new SleepAction(2),
                                loader.start(),
                                runIntake,
                                runLauncher,
//                                new SleepAction(3),
                                new SleepAction(3000)
//                                stopLoader,
//                                stopLauncher
                        )
        );

    }
}
