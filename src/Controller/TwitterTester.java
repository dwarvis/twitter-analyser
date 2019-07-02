package Controller;

import twitter4j.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class TwitterTester {
    private PrintStream consolePrint;
    private String twitterHandleToSearch;
    private TwitterGUIController myClient;

    public TwitterTester()
    {
        myClient = new TwitterGUIController();
    }

    public void tweetMessage()
    {
        //read in message
        Scanner myReader = new Scanner(System.in);
        System.out.println("What message would you like to tweet?");
        myReader = new Scanner(System.in);
        String message = myReader.nextLine();

        //send message string to client, if successfull a sentStatus will be returned.
        //print the sent status message
        String sentStatus = myClient.postTweet(message);
        System.out.println("Message sent: " + sentStatus);

        //close the reader so that memory doesn't leak.
        myReader.close();
    }

}
