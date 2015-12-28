package com.kulikov.havkareporter;

import com.skype.*;

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

    public String calculateVotes() throws Exception {
        Chat chat = consoleHavkaReporter.getSkypeChat(Constants.HAVATCHATID);

        ChatMessage[] chatMessages = chat.getAllChatMessages();

        List<ChatMessage> filteredTodayChatMessagesList = ChatMessageFilter.getMorningMessagesList(ChatMessageFilter.getTodayStartDate(),
                ChatMessageFilter.getTodayEndDate(),
                chatMessages);

        List<ChatMessage> positiveVotedMessagesList = new ChatMessageFilter().getVotedMessagesList("yes", filteredTodayChatMessagesList);
        List<ChatMessage> negativeVotedMessagesList = new ChatMessageFilter().getVotedMessagesList("no", filteredTodayChatMessagesList);

        List<User> positiveUsersList = ChatMessageFilter.getVotedUsersListFromChatMessagesList(positiveVotedMessagesList);

        List<User> negativeUsersList = ChatMessageFilter.getVotedUsersListFromChatMessagesList(negativeVotedMessagesList);

        UserGroups positiveUserGroups = UserGroups.calculatePeople(positiveUsersList);

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i<=4; i++){
            stringBuilder.append(Printer.printGroup(i, positiveUserGroups.getTodayGroup(i)));
        }

        stringBuilder.append(Printer.printNoVotedUserGroup(negativeUsersList));

        List<User> boycottUsersList = UserGroups.getBoycottUsersList(positiveUsersList, negativeUsersList);

        stringBuilder.append(Printer.printBoycottUserGroup(boycottUsersList));
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }


    public void printReport() throws SkypeException {
        Skype.setDaemon(false);

        Skype.addChatMessageListener(new ChatMessageAdapter() {
            public void chatMessageReceived(ChatMessage received)
                    throws SkypeException {

                String id = received.getChat().getId();
                String userMessage ="";

                System.out.println(id);
                //beseda2 chat
                if(id.equals("19:a33efa779e3b4eb680af32f1ef1c745a@thread.skype")){
                    userMessage = received.getContent();
                    if(userMessage.equalsIgnoreCase(Constants.PRINTREPORT)){
                        try {
                            String resultReport = calculateVotes();
                            System.out.println(resultReport);
                            Skype.chat(received.getSender().getId()).send(resultReport);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if(userMessage.equalsIgnoreCase(Constants.CLOSEPROGRAM)){
                        closeProgram();
                    }
                    else{
                        Skype.chat(received.getSender().getId()).send(String.format("'%s' - unsupported command, use 'report' command", userMessage));
                    }
                }
            }
        });
    }

    public void closeProgram() {
        System.exit(0);
    }
}
