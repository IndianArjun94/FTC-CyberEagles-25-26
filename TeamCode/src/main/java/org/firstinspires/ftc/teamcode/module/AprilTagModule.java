package org.firstinspires.ftc.teamcode.module;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagModule {

    public static AprilTagModule instance;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    private AprilTagModule(HardwareMap hw, boolean liveView) {
        aprilTag = new AprilTagProcessor.Builder().build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        builder.setCamera(hw.get(WebcamName.class, "Webcam 1"));
        builder.enableLiveView(liveView);

        builder.addProcessor(aprilTag);

        visionPortal = builder.build();
    }

    public static AprilTagModule init(HardwareMap hw, boolean liveView) {
        if (instance==null) instance = new AprilTagModule(hw, liveView);

        return instance;
    }

    private List<AprilTagDetection> getDetections() {
        return aprilTag.getDetections();
    }

    public ObeliskPattern getObeliskPattern() {
        List<AprilTagDetection> detections = getDetections();
        ObeliskPattern pattern = null;

        for (AprilTagDetection detection : detections) {
            if (pattern != null && pattern != ObeliskPattern.NONE_DETECTED) {
                pattern = ObeliskPattern.MULTIPLE_DETECTED;
            } else {
                if (detection.id == 21) pattern = ObeliskPattern.GPP;
                else if (detection.id == 22) pattern = ObeliskPattern.PGP;
                else if (detection.id == 23) pattern = ObeliskPattern.PPG;
                else pattern = ObeliskPattern.NONE_DETECTED;
            }
        }

        return pattern;
    }

    public SideColor getSideColor() {
        List<AprilTagDetection> detections = getDetections();
        SideColor color = null;

        for (AprilTagDetection detection : detections) {
            if (color != null && color != SideColor.NONE_DETECTED) {
                color = SideColor.MULTIPLE_DETECTED;
            } else {
                if (detection.id == 20) color = SideColor.BLUE;
                else if (detection.id == 24) color = SideColor.RED;
                else color = SideColor.NONE_DETECTED;
            }
        }

        return color;
    }
}
