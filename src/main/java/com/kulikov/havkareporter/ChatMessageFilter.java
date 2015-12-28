package com.kulikov.havkareporter;

import com.skype.ChatMessage;
import com.skype.SkypeException;
import com.skype.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Gene on 23-12-2015.
 */
public class ChatMessageFilter {

    private static final int STARTDATEHOUR = 6;
    private static final int STARTDATEMINUTE = 00;
    private static final int ENDDATEHOUR = 12;
    private static final int ENDDATEMINUTE = 00;
    private static Calendar calendar = Calendar.getInstance();

    /**
     * Method returns list of messages which were received between startDate and endDate
     * @param startDate startDate
     * @param endDate endDate
     * @param chatMessages chatMessages
     * @return list of messages in specified interval
     * @throws Exception
     */
    public static List<ChatMessage> getMorningMessagesList(Date startDate, Date endDate, ChatMessage [] chatMessages) throws Exception {
        if(chatMessages.length!=0){
            List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
            for(ChatMessage chatMessage: chatMessages){
                if(chatMessage.getTime().after(startDate) & chatMessage.getTime().before(endDate)){
                    chatMessageList.add(chatMessage);
                }
            }
            return chatMessageList;
        }
       else{
            throw new Exception(String.format("Chat messages size is zero in specified interval"));
        }
    }

    /**
     * Method returns
     * @param chatMessageList
     * @return
     * @throws SkypeException
     */
    public List<ChatMessage> getVotedMessagesList(String voteType, List<ChatMessage> chatMessageList) throws SkypeException {
        List<ChatMessage> votedMessagesList = new ArrayList<ChatMessage>();
        for(ChatMessage chatMessage: chatMessageList){
            if(checkMessageContent(voteType, chatMessage.getContent())){
                votedMessagesList.add(chatMessage);
            }
        }
        return votedMessagesList;
    }

    private static Date getTodayDate(int hours, int minutes){
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date getTodayStartDate(){
        return getTodayDate(STARTDATEHOUR, STARTDATEMINUTE);
    }

    public static Date getTodayEndDate(){
        return getTodayDate(ENDDATEHOUR, ENDDATEMINUTE);
    }

    public static List<User> getVotedUsersListFromChatMessagesList(List<ChatMessage> chatMessageList) throws SkypeException {
        List<User> votedUsersList = new ArrayList<User>();
        for(ChatMessage chatMessage: chatMessageList){
            votedUsersList.add(chatMessage.getSender());
        }
        return votedUsersList;
    }

    private boolean checkMessageContent(String voteType, String messageContent){
        if(voteType.equalsIgnoreCase("yes")){
            return messageContent.contains("+");
        }
        else if(voteType.equalsIgnoreCase("no")){
            return messageContent.contains("-");
        }
        else if(voteType.equalsIgnoreCase("all")){
            return ( messageContent.contains("+") | messageContent.contains("-"));
        }
        else{
            throw new IllegalArgumentException(String.format("'%s' - unsupported vote type", voteType));
        }
    }
}
