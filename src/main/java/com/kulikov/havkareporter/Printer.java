package com.kulikov.havkareporter;

import com.skype.SkypeException;
import com.skype.User;

import java.util.List;

/**
 * Created by Gene on 24-12-2015.
 */
public class Printer {

    public static void printGroup(int groupNumber, List<User> userList) throws SkypeException {
        int groupCounter = 1;
        if(userList.size()!=0){
            System.out.println(String.format("--- %s Group ---", groupNumber));
            System.out.println("----------------------------");
            for(User user: userList){
                System.out.println(groupCounter + ". " + user.getFullName());
                groupCounter++;
            }
            System.out.println("----------------------------");
        }
    }
}
