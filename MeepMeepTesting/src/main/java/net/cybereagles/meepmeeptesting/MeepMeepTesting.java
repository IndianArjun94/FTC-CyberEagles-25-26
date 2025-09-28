package net.cybereagles.meepmeeptesting;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Arrays;
import java.util.List;

public class MeepMeepTesting {

    public static double deg(double rad) {
        return Math.toRadians(rad);
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Action firstShoot = myBot.getDrive().actionBuilder(new Pose2d(-60.5, -37.5, deg(270)))
                .setTangent(deg(0))
                .splineToLinearHeading(new Pose2d(-45, -35, deg(235)), deg(90))
                .build();

        Action returnToZero = myBot.getDrive().actionBuilder(new Pose2d(-45, -35, deg(235)))
                .splineToLinearHeading(new Pose2d(0, 0, deg(180)), deg(180))
                        .build();

        List<Action> actionList = Arrays.asList(firstShoot, returnToZero);
        SequentialAction sequentialAction = new SequentialAction(actionList);

        myBot.runAction(sequentialAction);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}