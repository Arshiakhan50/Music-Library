// Name: Arshia Sharifi, Student ID: 501158323
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, ArrayList<Integer>> artistMap;
		private Map<String, ArrayList<Integer>> genreMap;
		private Map<String, Integer> titleMap;
		
		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			titleMap = new HashMap<String, Integer>();
			artistMap = new HashMap<String, ArrayList<Integer>>();
			genreMap = new HashMap<String, ArrayList<Integer>>();

			try{
			File audioContent = new File("store.txt");// file reader
            Scanner reader = new Scanner(audioContent);// passing the file reader to the scanner

			while (reader.hasNextLine()){
				String line = reader.nextLine();

				if (line.equals(Song.TYPENAME)){// there can be either a song or an audiobook
					//id, title, year, length, artist, composer, genre, number of lines of lyrics, lyrics
						//the order of these are determined by the structure of the store.txt
						String id = reader.nextLine();
						String title = reader.nextLine();
						int year  = reader.nextInt();
						int length = reader.nextInt();
						reader.nextLine();// without this it would read an empty line after an integer
						String artist = reader.nextLine();
						String composer = reader.nextLine();
						Song.Genre genre = Song.Genre.valueOf(reader.nextLine());
						int numLine = reader.nextInt();
						reader.nextLine();
						String lyrics = "";//this variable always gets reset every iteration
						for (int x = 0; x < numLine; x++){//used a for loop to add as many lines as needed by the numLine
							if (x + 1 >= numLine){//this line isn't necessary but it makes the lyrics nicer, without this, lyrics would look ugly
								lyrics += reader.nextLine();
								break;
							}
							lyrics += reader.nextLine() + "\n";//add to lyrics with new line
						}
						//here I add a new song object to the contents using the info retrieved
						contents.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, genre, lyrics));
					}
					if (line.equals(AudioBook.TYPENAME)){//similar to the song
						//id, title, year, length, author, narrator, # chapters, chaptertitles, # lines of a chapter, chapter lines, for each chapter
							String id = reader.nextLine();// like before, the order of the scanners are determined by the store.txt
							String title = reader.nextLine();
							int year  = reader.nextInt();
							int length = reader.nextInt();
							reader.nextLine();
							String author = reader.nextLine();	
							String narrator = reader.nextLine();
							int numChapters = reader.nextInt();
							reader.nextLine();
							ArrayList<String> chapterTitles = new ArrayList<String>();//arraylist of chapter names
							ArrayList<String> chapters = new ArrayList<String>();//arraylist of chapter contents
							String audioFile = "";
							for (int x = 0; x < numChapters; x++){
								chapterTitles.add(reader.nextLine());
							}
							for (int y = 0; y < numChapters; y++){
								int len = reader.nextInt();
								reader.nextLine();
								for (int i = 0; i < len; i++){
									audioFile += reader.nextLine() + "\n";
								}
								chapters.add(audioFile);// adding what has been made and resetting it for the next set of chapters
								audioFile = "";// audiofile must get reset or it will keep getting updated while having the previous chapters
							}
							// adding the audiobook object created
							contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, audioFile, length, author, narrator, chapterTitles, chapters));
						
						}
					}
					//title map
					// this map, i just get the title of each object and add to the keys of the hashmap using put method
					int count = 1;
					for (AudioContent x: contents){
						titleMap.put(x.getTitle(), count);
						count++;
					}

					// artist map
					//i check for type first, then I fill out the keys using the authors and the artist fields of the objects
					for (AudioContent x: contents){
						if (x.getType().equals(Song.TYPENAME)){
							Song song = (Song) x;
							artistMap.put(song.getArtist(), new ArrayList<Integer>());
						}
						else if(x.getType().equals(AudioBook.TYPENAME)){
							AudioBook book = (AudioBook) x;
							artistMap.put(book.getAuthor(), new ArrayList<Integer>());
						}
					}
					//after filling out the keys, I check for the number of instances a certain artist appears by looping throuhg and if a match is found then adding its index to 
					// the values of that particular key
					
					for (String name: artistMap.keySet()){
						int index = 1;
						for (AudioContent y: contents){
							if (y.getType().equals(Song.TYPENAME)){
								Song song = (Song) y;
								if (song.getArtist().equals(name)){
									artistMap.get(name).add(index);
								}
								index++;
							}
							else if (y.getType().equals(AudioBook.TYPENAME)){
								AudioBook book = (AudioBook) y;
								if (book.getAuthor().equals(name)){
									artistMap.get(name).add(index);
								}
								index++;
							}

						}
					}
					// genre map
					// same process as artistMap, fill out the keys with genres and skip audiobooks as those don't have a genre field
					for (AudioContent x: contents){
						if (x.getType().equals(Song.TYPENAME)){
							Song song = (Song) x;
							genreMap.put(song.getGenre().toString().toLowerCase(), new ArrayList<Integer>());
						}
						else if(x.getType().equals(AudioBook.TYPENAME)){
							continue;
						}
					}
					// count the number of times a certain key appears in the contents 
					for (String name: genreMap.keySet()){
						int index = 1;
						for (AudioContent y: contents){
							if (y.getType().equals(Song.TYPENAME)){
								Song song = (Song) y;
								if (song.getGenre().toString().equalsIgnoreCase(name)){
									genreMap.get(name).add(index);
								}
								index++;
							}
							else if (y.getType().equals(AudioBook.TYPENAME)){
								continue;
							}

						}
					}
			}
			catch (IOException e){
				System.out.println("Error file not found!");
			}
		}
			public void search(String title){
				if (titleMap.get(title) == null){// checks for if the title isn't in the keysets of the map
					throw new AudioContentNotFoundException("No matches for " + title);
				}
				System.out.print(titleMap.get(title) + ". ");//print the info of the index + 1 and the info the that particular object to the user
				contents.get(titleMap.get(title) - 1).printInfo();
				System.out.print("\n");
			}	
			public void searchA(String artist){//implementation of the artistMap. I first check if the inputted key exists in the map if not then throw an exception.
				if (!artistMap.containsKey(artist)) throw new AudioContentNotFoundException("No matches for " + artist);
				for (int x: artistMap.get(artist)){//loop through the integer arraylist of the that particular key and then get x-1 indexes and print their info
					System.out.print(x + ". ");
					contents.get(x-1).printInfo();
					System.out.print("\n");
				}
			}
			public void searchG(String genre){//exact same methodoly as searchA, check if the key exists then gets is integers and print the info of x-1 indexes in contents 
				if (!genreMap.containsKey(genre)) throw new AudioContentNotFoundException(genre + " not found!");
				for (int x: genreMap.get(genre)){
					System.out.print(x + ". ");
					contents.get(x-1).printInfo();
					System.out.print("\n");
				}
			}
			public void searchP(String anything){//count is for the index in the contents
				ArrayList<Integer> indexes = new ArrayList<Integer>();
				//id, title, year, length, artist, composer, genre, number of lines of lyrics, lyrics
				int count = 1;
				// should i add genre, 
				for (AudioContent x: contents){// the idea is that if a match found, then the other fields don't need to be considered and we can move to the next iteration
					boolean found = false;// i use this later on for looping through the chapters and the chapterTitles arraylists
					if (x.getType().equals(Song.TYPENAME)){
						Song song = (Song) x;// check if the object is a song, if so, then check each field and if a match is found add its index to indexes, increment count and continue
						//to next iteration
						if (song.getArtist().contains(anything) || song.getComposer().contains(anything) || song.getLyrics().contains(anything) || song.getGenre().toString().contains(anything) || song.getTitle().contains(anything)){
							indexes.add(count);
							count++;
							continue;
						}
						count++;
					}
					else if(x.getType().equals(AudioBook.TYPENAME)){
						AudioBook book = (AudioBook) x;// everything is the same as song type for the most part
						if (book.getTitle().contains(anything)){
							indexes.add(count);
							count++;
							continue;
						}
						else if(book.getAuthor().contains(anything)){
							indexes.add(count);
							count++;
							continue;
						}
						else if(book.getNarrator().contains(anything)){
							indexes.add(count);
							count++;
							continue;	
						}
						else if (book.getAudioFile().contains(anything)){
							indexes.add(count);
							count++;
							continue;
						}
						for (String names: book.getChapterTitles()){
							if (names.contains(anything)){//after this loop, if found is true then we can move to the next iteration otherwise a match hasn't been found and must continue looping
								indexes.add(count);
								count++;
								found = true;
								break;
							}
						}if (found) continue;// if found is true then no need to check the other ones
						for (String chapters: book.getChapters()){
							if (chapters.contains(anything)){//same as chapterTitles
								indexes.add(count);
								found = true;
								count++;
								break;
							}
							
						} if (found) continue;
						count++;

						}
						
					}
					for (int x: indexes){//after the indexes has been filled out, I can loop through the arraylist and print all of the x-1 indexes
						System.out.print(x + ". ");
						contents.get(x-1).printInfo();
						System.out.print("\n");
					}
				}
			
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		// the methods below are getters for the maps and the arraylists
		public Map<String, ArrayList<Integer>> getArtistMap(){
			return artistMap;
		}

		public Map<String, ArrayList<Integer>> getGenreMap(){
			return genreMap;
		}
		
		public ArrayList<AudioContent> getContents(){
			return contents;
		}
		
}
