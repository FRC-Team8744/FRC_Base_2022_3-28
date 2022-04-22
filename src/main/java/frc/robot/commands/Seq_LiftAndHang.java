// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.HookArm;
import frc.robot.subsystems.HookElevator;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html

public class Seq_LiftAndHang extends SequentialCommandGroup {
  /**
   * Command to run (semi-autonomous) lift to bar and hang sequence.
   *
   * @param this_hookarm The angled arms that hook the bar.
   * @param this_elevator The vertical hooks that raise up to lift the robot.
   */
  public Seq_LiftAndHang(HookArm this_hookarm, HookElevator this_elevator) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // This command assumes the robot has the elevator hooks positioned above the next bar!
      // new SetClimbElevator(-20, 1, this_elevator),
      // // Lift robot halfway and pull hook arms back at the same time
      // // Command group ends when BOTH items have ended.  (waits for last command to finish)
      // new ParallelCommandGroup(
      //   // Lift entire robot off the ground halfway.
      //   new SetClimbElevator(-20, 1, this_elevator),
      //   // Move hook arm to upright position.
        new SetClimbArmAngle(-0.35, 1.0, this_hookarm, 400),
      // ),

      // Complete lift
      new SetClimbElevator(-120, 1, this_elevator),

      new ParallelCommandGroup(
        // Hold elevator position
        new SetClimbElevator(-2.0, this_elevator),
        // Rotate robot arms forward to grab bar (elevator may drop slightly)
        new SetClimbArmAngle(0.1, 0.2,  this_hookarm, 3000)
      ),

      // Pop elevator up so the full weight of robot is on the hook arms
      new SetClimbElevator(20.0, 0.2, this_elevator)
    );
  }
}
