/*

* Copyright (c) Joan-Manuel Marques 2013. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This file is part of the practical assignment of Distributed Systems course.
*
* This code is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
*/

package udp.servidor;

import java.nio.ByteBuffer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.uoc.dpcs.lsim.logger.LoggerManager.Level;
import lsim.library.api.LSimLogger;

/**
 * @author Joan-Manuel Marques
 *
 */

public class RemoteMapUDPservidor {
	
	public RemoteMapUDPservidor(int server_port, Map<String, String> map){
		LSimLogger.log(Level.INFO, "Inici RemoteMapUDPservidor ");
		LSimLogger.log(Level.INFO, "server_port: " + server_port);
		LSimLogger.log(Level.INFO, "map: " + map);

		// Server waits for requests a maximum time (timeout_time)
		Timer timer = new Timer();
		timer.schedule(
				new TimerTask() {
					@Override
					public void run() {
						System.exit(0);
					}
				},
				90000 // 90 seconds
				); 

		// Implementation of the server
		DatagramSocket socket;
		byte[] message_bytes;
		DatagramPacket packet;
		String message;
		String value;
		
		try {
			socket = new DatagramSocket(server_port);
			message_bytes = new byte[256];
			packet = new DatagramPacket(message_bytes, 256);
            
            socket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
           
            value = map.get(message);
            LSimLogger.log(Level.INFO, "map: " + value);
            message_bytes = value.getBytes();
            
            packet = new DatagramPacket(message_bytes, value.length(), 
            		packet.getAddress(), packet.getPort());
            socket.send(packet);
	    
	        socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
		
	
	}
}
