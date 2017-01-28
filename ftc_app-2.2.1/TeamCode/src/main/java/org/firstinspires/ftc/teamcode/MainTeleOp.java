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
 * CONTROL SCHEME
 *
 * Gamepad 1:
 * Left wheel - left joystick
 * Right wheel - right joystick
 * Center wheel - left/right triggers
 * Color sensor enable - X
 * Button pusher - dpad up/down
 */

// It's a teleop. It ops the tele. Simple.
@TeleOp(name = "MainTeleOp", group = "Iterative Opmode")
public class MainTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // These things are static and final.
    // Static probably referring to static electricity.
    static final double MOTOR_FULL_POWER=1.0;
    static final double MOTOR_LESS_POWER=0.7;
    static final double MOTOR_HALF_POWER=0.5;
    static final double MOTOR_POWER_OFF=0.0;

    // YEEEEE DC MOTORS
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor centerMotor;
    DcMotor highMotor;

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
        centerMotor = hardwareMap.dcMotor.get("center motor");
        highMotor = hardwareMap.dcMotor.get("highMotor");

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
        // Left wheel
        leftMotor.setPower(joystickToMotorValue(gamepad1.left_stick_y)); // This sets power

        // Right wheel
        rightMotor.setPower(joystickToMotorValue(gamepad1.right_stick_y)); // This sets power. again. but the other motor.

        // Center wheels
        double centerPower;
        if (gamepad1.right_trigger>0.0 && gamepad1.left_trigger==0.0) {
            centerPower=gamepad1.right_trigger;
        }
        else if (gamepad1.left_trigger>0.0 && gamepad1.right_trigger==0.0) {
            centerPower=-gamepad1.left_trigger;
        }
        else {
            centerPower=MOTOR_POWER_OFF;
        }
        centerMotor.setPower(centerPower);
        //endregion

        // Button Clicker
        if(gamepad1.dpad_up){

            highMotor.setPower(-MOTOR_HALF_POWER);
        }
        else if (gamepad1.dpad_down) {

            highMotor.setPower(MOTOR_HALF_POWER);
        }
        else {
            highMotor.setPower(MOTOR_POWER_OFF);
        }
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
    double joystickToMotorValue(double joystickValue) {
        joystickValue = Range.clip(joystickValue, (float) -1.0, (float) 1.0);
        double scaled = scaleInput(joystickValue); // This scales input for some reason
        return scaled;
    }

}
