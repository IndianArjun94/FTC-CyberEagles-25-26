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

        Pose2d startingPos = new Pose2d(-52.5, 46.5, deg(145));

            RoadRunnerBotEntity drive = new DefaultBotBuilder(sim)

                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .setDimensions(18, 18)
                    .build();

            Pose2d goalPos = new Pose2d(-22.5, 14, deg(131));
            Vector2d goalVector = new Vector2d(-22.5, 14);
            //Pose2d ball2Pos = new Pose2d(-13, -27.5, deg(270));
            //Pose2d ball3Pos = new Pose2d(-13, -32, deg(270));
            Pose2d trip1pos = new Pose2d(-11.5, 45, deg(90));
//        Pose2d ball5Pos = new Pose2d(11.5, -26.5, deg(270));
//        Pose2d ball6Pos = new Pose2d(11.5, -31, deg(270));
            Pose2d trip2pos = new Pose2d(11.5, 45, deg(90));
            Pose2d trip3pos = new Pose2d(34.5, 45, deg(90));

            final float launcherPower = 0.66f;
            final float sleepTime = 0.75f;

            MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
                    new TranslationalVelConstraint(18), // 15 in. per sec cap
                    new AngularVelConstraint(deg(220) // 180 deg per sec cap
                    )));

            MinVelConstraint fastVelConstraint = new MinVelConstraint(Arrays.asList(
                    new TranslationalVelConstraint(25), // 15 in. per sec cap
                    new AngularVelConstraint(deg(220) // 180 deg per sec cap
                    )));


            Action goToGoal1 = drive.getDrive().actionBuilder(startingPos)
                    .setTangent(deg(305))
                    .splineToConstantHeading(
                            goalVector, deg(305))
                    .build();

            Action trip1 = drive.getDrive().actionBuilder(goalPos)
                    .setTangent(deg(0))
                    .splineToLinearHeading(trip1pos, deg(90), slowVelConstraint)
                    .build();

            Action goToGoal2 = drive.getDrive().actionBuilder(trip1pos)
                    .setTangent(deg(270))
                    .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                    .build();

            Action trip2 = drive.getDrive().actionBuilder(goalPos)
                    .setTangent(deg(0))
                    .splineToLinearHeading(trip2pos, deg(90), slowVelConstraint)
                    .build();

            Action goToGoal3 = drive.getDrive().actionBuilder(trip2pos)
                    .setTangent(deg(270))
                    .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                    .build();

            Action trip3 = drive.getDrive().actionBuilder(goalPos)
                    .setTangent(deg(0))
                    .splineToLinearHeading(trip3pos, deg(90), slowVelConstraint)
                    .build();

            Action goToGoal4 = drive.getDrive().actionBuilder(trip3pos)
                    .setTangent(deg(270))
                    .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                    .build();

            Action threeBall1 = new SequentialAction(
                    //launcher.startLauncher(launcherPower-0.05),
                    goToGoal1,
                    //loader.startSingleBallLoader(),
                    new SleepAction(sleepTime + 1.5)
                    //loader.stopSingleBallLoader(),
                    //launcher.stopLauncher()
            );

            Action threeBall2 = new SequentialAction(
                    //  intake.startActiveIntake(),
                    //  launcher.ejectLauncher(),
                    trip1,
//                intake.stopActiveIntake(),
                    //   loader.stopSingleBallLoader(),
                    //    launcher.startLauncher(launcherPower+0.035),
                    new ParallelAction(
                            goToGoal2,
                            new SequentialAction(
                                    new SleepAction(0.3)
                                    //  intake.stopActiveIntake()
                            )),
                    //         loader.startSingleBallLoader(),
                    new SleepAction(sleepTime + 1.5)
                    //     loader.stopSingleBallLoader(),
                    //   launcher.stopLauncher()
            );

            Action threeBall3 = new SequentialAction(
                    //   intake.startActiveIntake(),
                    //   launcher.ejectLauncher(),

                    trip2,
//                intake.stopActiveIntake(),
                    //        loader.stopSingleBallLoader(),
                    //       launcher.startLauncher(launcherPower+0.035),
                    new ParallelAction(
                            goToGoal3,
                            new SequentialAction(
                                    new SleepAction(0.3)
                                    //    intake.stopActiveIntake()
                            )),
                    //  loader.startSingleBallLoader(),
                    new SleepAction(sleepTime + 1.5)
                    //  loader.stopSingleBallLoader(),
                    //   launcher.stopLauncher()
            );

            Action threeBall4 = new SequentialAction(
                    //    intake.startActiveIntake(),
                    //   launcher.ejectLauncher(),

                    trip3,
//                intake.stopActiveIntake(),
                    //    loader.stopSingleBallLoader(),
                    //    launcher.startLauncher(launcherPower),
                    new ParallelAction(
                            goToGoal4,
                            new SequentialAction(
                                    new SleepAction(0.3)
                                    //    intake.stopActiveIntake()
                            )),
                    // loader.startSingleBallLoader(),
                    new SleepAction(sleepTime+1.5)
                    //  loader.stopSingleBallLoader(),
                    //  launcher.stopLauncher()
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
                    .setBackgroundAlpha(0.1f)
                    .addEntity(drive)
                    .start();
        }
    }