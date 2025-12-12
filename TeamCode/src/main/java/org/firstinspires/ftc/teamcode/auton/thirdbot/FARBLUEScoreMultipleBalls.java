package org.firstinspires.ftc.teamcode.auton.thirdbot;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.legacy_autons.legacy_modules.first_second_bot_modules.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.legacy_autons.legacy_modules.first_second_bot_modules.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.legacy_autons.legacy_modules.first_second_bot_modules.loader.SingleBallLoader;

import java.util.Arrays;

@Autonomous(name = "CLOSE BLUE Multiple Balls")
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
        //Pose2d ball2Pos = new Pose2d(-13, -27.5, deg(270));
        //Pose2d ball3Pos = new Pose2d(-13, -32, deg(270));
        Pose2d trip1pos = new Pose2d(-13, -36.5, deg(270));
//        Pose2d ball5Pos = new Pose2d(11.5, -26.5, deg(270));
//        Pose2d ball6Pos = new Pose2d(11.5, -31, deg(270));
        Pose2d trip2pos = new Pose2d(11.5, -35.5, deg(270));
        Pose2d trip3pos = new Pose2d(11.5, -35.5, deg(270));

        final float launcherPower = 0.66f;
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
                .setTangent(deg(55))
                .splineToConstantHeading(
                        goalVector, deg(55))
                .build();

        Action trip1 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(trip1pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal2 = drive.actionBuilder(trip1pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action trip2 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(trip2pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal3 = drive.actionBuilder(trip2pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action trip3 = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(trip3pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal4 = drive.actionBuilder(trip3pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action threeBall1 = new SequentialAction(
                launcher.startLauncher(launcherPower-0.05),
                goToGoal1,
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime+0.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action threeBall2 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),
                trip1,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower+0.035),
                new ParallelAction(
                        goToGoal2,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime+0.25),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action threeBall3 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                trip2,
//                intake.stopActiveIntake(),
                loader.stopSingleBallLoader(),
                launcher.startLauncher(launcherPower+0.035),
                new ParallelAction(
                        goToGoal3,
                        new SequentialAction(
                                new SleepAction(0.3),
                                intake.stopActiveIntake()
                        )),
                loader.startSingleBallLoader(),
                new SleepAction(sleepTime+0.25),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );

        Action threeBall4 = new SequentialAction(
                intake.startActiveIntake(),
                launcher.ejectLauncher(),

                trip3,
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



        Action fullAction = new SequentialAction(
                threeBall1,
                threeBall2,
                threeBall3,
                threeBall4
        );

        waitForStart();

        Actions.runBlocking(fullAction);

    }
}
