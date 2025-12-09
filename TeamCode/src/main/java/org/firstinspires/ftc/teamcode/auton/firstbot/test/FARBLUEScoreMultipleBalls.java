package org.firstinspires.ftc.teamcode.auton.firstbot.test;

import static org.firstinspires.ftc.teamcode.auton.firstbot.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.firstbot.module.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.firstbot.module.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.firstbot.module.loader.SingleBallLoader;

import java.util.Arrays;

@Disabled
@Autonomous(name = "FAR BLUE Multiple Balls")
public class FARBLUEScoreMultipleBalls extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(63,-15,deg(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap, drive, telemetry);

        Pose2d goalPos = new Pose2d(-22.5, -14, deg(229));
        Vector2d goalVector = new Vector2d(-22.5, -14);
        Pose2d ball2Pos = new Pose2d(-13, -30, deg(270));
        Pose2d ball3Pos = new Pose2d(-13, -36, deg(270));
        Pose2d ball4Pos = new Pose2d(-13, -41, deg(270));
        Pose2d ball5Pos = new Pose2d(11.5, -29.5, deg(270));
        Pose2d ball6Pos = new Pose2d(11.5, -34, deg(270));
        Pose2d ball7Pos = new Pose2d(11.5, -38, deg(270));

        final float launcherPower = 0.69f;
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
                .setTangent(deg(160))
                .splineTo(
                        goalVector, deg(190))
                .build();

        Action goToBall2 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball2Pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal2 = drive.actionBuilder(ball2Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall3 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball3Pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal3 = drive.actionBuilder(ball3Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall4 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball4Pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal4 = drive.actionBuilder(ball4Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall5 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball5Pos, deg(270), fastVelConstraint)
                .build();

        Action goToGoal5 = drive.actionBuilder(ball5Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall6 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball6Pos, deg(270), fastVelConstraint)
                .build();

        Action goToGoal6 = drive.actionBuilder(ball6Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall7 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball7Pos, deg(270), fastVelConstraint)
                .build();

        Action goToGoal7 = drive.actionBuilder(ball7Pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action fullBall1 = new SequentialAction(
                launcher.startLauncher(launcherPower-0.05),
                goToGoal1,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime+0.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall2 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                goToBall2,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower),
                new ParallelAction(
                        goToGoal2,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall3 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                goToBall3,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower),
                new ParallelAction(
                        goToGoal3,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall4 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                goToBall4,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower),
                new ParallelAction(
                        goToGoal4,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall5 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                goToBall5,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower),
                new ParallelAction(
                        goToGoal5,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall6 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                goToBall6,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower),
                new ParallelAction(
                        goToGoal6,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action fullBall7 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                goToBall7,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower),
                new ParallelAction(
                        goToGoal7,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
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
