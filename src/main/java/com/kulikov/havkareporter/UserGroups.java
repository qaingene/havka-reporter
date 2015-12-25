package com.kulikov.havkareporter;

/**
 * Created by Gene on 23-12-2015.
 */

import com.skype.SkypeException;
import com.skype.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class UserGroups {

    private static String [] firstTemplateGroup = Constants.FIRSTTEMPLATEGROUP;

    private static String [] secondTemplateGroup = Constants.SECONDTEMPLATEGROUP;

    private static String [] thirdTemplateGroup = Constants.THIRDTEMPLATEGROUP;

    private static String [] fourthTemplateGroup = Constants.FOURTHTEMPLATEGROUP;

    private static List<User> firstTargetGroup = new ArrayList<User>();
    private static List<User> secondTargetGroup = new ArrayList<User>();
    private static List<User> thirdTargetGroup = new ArrayList<User>();
    private static List<User> fourthTargetGroup = new ArrayList<User>();

    public static UserGroups calculatePeople(List<User> userList) {
        String userId ="";
        for(User user: userList){
            userId = user.getId();
            if(isUserPresentInList(firstTemplateGroup, userId)){
                firstTargetGroup.add(user);
            }
            else if(isUserPresentInList(secondTemplateGroup, userId)){
                secondTargetGroup.add(user);
            }
            else if(isUserPresentInList(thirdTemplateGroup, userId)){
                thirdTargetGroup.add(user);
            }
            else{
                fourthTargetGroup.add(user);
            }
        }
        return new UserGroups();
    }

    private static boolean isUserPresentInList(String [] listOfUsersId, String id){
        boolean isPresent = false;
        for(String userId: listOfUsersId){
            if(userId.equals(id)){
                return true;
            }
        }
        return isPresent;
    }

    public List<User> getTodayGroup(int groupNumber){
        if(groupNumber==1){
            return firstTargetGroup;
        }
        else if(groupNumber==2){
            return secondTargetGroup;
        }
        else if(groupNumber==3){
            return thirdTargetGroup;
        }
        else if(groupNumber==4){
            return fourthTargetGroup;
        }
        else {
            throw new IllegalArgumentException(String.format("%s - unsupported group number", groupNumber));
        }
    }
}
