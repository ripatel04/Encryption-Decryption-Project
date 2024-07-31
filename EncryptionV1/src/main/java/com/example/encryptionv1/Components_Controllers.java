//----------------------------------------------------------------------------------------------------
/////=CMPG215 P - Group 40=/////
//Anderson Brian, 40851842
//Bezuidenhout Nicolette, 33770182
//Coetzee Christian, 40513262
//Patel Riya, 41914228
//Ramsunaar Angelina, 41081269
//------------------------------------------------------------------------------------------------------
////BLOWFISH mostly referenced from https://www.knowledgefactory.net/2019/10/java-blowfish-encryption-and-decryption-with-example.html////
package com.example.encryptionv1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
///Blowfish///
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
///Blowfish/////
import java.net.URL;
import java.util.ResourceBundle;
///OWN ALGORITHM///
import java.nio.file.*;
import java.security.MessageDigest;


public class Components_Controllers implements Initializable{
    @FXML
    //encryption methods can be removed or added as one pleases through editing this array.
    //Otyher variables and components are declared.
    private String[] encryption_options = {"Blowfish", "Own algorithm (includes hash for password)"};
    private String  file_Path, file_Name, file_Format, options, enc_output, password;
    @FXML
    private Label lblEncryptResult;                                                                       //Label component declared to make 'connection between GUI(SceneBuilder) and Java'
    @FXML
    private Label lblDecryptedResult;
    @FXML
    private Label lblPath;
    @FXML
    private TextField txtFileKey;
    @FXML
    private TextField txtPassKey;
    @FXML
    private TextField txtPassword;
    @FXML
    private ChoiceBox cbxChoose;                                                                                        //ChoiceBox component declared to make 'connection' between GUI(SceneBuilder) and Java
    private FileChooser file_chosen;                                                                                    //This enables the user to choose which file they choose to encrypt/ decrypt
    private File filePathway, selectedFile, sampleFile, encryptedFile, decryptedFile;                                                //File variables declared to enable the user's ability to choose what file they can use

    ///BLOWFISH - Consistent variable declared for Blowfish and ease of use///
    private static final String ALGORITHM = "Blowfish";                                                                       //String constant variable used for clarification and eases the use of repeating double quotation marks
    private String pass_key, file_key;

    ///OWN ALGORITHM///
    int shift = 3;
    String plainPassword = "password";
    String hashedPassword = hashPassword(plainPassword);


    ///This event is for the "Browse" button, this enables the user to browse for a file to encrypt/ decrypt
    //* FileChooser object instantiated
    //* FileChooser's window is then given a title and given a default directory to start searching from.
    //* The selectedFile variable is then assigned with whatever file user chooses
    //* The selectedFile variable is then checked if it is null, if not. A filepath, file name and file format is received with a label showing the directory
    //* else if it is null, nothing would be assigned
    @FXML
    protected void onBrowseButtonClick()
    {
        file_chosen = new FileChooser();
        file_chosen.setTitle("Open source file");
        file_chosen.setInitialDirectory(new File(System.getProperty("user.home")));
        selectedFile = file_chosen.showOpenDialog(null);

        if (selectedFile != null)
        {
            filePathway = new File(selectedFile.getPath());
            file_Path = filePathway.getPath().toString();
            file_Name = filePathway.getName();
            file_Format = file_Name.substring(file_Name.lastIndexOf(".") + 1);
            lblPath.setText(file_Path);//
        }
    }

