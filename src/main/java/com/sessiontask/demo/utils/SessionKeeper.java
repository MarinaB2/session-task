package com.sessiontask.demo.utils;

import com.sessiontask.demo.models.TheUser;

import java.util.HashSet;

public class SessionKeeper {
    private static SessionKeeper sessionKeeperInstance = null;

    private HashSet<String> validSessions = new HashSet<>();

    private TheUser userSession = new TheUser();

    public TheUser getUserSession() {
        return userSession;
    }

    public void addUserSession(TheUser user) { userSession = user; }

    public boolean checkSession(String session) {

        return validSessions.contains(session);
    }

    public void addSession(String session){

        validSessions.add(session);
    }

    public void RemoveSession(String session){
        if(validSessions.contains(session)) {
            validSessions.remove(session);
        }
    }

    // Singleton
    public static SessionKeeper getInstance(){
        if (sessionKeeperInstance == null){
            sessionKeeperInstance = new SessionKeeper();
        }

        return sessionKeeperInstance;
    }

}
