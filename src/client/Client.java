package client;

import client.UI.AppConsole;
import common.Exceptions.ConnectionErrorException;
import common.Exceptions.DeclaredLimitException;

import java.io.*;

public class Client {

    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public Client(String host, int port, int reconnectionTimeOut, int maxReconnectionAttempts, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeOut;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }
    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        AppConsole.printError("Превышено количество попыток подключения!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        AppConsole.printError("Время ожидания подключения '" + reconnectionTimeout +
                                "' находится за пределами возможных значений!");
                        AppConsole.println("Повторное подключение будет произведено немедленно.");
                    } catch (Exception timeoutException) {
                        AppConsole.printError("Произошла ошибка при попытке ожидания подключения!");
                        AppConsole.println("Повторное подключение будет произведено немедленно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            AppConsole.println("Работа клиента успешно завершена.");
        } catch (DeclaredLimitException exception) {
            AppConsole.printError("Клиент не может быть запущен!");
        } catch (IOException exception) {
            AppConsole.printError("Произошла ошибка при попытке завершить соединение с сервером!");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, DeclaredLimitException {
        try {
            if (reconnectionAttempts >= 1) AppConsole.println("Повторное соединение с сервером...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            AppConsole.println("Соединение с сервером успешно установлено.");
            AppConsole.println("Ожидание разрешения на обмен данными...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            AppConsole.println("Разрешение на обмен данными получено.");
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("Адрес сервера введен некорректно!");
            throw new DeclaredLimitException();
        } catch (IOException exception) {
            AppConsole.printError("Произошла ошибка при соединении с сервером!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Server request process.
     */
    private boolean processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                OutputDeliver.print(serverResponse.getResponseBody());
            } catch (InvalidClassException exception) {
                OutputDeliver.printError("Произошла ошибка при отправке данных на сервер!");
            } catch (NotSerializableException exception) {
                System.out.println("Не удалось сериализовать");
            } catch (ClassNotFoundException exception) {
                OutputDeliver.printError("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                OutputDeliver.printError("Соединение с сервером разорвано!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionErrorException | DeclaredLimitException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        OutputDeliver.println("Команда не будет зарегистрирована на сервере.");
                    else OutputDeliver.println("Попробуйте повторить команду позднее.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }
}
