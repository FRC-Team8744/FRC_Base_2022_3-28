// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmLift;

public class MoveArmDown extends CommandBase {
  private final ArmLift m_ArmLift;

  /** Constructor for MoveArmDown.
   *  @param thisArmLift  The subsystem this command will act on
   */
  public MoveArmDown(ArmLift thisArmLift) {
    m_ArmLift = thisArmLift;

    addRequirements(thisArmLift);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ArmLift.releaseArm();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Disable motor
    m_ArmLift.releaseArm();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
