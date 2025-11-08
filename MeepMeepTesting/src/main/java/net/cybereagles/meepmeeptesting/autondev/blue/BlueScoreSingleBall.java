package net.cybereagles.meepmeeptesting.autondev.blue;

import static net.cybereagles.meepmeeptesting.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueScoreSingleBall {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18, 18)
                .build();

//        Actions:

        Pose2d startingPos = new Pose2d(63,-15,deg(180));

        Action goToGoal = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(200))
                .splineToLinearHeading(
                        new Pose2d(-26,-13,deg(230)), deg(170))
                .build();
// TODO update goal angle
        Action goAwayFromGoal = myBot.getDrive().actionBuilder(new Pose2d(-26,-13,deg(230)))
                .setTangent(deg(150))
                .splineToLinearHeading(new Pose2d(-61, -10, deg(180)), deg(180))
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
