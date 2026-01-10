package net.cybereagles.meepmeeptesting.autondev.blue.thirdbot;

import static net.cybereagles.meepmeeptesting.MeepMeepTesting.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Arrays;
public class CloseRedThirdBot {
    public static void main(String[] args) {

        MeepMeep sim = new MeepMeep(600);

        Pose2d startingPos = new Pose2d(-52.5, 46.5, deg(125));

            RoadRunnerBotEntity drive = new DefaultBotBuilder(sim)

                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .setDimensions(18, 18)
                    .build();

        Pose2d goalPos = new Pose2d(-23.5, 17, deg(133));
        Vector2d goalVector = new Vector2d(-23.5, 17);

        Pose2d secondBallsHalfwayPos = new Pose2d(-11.5, 20, deg(90));
        Pose2d secondBallsPos = new Pose2d(-11.5, 45, deg(90));
        Vector2d secondBallsVector = new Vector2d(-11.5, 45);

        Pose2d thirdBallsHalfwayPos = new Pose2d(11.5, 24, deg(90));
        Pose2d thirdBallsPos = new Pose2d(11.5, 45, deg(90));
        Vector2d thirdBallsVector = new Vector2d(11.5, 45);

        Pose2d fourthBallsHalfwayPos = new Pose2d(35.5, 28, deg(90));
        Pose2d fourthBallsPos = new Pose2d(35.5, 45, deg(90));
        Vector2d fourthBallsVector = new Vector2d(35.5, 45);

            MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
                    new TranslationalVelConstraint(18), // 15 in. per sec cap
                    new AngularVelConstraint(deg(220) // 180 deg per sec cap
                    )));

        Action goToGoal1 = drive.getDrive().actionBuilder(startingPos)
                .setTangent(deg(315))
                .strafeToLinearHeading(goalVector, deg(133))
                .build();

//        Action goToGoal1 = drive.actionBuilder(startingPos)
//                .lineToX(-15)
//                .build();

        Action goToSecondBalls = drive.getDrive().actionBuilder(goalPos)
                .setTangent(deg(50))
                .splineToSplineHeading(secondBallsHalfwayPos, deg(90), slowVelConstraint)
                // Finish the rest of the distance at that fixed heading
                .splineToConstantHeading(secondBallsVector, deg(90), slowVelConstraint)
                .build();

        Action goToGoal2 = drive.getDrive().actionBuilder(secondBallsPos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(240))
                .build();

        Action goToThirdBalls = drive.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToSplineHeading(thirdBallsHalfwayPos, deg(90))
                // Finish the rest of the distance at that fixed heading
                .splineToConstantHeading(thirdBallsVector, deg(90), slowVelConstraint)
                .build();

        Action goToGoal3 = drive.getDrive().actionBuilder(thirdBallsPos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180))
                .build();

        Action goToFourthBalls = drive.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToSplineHeading(fourthBallsHalfwayPos, deg(90))
                // Finish the rest of the distance at that fixed heading
                .splineToConstantHeading(fourthBallsVector, deg(90), slowVelConstraint)
                .build();

        Action goToGoal4 = drive.getDrive().actionBuilder(fourthBallsPos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180))
                .build();

        Action threeBall1 = new SequentialAction(
                new ParallelAction(
                        goToGoal1,
                    new SleepAction(3)
                )
        );

        Action threeBall2 = new SequentialAction(
                new ParallelAction(
                        goToSecondBalls),

                new ParallelAction(
                        goToGoal2,

                new SleepAction(3)
                )
        );

        Action threeBall3 = new SequentialAction(
                new ParallelAction(
                        goToThirdBalls),

                new ParallelAction(
                        goToGoal3,

                new SleepAction(3)
                )
        );

        Action threeBall4 = new SequentialAction(
                new ParallelAction(
                        goToFourthBalls),

                new ParallelAction(
                        goToGoal4
                )
        );


            Action fullAction = new SequentialAction(
                    threeBall1,
                    threeBall2,
                    threeBall3,
                    threeBall4
            );

            // waitForStart();

            drive.runAction(fullAction);
            sim.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.9f)
                    .addEntity(drive)
                    .start();
        }
    }