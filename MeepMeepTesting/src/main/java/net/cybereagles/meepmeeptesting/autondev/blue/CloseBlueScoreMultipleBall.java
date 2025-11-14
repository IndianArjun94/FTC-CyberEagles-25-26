package net.cybereagles.meepmeeptesting.autondev.blue;

import static net.cybereagles.meepmeeptesting.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Arrays;

public class CloseBlueScoreMultipleBall {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18, 18)
                .build();

//        Actions:

        Pose2d startingPos = new Pose2d(-52.5,-46.5,deg(235));
        Pose2d goalPos = new Pose2d(-22.5, -14, deg(235));
        Vector2d goalVector = new Vector2d(-22.5, -14);
        Pose2d ball2Pos = new Pose2d(-11, -34, deg(270));
        Pose2d ball3Pos = new Pose2d(-11, -38, deg(270));
        Pose2d ball4Pos = new Pose2d(-11, -43, deg(270));

        MinVelConstraint velConstraintForBalls = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(15), // 15 in. per sec cap
                new AngularVelConstraint(deg(180) // 180 deg per sec cap
                )));


        Action goToGoal1 = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(55))
                .splineToConstantHeading(
                        goalVector, deg(55))
                .build();

        Action goToBall2 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball2Pos, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal2 = myBot.getDrive().actionBuilder(ball2Pos)
                .setTangent(90)
                .splineToLinearHeading(goalPos, deg(180), velConstraintForBalls)
                .build();

        Action goToBall3 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball3Pos, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal3 = myBot.getDrive().actionBuilder(ball3Pos)
                .setTangent(90)
                .splineToLinearHeading(goalPos, deg(180), velConstraintForBalls)
                .build();

        Action goToBall4 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball4Pos, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal4 = myBot.getDrive().actionBuilder(ball4Pos)
                .setTangent(90)
                .splineToLinearHeading(goalPos, deg(180), velConstraintForBalls)
                .build();

        Action fullAction = new SequentialAction(
                goToGoal1,
                goToBall2,
                goToGoal2,
                goToBall3,
                goToGoal3,
                goToBall4,
                goToGoal4
        );

        myBot.runAction(fullAction);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
