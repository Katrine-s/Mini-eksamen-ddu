import processing.core.PApplet;
import processing.data.Table;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Gokkenet extends PApplet {
    
    LoginSide ls;
    String testUser = "Albert" , testPassword = "Abe123";
    long userId;
    BattelSequens bs;
    Table questions;

    private String databaseURL = "jdbc:ucanaccess://src//main//java//resources//database.accdb";
    private Connection connection = null;

    public static void main(String[] args) {
        PApplet.main("Gokkenet");
    }

    public Gokkenet() {
        try {
            connection = DriverManager.getConnection(databaseURL);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        println("Connected to MS Access database. ");
    }

    @Override
    public void settings() {
        size(500,500);
    }

    @Override
    public void setup() {
        questions = loadTable("sp.csv");
        ls = new LoginSide(this);
        bs = new BattelSequens(this, questions);


    }

    @Override
    public void draw() {

        clear();

        background(200);
        if(ls.visible){
        ls.drawSide();

        }else{
            bs.drawBattel();
        }

        if (ls.visible && ls.btnLogin.klikket == true) {
            login();
        }


    }

    @Override
    public void keyTyped() {
        if(ls.visible){
            ls.typede(key);
        }



    }

    @Override
    public void mouseClicked() {
        if (ls.visible) {
            ls.clik(mouseX, mouseY);
        }
        bs.clicked(mouseX,mouseY);

    }
    public String getHash(String passwordToHash){

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    private void login() {
        ls.btnLogin.registrerRelease();
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet rsUser = s.executeQuery("SELECT [username],[password], [userID] FROM [user]");

            while (rsUser.next()) {
                String rsUsername = rsUser.getString(1);
                String rsPassword = rsUser.getString(2);

                System.out.println(rsUsername);
                System.out.println(rsPassword);
                System.out.println("");

                if (ls.userName.indput.equals(rsUsername) && getHash(ls.password.indput).equals(rsPassword)
                        || testUser.equals(rsUsername) && getHash(testPassword).equals(rsPassword) ) {
                    ls.visible = false;
                    bs.visibal = true;
                    ls.password.klikket = false;
                    userId = rsUser.getLong(3);
                }
            }
        } catch (SQLException throwable) {
                throwable.printStackTrace();
        }
    }




}
