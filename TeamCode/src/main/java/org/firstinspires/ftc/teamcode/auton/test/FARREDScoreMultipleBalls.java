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

@Autonomous(name = "CLOSE RED Multiple Balls")
public class FARREDScoreMultipleBalls extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(63,15,deg(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        Pose2d goalPos = new Pose2d(-22.5, 14, deg(130));
        Vector2d goalVector = new Vector2d(-22.5, 14);
        Pose2d ball2Pos = new Pose2d(-10, 28, deg(90));
        Pose2d ball3Pos = new Pose2d(-10, 32, deg(90));
        Pose2d ball4Pos = new Pose2d(-10, 37, deg(90));
        Pose2d ball5Pos = new Pose2d(12.5, 26, deg(90));
        Pose2d ball6Pos = new Pose2d(12.5, 30, deg(90));
        Pose2d ball7Pos = new Pose2d(12.5, 35, deg(90));

        final float launcherPower = 0.68f;
        final float sleepTime = 0.75f;

        MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(18), // 15 in. per sec cap
                new AngularVelConstraint(deg(180) // 180 deg per sec cap
                )));

        MinVelConstraint fastVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(25), // 15 in. per sec cap
                new AngularVelConstraint(deg(180) // 180 deg per sec cap
                )));


        Action goToGoal1 = drive.actionBuilder(startingPos)
                .setTangent(deg(305))
                .splineToLinearHeading(
                        goalPos, deg(270))
                .build();

        Action goToBall2 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball2Pos, deg(90), slowVelConstraint)
                .build();

        Action goToGoal2 = drive.actionBuilder(ball2Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall3 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball3Pos, deg(90), slowVelConstraint)
                .build();

        Action goToGoal3 = drive.actionBuilder(ball3Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall4 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball4Pos, deg(90), slowVelConstraint)
                .build();

        Action goToGoal4 = drive.actionBuilder(ball4Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall5 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball5Pos, deg(90), fastVelConstraint)
                .build();

        Action goToGoal5 = drive.actionBuilder(ball5Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall6 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball6Pos, deg(90), fastVelConstraint)
                .build();

        Action goToGoal6 = drive.actionBuilder(ball6Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall7 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball7Pos, deg(90), fastVelConstraint)
                .build();

        Action goToGoal7 = drive.actionBuilder(ball7Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();



        Action fullBall1 = new SequentialAction(
                launcher.startLauncher(launcherPower-0.06),
                goToGoal1,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime+0.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall2 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall2,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower+0.06),
                goToGoal2,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall3 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall3,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal3,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall4 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall4,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal4,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall5 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall5,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal5,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall6 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall6,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal6,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall7 = new SequentialAction(
                intake.startActiveIntake(),
                goToBall7,
                intake.stopActiveIntake(),
                launcher.startLauncher(launcherPower),
                goToGoal7,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullAction = new SequentialAction(
                fullBall1,
                fullBall2,
                fullBall3,
                fullBall4,
                fullBall5,
                fullBall6,
                fullBall7
        );

        waitForStart();

        Actions.runBlocking(fullAction);

    }
}
