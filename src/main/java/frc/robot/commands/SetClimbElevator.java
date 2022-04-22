// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HookElevator;

/*
 * Creates a new SetClimbElevator command. This command will set the height goal for
 * the robot's elevator hook.
 */
public class SetClimbElevator extends CommandBase {
  private final HookElevator m_hookelevator;
  private long timeCommandStarted;
  private double positionElevatorAtStart;
  private double positionElevatorGoal;
  private double moveRequest;
  // private double moveToleranceInches = 2.0;  // Move to Constants, +/- range to check if arm moved to the right angle
  private double moveTolerance = 0.1;  // Move to Constants, convert tolerance
  private double maxSpeed; 

/**
   * Creates a new SetClimbElevator.
   *
   * @param this_distance The distance to move the elevator (positive is up)
   * @param this_elevator The elevator subsystem to move.
   * @param this_maxSpeed
   */
public SetClimbElevator(double this_distance, double this_maxSpeed, HookElevator this_elevator) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this_elevator);

    // Save references for later use
    m_hookelevator = this_elevator;
    // Convert distance?
    moveRequest = this_distance;

    maxSpeed = this_maxSpeed;
  }

  public SetClimbElevator(double this_distance, HookElevator this_elevator) {
    this(this_distance,1,this_elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Mark the time that the command was called.
    timeCommandStarted = System.currentTimeMillis();

    // Measure the encoder position at the start of the command.
    positionElevatorAtStart = m_hookelevator.getPosition();

    // Convert move requested into +/-1.0 encoder position?
    positionElevatorGoal = positionElevatorAtStart + moveRequest;

    // Set the new goal angle for the arm
    m_hookelevator.setPositionGoal(positionElevatorGoal, maxSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Disable motor
    m_hookelevator.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Check time - give up if over limit (for safety)
    if (System.currentTimeMillis() - timeCommandStarted > 3000) return true;

    // Get current elevator position
    // Check if elevator has reached the goal position (within reason)
    if (Math.abs(m_hookelevator.getPosition() - positionElevatorGoal) < moveTolerance) return true;

    return false;
  }
}
