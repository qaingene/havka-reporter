package com.kulikov.havkareporter;

import com.skype.SkypeException;

/**
 * Created by Gene on 23-12-2015.
 */
public interface HavkaReporter {

    void printReport() throws Exception;

    void closeProgram();
}
