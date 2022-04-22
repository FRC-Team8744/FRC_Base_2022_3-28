package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmLift;

public class MoveArmTime extends CommandBase{
 // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

  private final ArmLift m_ArmLift;
  private final double m_SpeedLimit;
  private final long m_ArmTime;
  private long m_startTime;
  /** Constructor for MoveArmUp.
   *  @param thisArmLift  The subsystem this command will act on
   *  @param liftSpeed The rate the arm will move (should be in range 0..1 )
   */
  public MoveArmTime(ArmLift thisArmLift, double liftSpeed, long this_ArmTime) {
    m_ArmLift = thisArmLift;
    m_SpeedLimit = liftSpeed;
    m_ArmTime = this_ArmTime; 
    addRequirements(thisArmLift);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_startTime= System.currentTimeMillis();
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ArmLift.raiseArm(m_SpeedLimit);
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
  return System.currentTimeMillis() - m_startTime > m_ArmTime; 
  }
}
   

