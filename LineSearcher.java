package cop2805;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class LineSearcher 
{
	
	private List<String> myList;
	/*	At construction the LineSearcher should open the file and read all of its contents into a class level List<String> object. 
		This object will be populated by the files.readAllLines() function call.*/
	public LineSearcher(File fileName)
	{
		try 
		{
			myList = Files.readAllLines(fileName.toPath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/*	The method for the LineSearcher class will take a single integer input and return a List<String>. 
		This list will contain 3 to 5 lines from the file. It will contain the two strings preceding the requested integer input,
		8 the line at the requested integer, and two lines after the requested integer.*/
	public List<String> LinesFound(int input)
	{
		//create a new list to fill and return
		List<String> newList = new ArrayList<String>();
		//calculate the indexes needed
		int sub2 = input - 2;
		int sub1 = input - 1;
		int add1 = input + 1;
		int add2 = input + 2;
		//Add the preceding lines if valid
		if(sub2 >= 0)
			newList.add("Line " + sub2 + ": " + myList.get(sub2));
		if(sub1 >= 0)
			newList.add("Line " + sub1 + ": " + myList.get(sub1));
		
		newList.add("Line " + input + ": " + myList.get(input));
		
		if(add1 < myList.size())
			newList.add("Line " + add1 + ": " + myList.get(add1));
		if(add2 < myList.size())
			newList.add("Line " + add2 + ": " + myList.get(add2));

		return newList;
	}

}
