package com.edusdk.entity;

/**
 * Created by Administrator on 2017/4/28.
 */

public class ChatData {
    private String peerid;
    private String message;
    private String nickName ;
    private int role ;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPeerid() {
        return peerid;
    }

    public void setPeerid(String peerid) {
        this.peerid = peerid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
