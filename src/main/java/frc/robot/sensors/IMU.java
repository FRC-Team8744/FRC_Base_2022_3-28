// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class IMU {
    AHRS KauaiLabsIMU;
    
    /* Constructor (may not work to have init here) */
    public IMU() {
        try {
            /***********************************************************************
             * navX-MXP: - Communication via RoboRIO MXP (SPI, I2C) and USB. - See
             * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
             * 
             * navX-Micro: - Communication via I2C (RoboRIO MXP or Onboard) and USB. - See
             * http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
             * 
             * VMX-pi: - Communication via USB. - See
             * https://vmx-pi.kauailabs.com/installation/roborio-installation/
             * 
             * Multiple navX-model devices on a single robot are supported.
             ************************************************************************/
            KauaiLabsIMU = new AHRS(SerialPort.Port.kUSB);
          } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
          }
      
    }
  
    /**
     * Get the rate of turn in degrees-per-second around the X-axis.
     *
     * @return rate of turn in degrees-per-second
     */
    public double getRateX() {
      return KauaiLabsIMU.getRawGyroX();
    }
  
    /**
     * Get the rate of turn in degrees-per-second around the Y-axis.
     *
     * @return rate of turn in degrees-per-second
     */
    public double getRateY() {
      return KauaiLabsIMU.getRawGyroY();
    }
  
    /**
     * Get the rate of turn in degrees-per-second around the Z-axis.
     *
     * @return rate of turn in degrees-per-second
     */
    public double getRateZ() {
      return KauaiLabsIMU.getRawGyroZ();
    }
  
    /**
     * Get the currently reported angle around the X-axis.
     *
     * @return current angle around X-axis in degrees
     */
    public double getAngleX() {
      return KauaiLabsIMU.getRoll();
    }
  
    /**
     * Get the currently reported angle around the X-axis.
     *
     * @return current angle around Y-axis in degrees
     */
    public double getAngleY() {
      return KauaiLabsIMU.getPitch();
    }
  
    /**
     * Get the currently reported angle around the Z-axis.
     *
     * @return current angle around Z-axis in degrees
     */
    public double getAngleZ() {
      return KauaiLabsIMU.getYaw();
    }
  
    /** Reset the gyro angles to 0. */
    public void reset() {
        KauaiLabsIMU.zeroYaw();
        KauaiLabsIMU.reset();
      }

    public void updateSmartDashboard() {
      /* Display 6-axis Processed Angle Data                                      */
      SmartDashboard.putBoolean(  "IMU_Connected",        KauaiLabsIMU.isConnected());
      SmartDashboard.putBoolean(  "IMU_IsCalibrating",    KauaiLabsIMU.isCalibrating());
      SmartDashboard.putNumber(   "IMU_Yaw",              KauaiLabsIMU.getYaw());
      SmartDashboard.putNumber(   "IMU_Pitch",            KauaiLabsIMU.getPitch());
      SmartDashboard.putNumber(   "IMU_Roll",             KauaiLabsIMU.getRoll());
      
      /* Display tilt-corrected, Magnetometer-based heading (requires             */
      /* magnetometer calibration to be useful)                                   */
      
      SmartDashboard.putNumber(   "IMU_CompassHeading",   KauaiLabsIMU.getCompassHeading());
      
      /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
      SmartDashboard.putNumber(   "IMU_FusedHeading",     KauaiLabsIMU.getFusedHeading());

      /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
      /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */
      
      SmartDashboard.putNumber(   "IMU_TotalYaw",         KauaiLabsIMU.getAngle());
      SmartDashboard.putNumber(   "IMU_YawRateDPS",       KauaiLabsIMU.getRate());

      /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
      
      SmartDashboard.putNumber(   "IMU_Accel_X",          KauaiLabsIMU.getWorldLinearAccelX());
      SmartDashboard.putNumber(   "IMU_Accel_Y",          KauaiLabsIMU.getWorldLinearAccelY());
      SmartDashboard.putBoolean(  "IMU_IsMoving",         KauaiLabsIMU.isMoving());
      SmartDashboard.putBoolean(  "IMU_IsRotating",       KauaiLabsIMU.isRotating());

      /* Display estimates of velocity/displacement.  Note that these values are  */
      /* not expected to be accurate enough for estimating robot position on a    */
      /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
      /* of these errors due to single (velocity) integration and especially      */
      /* double (displacement) integration.                                       */
      
      SmartDashboard.putNumber(   "Velocity_X",           KauaiLabsIMU.getVelocityX());
      SmartDashboard.putNumber(   "Velocity_Y",           KauaiLabsIMU.getVelocityY());
      SmartDashboard.putNumber(   "Displacement_X",       KauaiLabsIMU.getDisplacementX());
      SmartDashboard.putNumber(   "Displacement_Y",       KauaiLabsIMU.getDisplacementY());
      
      /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
      /* NOTE:  These values are not normally necessary, but are made available   */
      /* for advanced users.  Before using this data, please consider whether     */
      /* the processed data (see above) will suit your needs.                     */
      
      SmartDashboard.putNumber(   "RawGyro_X",            KauaiLabsIMU.getRawGyroX());
      SmartDashboard.putNumber(   "RawGyro_Y",            KauaiLabsIMU.getRawGyroY());
      SmartDashboard.putNumber(   "RawGyro_Z",            KauaiLabsIMU.getRawGyroZ());
      SmartDashboard.putNumber(   "RawAccel_X",           KauaiLabsIMU.getRawAccelX());
      SmartDashboard.putNumber(   "RawAccel_Y",           KauaiLabsIMU.getRawAccelY());
      SmartDashboard.putNumber(   "RawAccel_Z",           KauaiLabsIMU.getRawAccelZ());
      SmartDashboard.putNumber(   "RawMag_X",             KauaiLabsIMU.getRawMagX());
      SmartDashboard.putNumber(   "RawMag_Y",             KauaiLabsIMU.getRawMagY());
      SmartDashboard.putNumber(   "RawMag_Z",             KauaiLabsIMU.getRawMagZ());
      SmartDashboard.putNumber(   "IMU_Temp_C",           KauaiLabsIMU.getTempC());
      
      /* Omnimount Yaw Axis Information                                           */
      /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
      AHRS.BoardYawAxis yaw_axis = KauaiLabsIMU.getBoardYawAxis();
      SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
      SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
      
      /* Sensor Board Information                                                 */
      SmartDashboard.putString(   "FirmwareVersion",      KauaiLabsIMU.getFirmwareVersion());
      
      /* Quaternion Data                                                          */
      /* Quaternions are fascinating, and are the most compact representation of  */
      /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
      /* from the Quaternions.  If interested in motion processing, knowledge of  */
      /* Quaternions is highly recommended.                                       */
      SmartDashboard.putNumber(   "QuaternionW",          KauaiLabsIMU.getQuaternionW());
      SmartDashboard.putNumber(   "QuaternionX",          KauaiLabsIMU.getQuaternionX());
      SmartDashboard.putNumber(   "QuaternionY",          KauaiLabsIMU.getQuaternionY());
      SmartDashboard.putNumber(   "QuaternionZ",          KauaiLabsIMU.getQuaternionZ());
      
      /* Connectivity Debugging Support                                           */
      SmartDashboard.putNumber(   "IMU_Byte_Count",       KauaiLabsIMU.getByteCount());
      SmartDashboard.putNumber(   "IMU_Update_Count",     KauaiLabsIMU.getUpdateCount());
      
    }
}
