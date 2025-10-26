package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.action.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.action.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.action.loader.LauncherPower;
import org.firstinspires.ftc.teamcode.auton.action.loader.SingleBallLoader;

@Autonomous(name = "Launcher Bot Auton Test")
public class LauncherBotAutonTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(0, 0, deg(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Intake intake = new Intake(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);

        Action action = new SequentialAction(
                intake.startActiveIntake(), // start intake
                drive.actionBuilder(initialPose) // go forward
                        .lineToY(20)
                        .build(),
                intake.stopActiveIntake(), // stop intake
                launcher.startLauncher(LauncherPower.LOW), // start revving launcher at low power
                loader.startSingleBallLoader(), // start active loader
                new SleepAction(4), // wait 4 seconds
                loader.stopSingleBallLoader(), // stop loader
                launcher.stopLauncher() // stop launcher
        );

        if (isStopRequested()) return;
        waitForStart();

        Actions.runBlocking(action);

    }
}
