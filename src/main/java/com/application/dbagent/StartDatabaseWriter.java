package com.application.dbagent;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class StartDatabaseWriter {
    public static Logger dblogger = Logger.getLogger(StartDatabaseWriter.class);
    public StartDatabaseWriter(){
        try {
            String filePath = "E:\\LinkedLerrning\\TradingEngine\\src\\main\\java\\com\\application\\dbagent\\log4j.properties";
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            PropertyConfigurator.configure(filePath);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Exception : " +e.toString());
        }
    }

    public static void main(String args[]){
        StartDatabaseWriter.dblogger.debug("Starting database writer process");
        Server server = new Server("localhost", 8027, "/ws", null, DatabaseWriter.class);
        try {
            StartDatabaseWriter.dblogger.info("DBServer obj initialized" +server.toString());
            server.start();
            StartDatabaseWriter.dblogger.info("Database writer started...");
        } catch (DeploymentException e) {
            e.printStackTrace();
            StartDatabaseWriter.dblogger.fatal("Problem in starting database writer...");
        }
        System.out.println("Press [Enter] to stop server...");
        new Scanner(System.in).nextLine();
        StartDatabaseWriter.dblogger.info("Database writer stopped...");
    }
}
