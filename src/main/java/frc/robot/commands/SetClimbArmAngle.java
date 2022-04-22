// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.HookArm;

/*
 * Creates a new SetClimbArmAngle command. This command will set the angle goal for
 * the robot's climber hook arm.
 */
public class SetClimbArmAngle extends CommandBase {
  private final HookArm m_hookarm;
  private long timeCommandStarted;
  private double positionArmAtStart;
  private double positionArmGoal;
  private double angleRequest;
  private double angleToleranceDegrees = 1.0;  // Move to Constants, +/- range to check if arm moved to the right angle
  private double angleTolerance = angleToleranceDegrees/180.0;  // Move to Constants, convert angle tolerance from degrees to +/- 1.0 encoder range
  private long timeout;
  private double maxSpeed;
/**
   * Creates a new SetClimbArmAngle.
   *
   * @param this_angle The relative angle to move the arm (degrees from the current position, clockwise is positive)
   * @param this_maxSpeed The max speed
   * @param this_arm The arm subsystem to move.
   * @param this_timeout Milliseconds before timeout.
   */
public SetClimbArmAngle(double this_angle, double this_maxSpeed, HookArm this_arm, long this_timeout) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this_arm);

    // Save references for later use
    m_hookarm = this_arm;
    // Convert +/-180 degree requested angle into +/-1.0 encoder position
    angleRequest = this_angle;

    timeout = this_timeout;

    maxSpeed = this_maxSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_hookarm.setCurrentLimit(Constants.LOW_CURRENT_LIMIT);

    // Mark the time that the command was called.
    timeCommandStarted = System.currentTimeMillis();

    // Measure the encoder position at the start of the command.
    positionArmAtStart = m_hookarm.getPosition();

    // Convert +/-180 degree requested angle into +/-1.0 encoder position
    positionArmGoal = positionArmAtStart + angleRequest;

    // Set the new goal angle for the arm
    m_hookarm.setPositionGoal(positionArmGoal, maxSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Disable motor
    m_hookarm.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Check time - give up if over limit (for safety)
    if (System.currentTimeMillis() - timeCommandStarted > timeout) return true;

    // Get current arm position
    // Check if arm has reached the goal position (within reason)
    if (Math.abs(m_hookarm.getPosition() - positionArmGoal) < angleTolerance) return true;

    return false;
  }
}
