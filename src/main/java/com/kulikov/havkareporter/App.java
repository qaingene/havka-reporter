package com.kulikov.havkareporter;

import com.skype.*;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Gene on 23-12-2015.
 */
public class App {

    public static void main(String[] args) throws Exception {

        ConsoleHavkaReporter consoleHavkaReporter = ConsoleHavkaReporter.getInstance();
        try{
            consoleHavkaReporter.printReport();
        }
        catch (com.skype.NotAttachedException e){
            consoleHavkaReporter.closeProgram();
            JOptionPane.showMessageDialog(null, "Login to your skype and re-launch program");
        }
    }

}



