package voting;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Properties;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

/**
 *
 * @author Khatchig Anteblian & Vedant Shah
 */

public class Server {

    // Socket variables
    static Socket socket;
    static DataInputStream dis;
    static DataOutputStream dos;
    // Directory where database files are held
    static final String dbasePath = "../../Database/";
    // String arrays are used when counting votes and voter turnout
    static final String[] candidates = {hash("Justin Trudeau"), hash("Andrew Scheer"), 
                                        hash("Jagmeet Singh"), hash("Mario Beaulieu"), 
                                        hash("Elizabeth May"), hash("Maxime Bernier")};
    static final String[] gender = {hash("Male"), hash("Female"), hash("Other")};

    public static void main (String[] args) {
        String msgin = "";
        // Keep currentUser ID in a variable so server knows what file to
        // handle over multiple requests
        File currentUser = null;
        String code = "";
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            while (true) {
                System.out.println("Waiting for client...");
                socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                msgin = dis.readUTF();
                // Split message into array, and according to the length of the 
                // array, determine what kind of request it is and process it
                String[] inputData = msgin.split(" ");

                // Determine the type of request and process it
                if (inputData.length == 2) {
                    // Validate that user entered correct six digit code
                    if (inputData[0].equals("code")) {
                        if (inputData[1].equals(code)) {
                            // If user login is successful, check if user has
                            // already voted.
                            // If user has already voted, redirect to graphs 
                            // page, or else, continue to voting page.
                            String[] absPath = currentUser.getAbsolutePath().split("/");
                            String filePath = absPath[absPath.length - 1];
                            if (hasVoted(filePath)) {
                                String message = graphData();
                                try {
                                    dos.writeUTF(message);
                                    dos.flush();
                                    System.out.println("REDIRECT AOK!");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    dos.writeUTF("0");
                                    dos.flush();
                                    System.out.println("AUTH AOK!");
                                } catch(IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                dos.writeUTF("1");
                                dos.flush();
                                System.out.println("AUTH NOT OK!");
                            } catch(IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        // Validate username and password
                        if (validateLogin(inputData)) {
                            currentUser = new File(dbasePath + inputData[0]);
                            code = sendAuthCode(dbasePath + inputData[0]);
                            try {
                                dos.writeUTF("0");
                                dos.flush();
                                System.out.println("LOGIN AOK!");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                dos.writeUTF("1");
                                dos.flush();
                                System.out.println("LOGIN NOT OK!");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (inputData.length == 1) {
                    if (inputData[0].equals("count")) {
                        String message = graphData();
                        try {
                            dos.writeUTF(message);
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Write user vote into database
                        try {
                            writeToDbase(inputData, currentUser, 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // Validate user sign up info, then write to database
                    int inputValidation = validateUserInput(inputData);
                    currentUser = new File(dbasePath + inputData[0]);
                    if (inputValidation == 0) {
                        try {
                            writeToDbase(inputData, currentUser, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
    
                        dos.writeUTF("0");
                        dos.flush();
                        System.out.println("INPUT AOK!");
                    } else {
                        dos.writeUTF("" + inputValidation);
                        dos.flush();
                        System.out.println("INPUT NOT OK!");
                    }
                }
                socket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToDbase(String[] s, File f, int index) throws IOException {
        // Open a file (Create new if it doesn't exist) and write string array
        // into file
        FileWriter writer = new FileWriter(f, true);
        for (int i=index; i<s.length; i++) {
            writer.write(s[i]);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
        writer.close();
    }

    public static String hash(String text) {
        // SHA-256 hashing algorithm. Used to securely store information in the
        // database.
        String hex = "";
        try {
            // The algorithm mutates the string in an irreversable way 
            // making it impossible to get the original string from the returned
            // hash. This makes it ideal for securely storing information in a
            // database
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            hex = String.format("%064x", new BigInteger(1, digest));
      
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hex;
    }

    public static String[] collectLines(int lineNum) {
        // Go through database files and read the specified line number into 
        // an array and return that string array
        File directory = new File(dbasePath);
        String[] files = directory.list();
        String[] lines = new String[files.length];
        String line = "";
        for (int i=0; i<files.length; i++) {
            try {
                // get method reads specific line number from index 0.
                // to make the method user friendly, lineNum - 1 ensures that
                // the method can be called with the actual line number and return 
                // the desired line
                line = Files.readAllLines(Paths.get(dbasePath + files[i])).get(lineNum - 1);
            } catch (IOException e) {
                System.out.println("LineGet exception");
            }
            lines[i] = line;
        }
        return lines;
    }

    public static int[] countVotes(String[] votes, int[] voteCount, int index) {
        // Count the votes recursively going through array and returning an array 
        // of ints with each index representing the count for a different candidate 
        if (index == votes.length) {
            return voteCount;
        }
        
        for (int i=0; i<candidates.length; i++) {
            if (votes[index].equals(candidates[i])) {
                voteCount[i]++;
            }
        }
        return countVotes(votes, voteCount, index + 1);
    }

    public static int[] countTurnout(String[] turnout, int[] tCount, int index) {
        // Count the voter turnout for statistical purposes, to see the ratio
        // of different people voting
        if (index == turnout.length) {
            return tCount;
        }
        for (int i=0; i<gender.length; i++) {
            if (turnout[index].equals(gender[i])) {
                tCount[i]++;
            }
        }
        return countTurnout(turnout, tCount, index + 1);
    }

    public static String sendAuthCode(String recipient_dbPath) {
        Random rnd = new Random();
        int code = rnd.nextInt(900000) + 100000;

        // email ID of recipient
        String recipient = "";
        try {
            recipient = Files.readAllLines(Paths.get(recipient_dbPath)).get(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // email ID of  Sender. 
        String sender = "Maple_Leaf_Voting@gmail.com"; 
     
        // using host as localhost 
        String host = "127.0.0.1"; 
     
        // Getting system properties 
        Properties properties = System.getProperties(); 
     
        // Setting up mail server 
        properties.setProperty("mail.smtp.host", host); 
     
        // creating session object to get properties 
        Session session = Session.getDefaultInstance(properties); 
     
        try { 
            // MimeMessage object. 
            MimeMessage message = new MimeMessage(session); 
     
            // Set From Field: adding senders email to from field. 
            message.setFrom(new InternetAddress(sender)); 
     
            // Set To Field: adding recipient's email to from field. 
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); 
     
            // Set Subject: subject of the email 
            message.setSubject("Authentication Code"); 
     
            // set body of the email. 
            message.setText("Please enter the following authentication code into the field on your screen. \nThis code is for one time use only.\n\n" + code);
     
            // Send email. 
            Transport.send(message); 
            System.out.println("Mail successfully sent"); 
        } catch (MessagingException e) { 
            e.printStackTrace(); 
        }
        return Integer.toString(code);
    }

    public static String graphData() {
        String message = "";
        String[] votes = collectLines(11);
        String[] turnout = collectLines(4);
        int[] voteCount = {0, 0, 0, 0, 0, 0};
        int[] voterTurnout = {0, 0, 0};
        voteCount = countVotes(votes, voteCount, 0);
        voterTurnout = countTurnout(turnout, voterTurnout, 0);
        for (int i : voteCount) {
            message += (i + " ");
        }
        for (int i : voterTurnout) {
            message += (i + " ");
        }
        return message;
    }

    public static boolean hasVoted(String username) {
        String filePath = dbasePath + username;
        // Check number of lines in user database, if 11, then user has voted,
        // if 10, user hasn't voted yet
        int lines;
        try {
            lines = Files.readAllLines(Paths.get(filePath)).size();
            if (lines == 11) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public static boolean validateLogin(String[] input) {
        // Database file names are the usernames of people
        // To validate login, first check if enterd username exists in the
        // database. Then, read second line of file to compare validate password
        String filePath = dbasePath + input[0];
        File file = new File(filePath);
        boolean result = false;
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                // Password is on second line of files.
                // Discard first line of file.
                br.readLine();

                if (br.readLine().trim().equals(input[1])) {
                    result = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public static int validateUserInput(String[] input) {
        // Return val 0: User input is new and unique.
        // Return val 1: Username already taken.
        // Return val -1: Exact match with all input. Account already exists.
        // Return val 2: User info exists under a different username.
        File file = new File(dbasePath + input[0]);
        if (file.exists()) {
            if (accountExists(input)) {
                return -1;
            }
            return 1;
        } else {
            if (accountExists(input)) {
                return 2;
            }
            return 0;
        }
    }

    public static boolean accountExists(String[] input) {
        // Go through database and check for match with user input
        File directory = new File(dbasePath);
        String[] dbase_files = directory.list();
        for (String i : dbase_files) {
            // compareFiles method reads database file and compares each line
            // with a line of user input
            if (compareFiles(dbasePath + i, input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareFiles(String filePath, String[] input) {
        // Read file line by line and check for match with user input
        // Return true only if all lines match
        boolean match = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String fileContent = br.readLine();
            int lineNum = 1;
            // Read lines until end of file is reached
            // Compare each line of file with the same line in user input
            // As soon as a non matching line is found, return false and terminate
            while (fileContent != null) {
                if (!fileContent.equals(input[lineNum])) {
                    return match;
                } 
                lineNum++;
                fileContent = br.readLine();
            }
            match = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return match;
    }
}
