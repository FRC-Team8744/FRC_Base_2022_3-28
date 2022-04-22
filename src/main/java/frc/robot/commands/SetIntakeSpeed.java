// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class SetIntakeSpeed extends CommandBase {
  public final Intake m_Intake;
  private final double m_Speed;
  private final double m_duration;
  private long m_startTime;
  

  /** Creates a new SetIntakeSpeed.
   *  @param thisIntake  The subsystem this command will act on
   *  @param Speed The rate the arm will move (should be in range 0..1 )
   *  @param time
   */
  public SetIntakeSpeed(Intake thisIntake, double Speed, double time) {
    m_Intake = thisIntake;
    m_Speed = Speed;
    m_duration = time*1000;
    

    addRequirements(thisIntake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Intake.setMotor(m_Speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (System.currentTimeMillis() - m_startTime) >= m_duration;
  }
}
