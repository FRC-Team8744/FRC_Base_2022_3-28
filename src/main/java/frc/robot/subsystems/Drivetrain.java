// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;
import frc.robot.sensors.IMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.MotorController;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  // Found reference to these APIs in:
  // https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java%20General/DifferentialDrive
  private double TargetHeading;
  private Timer turnTimer;

  private WPI_TalonSRX  Talon_FrontLeft = new WPI_TalonSRX(1);
  private WPI_TalonSRX Talon_BackLeft = new WPI_TalonSRX(2);
  private WPI_TalonSRX Talon_FrontRight = new WPI_TalonSRX(3);
  private WPI_TalonSRX Talon_BackRight = new WPI_TalonSRX(4);

  private WPI_VictorSPX Victor_FrontLeft = new WPI_VictorSPX(1);
  private WPI_VictorSPX Victor_BackLeft = new WPI_VictorSPX(4);
  private WPI_VictorSPX Victor_FrontRight = new WPI_VictorSPX(2);
  private WPI_VictorSPX Victor_BackRight = new WPI_VictorSPX(3);

  // Both types implement the MotorController class!
  // MotorController ThisOne = Talon_FrontLeft;

  // MotorControllerGroup m_leftMotors = new MotorControllerGroup(Talon_FrontLeft, Talon_BackLeft);

  // MotorController m_frontLeft = (MotorController) new TalonSRX(1);
  // MotorController m_rearLeft = (MotorController) new TalonSRX(2);
  // MotorControllerGroup m_leftMotors = new MotorControllerGroup(m_frontLeft, m_rearLeft);

  // MotorController m_frontRight = (MotorController) new TalonSRX(3);
  // MotorController m_rearRight = (MotorController) new TalonSRX(4);
  // MotorControllerGroup m_rightMotors = new MotorControllerGroup(m_frontRight, m_rearRight);

  // Set up the differential drive controller
  private final DifferentialDrive m_diffDrive = SelectRobotDrive();

  // Set up the Inertial Measurement Unit
  private final IMU m_imu = new IMU();

  /** Creates a new Drivetrain based on robot selection */
  public Drivetrain() { 
     TargetHeading= m_imu.getAngleZ();

    switch(Constants.THIS_ROBOT) {
      case Constants.PABLO_2021_SUMMER:
        /* factory default values */
        Victor_FrontLeft.configFactoryDefault();
        Victor_BackLeft.configFactoryDefault();
        Victor_FrontRight.configFactoryDefault();
        Victor_BackRight.configFactoryDefault();
        
        /* set up followers */
        //  !!! Comment these out when bringing up a new system!
        Victor_BackLeft.follow(Victor_FrontLeft);
        Victor_BackRight.follow(Victor_FrontRight);

        /* Invert controls on one side */
        Victor_FrontLeft.setInverted(true);
        Victor_FrontRight.setInverted(false);
        // Victor_BackLeft.setInverted(true);
        // Victor_BackRight.setInverted(false);
        
        /* Set the invert of the followers to match their respective master controllers */
        Victor_BackLeft.setInverted(InvertType.FollowMaster);
        Victor_BackRight.setInverted(InvertType.FollowMaster);

        // Victor_FrontLeft.disable();
        //  Victor_BackLeft.disable();
        //  Victor_FrontRight.disable();
        //  Victor_BackRight.disable();
        
        break;
      case Constants.PABLO_2022_FRC:
        /* factory default values */
       //  Talon_FrontLeft.configFactoryDefault();
       //  Talon_BackLeft.configFactoryDefault();
        // Talon_FrontRight.configFactoryDefault();
        Talon_BackRight.configFactoryDefault();
        
        /* set up followers */
        //  !!! Comment these out when bringing up a new system!
        Talon_BackLeft.follow(Talon_FrontLeft);
        Talon_BackRight.follow(Talon_FrontRight);

        /* Invert controls on one side */
        Talon_FrontLeft.setInverted(true);
        Talon_FrontRight.setInverted(false);
        
        /* Set the invert of the followers to match their respective master controllers */
        Talon_BackLeft.setInverted(InvertType.FollowMaster);
        Talon_BackRight.setInverted(InvertType.FollowMaster);

        // Talon_FrontLeft.disable();
        // Talon_BackLeft.disable();
        // Talon_FrontRight.disable();
        // Talon_BackRight.disable();

        break;
    }
  }
// change these number to change da speed
  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    // boolean userIsTurning = Math.abs(zaxisRotate) > Constants.Z_AXIS_ROTATE_DEADBAND_VALUE;
    // boolean timerWithinGracePeriod = turnTimer.get() < Constants.TURN_TIMER_TURNING_GRACE_PERIOD;
    // boolean adjustHeadingDueToTimer = turnTimer.get() > 0 && timerWithinGracePeriod;
    
    // if (userIsTurning) {
    //   turnTimer.reset();
    //   turnTimer.start();
    //   TargetHeading = m_imu.getAngleZ();
    // }

    // if (userIsTurning == false && adjustHeadingDueToTimer) {
    //   TargetHeading = m_imu.getAngleZ();
    // }

    // if (userIsTurning == false && adjustHeadingDueToTimer == false) {
    //   turnTimer.stop();
    //   turnTimer.reset();
    // }

    // double gyroAdjust = getGyroAdjustment();
    // zaxisRotate = -gyroAdjust;

    m_diffDrive.arcadeDrive(xaxisSpeed * Constants.ROBOTSPEED, zaxisRotate * Constants.TURNSPEED);
  }

  private double getGyroAdjustment() {
    double headingDifference = m_imu.getAngleZ() - TargetHeading;
    headingDifference %= 360;

    if (headingDifference < -180) {
      headingDifference += 360;
    }

    if (headingDifference > 180) {
      headingDifference -= 360;
    }

    double gyroAdjust = headingDifference * Constants.GYRO_ADJUST_SCALE_COEFFICIENT;

    return gyroAdjust;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // m_imu.updateSmartDashboard();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  private DifferentialDrive SelectRobotDrive() {
    switch (Constants.THIS_ROBOT) {
      case Constants.PABLO_2021_SUMMER:
        return new DifferentialDrive(Victor_FrontLeft, Victor_FrontRight);
      case Constants.PABLO_2022_FRC:
      default: // Needed to suppress errors
        return new DifferentialDrive(Talon_FrontLeft, Talon_FrontRight);
    }
  }

}
