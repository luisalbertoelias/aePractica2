/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itt.utils;

/**
 *
 * @author rh1n0
 */
public class Message {
        private int code;
    private String message;
    private String detail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public Message getMessage(Message m, int code, String message, String detail){
        m.setCode(code);
        m.setMessage(message);
        m.setDetail(detail);
        return m;
    }    
}
