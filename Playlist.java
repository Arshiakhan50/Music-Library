// Name: Arshia Sharifi, Student ID: 501158323
import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		for (int x = 0; x < contents.size();x++){
			System.out.print((x+1) + ". ");//based on the video,  going through the contents arraylist and printing everything
			contents.get(x).printInfo();//printinfo is from audiocontent
			System.out.println("\n");
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		for (AudioContent x: contents){
			x.play();//play is from audiocontent 
			System.out.println("\n");//printing everything using a loop
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		if (index -1 < contents.size()){
			
		System.out.println(contents.get(index-1));//plays a specific thing in the contents
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();//checking if the index is within range
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{//Object other has be to casted to playlist to be able to use it
		Playlist otherE = (Playlist) other;//equals method that comparres based on song titles
		return this.title.equals(otherE.title);//this means referring to this object
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		if (!contains(index-1)) return;//if index isn't in range then doesn't do anything
		contents.remove(index-1);//else removes
	}
	
	
}
