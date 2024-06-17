package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.List;

public class Final_Project_Server 
{
	public static void main(String[] args) 
	{
		File hamlet = new File("hamlet.txt");
		File macbeth = new File("macbeth.txt");
		File merchantOfVenice = new File("merchantofvenice.txt");
		LineSearcher myLineObject;
		//After that it will open a ServerSocket and bind it to an available port
		boolean shutDown = false;
		ServerSocket server = null;
		try 
		{
			server = new ServerSocket(1236);
			System.out.println("Port has been Bound. Accepting Connection");
			while(!shutDown)
			{
				try 
				{
					myLineObject = new LineSearcher(hamlet);
					//1.Accept client socket connection
					Socket client = server.accept(); //code will block
					//2. Set up input/output streams from client connection
					OutputStream output = client.getOutputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
					//Setting the file selected
					String radio = reader.readLine();
					if(radio.compareTo("Hamlet") == 0)
						myLineObject = new LineSearcher(hamlet);
					if(radio.compareTo("Macbeth") == 0)
						myLineObject = new LineSearcher(macbeth);
					if(radio.compareTo("Merchant of Venice") == 0)
						myLineObject = new LineSearcher(merchantOfVenice);
					//3. Read in String from client
					String clientInput = reader.readLine();
					//4. Convert this string to an integer using Integer.parseInt()
					int clInput = Integer.parseInt(clientInput);
					//5. Pass int to LineSearcher object, receive List<String> output
					List<String> outputList = myLineObject.LinesFound(clInput);
					//6. Transmit List<String> to client
					for(String line : outputList)
					{
						//output.write(line.length());
						String response = line + "\n";
						output.write(response.getBytes());
					}
					//7. Close Connection
					client.close();
					//8. Return to top of while loop
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					System.out.println("ERROR: SHUTDOWN ACTIVATED");
					e.printStackTrace();
					continue; //continue to look for open server connection
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
