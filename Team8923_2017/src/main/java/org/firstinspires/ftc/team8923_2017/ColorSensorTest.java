/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.team8923_2017;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * the Adafruit RGB Sensor.  It assumes that the I2C
 * cable for the sensor is connected to an I2C port on the
 * Core Device Interface Module.
 *
 * It also assuems that the LED pin of the sensor is connected
 * to the digital signal pin of a digital port on the
 * Core Device Interface Module.
 *
 * You can use the digital port to turn the sensor's onboard
 * LED on or off.
 *
 * The op mode assumes that the Core Device Interface Module
 * is configured with a name of "dim" and that the Adafruit color sensor
 * is configured as an I2C device with a name of "sensor_color".
 *
 * It also assumes that the LED pin of the RGB sensor
 * is connected to the signal pin of digital port #5 (zero indexed)
 * of the Core Device Interface Module.
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "ColorSensorTest", group = "Sensor")
@Disabled
                            // Comment this out to add to the opmode list
public class ColorSensorTest extends LinearOpMode {

  ColorSensor sensorTopLeft;
  ColorSensor sensorTopRight;
  ColorSensor sensorBottomRight;
  //DeviceInterfaceModule cdim;

  // we assume that the LED pin of the RGB sensor is connected to
  // digital port 5 (zero indexed).
  static final int LED_CHANNEL = 5;


  @Override

  public void runOpMode() {

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValuesTopLeft[] = {0F,0F,0F};
    float hsvValuesTopRight[] = {0F,0F,0F};
    float hsvValuesBottomRight[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    //final float values[] = hsvValues;

    // get a reference to the RelativeLayout so we can change the background
    // color of the Robot Controller app to match the hue detected by the RGB sensor.
    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    // bPrevState and bCurrState represent the previous and current state of the button.
    boolean bPrevState = false;
    boolean bCurrState = false;

    // bLedOn represents the state of the LED.
    boolean bLedOn = true;

    // get a reference to our DeviceInterfaceModule object.
    //cdim = hardwareMap.deviceInterfaceModule.get("dim");

    // set the digital channel to output mode.
    // remember, the Adafruit sensor is actually two devices.
    // It's an I2C sensor and it's also an LED that can be turned on or off.
    //cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannel.Mode.OUTPUT);

    // get a reference to our ColorSensor object.
    sensorTopRight = hardwareMap.colorSensor.get("sensorTopRight");
    sensorTopLeft = hardwareMap.colorSensor.get("sensorTopLeft");
    sensorBottomRight = hardwareMap.colorSensor.get("sensorBottomRight");
    // turn the LED on in the beginning, just so user will know that the sensor is active.
    //cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);

    // wait for the start button to be pressed.
    waitForStart();

    // loop and read the RGB data.
    // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
    while (opModeIsActive())  {

      // check the status of the x button on gamepad.
      bCurrState = gamepad1.x;

      // check for button-press state transitions.
      if ((bCurrState == true) && (bCurrState != bPrevState))  {

        // button is transitioning to a pressed state. Toggle the LED.
        bLedOn = !bLedOn;
        //cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);
      }

      // update previous state variable.
      bPrevState = bCurrState;

      // convert the RGB values to HSV values.
      Color.RGBToHSV((sensorTopLeft.red() * 255) / 800, (sensorTopLeft.green() * 255) / 800, (sensorTopLeft.blue() * 255) / 800, hsvValuesTopLeft);
      Color.RGBToHSV((sensorTopRight.red() * 255) / 800, (sensorTopRight.green() * 255) / 800, (sensorTopRight.blue() * 255) / 800, hsvValuesTopRight);
      Color.RGBToHSV((sensorBottomRight.red() * 255) / 800, (sensorBottomRight.green() * 255) / 800, (sensorBottomRight.blue() * 255) / 800, hsvValuesBottomRight);

      // send the info back to driver station using telemetry function.
      //telemetry.addData("LED", bLedOn ? "On" : "Off");
      //telemetry.addData("Clear", sensorRGB.alpha());
      //telemetry.addData("Red  ", sensorRGB.red());
      //telemetry.addData("Green", sensorRGB.green());
      //telemetry.addData("Blue ", sensorRGB.blue());
      //telemetry.addData("Hue", hsvValues[0]);
      telemetry.addData("TopRight_S: ", hsvValuesTopRight[1]);
      //telemetry.addData("V: ", hsvValuesTopLeft[2]);
      telemetry.addData("TopLeft_S: ", hsvValuesTopLeft[1]);
      telemetry.addData("BottomRight_S: ", hsvValuesBottomRight[1]);
      telemetry.update();

    }

  }

}
