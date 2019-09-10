package Controller;

import java.awt.Cursor;
import twitter4j.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.io.PrintStream;
import java.io.*;
import twitter4j.auth.AccessToken;
import twitter4j.Relationship;

public class TwitterGUIController {
    private Twitter twitter;
    private List<Status> statuses;
    private List<Place> places;
    private List<String> tokens;
    private List<String> tempTokens;
    private Map<String, Integer> frequentWords;
    private String popularWord;
    private int frequencyMax;
    private List media;
    private List<HashtagEntity> hashtags;
    private List<String> hashtagText;
    private Map<String, Integer> frequentHashtags;
    private User userA;
    private User userB;

    public TwitterGUIController()
    {
        // Connects to Twitter and performs authorizations.
        twitter = TwitterFactory.getSingleton();
        statuses = new ArrayList<Status>();
        tokens = new ArrayList<String>();
        tempTokens = new ArrayList<String>();
        frequentWords = new HashMap<>();
    }

    public String postTweet(String message)
    {
        String statusTextToReturn = "";
        try
        {
            Status status = twitter.updateStatus(message);
            statusTextToReturn = status.getText();
        }
        catch (TwitterException e){
            System.out.println(e.getErrorMessage());
        }
        return statusTextToReturn;
    }

    public void findUserStats(String handle) throws TwitterException, IOException
    {
        /* 
         * TODO 9: you put it all together here. Call the functions you
         * finished in TODO's 2-8. They have to be in the correct order for the
         * program to work. 
         * Remember to user.clear() so that consecutive requests dont count 
         * words from previous requests. 
         */
        frequencyMax = 0;
        tokens.clear();
        fetchTweets(handle);
        splitIntoWords();
        for (int i = 0; i < tokens.size(); i++) {
            tempTokens.add(removePunctuation(tokens.get(i)));
        }
        tokens = tempTokens;
        removeCommonEnglishWords();
        countAndRemoveEmpties();
        findMostPopularWord();
    }

    // Example query with paging and file output.
    private void fetchTweets(String handle) throws TwitterException, IOException {

        //Create a file output and name the text to be written to.
        PrintStream printFileOut = new PrintStream(new FileOutputStream("tweetsFound.txt"));

        //Create a twitter paging object that will start at page 1 and hole 200 entries per page.
        Paging page = new Paging(1, 200);

        //Use a for loop to set the pages and get the necessary tweets.
        for (int i = 1; i <= 10; i++)
        {
            page.setPage(i);

            /* Ask for the tweets from twitter and add them all to the statuses ArrayList.
            Because we set the page to receive 200 tweets per page, this should return
            200 tweets every request. */
            statuses.addAll(twitter.getUserTimeline(handle, page));
        }

        //Write to the file a header message. Useful for debugging.
        int numberOfTweetsFound = statuses.size();
        printFileOut.println("Number of Tweets Found: " + numberOfTweetsFound);

        //Use the forEach function to print all the tweets found.
        int count = 1;
        for (Status tweet : statuses)
        {
            printFileOut.println(count+". "+tweet.getText());
            count++;
        }
    }

                        /********** PART 2 *********/

    /*
     * TODO 2: this method splits a whole status into different words. Each word
     * is considered a token. Store each token in the "tokens" arrayList
     * provided.
     */
    private void splitIntoWords()
    {
        for (int i = 0; i < statuses.size(); i++) {
            String SplitBySpaces = statuses.get(i).getText();
            List<String> indivudualWords = 
            new ArrayList<String>(Arrays.asList(SplitBySpaces.split("\\s+")));
            for (int e = 0; e < indivudualWords.size()-1; e++) {
                tokens.add(indivudualWords.get(e));
            }
        }
    }


    /* 
     * TODO 3: return a word after removing any punctuation from it.
     * If the word is "Edwin!!", this method should return "Edwin".
     * We'll need this method later on.
     */
    @SuppressWarnings("unchecked")
    private String removePunctuation(String word)
    {
//      I actually removed spaces and changed all letters to lowecase here
//      instead of in those other TODOs
        String editedWord = word;
        editedWord = editedWord.replaceAll("[^a-zA-Z ]", "");
        editedWord = editedWord.replaceAll("\\s+","");
        editedWord = editedWord.toLowerCase();
        return editedWord;
    }

    /*
     * TODO 4: remove all the words that are found in the commonWords.txt file.
     * You can use a new list or update the "tokens" List. Make sure to
     * remove punctuation before checking if its a common english word.
     */
    @SuppressWarnings("unchecked")
    private void removeCommonEnglishWords()
    {
        File wordsFile = new File("commonWords.txt"); 
  
//      BufferedReader br = new BufferedReader(new FileReader(commonWordsFile));
//        
        try
        {
            Scanner br = new Scanner(new FileReader(wordsFile));
            List <String> commonWordsList = new ArrayList<String>();
            while (br.hasNext()){
    	        commonWordsList.add(br.next());
    	    }

            for (int i = 0; i < tokens.size(); i++) {
                if (commonWordsList.contains(tokens.get(i))) {
                    tokens.remove(i);
                    i--;
                }
            }
            System.out.println(commonWordsList);
        }
        catch(Exception err)
        {
            err.printStackTrace();
            if (!wordsFile.exists()) {
                System.err.println("File doesn't exist");
            }
            if (wordsFile.isDirectory()) {
                System.err.println("File is directory");
            }
            if (!wordsFile.canRead()) {
                System.err.println("File cannot be read");
            }
        }  
    }

