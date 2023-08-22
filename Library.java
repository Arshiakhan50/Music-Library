// Name: Arshia Sharifi, Student ID: 501158323
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Map;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); 
		playlists   = new ArrayList<Playlist>();
	}

	public void download(int fromIndex, int toIndex)
	{//i first get the contents from the audioContentStore and get the contents 
		AudioContentStore store = new AudioContentStore();
		ArrayList<AudioContent> contents = store.getContents();
		//i loop through the contents, i check for the type and make the appropriate casting, then I check if it already exists
		if (fromIndex >= 0 && toIndex <= contents.size() && toIndex >= 0){
			for (int x = fromIndex; x <= toIndex; x++){
				int index = x-1;
				if (contents.get(index).getType().equals(Song.TYPENAME)){
					Song song = (Song) contents.get(index);//the is the song x-1 refers to from the contents
					if (!songs.contains(song)){//if the songs doesn't exist 
						songs.add(song);
						System.out.println("SONG " + song.getTitle() + " Added to Library");
					}
					else{// if it already exists in the songs arraylist
						System.out.println("SONG " + song.getTitle() + " already downloaded");
					}
					
				}
				else if(contents.get(index).getType().equals(AudioBook.TYPENAME)){
					AudioBook book = (AudioBook) contents.get(index);// same thing as song, check for the type, cast to appropriate class, check if it is alrady in the audiobooks, if not add it
					if (!audiobooks.contains(book)){
						audiobooks.add(book);
						System.out.println("AUDIOBOOK " + book.getTitle() + " Added to Library");
					}
					else{
						System.out.println("AUDIOBOOK " + book.getTitle() + " already downloaded");
					}

				}
			}
		}
		else{// error if the indexes are invalid
			String errorMsg = "Invalid Index!";
			throw new AudioContentNotFoundException(errorMsg);
			
		}
		
	}
	public void downloadA(String artist){// this uses the artistMap
		AudioContentStore store = new AudioContentStore();
		ArrayList<AudioContent> contents = store.getContents();
		Map<String, ArrayList<Integer>> artistMap = store.getArtistMap();

		if (!artistMap.containsKey(artist)){// i first check if the artist input is a key in the map if not then input is invalid
			String errorMsg = artist + " doesn't exist!";
			throw new AudioContentNotFoundException(errorMsg);
			
		}
		for (int x: artistMap.get(artist)){// i check the indexes of the valueset of that particular artist, and then use that to get the content from the contents using x-1 index
			if (contents.get(x-1).getType().equals(Song.TYPENAME)){
				Song song = (Song) contents.get(x-1);
				if (!songs.contains(song)){// if sone isn't already in there then add it 
					songs.add(song);
					System.out.println("SONG " + song.getTitle() + " Added to Library");
				}
				else{
					System.out.println("SONG " + song.getTitle() + " already downloaded");
				}

			}
			if (contents.get(x-1).getType().equals(AudioBook.TYPENAME)){//same thing as adding song,
				AudioBook book = (AudioBook) contents.get(x-1);// making the necessary casting, then check if the audiobook already exists in the audiobooks
				if (!audiobooks.contains(book)){
					audiobooks.add(book);
					System.out.println("AUDIOBOOK " + book.getTitle() + " Added to Library");
				}
				else{
					System.out.println("AUDIOBOOK " + book.getTitle() + " already downloaded");
				}
			}
		}
	}

	public void downloadG(String genre){// similar to downloadG
		AudioContentStore store = new AudioContentStore();
		ArrayList<AudioContent> contents = store.getContents();
		Map<String, ArrayList<Integer>> genreMap = store.getGenreMap();//getting genreMap instead

		if (!genreMap.containsKey(genre)){//if genre isn't in any of the keys genre inputted is invalid
			String errorMsg = genre + " doesn't exist!";
			throw new AudioContentNotFoundException(errorMsg);
		}
		for (int x: genreMap.get(genre)){//loop through the integer arraylist and print all of the appropriate indexes from the contents
			if (contents.get(x-1).getType().equals(Song.TYPENAME)){
				Song song = (Song) contents.get(x-1);
				if (!songs.contains(song)){
					songs.add(song);
					System.out.println("SONG " + song.getTitle() + " Added to Library");
				}
				else{
					System.out.println("SONG " + song.getTitle() + " already downloaded");
				}

			}
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;//loops through all of the objects of songs and prints their info
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;//loops through all of the objects of audiobooks and prints their info
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();	
			System.out.println();
		}
	}
	
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size();i++){
			int index = i + 1;
			System.out.println("" + index + ". " + playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> empty = new ArrayList<String>();
		for (Song x: songs){//adding to an empty arraylist if the artist isn't already there
			if (empty.indexOf(x.getArtist()) == -1){
				empty.add(x.getArtist());
			}
		}
		for (int x = 0; x < empty.size();x++){//printing all of the string names of the emtpy arraylist
			System.out.print(x+1 + ". " + empty.get(x) + "\n");

		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{//first of all the index must be in range 
		boolean found = false;
		if (index-1 >= 0 && index-1 < songs.size()){
			Song saved = songs.get(index-1);//the song that i will be deleting, using it as a reference for deleting later
			songs.remove(index-1);
			for (int x = 0; x < playlists.size();x++){// by this point the song has been deleted from the sogns
				for (int y = 0; y < playlists.get(x).getContent().size();y++){//i do a nested for loop here to check inside every single playlist for the presence of that song
					if (playlists.get(x).getContent().get(y).getType().equals(Song.TYPENAME)){
						if (playlists.get(x).getContent().get(y).equals(saved)){//the equals method uses compare to interface of songs and compares based on song titles of current and the one we have to delete
							playlists.get(x).getContent().remove(y);//remove uses deletecontent of songs 
						}
					}
				}
				}found = true;
			}
		if (!found){
			String errorMsg = "Index out of bounds";
			throw new SongNotFoundException(errorMsg);
		}
		// if the first part doesn't pass then the index is out of range of the songs
		
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		Collections.sort(songs, new SongYearComparator());//similar to the other comparators and comparables, it implements the comparator interface this one uses song yearcomparator
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			if (a.getYear() > b.getYear()) return 1;
			if (a.getYear() < b.getYear()) return -1;
			return 0; //can i do year1 minus year 2?
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
		Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			if (a.getLength() > b.getLength()) return 1;
			if (a.getLength() < b.getLength()) return -1;
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	   Collections.sort(songs);//comparable is in songs
		// class Song should implement the Comparable interface
		// see class Song code
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			String errorMsg = "Song Not Found";
			// throw new SongNotFoundException(errorMsg);
			throw new SongNotFoundException(errorMsg);
		}
		songs.get(index-1).play();
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size())
		{
			String errorMsg = "Audiobook Not Found";
			throw new AudiobookNotFoundException(errorMsg);
		}
		if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters()){// checking for the validity of the chapter and the index ranges, either of these conditions would be false
			String errorMsg = "Chapter Not Found";
			throw new ChapterNotFoundException(errorMsg);
			
		}
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();// if none of the cases fail then this we play the audiobook
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size())//the fail case
		{
			String errorMsg = "Audiobook Not Found";
			throw new AudiobookNotFoundException(errorMsg);
		}audiobooks.get(index-1).printTOC();//prints the toc
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		boolean found = false;
		boolean hasExistingTitle = playlists.stream()
			.anyMatch(playlist -> playlist.getTitle().equals(title));//stream that sets up a lambda (function) that filters for any matching titles
		if(!hasExistingTitle){//if hasexistingtitle is false then a match wasn't found
			playlists.add(new Playlist(title));//if no match then it adds a new playlist
			found = true;
		}
		if (!found){
			String errorMsg = "Playlist " + title +  " Already Exists";
			throw new PlaylistNotFoundException(errorMsg);
		}

	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		boolean found = false;
		for (Playlist x: playlists){
			if (x.getTitle().equals(title)){
				x.printContents();//prints the content of the playlist if there is a mathing title
				found = true;
			}
		}
		if (!found){
			String errorMsg = "Playlist Not Found";
			throw new PlaylistNotFoundException(errorMsg);
		}
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		boolean found = false;
		for (Playlist x: playlists){//this method plays all of the content
			if (x.getTitle().equals(playlistTitle)){
				x.playAll();//plays the info and the contetn of a particular object in playlists
				found = true;
			}
		}
		if (!found){
			throw new PlaylistNotFoundException("Playlist Not found!");
		}//is this correct? am i printing everything or just the one with the given title
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		boolean found = false;
		for (Playlist x: playlists){
			if (x.getTitle().equals(playlistTitle)){//overloading since this method takes in different parameters
				if (indexInPL-1 >= 0 && indexInPL - 1 < x.getContent().size()){
					x.getContent().get(indexInPL-1).play();//plays a specific item using the index
					found = true;
				}
			}
		}
		if (!found){
			String errorMsg = "Playlist not found";
			throw new PlaylistNotFoundException(errorMsg);
		}
		
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		boolean found = false;
		if (type.equalsIgnoreCase(AudioBook.TYPENAME)){//checks for the type 
			for (int x = 0; x < playlists.size(); x++){
				if (playlistTitle.equalsIgnoreCase(playlists.get(x).getTitle()) && index-1 >= 0 && index-1 < audiobooks.size()){//if the titles are matching and index is correct
					playlists.get(x).addContent(audiobooks.get(index-1));//adds a content to the contents of the playlist object 
					found = true;
				}
			}
		}else if (type.equalsIgnoreCase(Song.TYPENAME)){//same as before
			for (int x = 0; x < playlists.size(); x++){
				if (playlistTitle.equalsIgnoreCase(playlists.get(x).getTitle()) && index-1 >= 0 && index-1 < songs.size()){
					playlists.get(x).addContent(songs.get(index-1));
					found = true;
				}
			}
		}
		if (!found){
			String errorMsg = "Playlist not found";
			throw new PlaylistNotFoundException(errorMsg);
		}
		//the type is either incorrect or the index or title of the playlist aren't matching
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{//optional class type is basically protecting against the case that the value may be null 
		Optional<Playlist> plOpt = playlists.stream() //streams deals with a collections of objects 
			.filter(playlist -> playlist.getTitle().equals(title)) //here, the playlist is filtered based on whichever object has a matching title to the one given as a parameter
			.findFirst();
		if(!plOpt.isPresent()){//this is a method accompanied with optional class type, this checks for if the playlist is null
			String errorMsg = "Playlist not found";
			throw new PlaylistNotFoundException(errorMsg);
		}
		Playlist pl = plOpt.get();//if the playlist isn't null, then we get the playlist that the optional object holds
		if(!pl.contains(index)){
			// TODO update this error message
			String errorMsg = "Content not found";
			throw new AudioContentNotFoundException(errorMsg);
		}

		pl.getContent().remove(index-1);//deletes the requested object from contents if object isn't null and the index is within range
	}

}
// all of these are custome exceptions
class AudioContentNotFoundException extends RuntimeException{//just like the rest, make the appropriate class and get it to inherit RunTimeException
	public AudioContentNotFoundException(String errorMsg){
		super(errorMsg);// in the constructor, pass the errorMsg to the parent class and let it get defined
		// the rest are the exact same process
	}
}
class SongNotFoundException extends RuntimeException{
	public SongNotFoundException(String errorMsg){
		super(errorMsg);
	}
}
class AudiobookNotFoundException extends RuntimeException{
	public AudiobookNotFoundException(String errorMsg){
		super(errorMsg);
	}
}
class PlaylistNotFoundException extends RuntimeException{
	public PlaylistNotFoundException(String errorMsg){
		super(errorMsg);
	}
}
class ChapterNotFoundException extends RuntimeException{
	public ChapterNotFoundException(String errorMsg){
		super(errorMsg);
	}
}


