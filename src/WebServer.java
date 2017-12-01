import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {

	public static void main(String[] args) throws Exception{
		try (ServerSocket serverSocket = new ServerSocket(8080)) {
			while(true){
				Socket socket = serverSocket.accept();
				//System.out.println("connected");
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String a = br.readLine();
				while(br.ready()){
					br.readLine();
				}
				String[] b = a.split(" ");
				a = b[1].substring(1, b[1].length());
				File file = new File("www\\" + a);
				//System.out.println(a);
				if(file.exists()){
					generateHTML(socket, file, file.exists());
				} else {
					generateHTML(socket, new File("www\\NotFound.html"), file.exists());
				}
			}
		}
	}

	public static void generateHTML(Socket socket, File file, boolean found) throws Exception{
		PrintStream ps = new PrintStream(socket.getOutputStream(), true, "UTF-8");
		if(found){
			ps.println("HTTP/1.1 200 OK");
			ps.println("Content-type: text/html");
			ps.println("Content-length: 124\n");
		} else {
			ps.println("HTTP/1.1 404 Not Found");
			ps.println("Content-type: text/html");
			ps.println("Content-length: 126\n");
		}
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(fis));
		while(br2.ready()){
			ps.println(br2.readLine());
		}
		br2.close();
	}
}
