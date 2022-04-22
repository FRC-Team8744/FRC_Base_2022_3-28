// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Enumerated list of robot types
    public static final int PABLO_2022_FRC = 0;
    public static final int PABLO_2021_SUMMER = 1;
    
    /* IMPORTANT! This defines which robot you are using! */
    public static final int THIS_ROBOT = PABLO_2022_FRC;

    //List of joystick types
    public static final int xbox_controller = 0;
    public static final int Pro = 1;
    
    // This defines which joystick you are using
    public static final int This_Joystick = Pro;
    
    public static final double Arm_Voltage = 6.2;

    public static final double AutoDriveSpeed = 1;

    public static final double GYRO_ADJUST_SCALE_COEFFICIENT = 0.01;

    public static final double Z_AXIS_ROTATE_DEADBAND_VALUE = 1;

    public static final double TURN_TIMER_TURNING_GRACE_PERIOD = 0.6;

    // change this number to change the speed
    // summer robot speed: 0.7
    // 2022 robot speed: 0.6
    public static final double ROBOTSPEED = 0.5; 

    // change this numba to change da turn speeeed e
    public static final double TURNSPEED = 0.5;

    public static final double ARMLIFTSPEED = 0.9;

    public static final double INTAKE_SPEED = 0.8;

    // Hook arm current limit
    public static final double LOW_CURRENT_LIMIT = 20.0; 
    public static final double HIGH_CURRENT_LIMIT = 80.0;
    
}
