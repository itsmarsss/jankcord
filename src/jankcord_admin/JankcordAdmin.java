package jankcord_admin;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import jankcord.objects.FullUser;
import jankcord.objects.Message;
import jankcord.objects.User;
import jankcord_admin.apihandlers.GetFriends;
import jankcord_admin.apihandlers.GetMessages;
import jankcord_admin.apihandlers.Login;
import jankcord_admin.apihandlers.MainPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.*;

public class JankcordAdmin {

    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<FullUser> accounts = new ArrayList<>();
    public static HashMap<String, ArrayList<Message>> conversations = new HashMap<>();
    public static String parent;

    public static void startAdmin() {
        System.out.println("Welcome to JankCord Admin Dashboard.");

        try {
            parent = new File(JankcordAdmin.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (Exception e) {
            System.out.println("Unable to obtain parent path.");
            System.exit(1);
        }

        System.out.println("Parent path: " + parent);

        while (true) {
            System.out.print(">> ");

            String cmdReq = sc.next();

            try {
                System.out.println(JankcordAdmin.class.getMethod(cmdReq).invoke(null));
            } catch (Exception e) {
                System.out.println("Command \"" + cmdReq + "\" is not recognized. Use 'help' for more commands.");
            }
        }
    }

    public static String readMessages(String fileName) {
        String textJSON = "";

        // Try to read file
        try {
            // Create BufferedReader to ready inventory.txt
            BufferedReader br = new BufferedReader(new FileReader(JankcordAdmin.parent + "/messages/" + fileName + ".json"));


            // Declare String line to be used later on
            String line;

            // While the read line is not empty
            while ((line = br.readLine()) != null) {
                textJSON += line + "\n";
            }
        } catch (Exception e) { // If error
            // Notify user that there was a reading error
            System.out.println("IO error reading.");
        }

        ArrayList<Message> messages = new ArrayList<>();

        try {
            // Parse the JSON string
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(textJSON);

            // Get the "messages" array from the JSON object
            JSONArray messagesArray = (JSONArray) jsonObject.get("messages");

            // Loop through the "messages" array
            for (Object message : messagesArray) {
                JSONObject messageObject = (JSONObject) message;

                // Read values from each message object
                long id = (Long) messageObject.get("id");
                String username = (String) messageObject.get("username");
                String avatarURL = (String) messageObject.get("avatarURL");
                String content = (String) messageObject.get("content");
                long timestamp = (Long) messageObject.get("timestamp");

                messages.add(new Message(new User(id, username, avatarURL), content, timestamp));
            }
        } catch (Exception e) {
        }

        conversations.put(fileName, messages);

        return textJSON;
    }

    public static String createAccount() {
        System.out.println("Creating new JankCord account for user...");
        System.out.print("Username (no spaces | 20 characters max): ");
        sc.nextLine();
        String username = sc.nextLine();
        if (username.contains(" ")) {
            return "Username cannot contain spaces; command sequence exited";
        }

        if (username.length() > 20) {
            return "Username cannot be longer than 20 character; command sequence exited";
        }


        System.out.print("Password (no spaces | 20 characters max): ");
        String password = sc.nextLine();
        if (password.contains(" ")) {
            return "Password cannot contain spaces; command sequence exited";
        }

        if (password.length() > 20) {
            return "Password cannot be longer than 20 character; command sequence exited";
        }

        for (FullUser account : accounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return "Username already in use; command sequence exited";
            }
        }

        if (accounts.isEmpty()) {
            accounts.add(new FullUser(0, username, "N/A", password, "", "active"));
        } else {
            long id = accounts.get(accounts.size() - 1).getId() + 1;
            accounts.add(new FullUser(id, username, "N/A", password, "", "active"));
        }

        return "Account \"" + username + "\" with password \"" + password + "\" created successfully";
    }

    public static String viewAccounts() {
        if (accounts.isEmpty()) {
            return "Empty list.";
        }

        System.out.printf("%-10.10s | %-25.25s | %-25.25s | %-25.25s%n", "ID #", "USERNAME", "PASSWORD", "STATUS");
        System.out.println("---------------------------------------------------------------------------------");
        for (FullUser account : accounts) {
            System.out.printf("%-10.10s | %-25.25s | %-25.25s | %-25.25s%n", account.getId(), account.getUsername(), account.getPassword(), account.getStatus());
        }

        return "End of list.";
    }

    public static String deleteAccount() {
        System.out.print("Account ID #: ");
        String idString = sc.next();
        int idNum;

        try {
            idNum = Integer.parseInt(idString);
        } catch (Exception e) {
            return "Invalid ID #; command sequence exited";
        }

        int index = searchForAccount(idNum, 0, accounts.size() - 1);

        if (index == -1) {
            return "ID # not found.";
        }

        accounts.remove(index);

        return "Account ID [" + idNum + "] has been deleted.";
    }

    public static String editAccount() {
        System.out.print("Account ID #: ");
        String idString = sc.next();
        int idNum;

        try {
            idNum = Integer.parseInt(idString);
        } catch (Exception e) {
            return "Invalid ID #; command sequence exited";
        }

        int index = searchForAccount(idNum, 0, accounts.size() - 1);

        if (index == -1) {
            return "ID # not found.";
        }

        FullUser user = accounts.get(index);


        System.out.print("Username [" + user.getUsername() + "]: ");
        sc.nextLine();
        String username = sc.nextLine();
        if (username.contains(" ")) {
            return "Username cannot contain spaces; command sequence exited";
        }

        if (username.length() > 20) {
            return "Username cannot be longer than 20 character; command sequence exited";
        }


        System.out.print("Password [" + user.getPassword() + "]: ");
        String password = sc.nextLine();
        if (password.contains(" ")) {
            return "Password cannot contain spaces; command sequence exited";
        }

        if (password.length() > 20) {
            return "Password cannot be longer than 20 character; command sequence exited";
        }

        for (FullUser account : accounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return "Username already in use; command sequence exited";
            }
        }

        accounts.get(index).setUsername(username);
        accounts.get(index).setPassword(password);

        return "Account ID [" + idNum + "] now has username \"" + username + "\" and password \"" + password + "\".";
    }

