import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/*This class waits for packets and sends a simple ack that just consists of the original message back to the sender every time a packet is recieved.
*/

public class Receiver {

	public Receiver() {

	}
	
	public void send(String ack, InetAddress ip) throws IOException, InterruptedException{//Note that this just sends the message recieved as an ack
		Thread.sleep(50);
		DatagramSocket senderSocket = new DatagramSocket(9878);//create socket for sending
		byte[] sendData = new byte[1024];						//allocate message space
		sendData = ack.getBytes();								//convert string ack into byte array
		DatagramPacket sendPkt = new DatagramPacket(sendData, sendData.length, ip, 9879);//create packet to send to sender port 9879
		senderSocket.send(sendPkt);														//send packet
		senderSocket.close();															//close port
		System.out.println("Ack sent");
		
	}

	public void receive() throws IOException, InterruptedException {
		DatagramSocket receiverSocket = new DatagramSocket(9876);//open socket for listening on port 9876
		byte[] rcvData = new byte[1024];						//create byte array to accept incoming message
		while (true) {											//loop to keep listening
			DatagramPacket rcvPkt = new DatagramPacket(rcvData, rcvData.length);//initialize a packet 
			receiverSocket.receive(rcvPkt);						//set rcvPkt to the next packet waiting on the socket
			String message = new String(rcvPkt.getData());		//parse packet into a String
			InetAddress IPAddress = rcvPkt.getAddress();		//get IPAdress of sender
			int port = rcvPkt.getPort();						//get port of sender
			System.out.println(message);						//print message
			send(message, IPAddress);						//call send to send an ack with the message recieved and the Ip address
		    
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Receiver rec = new Receiver();//create instance of receiver class
		rec.receive();//call receive message
	}

}
