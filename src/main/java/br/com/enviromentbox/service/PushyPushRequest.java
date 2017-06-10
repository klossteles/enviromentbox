package br.com.enviromentbox.service;

/**
 * Created by Kloss Teles on 05/06/2017.
 */
public class PushyPushRequest {
    public Object data;
    public String[] tokens;

    public Object notification;

    public PushyPushRequest(Object data, String[] deviceTokens, Object notification) {
        this.data = data;
        this.tokens = deviceTokens;
        this.notification = notification;
    }
}
