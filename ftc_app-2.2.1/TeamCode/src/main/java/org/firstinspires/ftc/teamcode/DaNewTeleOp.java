/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This is Da New TeleOp. Not the old one. The old one sucked. This is Da New TeleOp.
 */

// It's a teleop. It ops the tele. Simple.
@TeleOp(name = "DaNewTeleOp", group = "Iterative Opmode")
public class DaNewTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // These things are static and final.
    // Static probably referring to static electricity.
    static final double MAXPOS = 1.0;
    static final double MINPOS = 0.0;

    // YEEEEE DC MOTORS
    DcMotor leftMotor;
    DcMotor rightMotor;

    /*
     * this is the init method, innit?
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /*
         * you gotta do this. don't ask why. just DO IT.
         */
        leftMotor = hardwareMap.dcMotor.get("left motor"); // MAP ALL THE HARDWARE
        rightMotor = hardwareMap.dcMotor.get("right motor"); // HARDWARE ALL THE MAP


        // This thing is useful
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /*
     * this is most certainly not the init method
     */
    @Override
    public void init_loop() {
    }

    /*
     * start. not end.
     */
    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    // This method loops. and loops. and loops. and loops. and loops. and loops. and loops.
    public void loop() {
        // If you put the cursor on a comment that says "region" and press command-minus, you can collapse the code

        //region Telemetry
        // This is the metry of the tele
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("Wheels", "l: " + leftMotor.getPower() + " r: " + rightMotor.getPower());
        //endregion

        //region WHEELS
        float left1 = gamepad1.left_stick_y; // Left stick y
        left1 = Range.clip(left1, (float) -1.0, (float) 1.0);
        left1 = (float) scaleInput(left1); // This scales input for some reason
        leftMotor.setPower(left1); // This sets power

        float right1 = gamepad1.right_stick_y; // Right stick y
        right1 = Range.clip(right1, (float) -1.0, (float) 1.0);
        right1 = (float) scaleInput(right1); // This scales input. again.
        rightMotor.setPower(right1); // This sets power. again. but the other motor.
        //endregion
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    // I don't know why this is here.
    // The comments in the method are not mine, that's why they're useless
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
