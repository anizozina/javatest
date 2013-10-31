import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MyThread extends Thread {
	private Socket socket;

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		if (socket == null) {
			return;
		}
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);

			while (inputStream.available() == 0)
				;

			int c;
			String line = "";
			while ((c = bufferedReader.read()) != '\n') {
				line = line + (char) c;
			}

			String[] cell = line.split(" ");

			if (cell[1].charAt(cell[1].length() - 1) == '/') {
				cell[1] = cell[1] + "index.html";
			}

			String fileName = "";
			fileName = cell[1].substring(1);
			System.out.println(fileName);

			String dataStr = getFileString(fileName);

			showContent(dataStr);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(bufferedReader, inputStreamReader, inputStream);
		}

	}

	private String getFileString(String fileName) throws IOException {
		FileInputStream fileIS = null;
		File file = new File(fileName);
		try {
			fileIS = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fileIS.read(data);
			String dataStr = new String(data);
			return dataStr;
		} catch (FileNotFoundException e) {
			throw new IOException("File not found");
		} catch (IOException e) {
			throw e;
		} finally {
			if (fileIS != null)
				fileIS.close();
		}
	}

	private void showContent(String dataStr) throws IOException {
		OutputStreamWriter output = null;
		BufferedWriter bufferedWriter = null;
		try {
			output = new OutputStreamWriter(socket.getOutputStream());
			bufferedWriter = new BufferedWriter(output);
			bufferedWriter.write(dataStr);
			bufferedWriter.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
		}
	}

	private void close(BufferedReader br, InputStreamReader isr, InputStream is) {
		try {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
