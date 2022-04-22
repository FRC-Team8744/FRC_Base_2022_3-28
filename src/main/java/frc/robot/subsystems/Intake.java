// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Intake extends SubsystemBase {
  private Spark IntakeMotor = new Spark(1);

  private WPI_VictorSPX IntakeMotorVictor = new WPI_VictorSPX(11); 

  /** Constructor for Intake. */
  public Intake() {
    IntakeMotor.stopMotor();
    IntakeMotorVictor.stopMotor();
  }

  public void setMotor(double Speed) {
    switch(Constants.THIS_ROBOT) {
      case Constants.PABLO_2021_SUMMER:
      if (Speed == 0) {
        IntakeMotor.setVoltage(0.0);
        // IntakeMotor.stopMotor();
      } else {
        IntakeMotor.setVoltage(Speed * Constants.INTAKE_SPEED);
      }
      break;
      case Constants.PABLO_2022_FRC:
      if (Speed == 0) {
        IntakeMotorVictor.setVoltage(0.0);
        // IntakeMotor.stopMotor();
      } else {
        IntakeMotorVictor.setVoltage(Speed * Constants.INTAKE_SPEED);
      }
      break;
      }
  


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
