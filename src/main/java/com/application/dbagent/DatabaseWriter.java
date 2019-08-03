package com.application.dbagent;

import com.message.Message;
import com.message.decoder.MessageDecoder;
import com.message.encoder.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

@ServerEndpoint(value = "/dbops/{database}/{orderAction}" ,encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class DatabaseWriter {

   private String databaseName, orderAction;

    @OnOpen
    public void onDatabaseWriterOpen(Session session, @PathParam("database") final String dbName, @PathParam("orderAction") final String clientDBAction){
            databaseName = dbName;
            orderAction = clientDBAction;
    }

    @OnMessage
    public void onMessage(Message message, Session session) {
       if (orderAction.equalsIgnoreCase("insert")){

           try {

               // create a mysql database connection
               String myDriver = "com.mysql.jdbc.Driver";
               String myUrl = "jdbc:mysql://localhost:3306/"+databaseName;
               StartDatabaseWriter.dblogger.debug("Writing to database...");
               StartDatabaseWriter.dblogger.info("Database Name:"+databaseName);
               /*Class.forName(myDriver);
               Connection conn = DriverManager.getConnection(myUrl, "trEngine", "trade#123");

               // create a sql date object so we can use it in our INSERT statement
               Calendar calendar = Calendar.getInstance();
               java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

               Date tm = new Date();

               // the mysql insert statement
               String query = "insert into MyTradingDB.ALL_ORDERS (Stock, Side, Price, Client, Quantity, CREATETIME, OrderState, Exchange)"
                       + " values (" + message.getStock() + ", " + message.getSide() + ", " + message.getPrice() + ", "
                       + message.getClient() + ", " + message.getQty() + "," + startDate + ", Accepted, " + message.getExch() + ");";

               String qry = "SELECT * FROM MyTradingDB.ALL_ORDERS";
               Statement statement = conn.createStatement();
               // create the mysql insert preparedstatement
               PreparedStatement preparedStmt = conn.prepareStatement(query);
               preparedStmt.setString(2, message.getStock());
               preparedStmt.setString(3, message.getSide());
               preparedStmt.setFloat(4, Float.parseFloat(message.getPrice()));
               preparedStmt.setString(5, message.getClient());
               preparedStmt.setInt(6, Integer.parseInt(message.getQty()));
               preparedStmt.setDate(7, startDate);
               preparedStmt.setString(8, "Accepted");
               preparedStmt.setString(5, message.getExch());
               // execute the preparedstatement
               //           preparedStmt.executeQuery();
               ResultSet rs = statement.executeQuery(qry);
               System.out.println(rs);


            conn.close();*/
           } catch (Exception e) {
               System.out.println(e.toString());
           }
       }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
            System.out.println("Completed " +orderAction+ " on database...");
    }
}
