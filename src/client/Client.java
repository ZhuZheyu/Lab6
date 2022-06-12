package client;

import client.Controller.UserController;
import client.UI.AppConsole;
import common.Exceptions.ConnectionErrorException;
import common.Exceptions.DeclaredLimitException;
import common.Interaction.Request;
import common.Interaction.Response;

import java.io.*;

public class Client {

    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserController userController;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public Client(String host, int port, int reconnectionTimeOut, int maxReconnectionAttempts, UserController userController) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeOut;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userController = userController;
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
                        AppConsole.printError("��������� ���������� ������� �����������!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        AppConsole.printError("����� �������� ����������� '" + reconnectionTimeout +
                                "' ��������� �� ��������� ��������� ��������!");
                        AppConsole.println("��������� ����������� ����� ����������� ����������.");
                    } catch (Exception timeoutException) {
                        AppConsole.printError("��������� ������ ��� ������� �������� �����������!");
                        AppConsole.println("��������� ����������� ����� ����������� ����������.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            AppConsole.println("������ ������� ������� ���������.");
        } catch (DeclaredLimitException exception) {
            AppConsole.printError("������ �� ����� ���� �������!");
        } catch (IOException exception) {
            AppConsole.printError("��������� ������ ��� ������� ��������� ���������� � ��������!");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, DeclaredLimitException {
        try {
            if (reconnectionAttempts >= 1) AppConsole.println("��������� ���������� � ��������...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            AppConsole.println("���������� � �������� ������� �����������.");
            AppConsole.println("�������� ���������� �� ����� �������...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            AppConsole.println("���������� �� ����� ������� ��������.");
        } catch (IllegalArgumentException exception) {
            AppConsole.printError("����� ������� ������ �����������!");
            throw new DeclaredLimitException();
        } catch (IOException exception) {
            AppConsole.printError("��������� ������ ��� ���������� � ��������!");
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
                requestToServer = serverResponse != null ? userController.handle(serverResponse.getResponseCode()) :
                        userController.handle(null);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                AppConsole.print(serverResponse.getResponseBody());
            } catch (InvalidClassException exception) {
                AppConsole.printError("��������� ������ ��� �������� ������ �� ������!");
            } catch (NotSerializableException exception) {
                System.out.println("�� ������� �������������");
            } catch (ClassNotFoundException exception) {
                AppConsole.printError("��������� ������ ��� ������ ���������� ������!");
            } catch (IOException exception) {
                AppConsole.printError("���������� � �������� ���������!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionErrorException | DeclaredLimitException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        AppConsole.println("������� �� ����� ���������������� �� �������.");
                    else AppConsole.println("���������� ��������� ������� �������.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }
}
