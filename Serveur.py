import socket
from pymavlink import mavutil
from _thread import *
import threading
import time
print_lock = threading.Lock()
 
HOST ='10.200.74.218'  
PORT = 32568  

#Starting a connection
def connexion():
    connection = mavutil.mavlink_connection('udpin:localhost:14540')
    #Waiting for the heartbeat 
    connection.wait_heartbeat()
    print("Heartbeat from system (system %u component %u)" % (connection.target_system, connection.target_component))
    print('\n')
    return connection

#Arming command
def arm(connection):
    connection.mav.command_long_send(connection.target_system, connection.target_component, 
                                     mavutil.mavlink.MAV_CMD_COMPONENT_ARM_DISARM, 0, 1, 0, 0, 0, 0, 0, 0)
    arming_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
    print(arming_ack_msg)
    print('\n')

#Takeoff command
def takeoff(connection):
    connection.mav.command_long_send(connection.target_system, connection.target_component, 
                                     mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 10)
    takeoff_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
    print(takeoff_ack_msg)
    print('\n')

#Landing command
def land(connection):
    connection.mav.command_long_send(connection.target_system, connection.target_component, mavutil.mavlink.MAV_CMD_NAV_LAND, 
                                     0, 0, 0, 0, 0, 0, 0, 0)
    landing_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
    print(landing_ack_msg)
    print('\n')

#Disarming command
def disarm(connection):
    connection.mav.command_long_send(connection.target_system, connection.target_component, 
                                     mavutil.mavlink.MAV_CMD_COMPONENT_ARM_DISARM, 0, 0, 0, 0, 0, 0, 0, 0)
    disarming_ack_msg = connection.recv_match(type='COMMAND_ACK', blocking=True)
    print(disarming_ack_msg)
    print('\n')

#gps coordinates
def gps(connection, s):
    alt_var = connection.messages['GLOBAL_POSITION_INT'].relative_alt #en mm
    s.sendall((alt_var/1000).encode)
    
def threadGPS(connection, s):
    while True:
        gps(connection, s)
        time.sleep(0.5)

def serverTCP():
    is_armed=False
    is_takeoff=False
    is_connected=False
    t=False 

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        print(f"Server listening on {HOST} : {PORT}")
        conn, addr = s.accept()
        #thread = threading.Thread(target=threadGPS,args=(s,))
        with conn:
            print('Connected by', addr)
            while True:
                data = conn.recv(1024)
                message = data.decode('utf-8')
                print(message)
                if(message =="connexion\n" and is_connected==False):
                    connection = connexion()
                    print("connexion")
                    is_connected = True
                    t = threading.Thread(target = gps, args =(connection,s))
                    t.start()
                elif(message =="arm\n" and is_armed==False and is_connected):
                    arm(connection)
                    is_armed=True
                elif(message =="disarm\n" and is_armed and is_takeoff == False):
                    disarm(connection)
                    is_armed=False
                elif(message =="takeoff\n" and is_armed and is_takeoff == False):
                    takeoff(connection)
                    is_takeoff=True
                elif(message =="land\n" and is_armed and is_takeoff):
                    land(connection)
                    is_takeoff=False
                elif(message =="exit\n"):
                    break
                else:
                    print("Command not found or unavailable\n")   
                if not message:
                    break
