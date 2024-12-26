// import java.awt.*;
// import java.awt.event.*;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.Scanner;
// import javax.swing.*;

// public class runIt extends JFrame implements ActionListener {

//     // UI Components
//     private JButton encryptButton, decryptButton;
//     private JLabel statusLabel;
//     private JFileChooser fileChooser;

//     // PRESENT parameters
//     private static final long KEY = 0x0123456789ABCDEFL; // 80-bit key (example)
//     private static final int ROUNDS = 31;

//     // Constructor
//     public runIt() {
//         setTitle("CipherGuard: File Encryption and Decryption");
//         setSize(400, 200);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());

//         // Initialize components
//         encryptButton = new JButton("Encrypt File");
//         decryptButton = new JButton("Decrypt File");
//         statusLabel = new JLabel("Select a file to encrypt or decrypt.", SwingConstants.CENTER);
//         fileChooser = new JFileChooser();

//         // Add action listeners
//         encryptButton.addActionListener(this);
//         decryptButton.addActionListener(this);

//         // Add components to the frame
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(encryptButton);
//         buttonPanel.add(decryptButton);
//         add(buttonPanel, BorderLayout.CENTER);
//         add(statusLabel, BorderLayout.SOUTH);

//         setVisible(true);
//     }

//     // Encrypt file using PRESENT
//     public static String encrypt(String text) {
//         StringBuilder encryptedText = new StringBuilder();
//         for (int i = 0; i < text.length(); i += 8) {
//             String block = text.substring(i, Math.min(i + 8, text.length()));
//             long blockData = stringToLong(block);
//             long encryptedBlock = PRESENT.encryptBlock(blockData, KEY, ROUNDS);
//             encryptedText.append(longToString(encryptedBlock));
//         }
//         return encryptedText.toString();
//     }

//     // Decrypt file using PRESENT
//     public static String decrypt(String text) {
//         StringBuilder decryptedText = new StringBuilder();
//         for (int i = 0; i < text.length(); i += 8) {
//             String block = text.substring(i, Math.min(i + 8, text.length()));
//             long blockData = stringToLong(block);
//             long decryptedBlock = PRESENT.decryptBlock(blockData, KEY, ROUNDS);
//             decryptedText.append(longToString(decryptedBlock));
//         }
//         return decryptedText.toString();
//     }

//     // Convert string to long (64-bit block)
//     private static long stringToLong(String str) {
//         long result = 0;
//         for (int i = 0; i < str.length(); i++) {
//             result = (result << 8) | str.charAt(i);
//         }
//         return result;
//     }

//     // Convert long to string (64-bit block)
//     private static String longToString(long value) {
//         StringBuilder result = new StringBuilder();
//         for (int i = 0; i < 8; i++) {
//             result.append((char) ((value >>> (56 - i * 8)) & 0xFF));
//         }
//         return result.toString();
//     }

//     // Read file content
//     public static String readFile(String filePath) throws IOException {
//         StringBuilder content = new StringBuilder();
//         File file = new File(filePath);
//         Scanner scanner = new Scanner(file);
//         while (scanner.hasNextLine()) {
//             content.append(scanner.nextLine()).append("\n");
//         }
//         scanner.close();
//         return content.toString();
//     }

//     // Write content to file
//     public static void writeFile(String filePath, String content) throws IOException {
//         FileWriter writer = new FileWriter(filePath);
//         writer.write(content);
//         writer.close();
//     }

//     // Handle button clicks
//     @Override
//     public void actionPerformed(ActionEvent e) {
//         int returnValue = fileChooser.showOpenDialog(this);
//         if (returnValue == JFileChooser.APPROVE_OPTION) {
//             File selectedFile = fileChooser.getSelectedFile();
//             String filePath = selectedFile.getAbsolutePath();

//             try {
//                 String originalText = readFile(filePath);
//                 String resultText;
//                 String resultFilePath;

//                 if (e.getSource() == encryptButton) {
//                     resultText = encrypt(originalText);
//                     resultFilePath = filePath.replace(".txt", "_encrypted.txt");
//                     writeFile(resultFilePath, resultText);
//                     statusLabel.setText("File encrypted successfully: " + resultFilePath);
//                 } else if (e.getSource() == decryptButton) {
//                     resultText = decrypt(originalText);
//                     resultFilePath = filePath.replace(".txt", "_decrypted.txt");
//                     writeFile(resultFilePath, resultText);
//                     statusLabel.setText("File decrypted successfully: " + resultFilePath);
//                 }
//             } catch (IOException ex) {
//                 statusLabel.setText("An error occurred: " + ex.getMessage());
//             }
//         }
//     }

