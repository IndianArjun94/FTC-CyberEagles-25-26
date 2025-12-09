package org.firstinspires.ftc.teamcode.auton.seconndbot;

import static org.firstinspires.ftc.teamcode.auton.firstbot.Util.deg;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class CLOSEBLUE9Ball extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(-52.5,-46.5,deg(235));

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, startingPos);

        Pose2d goalPos = new Pose2d(0,0,0);
    }
}
