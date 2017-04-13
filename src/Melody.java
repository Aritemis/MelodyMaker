/**
 *	@author Ariana Fairbanks
 */

import java.util.*;
import java.io.*;


public class Melody 
{
	private Queue<Note> song;
	
	public Melody(Queue<Note> song) 
	{
		this.song = song;
	}
	
	public void changeTempo(double ratio) 
	{
		int length = song.size();
		while(length > 0)
		{
			Note current = song.remove();
			current.setDuration(ratio * current.getDuration());
			song.add(current);
			length--;
		}
	}
	
	public double getTotalDuration() 
	{
		double duration = 0.0;
		int length = song.size();
		boolean repeat = false;
		while(length > 0)
		{
			Note current = song.remove();
			if(current.isRepeat())
			{
				if(!repeat)
				{
					repeat = true;
				}
				else
				{
					repeat = false;
				}
				
				duration += 2 * current.getDuration();
			}
			else if(repeat)
			{
				duration += 2 * current.getDuration();
			}
			else
			{
				duration += current.getDuration();
			}
			length--;
			song.add(current);
		}
		return duration;
	}
	
	public void play() 
	{
		Queue<Note> temp = new LinkedList<Note>();
		int length = song.size();
		boolean repeat = false;
		boolean repeatedSection = false;
		while(length > 0)
		{
			Note current = song.remove();
			if(repeatedSection)
			{
				while(!temp.isEmpty())
				{
					temp.remove().play();
				}
				repeatedSection = false;
			}
			if(current.isRepeat())
			{
				if(!repeat)
				{
					repeat = true;
				}
				else
				{
					repeat = false;
					repeatedSection = true;
				}
				current.play();
				temp.add(current);
				length--;
			}
			else if(repeat)
			{
				current.play();
				temp.add(current);
				length--;
			}
			else
			{
				current.play();
				length--;
			}
			song.add(current);
		}
	}
	
	public void reverse() 
	{
		Stack<Note> temp = new Stack<Note>();
		while(!song.isEmpty())
		{
			temp.push(song.remove());
		}
		while(!temp.isEmpty())
		{
			song.add(temp.pop());
		}
	}
	
	public String toString() 
	{
		String result = "";
		int length = song.size();
		while(length > 0)
		{
			Note current = song.remove();
			result = result + current.toString() + "\n";
			length--;
			song.add(current);
		}
		return result;
	}

	public void append(Melody other) 
	{
		while(!other.song.isEmpty())
		{
			song.add(other.song.remove());
		}
	}
}

