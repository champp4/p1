import java.util.Scanner;

public class program2_2 {
    private char[][] matrix;
    private String key;
    private String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

    private boolean contains(char[][] matrix, char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c)
                    return true;
            }
        }
        return false;
    }

    private char[][] generateMatrix() {
        char[][] matrix = new char[5][5];
        String keyStr = this.key + this.alphabet;
        int row = 0;
        int col = 0;
        for (int i = 0; i < keyStr.length(); i++) {
            char c = keyStr.charAt(i);
            if (!contains(matrix, c)) {
                matrix[row][col] = c;
                col++;
            }
            if (col == 5) {
                row++;
                col = 0;
            }
            if (row == 5) {
                break;
            }
        }
        return matrix;
    }

    public program2_2(String key) {
        this.key = key.toUpperCase().replaceAll("[^A-Z]", "");
        this.matrix = generateMatrix();
    }

    // Main Driver Code 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = sc.nextLine();
        System.out.println("Enter the key:");
        String key = sc.nextLine();

        program2_2 cipher = new program2_2(key);
        String ciphertext = cipher.encrypt(plaintext);
        String decryptedText = cipher.decrypt(ciphertext);

        System.out.println("Encrypted text: " + ciphertext);
        System.out.println("Decrypted text: " + decryptedText);

        sc.close();
    }

    private int[] findPosition(char c) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    pos[0] = i;
                    pos[1] = j;
                    break;
                }
            }
        }
        return pos;
    }

    public String encrypt(String plaintext) {

        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replaceAll("J", "I");

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            // select the digram and then find there position in the matrix
            char a = plaintext.charAt(i);
            char b = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            char encryptedA, encryptedB;
            // same row
            if (posA[0] == posB[0]) {
                encryptedA = matrix[posA[0]][(posA[1] + 1) % 5];
                encryptedB = matrix[posB[0]][(posB[1] + 1) % 5];
            }
            // same column
            else if (posA[1] == posB[1]) {
                encryptedA = matrix[(posA[0] + 1) % 5][posA[1]];
                encryptedB = matrix[(posB[0] + 1) % 5][posB[1]];
            }
            // rectangle (swap)
            else {
                encryptedA = matrix[posA[0]][posB[1]];
                encryptedB = matrix[posB[0]][posA[1]];
            }

            ciphertext.append(encryptedA).append(encryptedB);
        }
        
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            char decryptedA, decryptedB;
            // same row
            if (posA[0] == posB[0]) {
                decryptedA = matrix[posA[0]][(posA[1] + 4) % 5];
                decryptedB = matrix[posB[0]][(posB[1] + 4) % 5];
            }
            // same coloumn
            else if (posA[1] == posB[1]) {
                decryptedA = matrix[(posA[0] + 4) % 5][posA[1]];
                decryptedB = matrix[(posB[0] + 4) % 5][posB[1]];
            }
            // in rectangle
            else {
                decryptedA = matrix[posA[0]][posB[1]];
                decryptedB = matrix[posB[0]][posA[1]];
            }

            plaintext.append(decryptedA).append(decryptedB);
        }
        
        return plaintext.toString();
    }

}