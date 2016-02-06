[Important!]
Please read "How To: start a game"/"How to play a game" in the menu of the Applet to see more detailed instructions of controls

- You can start by choosing a difficulity[Easy/Normal/Hard/Any], or by choosing a word yourself
- The word list is provided for you to choose a word as the guess, it is updated as you mark "Must be in"/"Must not be in" letters in the Letter Panel
- Left click to mark "Must be in", left click once more to cancel the mark
- Right click to mark "Must not be in", click once more to cancel the mark

* The three views that are in MVC pattern is 
  + "Wordlog" as the logs for all previous guesses
  + "WordPanel" as the Letter Panel for player to mark letters
  + "InputPanel" as the Panel area for player to input words
* The control in MVC is "GameModel"
  + "addView", method to add a view [ICustomView]
  + other methods are for "views" to communicate with control
* The interface in MVC is "ICustomView"
  + "updateView(string w)" is the method to update view, where w is the player's input 
