import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Receiver {

	public Receiver() {

	}
	
	public void send(String ack, int port, InetAddress ip) throws IOException, InterruptedException{
		Thread.sleep(50);
		DatagramSocket senderSocket = new DatagramSocket(9878);//create socket for sending
		byte[] sendData = new byte[1024];//allocate message space
		sendData = ack.getBytes();
		DatagramPacket sendPkt = new DatagramPacket(sendData, sendData.length, ip, 9879);
		senderSocket.send(sendPkt);
		senderSocket.close();
		System.out.println("Message Sent");
		
	}

	public void receive() throws IOException, InterruptedException {
		DatagramSocket receiverSocket = new DatagramSocket(9876);
		byte[] rcvData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket rcvPkt = new DatagramPacket(rcvData, rcvData.length);
			receiverSocket.receive(rcvPkt);
			String message = new String(rcvPkt.getData());
			InetAddress IPAddress = rcvPkt.getAddress();
			int port = rcvPkt.getPort();
			System.out.println(message);
			send(message, port, IPAddress);
		    
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Receiver rec = new Receiver();
		rec.receive();
	}

}
