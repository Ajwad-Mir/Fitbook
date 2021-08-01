package com.example.fitbook.chats;

import java.sql.Timestamp;

public class message {
    private String senderuid;
    private String recieveruid;
    private String messagetime;
    private String messagetext;

    public message() {
    }

    public message(String senderuid, String recieveruid, String messagetime, String messagetext) {
        this.senderuid = senderuid;
        this.recieveruid = recieveruid;
        this.messagetime = messagetime;
        this.messagetext = messagetext;
    }

    public String getsenderuid() {
        return senderuid;
    }

    public void setsenderuid(String senderuid) {
        this.senderuid = senderuid;
    }

    public String getrecieveruid() {
        return recieveruid;
    }

    public void setrecieveruid(String recieveruid) {
        this.recieveruid = recieveruid;
    }

    public String getmessagetime() {
        return messagetime;
    }

    public void setmessagetime(String messagetime) {
        this.messagetime = messagetime;
    }

    public String getmessagetext() {
        return messagetext;
    }

    public void setmessagetext(String messagetext) {
        this.messagetext = messagetext;
    }
}
