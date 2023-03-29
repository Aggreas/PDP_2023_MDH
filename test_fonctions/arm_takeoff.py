from pymavlink import mavutil

# Start a connection listening on a UDP port
connection = mavutil.mavlink_connection('udpin:localhost:14540')

# Wait for the first heartbeat 
connection.wait_heartbeat()

#ARM #400
connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_COMPONENT_ARM_DISARM, 0, 1, 0, 0, 0, 0, 0, 0)

msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(msg)

#TAKEOFF #22
#connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 10)
connection.mav.command_int_send(connection.target_system, connection.target_component, 0, mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 0, 10.0)

msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(msg)