    /* 
     * TODO 5: count each word using. Use the frequentWords Hashmap.
     * make sure to remove any empty strings like "" or " ". 
     */
    @SuppressWarnings("unchecked")
    private void countAndRemoveEmpties()
    {
//  my remove punctuation function already removes spaces i think
        for (int i = 0; i < tokens.size(); i++) {
            if (!tokens.get(i).isEmpty()) {
                if (!frequentWords.containsKey(tokens.get(i))) {
                    frequentWords.put(tokens.get(i),1);
                }
                else {
                    frequentWords.put (
                    tokens.get(i),
                    frequentWords.get(tokens.get(i)) + 1);
                }
            }
        }
    }

    /*
     * TODO 6: find the most popular word and store the string and frequency.
     * It is up to you to check for case sensitivity.
     */
    @SuppressWarnings("unchecked")
    private void findMostPopularWord()
    {
        for (Map.Entry<String, Integer> entry : frequentWords.entrySet()) {
            if (entry.getValue() > frequencyMax) {
                frequencyMax = entry.getValue();
                popularWord = entry.getKey();
            }
        }
    }

    //TODO 7: return the most frequent word's string
    @SuppressWarnings("unchecked")
    public String getMostPopularWord()
    {
        return popularWord.replace(":", "");
    }

    //TODO 8: return the most frequent word's count.
    @SuppressWarnings("unchecked")
    public int getFrequencyMax()
    {
        return frequencyMax;
    }


    /*********** PART 3 **********/

    //TODO 10: Create your own method that provides any service you want.
    public void getHashtagsFrom(String handle)
    throws TwitterException, IOException
    {
        frequencyMax = 0;
        tokens.clear();
        fetchTweets(handle);
        splitIntoWords();
        removeNonHashtags();
        countAndRemoveEmpties();
        findMostPopularWord();
    }
    
    private void removeNonHashtags() //remove all non hashtag tokens
    {
        for (int i = 0; i < tokens.size(); i++) 
        {
            if (!tokens.get(i).contains("#")) 
            {
                tokens.remove(i);
                i--;
            }
        }
    }
    /*
     * TODO 11: HL -> You have to use atleast TWO more twitter methods, 
     * other than Query, in your investigation. If you want full points, 
     * record in your README why this method is sufficiently complex.
     */
    public void getMentionsFrom(String handle)
    throws TwitterException, IOException
    {
        frequencyMax = 0;
        tokens.clear();
        fetchTweets(handle);
        splitIntoWords();
        removeNonMentions();
        countAndRemoveEmpties();
        findMostPopularWord();
    }
    private void removeNonMentions() //remove all non mention tokens
    {
        for (int i = 0; i < tokens.size(); i++) 
        {
            if (!tokens.get(i).contains("@")) 
            {
                tokens.remove(i);
                i--;
            }
        }
    }
    
    public void getMutualFollow(String handle, String handle2)
    throws TwitterException, IOException
    {
        fetchUsers(handle, handle2);
    }
    
    private void fetchUsers(String tagA, String tagB) 
    throws TwitterException, IOException 
    {
        userA = twitter.getUserTimeline(tagA).get(0).getUser();
        userB = twitter.getUserTimeline(tagB).get(0).getUser();
    }
    
    public List<String> mutualConnections()
    {
        
        try 
        {
            //get followers and followed
            List<Long> friendsList = new ArrayList<>();
            long cursor = -1;
            boolean hasNext = true;
            while (hasNext) {
                IDs friendsA = twitter.getFriendsIDs(userA.getId(), cursor);
                for (long friend : friendsA.getIDs()) {
                    friendsList.add(friend);
                }
                cursor = friendsA.getNextCursor();
                hasNext = friendsA.hasNext();
            }

            List<Long> friendsBList = new ArrayList<>();
            cursor = -1;
            hasNext = true;
            while (hasNext) {
                IDs friendsB = twitter.getFriendsIDs(userB.getId(), cursor);
                for (long friend : friendsB.getIDs()) {
                    friendsBList.add(friend);
                }
                cursor = friendsB.getNextCursor();
                hasNext = friendsB.hasNext();
            }

            List<Long> equalFriends = new ArrayList<>();

            // Get intersection
            friendsList.retainAll(friendsBList); 
            //https://docs.oracle.com/javase/7/docs/api/java/util/Set.html

            // Convert IDs to names
            List<String> friendNames = new ArrayList<>();
            for (long friendID : friendsList) 
            {
//                User friend = twitter.showUser(friendID);
                User friend = twitter.showUser(friendID);
                String friendName = friend.getScreenName();
                friendNames.add(friendName);
            }
            return friendNames;
        } 
        catch (TwitterException e) 
        {
            System.err.println("Twitter is down");
            return null;
        }
    }
}