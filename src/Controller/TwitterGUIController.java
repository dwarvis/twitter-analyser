package Controller;

import twitter4j.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.io.PrintStream;

public class TwitterGUIController {
    private Twitter twitter;
    private List<Status> statuses;
    private List<String> tokens;
    private Map<String, Integer> frequentWords;
    private String popularWord;
    private int frequencyMax;

    public TwitterGUIController()
    {
        // Connects to Twitter and performs authorizations.
        twitter = TwitterFactory.getSingleton();
        
        statuses = new ArrayList<Status>();
        tokens = new ArrayList<String>();
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

    }


    /* 
     * TODO 3: return a word after removing any punctuation from it.
     * If the word is "Edwin!!", this method should return "Edwin".
     * We'll need this method later on.
     */
    @SuppressWarnings("unchecked")
    private String removePunctuation(String word)
    {
        return "";
    }

    /*
     * TODO 4: remove all the words that are found in the commonWords.txt file.
     * You can use a new list or update the "tokens" List. Make sure to
     * remove punctuation before checking if its a common english word.
     */
    @SuppressWarnings("unchecked")
    private void removeCommonEnglishWords()
    {

    }

    /* 
     * TODO 5: count each word using. Use the frequentWords Hashmap.
     * make sure to remove any empty strings like "" or " ". 
     */
    @SuppressWarnings("unchecked")
    private void countAndRemoveEmpties()
    {



    }

    /*
     * TODO 6: find the most popular word and store the string and frequency.
     * It is up to you to check for case sensitivity.
     */
    @SuppressWarnings("unchecked")
    private void findMostPopularWord()
    {

    }

    //TODO 7: return the most frequent word's string
    @SuppressWarnings("unchecked")
    public String getMostPopularWord()
    {
        return "";

    }

    //TODO 8: return the most frequent word's count.
    @SuppressWarnings("unchecked")
    public int getFrequencyMax()
    {
        return 0;
    }


    /*********** PART 3 **********/

    //TODO 10: Create your own method that provides any service you want.
    
    /*
     * TODO 11: HL -> You have to use atleast TWO more twitter methods, 
     * other than Query, in your investigation. If you want full points, 
     * record in your README why this method is sufficiently complex.
     */

    // Example: A method that returns 100 tweets from keyword(s).
    public List<Status> searchKeywords(String keywords)
    {
        Query query = new Query(keywords);
        query.setCount(100);
        query.setSince("2015-12-1");
        List<Status> searchResults = new ArrayList<>();
        try
        {
            QueryResult result = twitter.search(query);
            searchResults = result.getTweets();
        }
        catch (TwitterException e)
        {
            e.printStackTrace();
        }
        return searchResults;
    }
}

