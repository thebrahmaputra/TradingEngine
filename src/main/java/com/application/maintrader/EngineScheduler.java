package com.application.maintrader;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class EngineScheduler {

    @Schedule(minute = "*/20", hour = "*")
    private void interrupt() {
        //ChatServerEndpoint.getRooms().forEach((s, room) -> room.sendMessage(Messages.objectify("Hello from duke bot")));
    }
}
