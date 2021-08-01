package com.example.fitbook.chats;

public class chats {
    private String chatid;
    private String chatroomsenderuid;
    private String chatroomrecieveruid;
    public chats() {
    }

    public chats(String chatid, String chatroomsenderuid, String chatroomrecieveruid){
        this.chatid = chatid;
        this.chatroomsenderuid = chatroomsenderuid;
        this.chatroomrecieveruid = chatroomrecieveruid;
    }

    public String getchatid() {
        return chatid;
    }

    public void setchatid(String chatid) {
        this.chatid = chatid;
    }

    public String getchatroomsenderuid() {
        return chatroomsenderuid;
    }

    public void setchatroomsenderuid(String chatroomsenderuid) {
        this.chatroomsenderuid = chatroomsenderuid;
    }

    public String getchatroomrecieveruid() {
        return chatroomrecieveruid;
    }

    public void setchatroomrecieveruid(String chatroomrecieveruid) {
        this.chatroomrecieveruid = chatroomrecieveruid;
    }
}
