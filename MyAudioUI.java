// Name: Arshia Sharifi, Student ID: 501158323
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) //bascially in each of these cases, we get the necessary input from the user using scanner and the prompt then we call the appropriate method
			{//from the library or the AudioContentStore files. If a certain exception is cought then we catch it and print out the appropriate message.
				try{// so for all of the try catches, if an error is found, I catch each individual without breaking the loop, then print out their message and then move on
					int fromIndex = 0;
					System.out.print("From Store Content #: ");
					fromIndex = scanner.nextInt();
					System.out.print("To Store Content #: ");
					int toIndex = scanner.nextInt();
					mylibrary.download(fromIndex, toIndex);	
				}
				catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());	
				}
				catch(InputMismatchException e){
					System.out.println("Incorrect input!");
				}

									
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Print error message if the song doesn't exist in the library
				try{
					int index = 0;
					System.out.print("Song Number: ");
					index = scanner.nextInt();
					mylibrary.playSong(index);
				}
				catch(SongNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
			try{
				int index = 0;
				System.out.print("Audio book Number: ");
				index = scanner.nextInt();
				mylibrary.printAudioBookTOC(index);
			}
			catch(AudiobookNotFoundException e){
				System.out.println(e.getMessage());
			}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				try{
					int index = 0;
					System.out.print("Audio book number: ");
					int chapter = 0;
					index = scanner.nextInt();
					System.out.print("Chapter: ");
					chapter = scanner.nextInt();
					mylibrary.playAudioBook(index, chapter);		
				}
				catch(AudiobookNotFoundException e){
					System.out.println(e.getMessage());
				}
				catch(ChapterNotFoundException e){
					System.out.println(e.getMessage());
				}

			}

			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				try{
					System.out.print("Playlist title: ");
					String name = scanner.nextLine();
					mylibrary.playPlaylist(name);
				}
				catch(PlaylistNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				try{
					System.out.print("Playlist Title: ");
					String name = scanner.nextLine();
					System.out.print("Content Number : ");
					int idx = scanner.nextInt();
	
					mylibrary.playPlaylist(name, idx);
				}
				catch(PlaylistNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				try{
					System.out.print("Library Song #: ");
					int idx = scanner.nextInt();
					mylibrary.deleteSong(idx);
				}
				// TODO update these prompts
				catch(SongNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				try{
					System.out.print("Playlist Title: ");
					String title = scanner.nextLine();
					mylibrary.makePlaylist(title);
				}
				catch(PlaylistNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				try{
					System.out.print("Playlist Title: ");
					String title = scanner.nextLine();
					mylibrary.printPlaylist(title);
				}
				catch(PlaylistNotFoundException e){
					System.out.println(e.getMessage());
				}

				//todo check here
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				try{
					System.out.print("Playlist Title: ");
					String title = scanner.nextLine();
					System.out.print("Content Type [SONG, AUDIOBOOK]: ");
					String type = scanner.nextLine();
					System.out.print("Library Content #: ");
					int idx = scanner.nextInt();
					mylibrary.addContentToPlaylist(type, idx, title);
				}
				catch(PlaylistNotFoundException e){
					System.out.println(e.getMessage());
				}

				
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				try{
					System.out.print("Playlist Title: ");
					String title = scanner.nextLine();
					System.out.print("Playlist Content #: ");
					int idx = scanner.nextInt();
					mylibrary.delContentFromPlaylist(idx, title);
				}
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
				catch(PlaylistNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			else if (action.equalsIgnoreCase("SEARCH")){
				System.out.print("Title: ");
				try{
					String title = scanner.nextLine();
					store.search(title);
				}
				catch (InputMismatchException e){
					System.out.println("Incorrect input!");
				}
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			else if (action.equalsIgnoreCase("SEARCHA")){
				System.out.print("Author: ");
				try{
					String author = scanner.nextLine();
					store.searchA(author);
				}
				catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			else if (action.equalsIgnoreCase("SEARCHG")){
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				try{
					String genre = scanner.nextLine();
					store.searchG(genre.toLowerCase());
				}
				catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			else if (action.equalsIgnoreCase("SEARCHP")){
				System.out.print("Enter any string: ");
				try{
					String pds = scanner.nextLine();
					store.searchP(pds);
				}
				catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("DOWNLOADA")){
				try{
					System.out.print("Artist Name: ");
					String artist = scanner.nextLine();
					mylibrary.downloadA(artist);
				}
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

			}

			else if (action.equalsIgnoreCase("DOWNLOADG")){
				try{
					System.out.print("Genre: ");
					String genre = scanner.nextLine();
					mylibrary.downloadG(genre.toLowerCase());
				}
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}


			System.out.print("\n>");
		}
	}
}

