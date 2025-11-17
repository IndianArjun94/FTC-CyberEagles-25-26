package net.cybereagles.meepmeeptesting.autondev.blue;

import static net.cybereagles.meepmeeptesting.MeepMeepTesting.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Arrays;


public class BlueFarOneBall  {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18,18)
                .build();


        Pose2d startingPos = new Pose2d(63,-15,deg(180));
        Pose2d shootingPos = new Pose2d(-22.5,-16,deg(230));
        Pose2d intake1 = new Pose2d(-11.5,-37, deg(270));
        Pose2d intake2 = new Pose2d(-11.5,-39, deg(270));
        Pose2d intake3 = new Pose2d(-11.5,-41, deg(270));
        Pose2d intake4 = new Pose2d(11.5,-37, deg(270));
        Pose2d intake5 = new Pose2d(11.5,-39, deg(270));
        Pose2d intake6 = new Pose2d(11.5,-41, deg(270));

        MinVelConstraint velConstraintForBalls = new MinVelConstraint(
                Arrays.asList(
                        new TranslationalVelConstraint(25),
                        new AngularVelConstraint(deg(230))));


        Action goToGoal = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(200))
                .splineToLinearHeading(
                        new Pose2d(-22.5,-16,deg(235)), deg(170))
                .build();
// TODO update goal angle
        Action goIntakeBalls = myBot.getDrive().actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake1, deg(270),velConstraintForBalls)
                .splineToLinearHeading(shootingPos, deg(180),velConstraintForBalls)

                .setTangent(deg(0))
                .splineToLinearHeading(intake2, deg(270),velConstraintForBalls)
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)

                .setTangent(deg(0))
                .splineToLinearHeading(intake3, deg(270),velConstraintForBalls)
                .splineToLinearHeading(shootingPos, deg(150), velConstraintForBalls)
                .build();
        Action goIntakeBalls2 = myBot.getDrive().actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake4, deg(270), velConstraintForBalls)
                .setTangent(deg(90))
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)

                .setTangent(deg(0))
                .splineToLinearHeading(intake5, deg(270),velConstraintForBalls)
                .setTangent(deg(90))
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)

                .setTangent(deg(0))
                .splineToLinearHeading(intake6, deg(270), velConstraintForBalls)
                .setTangent(deg(90))
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)
                .build();

        Action fullAction = new SequentialAction(
                //launcher.startLauncher(0.65),
                goToGoal,
                //new SleepAction(0.25),
                //loader.startSingleBallLoader(),
                //new SleepAction(1.5),
                //loader.stopSingleBallLoader(),
                //launcher.stopLauncher(),
                goIntakeBalls,
                goIntakeBalls2
        );

        myBot.runAction(fullAction);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
            .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}
