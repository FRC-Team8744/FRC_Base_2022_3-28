// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber extends SubsystemBase {
  private CANSparkMax LiftMotorMax = new CANSparkMax(9, MotorType.kBrushless);

  /** Constructor for ArmLift. */
  public Climber() {
    LiftMotorMax.stopMotor();
  }

  public void raiseArm(double liftSpeed) {
     LiftMotorMax.setVoltage(liftSpeed * Constants.ARMLIFTSPEED);
  }

  public void releaseArm() {
    LiftMotorMax.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
