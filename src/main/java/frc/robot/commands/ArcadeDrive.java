// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final DoubleSupplier m_throttleAxis;
  private final DoubleSupplier m_rotateAxis;

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param throttleAxis Lambda supplier of forward/backward speed
   * @param rotateAxis Lambda supplier of rotational speed
   */
  public ArcadeDrive(Drivetrain thisDrivetrain,
      DoubleSupplier throttleAxis, DoubleSupplier rotateAxis) {

    m_drivetrain = thisDrivetrain;
    m_throttleAxis = throttleAxis;
    m_rotateAxis = rotateAxis;

    addRequirements(thisDrivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_throttleAxis.getAsDouble(), m_rotateAxis.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Set motor drivers to zero if operator control is interrupted
    if (interrupted) m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  // Never ends but could be interrupted by another command (autonomous helpers)
  @Override
  public boolean isFinished() {
    return false;
  }
}
