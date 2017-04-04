

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;


public class MelodyMain 
{
	public static void main(String[] args) throws FileNotFoundException 
	{
		intro();
		
		Melody melody = null;
		
		Scanner console = new Scanner(System.in);
		String command = "nothing";
		while(!command.equalsIgnoreCase("quit")) 
		{
			System.out.print("What would you like to do? ");
			command = console.next();
			
			if(!(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("load")) 
					&& melody == null) 
			{
				System.out.println("You must load a song before trying to manipulate it");    
			} 
			else if(command.equalsIgnoreCase("load")) 
			{
				System.out.print("File name? ");
				File file = checkFile(console.next(), console);
				Scanner input = new Scanner(file);
				Queue<Note> song = read(input);
				melody = new Melody(song);
				input.close();
			} 
			else if (command.equalsIgnoreCase("play")) 
			{
				melody.play();
			}
			else if (command.equalsIgnoreCase("reverse"))
			{
				melody.reverse();
			}
			else if (command.equalsIgnoreCase("save")) 
			{
				System.out.print("Output file? ");
				PrintStream output = new PrintStream(new File(console.next()));
				output.print(melody);
			} 
			else if (command.equalsIgnoreCase("print")) 
			{
				System.out.print(melody);
			} 
			else if (command.equalsIgnoreCase("duration")) 
			{
				System.out.println(melody.getTotalDuration() + " seconds long");
			} 
			else if (command.equalsIgnoreCase("tempo"))
			{
				System.out.print("Percentage? ");
				double tempo = console.nextDouble();
				melody.changeTempo(tempo);
			}
			else if (command.equalsIgnoreCase("append")) 
			{
				System.out.print("File name of second song? ");
				File file = checkFile(console.next(), console);
				Melody other = new Melody(read(new Scanner(file)));
				melody.append(other);
			} 
			else if (!command.equalsIgnoreCase("quit")) 
			{
				System.out.println("Invalid command. Please try again.");
				intro();
			}		
		}
	}
	
	// prints out an introduction describing how to use the program
	private static void intro() 
	{
		System.out.println("Welcome to MelodyMain. Type the word in the left column to do the action on the right");
		System.out.println("load     : load a new input file");
		System.out.println("save     : output to a file");
		System.out.println("print    : prints the contents of the last loaded song");
		System.out.println("play     : play the last loaded song");
		System.out.println("reverse  : reverse the last loaded song");
		System.out.println("duration : print out the length of the last loaded song in seconds");
		System.out.println("tempo    : change the speed by a percentage");
		System.out.println("append   : appends notes from a second melody to the loaded one");
		System.out.println("quit     : exit the program");
	}
	
	// checks to make sure the file exists. Prompts the user for a new file until they 
	// input a valid one. Returns a file that exists.
	public static File checkFile(String name, Scanner console) 
	{
		File file = new File(name);
		while (!file.exists()) 
		{
			System.out.print("Invalid file. File name? ");
			file = new File(console.next());
		}
		return file;
	}
   
	// returns a Queue filled with the notes specified in the passed in 
	// Scanner. The notes will appear in the same order in the Queue
	// as they did in the file.
	public static Queue<Note> read (Scanner input) 
	{
		Queue<Note> song = new LinkedList<Note>();
      
		while(input.hasNext())
		{
			double duration = input.nextDouble();
			Pitch pitch = Pitch.valueOf(input.next());
			if(pitch.equals(Pitch.R)) 
			{
				song.add(new Note(duration, pitch, input.nextBoolean()));          
			} 
			else 
			{
				song.add(new Note(duration, pitch, input.nextInt(), 
					Accidental.valueOf(input.next()), input.nextBoolean()));
			}          
		}
		return song;
	}
}
