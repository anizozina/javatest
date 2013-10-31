import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	private final static String PORT = 8081
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				MyThread thread = new MyThread();
				thread.setSocket(socket);
				thread.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
