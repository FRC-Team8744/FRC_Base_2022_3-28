// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.Arm_Voltage;

// import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.Joystick.AxisType;
//import edu.wpi.first.wpilibj.XboxController.Axis;
//import edu.wpi.first.wpilibj.simulation.JoystickSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.subsystems.UnknownMotor;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutonomousTime;
import frc.robot.commands.FollowBall;
import frc.robot.commands.MoveArmDown;
import frc.robot.commands.MoveArmUp;
import frc.robot.commands.Seq_LiftAndHang;
import frc.robot.commands.Seq_MoveToNextBar;
import frc.robot.commands.Seq_TraversalRung;
import frc.robot.commands.SetIntakeSpeed;
import frc.robot.subsystems.ArmLift;
// import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HookArm;
import frc.robot.subsystems.HookElevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
// import frc.robot.commands.ClimberUp;
// import frc.robot.commands.ClimberDown;   
import frc.robot.commands.SetClimbArmAngle;
// import frc.robot.commands.SetClimbArmHold;
import frc.robot.commands.SetClimbElevator;
// import edu.wpi.first.wpilibj2.command.PrintCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Create operator interface objects
  // Assumes a gamepad plugged into channnel 0
  private final Joystick m_joystick = new Joystick(0);

  //Map of buttons for the Pro joystick
  private final JoystickButton Trigger = new JoystickButton(m_joystick, 1);
  private final JoystickButton Button2 = new JoystickButton(m_joystick, 2);
  private final JoystickButton Button3 = new JoystickButton(m_joystick, 3);
  private final JoystickButton Button4 = new JoystickButton(m_joystick, 4);
  private final JoystickButton Button5 = new JoystickButton(m_joystick, 5);
  private final JoystickButton Button6 = new JoystickButton(m_joystick, 6);
  private final JoystickButton Button7 = new JoystickButton(m_joystick, 7);
  private final JoystickButton Button8 = new JoystickButton(m_joystick, 8);
  private final JoystickButton Button9 = new JoystickButton(m_joystick, 9);
  private final JoystickButton Button10= new JoystickButton(m_joystick, 10);
  private final JoystickButton Button11 = new JoystickButton(m_joystick, 11);
  private final JoystickButton Button12 = new JoystickButton(m_joystick, 12);

  //Map of buttons for the xbox controller joystick

  // private final JoystickButton ButtonA = new JoystickButton(m_joystick, 1);
  // private final JoystickButton ButtonB = new JoystickButton(m_joystick, 2);
  // private final JoystickButton ButtonX = new JoystickButton(m_joystick, 3);
  // private final JoystickButton ButtonY = new JoystickButton(m_joystick, 4);
  // private final JoystickButton ButtonLeft = new JoystickButton(m_joystick, 5);
  // private final JoystickButton ButtonRight = new JoystickButton(m_joystick, 6);
  // private final JoystickButton ButtonBack = new JoystickButton(m_joystick, 7);
  // private final JoystickButton ButtonStart = new JoystickButton(m_joystick, 8);
  

  // Create robot subsystem objects based on which robot is connected
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final ArmLift m_armlift = new ArmLift();
  private final Intake m_intake = new Intake();
  // private final Climber m_climber = new Climber(); 
  private final HookArm m_hookarm = new HookArm();
  private final HookElevator m_hookelevator = new HookElevator();
  private final Vision m_vision = new Vision();
  // Shuffleboard data
  // private ShuffleboardTab myTab = Shuffleboard.getTab("MyTab");
  // private NetworkTableEntry myEntry = myTab.add("Gyro Z", 0).getEntry();
  
  // The robot's commands are defined here...
  private final AutonomousTime m_autoCommand = new AutonomousTime(m_drivetrain, m_armlift, m_intake);

  // Create SmartDashboard chooser for autonomous routines
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set joystick configuration
    m_joystick.setThrottleChannel(1);
    m_joystick.setTwistChannel(0);
    // Steg chanenelrin

    // Setup Shuffleboard items for debug
    // Shuffleboard.getTab("MyTab").add("Left Encoder", m_leftEncoder);
    // Shuffleboard.getTab("MyTab").add("Right Encoder", m_rightEncoder);
    // Shuffleboard.getTab("MotorPID").add("HookArm", m_hookarm);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command is arcade drive. This will run unless another command is scheduled over it.
    m_drivetrain.setDefaultCommand(getArcadeDriveCommand());
    switch(Constants.This_Joystick) {
      //case for the pro joystick
      case Constants.Pro:
        Trigger.whenPressed(new MoveArmUp(m_armlift, Arm_Voltage))
        .whenReleased(new MoveArmDown(m_armlift));

        Button2.whenPressed(new MoveArmUp(m_armlift, -0.1 * Arm_Voltage))
        .whenReleased(new MoveArmDown(m_armlift));     

        Button3.whenPressed(new SetIntakeSpeed(m_intake, 10.0, 0))
        .whenReleased(new SetIntakeSpeed(m_intake, 0.0, 0));

        Button4.whenHeld(new FollowBall(m_vision, m_drivetrain ));

        Button5.whenPressed(new SetIntakeSpeed(m_intake, -10.0, 0))
        .whenReleased(new SetIntakeSpeed(m_intake, 0.0, 0));
        
        Button6.whenPressed(new Seq_TraversalRung(m_hookarm, m_hookelevator, m_armlift));

        Button7.whenPressed(new SetClimbArmAngle(0.1, 0.2, m_hookarm, 1000));

        Button8.whenPressed(new SetClimbArmAngle(-0.25,0.2, m_hookarm, 1000));

        Button9.whenPressed(new SetClimbElevator(9.0, m_hookelevator));

        Button10.whenPressed(new SetClimbElevator(-9.0, m_hookelevator));

        Button11.whenPressed(new Seq_LiftAndHang(m_hookarm, m_hookelevator));

        Button12.whenPressed(new Seq_MoveToNextBar(m_hookarm, m_hookelevator, m_armlift));


        // Button8.whenPressed(new ClimberUp(m_climber, Arm_Voltage))
        //  .whenReleased(new ClimberDown(m_climber));   
        break;
        
      //case for the xbox controller  
      case Constants.xbox_controller:
      // ButtonA.whenPressed(new PrintCommand("Button A Pressed"))
      // .whenReleased(new PrintCommand("Button A Released"));

    // ButtonLeft.whenPressed(new MoveArmUp(m_armlift, Arm_Voltage))
    // .whenReleased(new MoveArmDown(m_armlift));

    //   ButtonB.whenPressed(new SetIntakeSpeed(m_intake, 10.0, 0))
    //   .whenReleased(new SetIntakeSpeed(m_intake, 0.0, 0));

    //   ButtonA.whenPressed(new SetIntakeSpeed(m_intake, -10.0, 0))
    //   .whenReleased(new SetIntakeSpeed(m_intake, 0.0, 0));

      // ButtonX.whenPressed(new SetClimbArmAngle(30, m_hookarm));

      // ButtonY.whenPressed(new SetClimbArmAngle(-40, m_hookarm));

      // ButtonLeft.whenPressed(new SetClimbElevator(0.2, m_hookelevator));
 
      // ButtonRight.whenPressed(new SetClimbElevator(-9.0, m_hookelevator));
 
      // ButtonStart.whenPressed(new RunClimbSequence(m_drivetrain, m_hookarm, m_hookelevator));



 
         break;
    }

          


    // Use InstantCommand for extremely short actions (i.e. activate a valve)
    // ButtonA.whenPressed(new InstantCommand(m_hatchSubsystem::grabHatch, m_hatchSubsystem))
    //       .whenReleased(new InstantCommand(m_hatchSubsystem::releaseHatch, m_hatchSubsystem));

    // Setup SmartDashboard options
    // m_chooser.setDefaultOption("Auto Routine Distance", new AutonomousDistance(m_drivetrain));
    // m_chooser.addOption("Auto Routine Time", new AutonomousTime(m_drivetrain));
    SmartDashboard.putData(m_chooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

    /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getArcadeDriveCommand() {
    return new ArcadeDrive(
        m_drivetrain, () -> m_joystick.getThrottle() * (1-(m_joystick.getRawAxis(3)+1)/2), () -> -m_joystick.getTwist());
        //m_drivetrain, () -> m_joystick.getThrottle(), () -> -m_joystick.getTwist());
  }

}
