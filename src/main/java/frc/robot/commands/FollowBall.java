package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class FollowBall extends CommandBase{
  Vision m_Vision;
  Drivetrain m_driveTrain; 

    public FollowBall(Vision this_vision, Drivetrain this_driveTrain) {
        m_Vision = this_vision;
        m_driveTrain = this_driveTrain; 
    }

     // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.arcadeDrive(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = m_Vision.GetX(); 
    m_driveTrain.arcadeDrive(0, -x/10);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_driveTrain.arcadeDrive(0, 0);%
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
