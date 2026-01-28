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
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.Lifter;

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

        Pose2d goalPos = new Pose2d(-23.5, -17, deg(225)); // DO NOT INTERCHANGE BETWEEN RED/BLUE
        Vector2d goalVector = new Vector2d(-23.5, -17);

        Pose2d secondBallsHalfwayPos = new Pose2d(-11.5, -20, deg(270));
        Pose2d secondBallsPos = new Pose2d(-11.5, -42.5, deg(270));
        Vector2d secondBallsVector = new Vector2d(-11.5, -45);

        Pose2d thirdBallsHalfwayPos = new Pose2d(11.5, -21, deg(270));
        Pose2d thirdBallsPos = new Pose2d(11.5, -42.5, deg(270));
        Vector2d thirdBallsVector = new Vector2d(11.5, -45);

        Pose2d fourthBallsHalfwayPos = new Pose2d(35.5, -28, deg(270));
        Pose2d fourthBallsPos = new Pose2d(35.5, -42.5, deg(270));
        Vector2d fourthBallsVector = new Vector2d(35.5, -45);

        MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(15), // 15 in. per sec cap
                new AngularVelConstraint(deg(220) // 180 deg per sec cap
                )));

        Action goToGoal1 = drive.actionBuilder(startingPos)
                .setTangent(deg(45))
                .strafeToLinearHeading(goalVector, deg(225)) // DO NOT INTERCHANGE BETWEEN RED/BLUE
                .build();

//        Action goToGoal1 = drive.actionBuilder(startingPos)
//                .lineToX(-15)
//                .build();

        Action goToSecondBalls = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToSplineHeading(secondBallsHalfwayPos, deg(270), slowVelConstraint)
                // Finish the rest of the distance at that fixed heading
                .splineToConstantHeading(secondBallsVector, deg(270), slowVelConstraint)
                .build();

        Action goToGoal2 = drive.actionBuilder(secondBallsPos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(120))
                .build();

        Action goToThirdBalls = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToSplineHeading(thirdBallsHalfwayPos, deg(270))
                // Finish the rest of the distance at that fixed heading
                .splineToConstantHeading(thirdBallsVector, deg(270), slowVelConstraint)
                .build();

        Action goToGoal3 = drive.actionBuilder(thirdBallsPos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180))
                .build();

        Action goToFourthBalls = drive.actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToSplineHeading(fourthBallsHalfwayPos, deg(270))
                // Finish the rest of the distance at that fixed heading
                .splineToConstantHeading(fourthBallsVector, deg(270), slowVelConstraint)
                .build();

        Action goToGoal4 = drive.actionBuilder(fourthBallsPos)
                .setTangent(deg(90))
                .splineToLinearHeading(goalPos, deg(180))
                .build();

        //actions with shooting+moving

        Action scoreFirstBalls = new SequentialAction(
                new ParallelAction(
                        goToGoal1,
                        stopper.open()
                ),

                new SleepAction(1),

                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(), // lower
                loader.start(), // load ball
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
                loader.start(),
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
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

                new SleepAction(0.3),

                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(), // lower
                loader.start(), // load ball
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
                loader.start(),
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
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

                new SleepAction(0.3),

                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(), // lower
                loader.start(), // load ball
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
                loader.start(),
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
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

                new SleepAction(0.3),

                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(), // lower
                loader.start(), // load ball
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
                loader.start(),
                new SleepAction(0.4),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.25),
                lifter.reset(),
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
                new ParallelAction(launcher.revLauncher(165), fullAction)
        );

    }
}