//     // Main method
//     public static void main(String[] args) {
//         new runIt();
//     }
// }

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.swing.*;

public class runIt extends JFrame implements ActionListener {

    // UI Components
    private JButton encryptButton, decryptButton;
    private JLabel statusLabel;
    private JFileChooser fileChooser;

    // PRESENT parameters
    private static final long KEY = 0x0123456789ABCDEFL; // 80-bit key (example)
    private static final int ROUNDS = 31;

    // Constructor
    public runIt() {
        setTitle("CipherGuard: File Encryption and Decryption");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        encryptButton = new JButton("Encrypt File");
        decryptButton = new JButton("Decrypt File");
        statusLabel = new JLabel("Select a file to encrypt or decrypt.", SwingConstants.CENTER);
        fileChooser = new JFileChooser();

        // Add action listeners
        encryptButton.addActionListener(this);
        decryptButton.addActionListener(this);

        // Add components to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        add(buttonPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Add PKCS7 padding
    public static byte[] addPadding(byte[] bytes) {
        int blockSize = 8;
        int paddingLength = blockSize - (bytes.length % blockSize);
        byte[] paddedBytes = new byte[bytes.length + paddingLength];
        System.arraycopy(bytes, 0, paddedBytes, 0, bytes.length);
        for (int i = bytes.length; i < paddedBytes.length; i++) {
            paddedBytes[i] = (byte) paddingLength;
        }
        return paddedBytes;
    }

    // Remove PKCS7 padding
    public static byte[] removePadding(byte[] bytes) {
        if (bytes.length == 0) {
            return bytes;
        }
        int paddingLength = bytes[bytes.length - 1];
        if (paddingLength < 1 || paddingLength > 8) {
            throw new IllegalArgumentException("Invalid padding length.");
        }
        return Arrays.copyOfRange(bytes, 0, bytes.length - paddingLength);
    }

    // Convert bytes to long (64-bit block)
    public static long bytesToLong(byte[] bytes, int offset) {
        if (offset + 8 > bytes.length) {
            throw new IllegalArgumentException("Invalid offset or bytes array length.");
        }
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result = (result << 8) | (bytes[offset + i] & 0xFF);
        }
        return result;
    }

    // Convert long to bytes (64-bit block)
    public static void longToBytes(long value, byte[] bytes, int offset) {
        for (int i = 0; i < 8; i++) {
            bytes[offset + i] = (byte) ((value >>> (56 - i * 8)) & 0xFF);
        }
    }

    // Encrypt file using PRESENT
    public static byte[] encrypt(byte[] bytes) {
        bytes = addPadding(bytes); // Add padding
        byte[] encryptedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i += 8) {
            long blockData = bytesToLong(bytes, i);
            long encryptedBlock = PRESENT.encryptBlock(blockData, KEY, ROUNDS);
            longToBytes(encryptedBlock, encryptedBytes, i);
        }
        return encryptedBytes;
    }

    // Decrypt file using PRESENT
    public static byte[] decrypt(byte[] bytes) {
        // Ensure the length is a multiple of 8
        if (bytes.length % 8 != 0) {
            throw new IllegalArgumentException("Invalid encrypted text length. It must be a multiple of 8.");
        }

        byte[] decryptedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i += 8) {
            long blockData = bytesToLong(bytes, i);
            long decryptedBlock = PRESENT.decryptBlock(blockData, KEY, ROUNDS);
            longToBytes(decryptedBlock, decryptedBytes, i);
        }

        // Remove padding
        decryptedBytes = removePadding(decryptedBytes);
        return decryptedBytes;
    }

    // Read file content as bytes
    public static byte[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytes);
        }
        return bytes;
    }

    // Write bytes to file
    public static void writeFile(String filePath, byte[] bytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytes);
        }
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            try {
                byte[] originalBytes = readFile(filePath);
                byte[] resultBytes;
                String resultFilePath;

                if (e.getSource() == encryptButton) {
                    resultBytes = encrypt(originalBytes);
                    resultFilePath = filePath.replace(".txt", "_encrypted.bin");
                    writeFile(resultFilePath, resultBytes);
                    statusLabel.setText("File encrypted successfully: " + resultFilePath);
                } else if (e.getSource() == decryptButton) {
                    resultBytes = decrypt(originalBytes);
                    resultFilePath = filePath.replace(".bin", "_decrypted.txt");
                    writeFile(resultFilePath, resultBytes);
                    statusLabel.setText("File decrypted successfully: " + resultFilePath);
                }
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        new runIt();
    }
}