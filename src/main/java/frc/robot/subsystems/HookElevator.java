// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// This code adapted from:
// https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Position%20Closed%20Loop%20Control/src/main/java/frc/robot/Robot.java
// https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Encoder%20Feedback%20Device/src/main/java/frc/robot/Robot.java

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HookElevator extends SubsystemBase {
  private static final int deviceID = 21;  // Move to Constants once set in hardware!
  private static final boolean debugHookElevator = false;  // Move to Constants!

  private CANSparkMax m_motor;
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, positionGoal;
  double p, i, d, iz, ff, max, min, driverSetPoint = 0;

  /** Creates a new HookElevator. */
  public HookElevator() {
    switch(Constants.THIS_ROBOT) {
      case Constants.PABLO_2021_SUMMER:
        /* No similar hardware on this robot */
        break;

      case Constants.PABLO_2022_FRC:
        // initialize motor
        m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);

        /**
         * The restoreFactoryDefaults method can be used to reset the configuration parameters
         * in the SPARK MAX to their factory default state. If no argument is passed, these
         * parameters will not persist between power cycles
         */
        m_motor.restoreFactoryDefaults();

        // Need to experiment with these...
        m_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        // m_motor.setClosedLoopRampRate(rate);
        m_motor.setSecondaryCurrentLimit(20.0);  // Amps (80 Max, primary SmartCurrentLimit only works for brushless)
        m_motor.setInverted(false);
        // m_motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, (float) 1.0);
        // m_motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        // m_motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, (float) -1.0);
        // m_motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        // m_motor.stopMotor();

        /**
         * In order to use PID functionality for a controller, a SparkMaxPIDController object
         * is constructed by calling the getPIDController() method on an existing
         * CANSparkMax object
         */
        m_pidController = m_motor.getPIDController();

        // m_pidController.setSmartMotionMaxVelocity(maxVel, 0);  // maxVel in RPM!
        // m_pidController.setSmartMotionMaxAccel(maxAccel, 0);  // maxAccel in RPM/s!

        // Encoder object created to display position values
        m_encoder = m_motor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);

        m_motor.setInverted(true);

        // PID coefficients
        kP = 5; // 0.1; 
        kI = 0; // 1e-4;
        kD = 0; // 1; 
        kIz = 0; // Set to one to prevent integrator windup?
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        // Set inital position
        // positionGoal = 0.0;

        // display PID coefficients on SmartDashboard
        if (debugHookElevator) {
          SmartDashboard.putNumber("P Gain", kP);
          SmartDashboard.putNumber("I Gain", kI);
          SmartDashboard.putNumber("D Gain", kD);
          SmartDashboard.putNumber("I Zone", kIz);
          SmartDashboard.putNumber("Feed Forward", kFF);
          SmartDashboard.putNumber("Max Output", kMaxOutput);
          SmartDashboard.putNumber("Min Output", kMinOutput);
          SmartDashboard.putNumber("Position Goal", positionGoal);
        }

        break;
    }

  }

  public void setPositionGoal(double Position, double maxSpeed) {
    m_pidController.setOutputRange(-maxSpeed, maxSpeed);
    m_pidController.setReference(Position, CANSparkMax.ControlType.kPosition);
    
  }

  public double getPosition() {
    return m_encoder.getPosition();
  }

  public double getVelocity() {
    return m_encoder.getVelocity();
  }

  public void stopMotor() {
    m_motor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    switch(Constants.THIS_ROBOT) {
      case Constants.PABLO_2021_SUMMER:
        /* No similar hardware on this robot */
        break;

      case Constants.PABLO_2022_FRC:
        // read PID coefficients from SmartDashboard
        if (debugHookElevator) {
          p = SmartDashboard.getNumber("P Gain", 0);
          i = SmartDashboard.getNumber("I Gain", 0);
          d = SmartDashboard.getNumber("D Gain", 0);
          iz = SmartDashboard.getNumber("I Zone", 0);
          ff = SmartDashboard.getNumber("Feed Forward", 0);
          max = SmartDashboard.getNumber("Max Output", 0);
          min = SmartDashboard.getNumber("Min Output", 0);
          driverSetPoint = SmartDashboard.getNumber("Position Goal", 0);

          // if PID coefficients on SmartDashboard have changed, write new values to controller
          if((p != kP)) { m_pidController.setP(p); kP = p; }
          if((i != kI)) { m_pidController.setI(i); kI = i; }
          if((d != kD)) { m_pidController.setD(d); kD = d; }
          if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
          if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
          if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_pidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max; 
          }

          SmartDashboard.putNumber("Applied Output", m_motor.getAppliedOutput());
          SmartDashboard.putNumber("Bus Voltage", m_motor.getBusVoltage());
          SmartDashboard.putNumber("Closed Loop Ramp Rate", m_motor.getClosedLoopRampRate());
          SmartDashboard.putNumber("Output Current", m_motor.getOutputCurrent());

          SmartDashboard.putNumber("Encoder position", m_encoder.getPosition());
          SmartDashboard.putNumber("Enc Position Conv Factor", m_encoder.getPositionConversionFactor());
          SmartDashboard.putNumber("Encoder velocity", m_encoder.getVelocity());
          SmartDashboard.putNumber("Enc Velocity Conv Factor", m_encoder.getVelocityConversionFactor());
          SmartDashboard.putNumber("Enc Cnts Per Rev", m_encoder.getCountsPerRevolution());

          /**
           * PIDController objects are commanded to a set point using the 
           * SetReference() method.
           * 
           * The first parameter is the value of the set point, whose units vary
           * depending on the control type set in the second parameter.
           * 
           * The second parameter is the control type can be set to one of four 
           * parameters:
           *  com.revrobotics.CANSparkMax.ControlType.kDutyCycle
           *  com.revrobotics.CANSparkMax.ControlType.kPosition
           *  com.revrobotics.CANSparkMax.ControlType.kVelocity
           *  com.revrobotics.CANSparkMax.ControlType.kVoltage
           */
          m_pidController.setReference(driverSetPoint, CANSparkMax.ControlType.kPosition);
          // m_motor.stopMotor();  // for debug
          
          SmartDashboard.putNumber("Elevator Set Point", driverSetPoint);
          SmartDashboard.putNumber("Elevator Position Sensor", m_encoder.getPosition());
        }
      break;
    }

  }
}
