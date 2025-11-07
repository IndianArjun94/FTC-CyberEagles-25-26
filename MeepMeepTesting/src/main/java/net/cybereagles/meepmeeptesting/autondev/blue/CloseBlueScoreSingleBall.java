package net.cybereagles.meepmeeptesting.autondev.blue;

import static net.cybereagles.meepmeeptesting.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class CloseBlueScoreSingleBall {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18, 18)
                .build();

//        Actions:

        Pose2d startingPos = new Pose2d(-52.5,-46.5,deg(235));

        Action goToGoal = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(55))
                .splineToConstantHeading(
                        new Vector2d(-22.5,-14), deg(55))
                .build();
// TODO update goal angle
        Action goAwayFromGoal = myBot.getDrive().actionBuilder(new Pose2d(-22.5,-14,deg(235)))
                .setTangent(deg(270))
                .splineToLinearHeading(new Pose2d(-34, -53, deg(270)), deg(280))
                .build();

        Action fullShoot = new SequentialAction(
                goToGoal,
                goAwayFromGoal
        );

        myBot.runAction(fullShoot);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
