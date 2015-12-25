package com.kulikov.havkareporter;

import com.skype.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gene on 23-12-2015.
 *
 */
public class App {


    public static void main(String[] args) throws Exception {

        ConsoleHavkaReporter consoleHavkaReporter = ConsoleHavkaReporter.getInstance();
        consoleHavkaReporter.printReport();
    }
}



