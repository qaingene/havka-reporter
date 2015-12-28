package com.kulikov.havkareporter;

import com.skype.SkypeException;
import com.skype.User;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by Gene on 24-12-2015.
 */
public class Printer {

    public static String printGroup(int groupNumber, List<User> userList) throws SkypeException {
        int groupCounter = 1;

        StringBuilder stringBuilder = new StringBuilder();
        if(userList.size()!=0){
            stringBuilder.append(String.format("--- Группа %s ---" +"\n", groupNumber));
            stringBuilder.append("----------------------------" + "\n");
            for(User user: userList){
                stringBuilder.append(groupCounter + ". " + getUserName(user) +"\n");
                groupCounter++;
            }
            stringBuilder.append("----------------------------" +"\n");
        }
        return stringBuilder.toString();
    }

    public static String printNoVotedUserGroup(List<User> userList) throws SkypeException {
        int groupCounter = 1;
        StringBuilder stringBuilder = new StringBuilder();
        if(userList.size()!=0) {
            stringBuilder.append("--- Сегодня на диете:");
            stringBuilder.append("\n");
            for (User user : userList) {
                stringBuilder.append(groupCounter + ". " + getUserName(user));
                stringBuilder.append("\n");
                groupCounter++;
            }
            stringBuilder.append("----------------------------");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String printBoycottUserGroup(List<User> boycottUserList) throws SkypeException {
        int groupCounter = 1;
        StringBuilder stringBuilder = new StringBuilder();

        if(boycottUserList.size()!=0) {
            stringBuilder.append("--- Вольные хавальщики:");
            stringBuilder.append("\n");
            for (User user : boycottUserList) {
                stringBuilder.append(groupCounter + ". " + getUserName(user));
                stringBuilder.append("\n");
                groupCounter++;
            }
            stringBuilder.append("----------------------------");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private static String getUserName(User user) throws SkypeException {
        if(StringUtils.isNotEmpty(user.getFullName())){
            return user.getFullName();
        }
        else{
            return user.getId();
        }
    }
}