    ///OWN ALGORITHM///
    public static byte[] readFileAsBinary(String filename) throws IOException
    {
        return Files.readAllBytes(Paths.get(filename));
    }
    ///OWN ALGORITHM///
    public static void writeBinaryFile(String filename, byte[] data) throws IOException
    {
        Files.write(Paths.get(filename), data);
    }
    ///OWN ALGORITHM///
    public static byte[] reverseCaesarCipher(byte[] inputBytes, int shift) {
        byte[] result = new byte[inputBytes.length];
        for (int i = 0; i < inputBytes.length; i++) {
            result[i] = (byte) ((inputBytes[i] - shift + 256) % 256);
        }
        return result;
    }
    ///OWN ALGORITHM///
    public static String hashPassword(String password)
    {
        try {
            MessageDigest Md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = Md.digest(password.getBytes());
            StringBuilder sbr = new StringBuilder();
            for (byte bt : hashBytes) {
                sbr.append(String.format("%02x", bt));
            }
            return sbr.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }


    ///BLOWFISH///
    //Encryption/ decryption of files
    public static void doCrypto(int cipherMode, File sampleFile, File outputFile, String file_key) throws Exception
    {
        Key secretKey = new SecretKeySpec(file_key.getBytes(), ALGORITHM);
        Cipher cipher_two = Cipher.getInstance(ALGORITHM);
        cipher_two.init(cipherMode, secretKey);

        InputStream inputStreamFile = new FileInputStream(sampleFile);
        byte[] inputBytes = new byte[(int) sampleFile.length()];
        inputStreamFile.read(inputBytes);

        byte[] outputBytes = cipher_two.doFinal(inputBytes);

        OutputStream outputStreamFile = new FileOutputStream(outputFile);
        outputStreamFile.write(outputBytes);

        inputStreamFile.close();
        outputStreamFile.close();
    }

    ///BLOWFISH///
    //Decrypts files from input (encrypted files)
    public static void encrypt_file(File sampleFile, File outputFile, String file_key) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, sampleFile, outputFile, file_key);
    }

    ///BLOWFISH///
    //Decrypts files from input (encrypted files)
    public static void decrypt_file(File sampleFile, File outputFile, String file_key) throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, sampleFile, outputFile, file_key);
    }

    ///BLOWFISH///
    //String method that returns encrypted text from original text received.
    //A key is also received
    public String encrypt(String password, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] KeyData = key.getBytes();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        Cipher cipher_one = Cipher.getInstance("Blowfish");
        cipher_one.init(Cipher.ENCRYPT_MODE, KS);
        String encryptedtext = Base64.getEncoder().encodeToString(cipher_one.doFinal(password.getBytes("UTF-8")));
        return encryptedtext;
    }

    ///BLOWFISH///
    //String method that returns decrypted text from encrypted text received.
    //A key is also received
    public String decrypt(String encryptedtext, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] KeyData = key.getBytes();
        SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
        byte[] encryptedTextToBytes = Base64.getDecoder().decode(encryptedtext);
        Cipher cipher_one = Cipher.getInstance("Blowfish");
        cipher_one.init(Cipher.DECRYPT_MODE, KS);
        byte[] decrypted = cipher_one.doFinal(encryptedTextToBytes);
        String decryptedString = new String(decrypted, Charset.forName("UTF-8"));
        return decryptedString;
    }

    //Button that encrypts the password with key or hash depending on encryption method chosen
    @FXML
    protected void onEncryptButtonPassClick() throws Exception
    {
        options = cbxChoose.getValue().toString();
        pass_key = txtPassKey.getText();
        password = txtPassword.getText();

        if (options == encryption_options[0])
        {
            ///BLOWFISH///
            // instantiates object class to encrypt the original text(password) and gives appropriate output on label
            Components_Controllers obj_encrypt = new Components_Controllers();
            enc_output = obj_encrypt.encrypt(password, pass_key);
            lblEncryptResult.setText(enc_output);
            System.out.println("Password encrypted with " + ALGORITHM);

        }
        else if (options == encryption_options[1])
        {
            //Own algorithm
            //Hashes password and gives result in label
            lblEncryptResult.setText(hashPassword(password));
            System.out.println("Password Hashed");
        }
    }

    //Button that decrypts the password with a key depending on encryption method chosen
    @FXML
    protected void onDecryptButtonPassClick() throws Exception
    {
        options = cbxChoose.getValue().toString();

        if (options == encryption_options[0])
        {
            ///BLOWFISH///
            // instantiates object class to decrypt the encrypted text(password) and gives appropriate output on label
            Components_Controllers obj_decrypt = new Components_Controllers();
            lblDecryptedResult.setText(obj_decrypt.decrypt(enc_output, pass_key));
            System.out.println("Password decrypted with " + ALGORITHM);

        }
    }

    //Button that encrypts file
    @FXML
    protected void onEncryptButtonFileClick() throws Exception
    {
        options = cbxChoose.getValue().toString();

        if (options == encryption_options[0])
        {
            ///BLOWFISH///
            // receives original file from user (browse button) and gets key from textbox/textfield
            sampleFile = filePathway;
            encryptedFile = new File(file_Path + file_Name + ".encrypted");
            file_key = txtFileKey.getText();

            //try catch block to give appropriate error if file couldn't be encrypted
            //Message displayed if file is decrypted and with what encryption method used
            try
            {
                Components_Controllers.encrypt_file(sampleFile, encryptedFile, file_key);
                System.out.println("File Encrypted with " + ALGORITHM);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (options == encryption_options[1])
        {
            ///Own algorithm
            // Encrypts file
            sampleFile = new File(file_Name);
            byte[] inputData = readFileAsBinary(String.valueOf(sampleFile));
            byte[] encryptedData = reverseCaesarCipher(inputData, shift);
            writeBinaryFile("Encrypted_" + file_Name, encryptedData);
            System.out.println("File Encrypted with own algorithm.");

            //Removes original file after encryption thereof
            try
            {
                Files.delete(Paths.get(file_Path));
                System.out.println("Original file removed");
            }
            catch (Exception e)
            {
                System.out.println("File was unable to be removed");
            }
        }
    }

    //Button that decrypts Files
    @FXML
    protected void onDecryptButtonFileClick() throws Exception
    {
        options = cbxChoose.getValue().toString();
        if (options == encryption_options[0])
        {
            ///BLOWFISH///
            // receives encrypted file from user (browse button) and gets key from textbox/textfield
            decryptedFile = new File(file_Path + file_Name + "_decrypted." + file_Format);
            file_key = txtFileKey.getText();

            //try catch block to give appropriate error if file couldn't be decrypted
            //Message displayed if file is decrypted and with what encryption method used
            try
            {
                Components_Controllers.decrypt_file(encryptedFile, decryptedFile, file_key);
                System.out.println("File Decrypted with " + ALGORITHM);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (options == encryption_options[1])
        {
            ///OWN ALGORITHM///
            // Decrypts file
            byte[] encryptedInputData = readFileAsBinary(file_Name);
            byte[] decryptedData = reverseCaesarCipher(encryptedInputData, -shift);
            writeBinaryFile("Decrypted_" + file_Name, decryptedData);
            System.out.println("File Decrypted with own algorithm");

        }
    }

    ///Exits application with return value of 1///
    @FXML
    protected void onExitButtonClick()
    {
        System.exit(1);
    }

    ///ChoiceBox is populated when application starts///
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        cbxChoose.getItems().addAll(encryption_options);
    }
}
