package www.test720.com.naneducation.bean;

import com.talkcloud.roomsdk.RoomUser;

/**
 * Created by Administrator on 2017/4/28.
 */

public class ChatData {


    private RoomUser user;
    private String message;
    private boolean isStystemMsg;
    private boolean inOut;
    private String trans = "";
    private boolean isTrans = false;
    private String time;
    private boolean isHold = false;
    private int state = 0;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isHold() {
        return isHold;
    }

    public void setHold(boolean hold) {
        isHold = hold;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public boolean isTrans() {
        return isTrans;
    }

    public void setTrans(boolean trans) {
        isTrans = trans;
    }

    public boolean isInOut() {
        return inOut;
    }

    public void setInOut(boolean inOut) {
        this.inOut = inOut;
    }

    public boolean isStystemMsg() {
        return isStystemMsg;
    }

    public void setStystemMsg(boolean stystemMsg) {
        isStystemMsg = stystemMsg;
    }

    public RoomUser getUser() {
        return user;
    }

    public void setUser(RoomUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
