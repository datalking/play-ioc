package com.github.datalking.misc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleTutorial {

    public static void main(String[] args) {

        System.out.println("One");

        // Storing console output to consoleStorage.
        ByteArrayOutputStream consoleStorage = new ByteArrayOutputStream();
        PrintStream newConsole = System.out;
        System.setOut(new PrintStream(consoleStorage));

        // Here all System.out.println() calls will be stored in consoleStorage.
        System.out.println("two");     // Note: The output "two" you see from the console
        //        doesn't come from this line but from the lines below(newConsole.println());

        newConsole.println(consoleStorage.toString());
        newConsole.println(consoleStorage.toString());

        // Restore back the standard console output.
        System.setOut(newConsole);

        // Print to console.
        System.out.println("three");
        System.out.println(consoleStorage.toString());
    }
}
