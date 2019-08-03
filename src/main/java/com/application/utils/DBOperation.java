package com.application.utils;

import com.application.infra.DBWriterClient;
import com.message.Message;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DBOperation {
    public static boolean executeDatabaseQuery(String dbName, String dbOperation, Message message){
        ClientManager clientManager = ClientManager.createClient();
        Session dbWriterSession = null;

        try {
            dbWriterSession = clientManager.connectToServer(DBWriterClient.class, new URI("ws://localhost:8027/ws/dbops/" +dbName+ "/" + dbOperation));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            dbWriterSession.getBasicRemote().sendObject(message);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        try {
            dbWriterSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
