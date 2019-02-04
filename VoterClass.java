package voting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author Vedant
 */
public class VoterClass extends UserClass{

    //The following 11 variables are personal data of the voter.
    private String userName;
    private String fName;
    private String pass;
    private String emailAddress;
    private String gender;
    private String homeAddress;
    private String postalCode;
    private String city;
    private String provinceTerritory;
    private String phoneNumber;
    private String birthdate;
    
    //The following four variables are required to connect to the server.
    private static int port = 8888;
    private static String host = "172.20.10.3";
    private static DataInputStream dis;
    private static DataOutputStream dos;
    
    //Constructor that constructs an empty VoterClass object.
    public VoterClass(){
        
    }
    
    //Constructor that constructs a VoterClass object according to the specified parameters.
    public VoterClass(String username, String fullName, String password, String email, String g, String address, String postalCode, String city, String province_Territory, String pNumber, String birth){
        this.setUserName(username);
        this.setfName(fullName);
        this.setPass(password);
        this.setEmailAddress(email);
        this.setGender(g);
        this.setHomeAddress(address);
        this.setPostalCode(postalCode);
        this.setCity(city);
        this.setProvinceTerritory(province_Territory);
        this.setPhoneNumber(pNumber);
        this.setBirthdate(birth);
    }
    
    //The method below searches the IP address and port number of the host and connects to it
    public static Socket connectToServer(int port, String host) {
        Socket socket;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            socket = null;
        }
        return socket;
    }
    
    @Override
    //This method hashes the voter's information using SHA-256, a cryptographic hashing algorithm.
    public String hash(String text) {
        String hex = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            hex = String.format("%064x", new BigInteger(1, digest));
      
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hex;
    }

    //The method below sends the user's information to the server
    public String sendUserInfo() {
        // Send user information to server and return server feedback
        String serverFeedback = "";
        // Construct the message by hashing each piece of info and
        // concatenating them together
        String message = hash(this.userName) + " "
                         + hash(this.fName) + " "
                         + hash(this.pass) + " "
                         + this.emailAddress + " " // Store email in plaintext so we can send authentication code later
                         + hash(this.gender) + " "
                         + hash(this.homeAddress) + " "
                         + hash(this.city) + " "
                         + hash(this.postalCode) + " "
                         + hash(this.provinceTerritory) + " "
                         + hash(this.phoneNumber) + " "
                         + hash(this.birthdate);
        try (Socket socket = connectToServer(port, host)) {
            // Write message through socket, then read server feedback
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message);
            dos.flush();
            serverFeedback = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverFeedback;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the homeAddress
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * @param homeAddress the homeAddress to set
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the provinceTerritory
     */
    public String getProvinceTerritory() {
        return provinceTerritory;
    }

    /**
     * @param provinceTerritory the provinceTerritory to set
     */
    public void setProvinceTerritory(String provinceTerritory) {
        this.provinceTerritory = provinceTerritory;
    }  
    
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
