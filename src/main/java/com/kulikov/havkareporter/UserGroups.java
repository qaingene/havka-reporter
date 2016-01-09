package com.kulikov.havkareporter;

/**
 * Created by Gene on 23-12-2015.
 */

import com.skype.Skype;
import com.skype.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserGroups {

    private static String [] firstTemplateGroup = Constants.FIRSTTEMPLATEGROUP;

    private static String [] secondTemplateGroup = Constants.SECONDTEMPLATEGROUP;

    private static String [] thirdTemplateGroup = Constants.THIRDTEMPLATEGROUP;

    private static String [] fourthTemplateGroup = Constants.FOURTHTEMPLATEGROUP;

    private static List<User> firstTargetGroup;
    private static List<User> secondTargetGroup;
    private static List<User> thirdTargetGroup;
    private static List<User> fourthTargetGroup;

    public static UserGroups calculatePeople(List<User> userList) {
        String userId ="";

        firstTargetGroup = new ArrayList<User>();
        secondTargetGroup = new ArrayList<User>();
        thirdTargetGroup = new ArrayList<User>();
        fourthTargetGroup = new ArrayList<User>();

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

    public static List<User> getAllUserNamesListFromTemplates(){
        List<String> allUserNamesList = new ArrayList<String>();
        allUserNamesList.addAll(Arrays.asList(firstTemplateGroup));
        allUserNamesList.addAll(Arrays.asList(secondTemplateGroup));
        allUserNamesList.addAll(Arrays.asList(thirdTemplateGroup));
        allUserNamesList.addAll(Arrays.asList(fourthTemplateGroup));

        List<User> usersFrom4Groups = new ArrayList<User>();
        for(String username: allUserNamesList){
            usersFrom4Groups.add(Skype.getUser(username));

        }
        return usersFrom4Groups;
    }

    public static List<User> getBoycottUsersList(List<User> positiveVotedMessagesList, List<User> negativeVotedMessagesList) {
        List<User> boycottUsersList = new ArrayList<User>();
        boycottUsersList = UserGroups.getAllUserNamesListFromTemplates();
        boycottUsersList.removeAll(positiveVotedMessagesList);
        boycottUsersList.removeAll(negativeVotedMessagesList);
        return boycottUsersList;
    }
}