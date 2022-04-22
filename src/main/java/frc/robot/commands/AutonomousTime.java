//Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.ArmLift;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import static frc.robot.Constants.*;



public class AutonomousTime extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous Drive based on time. This will drive out for a period of time, turn
   * around for time (equivalent to time to turn around) and drive forward again. This should mimic
   * driving out, turning around and driving back.
   *
   * @param drivetrain The drive subsystem on which this command will run
   * @param m_armlift 
   * @param m_intake 
   */
  

  //private final ArmLift m_armlift = new ArmLift();

  public AutonomousTime(Drivetrain drivetrain, ArmLift m_armlift, Intake m_intake) {
    addCommands(
        //new DriveTime(-AutoDriveSpeed, 1.0, drivetrain),
        new SetIntakeSpeed(m_intake, 10.0, 2),
        new SetIntakeSpeed(m_intake, 0, 0),
        // new DriveTime( -0.5, 1.0, drivetrain),
        // new SetIntakeSpeed(m_intake, 0, 1),
        new DriveTime(AutoDriveSpeed, 2.0, drivetrain),
        new MoveArmDown(m_armlift)
        );
  }
}

