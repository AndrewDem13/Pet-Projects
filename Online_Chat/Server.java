package com.javarush.test.level30.lesson15.big01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

public class Server
{
    private static Map<String, Connection> connectionMap = new java.util.concurrent.ConcurrentHashMap<>();

    private static class Handler extends Thread
    {
        private Socket socket;

        private Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            String userName = null;
            try (Connection connection = new Connection(socket))
            {
                SocketAddress socketAddress = connection.getRemoteSocketAddress();
                ConsoleHelper.writeMessage("Установлено новое соединение с удаленным адресом: " + socketAddress);

                userName = serverHandshake(connection);

                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));

                sendListOfUsers(connection, userName);

                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка обмена данных с удаленным адресом");
            }

            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }

            ConsoleHelper.writeMessage("Закрыто соединение с удаленным адресом");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException
        {
            while (true)
            {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message received = connection.receive();
                if (received.getType() == MessageType.USER_NAME)
                {
                    String name = received.getData();
                    if (!name.isEmpty() && !connectionMap.containsKey(name))
                    {
                        connectionMap.put(name, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        return name;
                    }
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException
        {
            for (String clientName : connectionMap.keySet()) {
                if (!clientName.equals(userName))
                    connection.send(new Message(MessageType.USER_ADDED, clientName));
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException
        {
            while (true)
            {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT)
                {
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + message.getData()));
                }
                else
                    ConsoleHelper.writeMessage(
                            String.format("Ошибка! Недопустимый тип сообщения (MessageType.%s) от клиента: %s",
                                    message.getType().toString() ,userName)
                    );
            }
        }
    }

    public static void sendBroadcastMessage(Message message)
    {
        for (Map.Entry<String, Connection> entry : connectionMap.entrySet())
        {
            try
            {
                entry.getValue().send(message);
            }
            catch (IOException e)
            {
                ConsoleHelper.writeMessage("Не могу отправить сообщение клиенту с именем: " + entry.getKey());
            }
        }
    }

    public static void main(String[] args)
    {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }
}
