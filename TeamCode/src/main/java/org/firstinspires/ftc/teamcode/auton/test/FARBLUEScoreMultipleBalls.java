package org.firstinspires.ftc.teamcode.auton.test;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.module.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.module.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.module.loader.SingleBallLoader;

import java.util.Arrays;

@Autonomous(name = "CLOSE BLUE Multiple Balls")
public class FARBLUEScoreMultipleBalls extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(-52.5,-46.5,deg(235));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        Pose2d goalPos = new Pose2d(-22.5, -14, deg(235));
        Vector2d goalVector = new Vector2d(-22.5, -14);
        Pose2d ball2Pos = new Pose2d(-11, -34, deg(270));
        Pose2d ball3Pos = new Pose2d(-11, -38, deg(270));
        Pose2d ball4Pos = new Pose2d(-11, -43, deg(270));

        final float launcherPower = 0.63f;

        MinVelConstraint velConstraintForBalls = new MinVelConstraint(
                Arrays.asList(
                        new TranslationalVelConstraint(15), // 15 in. per sec cap
                        new AngularVelConstraint(Math.toRadians(180) // 180 deg per sec cap
                )));

        Action goToGoal1 = drive.actionBuilder(startingPos)
                .setTangent(deg(55))
                .splineToConstantHeading(
                        goalVector, deg(55))
                .build();

        Action goToBall2 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball2Pos, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal2 = drive.actionBuilder(ball2Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), velConstraintForBalls)
                .build();

        Action goToBall3 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball3Pos, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal3 = drive.actionBuilder(ball3Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), velConstraintForBalls)
                .build();

        Action goToBall4 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball4Pos, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal4 = drive.actionBuilder(ball4Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), velConstraintForBalls)
                .build();

        Action fullBall1 = new SequentialAction(
                launcher.startLauncher(launcherPower),
                goToGoal1,
                loader.startSingleBallLoader(),
                new SleepAction(1),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall2 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall2,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal2,
                loader.startSingleBallLoader(),
                new SleepAction(1),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall3 = new SequentialAction(
                intake.stopActiveIntake(),
                goToBall3,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal3,
                loader.startSingleBallLoader(),
                new SleepAction(1),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall4 = new SequentialAction(
                intake.stopActiveIntake(),
                goToBall4,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal4,
                loader.startSingleBallLoader(),
                new SleepAction(1),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullAction = new SequentialAction(
                fullBall1,
                fullBall2,
                fullBall3,
                fullBall4
        );

        waitForStart();

        Actions.runBlocking(fullAction);

    }
}
