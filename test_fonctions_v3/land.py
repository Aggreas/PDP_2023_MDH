from pymavlink import mavutil

# Start a connection listening on a UDP port
connection = mavutil.mavlink_connection('udpin:localhost:14540')

# Wait for the first heartbeat 
connection.wait_heartbeat()


connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_NAV_LAND, 0, 0, 0, 0, 0, 0, 0, 0)

msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
print(msg)

