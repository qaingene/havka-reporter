package com.kulikov.havkareporter;

import com.skype.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Gene on 23-12-2015.
 */
public class ConsoleHavkaReporter implements HavkaReporter {

    private static ConsoleHavkaReporter consoleHavkaReporter;

    public static synchronized ConsoleHavkaReporter getInstance(){
        if(consoleHavkaReporter==null){
            return new ConsoleHavkaReporter();
        }
        return new ConsoleHavkaReporter();
    }

    public static Chat getSkypeChat(String id) throws SkypeException {
        Chat [] chats = Skype.getAllChats();
        for(Chat chat: chats){
            if(chat.getId().equalsIgnoreCase(id)){
                return chat;
            }
        }
        throw new IllegalArgumentException(String.format("Chat with '%s' chat id is absent", id));
    }

    public void printReport() throws Exception {
        Chat chat = consoleHavkaReporter.getSkypeChat(Constants.HAVATCHATID);

        ChatMessage[] chatMessages = chat.getRecentChatMessages();

        List<ChatMessage> filteredTodayChatMessagesList = ChatMessageFilter.getMorningMessagesList(ChatMessageFilter.getTodayStartDate(),
                ChatMessageFilter.getTodayEndDate(),
                chatMessages);

        List<ChatMessage> negativeVotedMessagesList = new ChatMessageFilter().getVotedMessagesList("yes", filteredTodayChatMessagesList);
        List<User> negativeUsersList = ChatMessageFilter.getVotedUsersList(negativeVotedMessagesList);
        UserGroups negativeUserGroups = UserGroups.calculatePeople(negativeUsersList);

        for(int i = 1; i<=4; i++){
            Printer.printGroup(i, negativeUserGroups.getTodayGroup(i));
        }
    }

    public void closeProgram() {
        System.exit(0);
    }
}
