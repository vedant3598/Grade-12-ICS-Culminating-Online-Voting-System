package voting;

/**
 *
 * @author Vedant
 */
abstract class UserClass {

    //The following 11 variables are personal data of the voter.
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String gender;
    private String address;
    private String postalCode;
    private String city;
    private String provinceTerritory;
    private String phoneNumber;
    private String birthdate;
     
    //Constructor that constructs an empty UserClass object.
    public UserClass(){
        
    }
    
    //An abstract method that hashes the voter's information using SHA-256, a cryptographic hashing algorithm.
    public abstract String hash(String text);
    
    //An abstract method that sends the voter's information to the server.
    public abstract String sendUserInfo();
    
    public String toString(){
        return "Full Name: " + this.fullName + " " + this.username + " Password: " + this.password + " Email Address: " + this.email + " Gender: " + this.gender + " Home Address: " + this.address + " Postal Code: " + this.postalCode
                + " City: " + this.city + " Province/Territory: " + this.provinceTerritory + " Phone Number: " + this.phoneNumber + " Birthdate: " + this.birthdate;
    }
    
    
    /**
     * @return the firstName
     */
    public String getFullName(){
        return fullName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the lastName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
