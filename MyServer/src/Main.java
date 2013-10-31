import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8081);
			while (true) {
				Socket socket = serverSocket.accept();
				MyThread thread = new MyThread();
				thread.setSocket(socket);
				thread.run();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
