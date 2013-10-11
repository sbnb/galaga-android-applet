galaga-android-applet
=====================

A Galaga style game using code sharing between Android and an Applet.

Android code is Java, but not standard Java. The differences are mostly around the handling of graphics, as shown here:

http://en.wikipedia.org/wiki/Comparison_of_Java_and_Android_API

This means that you can't just interchangeably switch code between traditional Java and Android Java.

However, when developing games for an Android device it can be a pain to have to use an emulator or real phone to test every change. It is much faster to run your game as a standard Java applet (or stand-alone program).

This can only be done if the standard and Android Java are isolated from one another in some way. This project is an example of one way to do this.

It consists of four Eclipse projects:

* galaga-shared: shared code used by both targets
* galaga-android: the Android specific project
* galaga-applet: the Applet specific project
* galaga-tests: unit tests for the shared project

The android and applet projects both depend on the shared project. The shared project conatins the bulk of the projects code: game classes and logic, behaviour, scoring etc.

The device specific projects contain the minimum amount of code necessary to run in that environment.

To use import all four projects into Eclipse (Import -> General -> Existing Projects into Workspace).

Then build from either the Applet or Android project to build and run that version.
