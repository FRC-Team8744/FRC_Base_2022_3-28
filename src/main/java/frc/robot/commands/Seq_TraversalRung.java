// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmLift;
import frc.robot.subsystems.HookArm;
import frc.robot.subsystems.HookElevator;

// import static frc.robot.Constants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Seq_TraversalRung extends SequentialCommandGroup {
  /**
   * Command to run (semi-autonomous) move from one hanger bar to the next.
   *
   * @param this_hookarm The angled arms that hook the bar.
   * @param this_elevator The vertical hooks that raise up to lift the robot.
   * @param this_ArmLift The intake arm 
   */
  public Seq_TraversalRung(HookArm this_hookarm, HookElevator this_elevator, ArmLift this_ArmLift) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // This command assumes the robot is already hanging from a bar with elevator hooks free!

      // Angle robot back and reach up with the elevator hooks
      // // Command group ends when FIRST item ends.  (any command finish will cancel group)
      new ParallelCommandGroup(
        // Rotate robot elevator to aim at next bar.
        new SetClimbArmHold(0.2, this_hookarm),  // CAUTION - HIGH POWER
        // Lift elevator to be past next bar.
        new SetClimbElevator(9.0, this_elevator)
      ),
      new MoveArmTime(this_ArmLift, 0.6, 1000),

      //swing forward
      new SetClimbArmAngle(-0.25, 1, this_hookarm, 1000),

      //hold angle and hook
      new ParallelCommandGroup(
        new SetClimbElevator(-5, this_elevator), 
        new SetClimbArmHold(0, this_hookarm)
      ),
      //arms swing off the mid rung
      new SetClimbElevator(-6, this_elevator),
      new ParallelCommandGroup(
        new SetClimbElevator(-4, this_elevator),
        new SetClimbArmAngle(-0.25, 0.4, this_hookarm, 1000)
      ),
      new SetClimbArmAngle(-0.25, 0.4, this_hookarm, 1000),
      new SetClimbElevator(5, this_elevator),
      new SetClimbArmAngle(-0.25, 0.4, this_hookarm, 1000)
    

      

      // // Step robot angle back until arm contacts bar
      // new SetClimbArmAngle(0.2, this_hookarm, 1000),
      // // Step robot angle back until arm contacts bar
      // new SetClimbArmAngle(0.2, this_hookarm, 1000),
      // // Step robot angle back until arm contacts bar
      // new SetClimbArmAngle(0.2, this_hookarm, 1000),
      // // Step robot angle back until arm contacts bar
      // new SetClimbArmAngle(0.2, this_hookarm, 1000),
      // // Step robot angle back until arm contacts bar
      // new SetClimbArmAngle(0.2, this_hookarm, 1000)

    ); 
  }
}
