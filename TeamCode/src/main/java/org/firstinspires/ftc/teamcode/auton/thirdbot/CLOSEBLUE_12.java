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
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loading.TripleBallQuadLoader;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.PIDFlyWheel;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.Stopper;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loading.Lifter;

import java.util.Arrays;

@Autonomous(name = "CLOSE BLUE")
public class CLOSEBLUE_12 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //Making the modules and startingPos
        Pose2d startingPos = new Pose2d(-52.5, -46.5, deg(235));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        TripleBallQuadLoader loader = new TripleBallQuadLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap, telemetry);
        Stopper stopper = new Stopper(hardwareMap, telemetry);
        Lifter lifter = new Lifter(hardwareMap, telemetry);

        Pose2d goalPos = new Pose2d(-22.5, -14, deg(235));
        Vector2d goalVector = new Vector2d(-22.5, -14);
        Pose2d trip1pos = new Pose2d(-11.5, -45, deg(270));
        Pose2d trip2pos = new Pose2d(11.5, -45, deg(270));
        Pose2d trip3pos = new Pose2d(34.5, -45, deg(270));

        MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(18), // 15 in. per sec cap
                new AngularVelConstraint(deg(220) // 180 deg per sec cap
                )));

        MinVelConstraint fastVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(25), // 15 in. per sec cap
                new AngularVelConstraint(deg(220) // 180 deg per sec cap
                )));

        //moving actions
        Action goToGoal1 = drive.actionBuilder(startingPos)
                .strafeTo(
                        goalVector)
                .build();

        Action goToSecondBalls = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(trip1pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal2 = drive.actionBuilder(trip1pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToThirdBalls = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(trip2pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal3 = drive.actionBuilder(trip2pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToFourthBalls = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(trip3pos, deg(270), slowVelConstraint)
                .build();

        Action goToGoal4 = drive.actionBuilder(trip3pos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        //actions with shooting+moving

        Action scoreBalls = new SequentialAction(
                lifter.lift(), // shoot
                new SleepAction(0.4),
                lifter.reset(), // lower
                loader.start(), // load ball
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.4),
                lifter.reset(),
                loader.start(),
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.4),
                lifter.reset()
        );

        Action scoreFirstBalls = new SequentialAction(
                new ParallelAction(
                    goToGoal1,
                    stopper.open()
                ),

                scoreBalls,
                stopper.initiate()
        );

        Action getSecondBalls = new SequentialAction(
                new ParallelAction(
                    intake.start(),
                    loader.start(),
                    goToSecondBalls
                ),

                intake.stop()
        );

        Action scoreSecondBall = new SequentialAction(
                new ParallelAction(
                        goToGoal2,
                        new SequentialAction(
                                new SleepAction(1),
                                loader.stop(),
                                stopper.open()
                        )
                ),

                scoreBalls,
                stopper.initiate()
        );

        Action getThirdBalls = new SequentialAction(
                new ParallelAction(
                        intake.start(),
                        loader.start(),
                        goToThirdBalls
                ),

                intake.stop()
        );

        Action scoreThirdBalls = new SequentialAction(
                new ParallelAction(
                        goToGoal3,
                        new SequentialAction(
                                new SleepAction(1),
                                loader.stop(),
                                stopper.open()
                        )
                ),

                scoreBalls,
                stopper.initiate()
        );

        Action getFourthBalls = new SequentialAction(
                new ParallelAction(
                        intake.start(),
                        loader.start(),
                        goToFourthBalls
                ),

                intake.stop()
        );

        Action scoreFourthBalls = new SequentialAction(
                new ParallelAction(
                        goToGoal4,
                        new SequentialAction(
                                new SleepAction(1),
                                loader.stop(),
                                stopper.open()
                        )
                ),

                scoreBalls,
                stopper.initiate()
        );


        Action fullAction = new SequentialAction(
                scoreFirstBalls,
                getSecondBalls,
                scoreSecondBall,
                getThirdBalls,
                scoreThirdBalls,
                getFourthBalls,
                scoreFourthBalls
        );

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(launcher.revLauncher(175), fullAction)
        );

    }
}
