package com.application.maintrader;

import com.application.utils.Messages;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalTime;
import java.util.Scanner;

public class StartTradingClient {

    public static Logger clientLogger = Logger.getLogger(StartTradingClient.class);

    public StartTradingClient(){
        try {
            String filePath = "E:\\LinkedLerrning\\TradingEngine\\src\\main\\resources\\clientlogger.properties";
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            PropertyConfigurator.configure(filePath);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Exception : " +e.toString());
        }
        StartTradingClient.clientLogger.debug("Started logging service for client");
    }

    public static void main (String[] args) throws Exception{
        StartTradingClient.clientLogger.debug("Starting trading client");
        ClientManager clientManager = ClientManager.createClient();
        String stock, side, client;
        String price;
        String qty;
        String cont;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Smart trading engine of the country");
        System.out.println("Enter your username:");
        String user = scanner.nextLine();
        System.out.println("Enter the trading exchange:");
        String tradingExch = scanner.nextLine();
        StartTradingClient.clientLogger.info("Client "+user+ " is attempting to login...");
        Session session = null;
        try {
            session = clientManager.connectToServer(TradingClient.class, new URI("ws://localhost:8025/ws/chat/"+ tradingExch + "/" + user));
        }catch (Exception e){
            StartTradingClient.clientLogger.fatal("Login failed: " + e.getStackTrace().toString());
        }
        StartTradingClient.clientLogger.info(user +" logged in to exchange " +tradingExch);

        System.out.println("Your are logged in as user : " +user);
        do {
            System.out.println("Enter stock to trade:");
            stock = scanner.nextLine();
            System.out.println("Enter side for which to trade (1-Buy, 2-Sell, 3-ShortSell):");
            side = scanner.nextLine();
            System.out.println("Client ID:");
            client = scanner.nextLine();
            System.out.println("Stock price:");
            price = scanner.nextLine();
            System.out.println("Qty:");
            qty = scanner.nextLine();
            //String stock, String side, String exch, float price, int qty, String client, String received
            session.getBasicRemote().sendObject(Messages.objectify(stock, side, tradingExch, price, qty, client, (LocalTime.now().toString())));
            StartTradingClient.clientLogger.info("OrderPlaced:: stock:"+stock+" side:"+side+" tradingExchange:"+tradingExch
            +" price:"+price+" qty:"+qty+" client:"+client);

            System.out.println("Next order (yes/no):");
            cont = scanner.nextLine();
            if(cont.equalsIgnoreCase("no")){
               StartTradingClient.clientLogger.info(user +" have requested for logout from exchange " +tradingExch);
            }
        }while (!cont.equalsIgnoreCase("no"));

        session.close();
    }
}
