Program Title: Problem Set 1 - Twitter Analysis
Author: Davis Cheng
Class: Year 12, Mr. Lagos
Date: 10/09/2019 (DD/MM/YY)

----- Program Purpose -----

This program predominantly does, or attempts to do, 4 things:
1. Get a users most used popular word from their tweets.
2. Get a users most used hashtag from their tweets.
3. Get a users most mentioned user from their tweets.
4. Get each user that two users both follow. Eg. if @elag87 and @tadalton17 both
followed @JayThompsonEdu, then this code would display @JayThompsonEdu after 
@elag87 and @tadalton17 are inputted.

each time you want to do a thing you have to close and re-open the program.

----- Design ----- 

TwitterGUIController and TwitterGUI are the only classes and each layer 
represents a method. 

(class) TwitterGUIController-
    
    TwitterGUIController -- Connects to Twitter and performs authorizations.
    findUserStats -- executes methods fetchTweets, splitIntoWords, 
    removeCommonEnglishWords, countAndRemoveEmpties and findMostPopularWord.In
    addition to this, it runs executes removePunctuation on each token.
        removePunctuation -- takes a string and removes all non-lowercase letter 
        characters.
        fetchTweets -- fetches tweets and stores them in a list of statuses.
        splitIntoWords -- gets each status and splits it into a list of "tokens", 
        which are just strings.
        removeCommonEnglishWords -- reads commonWords.txt, and checks for each 
        token that is in commonWords.txt, and is therefore a common word, and 
        removes it.
        countAndRemoveEmpties --  creates a hashmap frequentWords and adds all 
        the tokens to it. The key of the token represents any token, and the    
        value represents how many times it appears in the token list.
        findMostPopularWord -- finds the highest value word in frequentWords,
        and gets its numerical value.
        getMostPopularWord -- returns most popular word
        getFrequencyMax -- returns highest frequency value.
    getHashtagsFrom -- is virtually the same as findUserStats except that
    instead of removing punctuation, it does removeNonHashtags. Still ultimately
    finds most popular hashtag and the amount of times it has been used.
        removeNonHashtags -- removes all non hashtag tokens.
    getMentionsFrom -- is virtually the same as findUserStats except that
    instead of removing punctuation, it does removeNonMentions. Still ultimately
    finds most mentioned user and the amount of times they have been mentioned.
        removeNonMentions -- removes all non mention tokens.
    getMutualFollow -- puts jtextfield2 and jtextfield3 through fetchUsers
        fetchUsers -- gets users from their screen names
        mutualConnections -- gets two lists of follower ids from two different
        users, then returns the intersection of that.

(class) NewJFrame-

    NewJFrame -- Creates new form TwitterGUI
    twitterButtonMouseClicked -- gets text from text field and feeds that
    though findUserStats, then sets the text area to show getMostPopularWord and
    getFrequencyMax.
    twitterHashtagButtonMouseClicked -- gets text from text field and feeds that
    though getHashtagsFrom, then sets the text area to show getMostPopularWord
    and getFrequencyMax.
    twitterMentionButtonMouseClicked -- gets text from text field and feeds that
    though getMentionsFrom, then sets the text area to show getMostPopularWord and
    getFrequencyMax.
    twitterFriendsButtonMouseClicked -- gets text from both text fields and 
    feeds that through getMutualFollow to show the number of mutual friends and 
    a list of mutual friends in the text area.