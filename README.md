# Two Side PLL Recognition Practice Program by Jack Lanois
There exists a desire to get faster at speedcubing. As a fellow speedcuber, I too want to improve my speed at solving the cube. However, when looking for ways to improve you times, improving last layer recognition is often overlooked. Two side PLL recognition is a way to improve times a bit by knocking some time off PLLs by reducing the time it takes to recognize which case you have. Ideally, by recognizing the pattern of colors on two sides of the cube, you lose the time it takes you to look around the cube or to look at other sides of the top by using extra U moves to recognize the case.

Why doesn't everyone, especially newer speedcubers, do this? Well... learning two side PLL is kind of daunting... especially at first... kind of like learning full OLL algorithms in my opinion. Well, there's 22 PLL cases (including skips as cases, because it's *technically* a case, and I'd lose faith in humanity if someone couldn't recognize it) and 4 different ways each case can be looked at, meaning you would have to differentiate between 88 different color patterns. I don't know about you, but that seems like a rather large number just looking at it straight up. In addition, there's some patterns that are so similar that the color of 1 or 2 pieces could indicate completly different cases.

How would you go about learning the different setups though? Well, this is one area where there is no shortage of material online. You can easily find websites and/or Youtube videos to teach you how to distinguish between the different PLL cases. With some effort and diligence, you can learn the different patterns.

Practicing it though, is a completely different story. Sure, there's some **free** things that exist to help with practicing two side PLL recognition, but nothing I found was...satisfactory...in my opinion (no disrespect to those **free** things though). So, I figured that since know how to code Java, and having learned how to use a bit of JavaFX in high school, that I would make my own program and share it to the cubing community in a way that everyone can use it. I also deciced to make it open source, so that anyone could work on the code if they wanted to. My goal with this project of mine is to provide this free for anyone to use, so that good practice is accessible and not locked behind a paywall.

### Sidenote
To those who actually know how to code and have a lot of experience with it, I just want you to know that I coded the bulk of this before starting college, and so I didn't javadoc everything like I should have. Coming back to this to add a few more things after a year in college, I'm now regretting it. I'm sorry, but I learned to code by throwing in a bunch of comments to describe things going on in the code instead of Javadocing everything. I'll try to add the Javadoc as time goes on, I just decided to post this to get it out to people because my schedule is about to get a bit more busy. So, I apologize to anybody attempting to do this early on after the initial posting about the mountain of comments and code they'll need to go through to understand what's going on.

## Table of Contents
- [How to use](#how-to-use)
  * [Main Menu](#main-menu)
    + [Mode](#mode)
    + [Cross Color](#cross-color)
    + [Difficulty](#difficulty)
    + [Number of Cases](#number-of-cases)
    + [Cube Colors](#cube-colors)
  * [Case Selection](#case-selection)
  * [Testing](#testing)
  * [Results Screen](#results-screen)
- [Downloads](#downloads)
  * [.jar File](#jar-file)
  * [Windows](#windows)
  * [Mac](#mac)
  * [Linux](#linux)
  * [Troubleshooting](#troubleshooting)
- [Final Notes](#final-notes)
  * [Discord](#discord)
  * [The Code*](#the-code----people-looking-to-play-with-the-code-should-read-this)
  * [Improvements](#improvementssuggestions)

## How to Use

### Main Menu
![Main Menu](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Main%20Menu.PNG)

So this is the main menu. This is what you open up to when you run the application. Take note, I locked the window size because scaling everything could've been done in many different ways and would've been a nightmare to implement. You can select what you want to do from the main menu. You can select the mode, choose you cross color, choose the difficulty, set how many cases you want to be tested on, and what colors you want your cube to be.

#### Mode
The mode could either be **normal** or **debug**.
- The **normal** mode is the mode you will probably be on most of, if not all of the time. In this mode, random PLLs based those selected in the *Select Cases* menu. The cube colors abide by the cross color chosen. The cube colors will match those set on the menu.
- The **debug** mode is essentially the testing setup without the testing. It outputs stuff into the console about what's going on. It has a menu bar at the top where you can decide what actions to do. You can also change the colors of the cube and see how they look on the cube after you change them, if that will help you pick colors better. You can also experiment and see how it looks on different permutations by clicking the menu option that generates a PLL. A new case will randomize the PLL and the colors of the cube while still abiding by the selected cross from the menu.
#### Cross Color
With this setting, you can choose your cross color. You can set your cross color to ANY of the 6 colors on the cube. You can even set it to be color neutral (actually that's what it defaults to because I'm color neutral, but that's besides the point). By setting the cross color, the cube and PLL cases are shown to you in a way such that its colors will line up like the cross color is on the bottom. Meaning, that if you set the cross color to white, you will always have yellow on top.
#### Difficulty
Difficulty, (the best term I could use for it), could either be **simple** or **full**. The difficulty basically changes what PLLs you could guess. I think it's best to use an example to describe this:
Say your given a case that you recognize to be an R-perm. For **simple**, you only need to answer **R** because you know it's an R-perm. For **full**, you would need to differentiate between an **R1/Ra** and **R2/Rb** and answer with one of those instead. Stats are tracked individually for each difficulty, and you can easily check them both in the *Session Stats* menu. More on this later.
#### Number of Cases
This number is the number of cases you can set for a single quiz session. Right now, I have it set to be a number between 1-999, because I don't know why you would need to do more than 999 at a time, because it seems like it would be a nightmare going through results. Also, don't have a pause function implemented, because I feel like it would be weird starting back in. So, choose your case numbers wisely. Furthermore, stats are saved cumulatively in *Session Stats* so I don't see there being much of a point.
#### Cube Colors
Cube colors are RGB values, and can be switched individually by changing their indiviudal RGB values. Changing the values here will not only be changed when testing, but will also be changed for the cubes when look in the *Select Cases* menu and when you go into debug. You can load colors from a file and save them to a file. **If you want to update the cube colors for a saved session file (more on this later), replace the file. It will change the cube colors in that file, and keep all of the session data intact.**

### Case Selection
![Case Selection 1](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Case%20Seletion%201.PNG) ![Case Selection 2](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Case%20Selection%202.PNG)

This menu is the one you are brought to when click *Select Cases* on the main menu. In this menu, you can select which cases you want to be tested on. Each item seen inside the scrolling area is a dropdown that shows the 4 different ways each PLL case can be setup. You can uncheck the boxes for the cases you do **not** want to see in the tests. These cases will stay the same for both difficulties. In addition, there is also presets that can be set. I set the presets based on the categories made in [Sarah's Cubing Site](https://sarah.cubing.net/3x3x3/pll-recognition-guide). I am not affiliated with that website- it's just the place I learend my two side pll recognition. And I think it's a good way of organizing them. So, I used those groups for presets. Credit goes to that website for the grouping though. The preset checkboxes work like this: checking them off will turn all the cases for that specific preset off while checking it on will turn on all the cases for that specific preset. Cases can be modified manually outside of setting them. You can also select all and select none to be highlighted at once. The colors of the cubes are consistent throughout all of the cases. The colors match thosein the main menu, and the cubes abide by the cross color set in the main menu as well.

### Session Statistics
![Session Stats 1](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Session%20Stats%201.PNG) ![Session Stats 2](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Session%20Stats%202.PNG)

This menu is the one you are brought to when click *Session Stats* on the main menu. In this menu, you can select which cases you want to be tested on. This is where the statistics for the test sessions are stored. It tracks stats for each different orientation of each different PLL case. Each case has 4 different orientations. You can refer to *Case Selection* for matching specific cases/orientations with certain block setups. This menu stores stats of the full and simple difficulties individually of each other. That means that testing in full difficulty will **not** affect your simple difficulty stats. You can switch between them with a button near the top left of the scroll window. Sessions stats records a variety things **for each case/orientation** which I thought may be helpful for people trying to improve including: total test questions, how many correct/incorrect guesses, accuracy, fastest (correct) guess time, slowest (correct) guess time, average (correct) guess time, standard deviation of (correct) guess times, and most wrong guess (which case do you guess the most when you get it wrong). You can switch between viewing the number of correct guesses and the number of incorrect guesses by clicking the button near the top right of the scroll window. Because what you're viewing is a table, you can also sort between highest/lowest for each thing by clicking the column labels at the top of the table. You can also resize the columns to your own desire. The last 2 things of note on the screen are the *Import...* and *Export...* buttons. Yes, you **can** save your session to a file. It will save **both** your full and simple stats as well as your custom cube colors set on the main menu. It saves the file as a text file with the a specific formatting in the file. If you want to mess with the files, you do you. There is *some* error checking, but it will not crash the program if you give it a file with invalid data and instead give a pop-up window with the error. The colors go first, followed by the simple difficulty entries and then the full difficulty entries. Speaking of colors, if you wish to change the colors on a save file, you can do that in the main menu. When doing so, **REPLACE** the file with session stats that you wish to change. It will only change the colors in the file and keep all of the other data in it. The main goal of my file saving/loading was so that someone could save their session and come back to it later on and continue.

### Testing
![Simple Setup](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Simple%20Setup.PNG) ![Full Setup](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Full%20Setup.PNG)

This is where the testing happens. Testing will give you different PLL cases with random cube color orientations abiding by A) the cross color set on the main menu and B) the color orientations would match that of an actual rubiks cube so it looks legit. In addition, it will only test you on cases that are checked in the *Case Selection*U. Upon clicking *Start* in the main menu, you will be brought to the testing window. A timer will count down to 0, which is when the first case will appear. The case, as seen in the pictures is presented in the window. Below it, you will see the timer. And below and to the right of the case, you will see a number representing the question number. The program will test you on as many cases as you have specified in the main menu. **Beware though, there is no pause function. Stats are saved cumulatively anyway, so this shouldn't be an issue.** Buttons will appear on the bottom matching the selected difficulty in the main menu. The button layouts will appear as seen in the images. *"But clicking the buttons is SLOOOOWWWWW- is there some other way I can do it to better show my speed?"* Actually, yes there is. I have implemented keybinds that you could use instead for guessing. However, they work *slightly* differently between the simple and full. Generally, I have set the keybinds to match those the name of the PLLs. So for instance, let's say you're given a pattern you recognize to be an A2. In **simple** (left image), you would only need to press the *A* key to guess. In **full** (right image), you would need to press the *A* key **AND** the *2* key **AT THE SAME TIME** to make the correct guess. This goes for all similar cases in **full**, such as J1/J2, R1/R2, etc. For the different G perms in **full**, G1/Ga = *G* key + *1* key, G2/Gb = *G* key + *2* key, G3/Gc = *G* key + *3* key, G4/Gd = *G* key + *4* key. However, for PLLs like T and Z, you only need to press those that specific key in either difficulty. So you do **NOT** need to press the *Z* key and the *1* key to guess a Z perm in **full** difficulty. Just wanted to make that clear. One more thing to note, I set the keybind for the skip case to the *S* key.

**I feel that I have to justify my choices for the PLL names, so I'll do it here. What I'm referring to, is the fact that I referrence PLLs that have multiple cases such as the J perms, R perms, etc as J1/J2 and R1/R2 instead of Ja/Jb and Ra/Rb. I'm sorry to those who may get put off by this decision, but I have a few reasons for doing so. First of all, that's just how I learned them. I may be weird, but it's just how I learned. Secondly, it makes keybinds for the full difficulty simpler. Sure, holding a the perm letter and the *a* or *b* key may be the fine, but what about Aa and Ab? Hmm? Makes assigning those a bit weird and would ruin the overall consistent nature of the keybind patterns I've intended. Thirdly, it really isn't that much different. All [PERM LETTER]1s and [PERM LETTER]2s correspond to all [PERM LETTER]as and [PERM LETTER]bs. It's not like the kind of switch an experienced cuber would percieve for switching from crossing on white to color neutral. Nah. So, I hope this doesn't put anyone off. Just felt like I needed to mention why I did what I did.**

### Results Screen
![Simple Results](https://github.com/jackdeath5/TwoSidePLLPractice/blob/master/TwoSidePLLPractice/images/Simple%20Results.PNG)

This is the screen that comes up after you finish a test. It will only show the results of the **most recent (literally just taken)** test. Cumulative states are saved in the *Session Stats* menu. At the top of the results screen, you will see basic stats from the test: total correct and total number of cases tested, average overall recognition time (including incorrect guesses), overall test accuracy, best (correct) case guess and time for it, worst (correct) case guess and time for it. Below that is a scrolling window that shows the stats of the test. It shows the question number, the time stamp at which the case was answered, a picture of the tested case, the correct PLL name (dependent on difficulty for whether it includes differences for cases like U1/U2, etc), what **you** answered, the time it took to answer it, and whether or not it was correct (check character) or incorrect (X). Because it is a table, you can sort these by highest/lowest by clicking the column labels at the top of the table. You can also resize the columns to your own desire. By clicking the *Test Again* button, you will be returned back to the main menu.

## Downloads
In order to run this, you **MUST** have Java 8. Most of you might already have it, since it's the first thing that comes up when you Google something like "java download". But for those who don't, you can get it [here](https://www.java.com/en/download/). There will be a few downloads (hopefully most people can run at least one), including the runnable .jar file and a runnable .exe file for Windows people who may choose to do that instead or might be having issues running the .jar file.

### .jar File
This file should work for most people who have Java 8 installed. I have been able to test run it on a few different computers with Java 8 and it has ran fine. For computers with later and/or multiple versions of Java installed, I can't say if it will work or not. Check troubleshooting if you run into problems with this.

[Download TwoSidePLLPractice.jar](https://github.com/jackdeath5/TwoSidePLLPractice/raw/master/TwoSidePLLPractice/Downloads/TwoSidePLLPractice.jar)

### Windows
I'm going to try and be as transparent as I need to be with this, because I know downloading .exe files can be kind of sketchy and might trigger some anti-virus software. For one, I made the Java Code open source so that if you want to, you can take a look at it. Why does this matter? I literally just took the .jar file, and basically threw it into [launch4j](http://launch4j.sourceforge.net/) to get the .exe file. Then, I signed it to stop anti-virus checks for that. That's it. No other modification or anything done to the file. If you're still super skeptical of a virus for whatever reason, message me on Discord (mentioned below) we can discuss it there. If anyone has any idea to help me make this .exe less trigger happy, please let me know.

**I did this to try and address the problems with multiple Java versions on a Windows computer. If for some reason on Windows the .jar file doesn't do anything when you try and open it, try downloading and running this .exe file instead.**

[Download TwoSidePLLPractice.exe](https://github.com/jackdeath5/TwoSidePLLPractice/raw/master/TwoSidePLLPractice/Downloads/TwoSidePLLPractice.exe)

### Mac
I'll be honest- I don't have a Macbook and neither do any of my close friends, so I apologize that I haven't been able to test it on Macs. Try making sure you have Java 8 downloaded and **running the .jar version**. If that doesn't work for some reason, maybe someone with some knowledge about potential fixes or ways to make it work can reach out to me on Discord or something and we can discuss potential fixes.

### Linux
I'll be honest- I don't use Linux, and neither do any of my close friends, so I apologize that I haven't been able to test it on Linux. Try making sure you have Java 8 downloaded and **running the .jar version**. If that doesn't work for some reason, maybe someone with some knowledge about potential fixes or ways to make it work can reach out to me on Discord or something and we can discuss potential fixes.

### Troubleshooting
- If the .jar not running/working for Windows users, make sure you have the right version of Java: Java 8. If you do and it's still not running/working, try downloading the most recent version of Java 8. If it still won't run, you could either A) try running the .exe version or B) continue reading.
- Assuming you have the right Java version installed and the .jar file is not running/working, try opening a command prompt window in the folder/directory that the .jar file is in and run the command *java -jar TwoSidePLLPractice.jar* and see if that works/runs the .jar file. If it does, great. You may want to try the .exe version if you don't want to do this each time to run the program. If it doesn't work/run and throws an error, continue reading.
- **If you have the correct version of Java, you should find that typing *java -version* in a command prompt window responds with something like *java version "1.8.0_221"* (221 = number for update number, which doesn't matter as much as the numbers before it. So this number you can disregard if yours is equal or higher, but I can't be sure if it's below. Maybe update Java?).**
  - **If it is the correct version**, check the previous bullet point. If that has failed, then I don't know. Try running the .exe version? If that still fails, maybe contact me on Discord or something and we might be able to discuss what may be going on. Outside of that, assuming you've followed everything properly, I'm not sure.
  - **If it is a different version,** then you may have multiple versions of Java (perhaps because you code in a later version of Java or for some other reason). If it isn't working/running, try making sure your JAVA_HOME environment path variable is set to the Java 8 bin folder, which will look something like "C:\Program Files\Java\jre1.8.0_221\bin", and that %JAVA_HOME% is set to be the first thing in the "Path" variable. (You can find tutorials for how to set this online). Once done, verify by doing the command line check (*java -version*) again that was previously mentioned.

## Final Notes

### Discord
If you want/need to contact me on Discord to discuss something such as major bugs, the code, launching issues, or maybe because you're a .exe file skeptic, add me at Jack Death#3391 and we can talk about it. Can't guarentee how active I'll be since my schedule is starting to pick up, but I can try. If enough people need/want to message me for some reason, I may start a Discord server which if done, will be posted here in the future. **Message me if you find a crippling bug that impacts performance/usability of the program.**

### The Code -- PEOPLE LOOKING TO PLAY WITH THE CODE SHOULD READ THIS
I coded this in Eclipse using **Java 11 and JavaFX 11**. The code of which is what is made open source. However, as some might've noiced, the .jar requires Java 8. This is because I found exporting it from Eclipse using JavaFX11 was weird and annoying and required extra steps (VM args), which is not exactly great for usability for other people. So, I converted the there back to Java 8 for the released .jar (which is what is turned into the .exe as well) and made it in the specialized Eclipse build meant for coding with JavaFX and [e(FX)clipse](https://www.eclipse.org/efxclipse/index.html) and exported it from that. There are a few minor change that needed to be made, but those are noted in comments made near the top of the program. Outside of those changes mentioned, nothing else is changed. And again, sorry for the poor documentation, I'm hoping to get around to it eventually. Talk to me on Discord for the time being if you have questions.

### Improvements/Suggestions
Like anything out there, improvements can always be made and features can always be added. At this point though, I'm happy with how the program works right now. As mentioned previously, my schedule is starting to pick up and I will a be a bit more busy so I don't know if I'll be able to add many more things soon. I'd also rather focus on actually documenting it so it is h-e-double-hockeysticks to go through for people who want to check it out and perhaps modify the code on their own because I made the code open source. I'm not going to stop these though, but please do not spam me with suggestion. I doubt there's going to be a chance that I can get to them anytime soon because of my schedule. But anyone who wants to contribute something useful/beneficial to the program, feel free to ask.
