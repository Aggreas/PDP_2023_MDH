import random
from pymavlink import mavutil



def authentification():
    id="root"
    password="1234"
    id_client = input("Entrez votre identifiant :\n")
    password_client = input("Entrez votre mot de passe :\n")
    if(id == id_client and password == password_client):
        return True
    
    else:
        return False
    
def send_data(is_root,the_connection):
           

    while(True): 
        x = the_connection.messages['LOCAL_POSITION_NED'].x
        y = the_connection.messages['LOCAL_POSITION_NED'].y
        z = the_connection.messages['LOCAL_POSITION_NED'].z
        choice1 = input("Que voulez vous faire : \n\t 1-Coordonnées GPS \n\t 2-Caméra \n\t 0-Pour quitter\n ")
        if(is_root):
            if(choice1=="1"):
                print("Les coordonnées GPS sont : x = "+str(x)+" y = "+str(y)+" z = "+str(z)+"\n")
                continue
            if(choice1=="2"):
                print("La caméra est allumée ...\n")
                continue
            if(choice1=="0"):
                return 0
            else:
                print("Nous n'avons pas compris votre demande \n")
                continue
        else:
            if(choice1=="1"):
                x2,y2,z2= encode_gps(x,y,z)
                print("Les coordonnées GPS sont : x = "+str(x2)+" y = "+str(y2)+" z = "+str(z2)+"\n")
                continue
            if(choice1=="2"):
                print("La caméra affiche un écran noir...\n")        
                continue
            if(choice1=="0"):
                return 0
            else:
                print("Nous n'avons pas compris votre demande \n")
                continue
def start(is_root):
    # Start a connection listening on a UDP port
  
  the_connection = mavutil.mavlink_connection('udpin:localhost:14540')

	# Wait for the first heartbeat 
	#   This sets the system and component ID of remote system for the link
  
  the_connection.wait_heartbeat()
  print("Heartbeat from system (system %u component %u)" % (the_connection.target_system, the_connection.target_component))
  
	# Once connected, use 'the_connection' to get and send messages
	# print continuously all informations
    #msg = the_connection.recv_match(type='LOCAL_POSITION_NED',blocking=True)
	#print(msg[0])
    
  print("Connected\n")
  send_data(is_root,the_connection)

  return 0

def encode_gps(x,y,z):
  x = x+random.uniform(1.0,10.0)
  y = y+random.uniform(1.0,10.0)
  z = z+random.uniform(1.0,10.0)
  return (x,y,z)



def main():
  while(True):
    is_root = authentification()
    start(is_root)
    disconnect = input("Voulez vous vous déconnecter et reconnecter : \n\t 1-Oui \n\t 2-Non\n")
    if(disconnect =="1"):
      continue
    else:
      break
  return 0
        
main()