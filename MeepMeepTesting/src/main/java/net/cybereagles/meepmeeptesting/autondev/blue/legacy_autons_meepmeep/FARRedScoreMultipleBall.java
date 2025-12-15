package net.cybereagles.meepmeeptesting.autondev.blue.legacy_autons_meepmeep;

import static net.cybereagles.meepmeeptesting.Util.deg;

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

public class FARRedScoreMultipleBall {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18, 18)
                .build();

//        Actions:

        Pose2d startingPos = new Pose2d(63,15,deg(180));
        Pose2d goalPos = new Pose2d(-22.5, 14, deg(125));
        Vector2d goalVector = new Vector2d(-22.5, 14);
        Pose2d ball2Pos = new Pose2d(-11.5, 34, deg(90));
        Pose2d ball3Pos = new Pose2d(-11.5, 38, deg(90));
        Pose2d ball4Pos = new Pose2d(-11.5, 43, deg(90));
        Pose2d ball5Pos = new Pose2d(11.5, 34, deg(90));
        Pose2d ball6Pos = new Pose2d(11.5, 38, deg(90));
        Pose2d ball7Pos = new Pose2d(11.5, 43, deg(90));

        MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(18), // 15 in. per sec cap
                new AngularVelConstraint(deg(180) // 180 deg per sec cap
                )));

        MinVelConstraint fastVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(22.5), // 15 in. per sec cap
                new AngularVelConstraint(deg(180) // 180 deg per sec cap
                )));


        Action goToGoal1 = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(270))
                .splineToLinearHeading(
                        goalPos, deg(270))
                .build();

        Action goToBall2 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball2Pos, deg(90), slowVelConstraint)
                .build();

        Action goToGoal2 = myBot.getDrive().actionBuilder(ball2Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall3 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball3Pos, deg(90), slowVelConstraint)
                .build();

        Action goToGoal3 = myBot.getDrive().actionBuilder(ball3Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall4 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball4Pos, deg(90), slowVelConstraint)
                .build();

        Action goToGoal4 = myBot.getDrive().actionBuilder(ball4Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall5 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball5Pos, deg(90), fastVelConstraint)
                .build();

        Action goToGoal5 = myBot.getDrive().actionBuilder(ball5Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall6 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball6Pos, deg(90), fastVelConstraint)
                .build();

        Action goToGoal6 = myBot.getDrive().actionBuilder(ball6Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        Action goToBall7 = myBot.getDrive().actionBuilder(goalPos)
                .setTangent(deg(0))
                .splineToLinearHeading(ball7Pos, deg(90), fastVelConstraint)
                .build();

        Action goToGoal7 = myBot.getDrive().actionBuilder(ball7Pos)
                .setTangent(deg(270))
                .splineToLinearHeading(goalPos, deg(180), fastVelConstraint)
                .build();

        final float sleepTime = 0.25f;

        Action fullAction = new SequentialAction(
                goToGoal1,
                new SleepAction(sleepTime),
                goToBall2,
                goToGoal2,
                new SleepAction(sleepTime),
                goToBall3,
                goToGoal3,
                new SleepAction(sleepTime),
                goToBall4,
                goToGoal4,
                new SleepAction(sleepTime),
                goToBall5,
                goToGoal5,
                new SleepAction(sleepTime),
                goToBall6,
                goToGoal6,
                new SleepAction(sleepTime),
                goToBall7,
                goToGoal7,
                new SleepAction(sleepTime)
        );

        myBot.runAction(fullAction);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
