package com.application.maintrader;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class StartTradingServer {

    public static final Logger log = Logger.getLogger(TradingEngineMainServer.class.getSimpleName());

    public StartTradingServer(){
        try {
            String filePath = "E:\\LinkedLerrning\\TradingEngine\\src\\main\\resources\\log4j.properties";
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            PropertyConfigurator.configure(filePath);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Exception : " +e.toString());
        }
    }

    public static void main(String [] args){
        Server server = new Server("localhost", 8025, "/ws", null, TradingEngineMainServer.class);
        try {
            StartTradingServer.log.debug("Logging service started");
            server.start();
            StartTradingServer.log.info("TradeEngine started...");
            System.out.println("Press a button to exit server");
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            StartTradingServer.log.info("Stopping TradeEngine");
            server.stop();
        }
    }
}
