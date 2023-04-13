from pymavlink import mavutil

#Starting a connection
connection = mavutil.mavlink_connection('udpin:localhost:14540')

#Waiting for the heartbeat 
connection.wait_heartbeat()

#Connection set up confirmation
print("Heartbeat from system (system %u component %u)" % (connection.target_system, connection.target_component))

#Arming command
connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_COMPONENT_ARM_DISARM, 0, 1, 0, 0, 0, 0, 0, 0)

#Printing command_ack(nowledge) to see if the command worked (0 means yes and 4 no)
arming_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(arming_ack_msg)

#Takeoff command
connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 10)

takeoff_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(takeoff_ack_msg)

#Landing command
connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_NAV_LAND, 0, 0, 0, 0, 0, 0, 0, 0)

landing_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(landing_ack_msg)

#Disarming command
connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_COMPONENT_ARM_DISARM, 0, 0, 0, 0, 0, 0, 0, 0)

disarming_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(disarming_ack_msg)
