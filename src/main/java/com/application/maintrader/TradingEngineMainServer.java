package com.application.maintrader;

import com.application.domain.Room;
import com.application.infra.DBWriterClient;
import com.application.utils.DBOperation;
import com.application.utils.Messages;
import com.message.Message;
import com.message.decoder.MessageDecoder;
import com.message.encoder.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.Date;

@ServerEndpoint(value = "/chat/{tradingExchange}/{userName}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class TradingEngineMainServer {
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

//    public static final Logger log = Logger.getLogger(TradingEngineMainServer.class.getSimpleName());

    private static final Map<String, Room> tradingRooms = Collections.synchronizedMap(new HashMap<String, Room>());

    private static final String[] tradingExchanges = {"NSE", "BSE"};

/*
    @PostConstruct
    public TradingEngineMainServer(){
        try {
            String filePath = "";
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            PropertyConfigurator.configure(filePath);
            fileInputStream.close();
            log.info("Wow! I'm configured!");
        } catch (IOException e) {
            System.out.println("Exception : " +e.toString());
        }
    }

*/


    @OnOpen
    public void onOpen(Session session, @PathParam("tradingExchange") final String tradingExchange, @PathParam("userName") final String userName) throws IOException, EncodeException {
        Arrays.stream(tradingExchanges).forEach(roomNam -> tradingRooms.computeIfAbsent(roomNam, key -> new Room(roomNam)));
        session.setMaxIdleTimeout(5*60*1000);
        session.getUserProperties().putIfAbsent("tradingExchange", tradingExchange);
        session.getUserProperties().putIfAbsent("userName", userName);
        StartTradingServer.log.info(userName + " is attempting to login with exchange "+tradingExchange);
        Room room = tradingRooms.get(tradingExchange);
        room.join(session);
        System.out.println(tradingRooms.get(tradingExchange));
        System.out.println(userName +" joined the chat room "+session.getId());
        StartTradingServer.log.info(userName + " logged in to exchange " +tradingExchange);
        peers.add(session);
        session.getBasicRemote().sendObject(Messages.objectify(Messages.WELCOME_MESSAGE));
        //log.info(userName + " logged in to exchange " +tradingExchange);

        //StartTradingServer.log.info(userName + " logged in to exchange " +tradingExchange);

    }

    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException {

        System.out.println("This is for test");
        StartTradingServer.log.info("Order inserted with details client: " + message.getClient()+ ", stock: " +message.getStock()
                + ", price: " +message.getPrice()+ ", Qty: " +message.getQty()+ ", side: " +message.getSide());

        /*for(Session peer : peers){
            if(!session.getId().equals(peer.getId())){
                peer.getBasicRemote().sendObject(message);
                }
        }

        */
        boolean dbStatus = DBOperation.executeDatabaseQuery("MyTradingDB", "insert", message);
        if (dbStatus == true) {
            StartTradingServer.log.info("Message sent to database");
        } else {
            StartTradingServer.log.fatal("Problem in sending message to database");
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(session.getId()+" left the chat room due to some reasons");
        for (Session peer : peers){
            String leavingUser = (String) session.getUserProperties().get("userName");
            Message message = new Message(leavingUser + " have left the chat", leavingUser);
            message.setClient("TradingServer");
            //message.setStock(format("%s have left the chat room", (String) session.getUserProperties().get("User")));
            message.setReceived((new Date()).toString());
            peer.getBasicRemote().sendObject(message);
        }



        System.out.println("test message");
        String leavingUser = (String) session.getUserProperties().get("userName");
        StartTradingServer.log.info(leavingUser +" have left the trading activity");
        peers.remove(session);
    }
}
