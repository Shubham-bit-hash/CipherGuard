
# CipherGuard: File Encryption and Decryption Tool


**CipherGuard** is a lightweight Java application for encrypting and decrypting files using the **PRESENT algorithm**, an ultra-lightweight block cipher designed for resource-constrained environments. It provides a simple graphical user interface (GUI) for selecting files and performing encryption or decryption operations.

---

## Features

- **PRESENT Algorithm**: Uses the ultra-lightweight PRESENT block cipher for encryption and decryption.
- **File Selection**: Allows users to select any `.txt` file for encryption or decryption.
- **Simple UI**: Easy-to-use graphical interface built with Java Swing.
- **No External Libraries**: Uses only core Java libraries for encryption/decryption and file handling.

---

## About the PRESENT Algorithm

The **PRESENT algorithm** is a lightweight block cipher designed for resource-constrained environments such as IoT devices and embedded systems. It operates on **64-bit blocks** and supports **80-bit or 128-bit keys**. The algorithm is highly efficient in terms of both hardware and software implementations, making it ideal for applications where computational resources are limited.

### Key Features of PRESENT:
- **Block Size**: 64 bits
- **Key Sizes**: 80 bits or 128 bits
- **Rounds**: 31 (for 80-bit keys) or 32 (for 128-bit keys)
- **Efficiency**: Optimized for both hardware and software implementations

---

## Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 8 or later installed.
- **Text Editor or IDE**: Use any text editor or IDE (e.g., IntelliJ IDEA, Eclipse) to run the project.

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Shubham-bit-hash/CipherGuard.git
   cd CipherGuard
   ```

2. **Compile the Code**:
   ```bash
   javac src/PRESENT.java src/runIt.java
   ```

3. **Run the Application**:
   ```bash
   java -cp src runIt
   ```

---

## Usage

1. **Launch the Application**:
   - Run the program using the command above.
   - A window with two buttons ("Encrypt File" and "Decrypt File") will appear.

2. **Encrypt a File**:
   - Click "Encrypt File".
   - Select a `.txt` file from the file chooser dialog.
   - The encrypted file will be saved with `_encrypted.bin` appended to the original file name.

3. **Decrypt a File**:
   - Click "Decrypt File".
   - Select an encrypted `.bin` file.
   - The decrypted file will be saved with `_decrypted.txt` appended to the original file name.

---

## Example

### Input File (`input.txt`):
```
Hello, World!
This is a secret message.
12345
```

### Encrypted File (`input_encrypted.bin`):
```
[Binary data, not human-readable]
```

### Decrypted File (`input_decrypted.txt`):
```
Hello, World!
This is a secret message.
12345
```

---

## Contributing

Contributions are welcome! If you'd like to improve CipherGuard, follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeatureName`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeatureName`).
5. Open a pull request.

---

## Contact

For questions or feedback, feel free to reach out:

- **Email**: [crypt.4007@proton.me](mailto:crypt.4007@proton.me)
- **GitHub**: [Shubham-bit-hash](https://github.com/Shubham-bit-hash)

---

Enjoy using **CipherGuard** to secure your files! 🔒

---

### Repository Name: **CipherGuard**

### Repository Link:  
`https://github.com/Shubham-bit-hash/CipherGuard`

---