    public static String setAccountStatus() {
        System.out.print("Account ID #: ");
        String idString = sc.next();
        int idNum;

        try {
            idNum = Integer.parseInt(idString);
        } catch (Exception e) {
            return "Invalid ID #; command sequence exited";
        }

        int index = searchForAccount(idNum, 0, accounts.size() - 1);

        if (index == -1) {
            return "ID # not found.";
        }

        System.out.print("Status (10 characters max): ");
        String status = sc.next();

        if (status.length() > 10) {
            return "Status cannot be longer than 10 character; command sequence exited";
        }

        accounts.get(index).setStatus(status);

        return "Account ID [" + idNum + "] new has status \"" + status + "\".";
    }


    private static int port;
    private static HttpServer server;


    public static String startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", 0), 0);
        } catch (IOException e) {
            return "JankCord server failed to start.";
        }
        server.createContext("/", new MainPage());
        server.createContext("/api/v1/login", new Login());
        server.createContext("/api/v1/getmessages", new GetMessages());
        server.createContext("/api/v1/getfriends", new GetFriends());
        server.setExecutor(null);
        server.start();

        port = server.getAddress().getPort();

        System.out.println(port);

        return "JankCord server started.";
    }

    public static String stopServer() {
        server.stop(0);
        return "JankCord server stopped.";
    }

    private static int searchForAccount(long idNum, int leftPoint, int rightPoint) {
        // Check if left and right point have converged
        if (leftPoint > rightPoint) { // If converged
            // Return null, no matching found
            return -1;
        }

        // Set a middle "pivot" point, a point of comparison
        int middle = (leftPoint + rightPoint) / 2;

        // Get the middle's value
        long current = accounts.get(middle).getId();

        // Check if it is what we're looking for
        if (current == idNum) { // if equal
            // Return title
            return middle;
        }

        // Check if current is larger or smaller
        if (current > idNum) { // If current reference number is larger
            // Use recursion; search left of middle, so leftPoint to middle - 1
            return searchForAccount(idNum, leftPoint, middle - 1);
        } else { // If current reference number is smaller
            // Use recursion; search right of middle, so middle + 1 to rightPoint
            return searchForAccount(idNum, middle + 1, rightPoint);
        }
    }

    public static boolean authorized(HttpExchange exchange) {
        Map<String, List<String>> requestHeaders = exchange.getRequestHeaders();

        String username = requestHeaders.get("username").get(0);
        String password = requestHeaders.get("password").get(0);

        for(FullUser account : JankcordAdmin.accounts) {
            if(account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }
}