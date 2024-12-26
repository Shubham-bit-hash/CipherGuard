// public class PRESENT {

//     // PRESENT S-box
//     private static final int[] S_BOX = {
//         0xC, 0x5, 0x6, 0xB, 0x9, 0x0, 0xA, 0xD, 0x3, 0xE, 0xF, 0x8, 0x4, 0x7, 0x1, 0x2
//     };

//     // PRESENT inverse S-box
//     private static final int[] INVERSE_S_BOX = {
//         0x5, 0xE, 0xF, 0x8, 0xC, 0x1, 0x2, 0xD, 0xB, 0x4, 0x6, 0x3, 0x0, 0x7, 0x9, 0xA
//     };

//     // PRESENT permutation layer
//     private static final int[] P_LAYER = {
//         0, 16, 32, 48, 1, 17, 33, 49, 2, 18, 34, 50, 3, 19, 35, 51,
//         4, 20, 36, 52, 5, 21, 37, 53, 6, 22, 38, 54, 7, 23, 39, 55,
//         8, 24, 40, 56, 9, 25, 41, 57, 10, 26, 42, 58, 11, 27, 43, 59,
//         12, 28, 44, 60, 13, 29, 45, 61, 14, 30, 46, 62, 15, 31, 47, 63
//     };

//     // Generate round keys
//     private static long[] generateRoundKeys(long key, int rounds) {
//         long[] roundKeys = new long[rounds];
//         for (int i = 0; i < rounds; i++) {
//             roundKeys[i] = key >>> 16;
//             key = ((key & 0x7) << 61) | (key >>> 3);
//             key = (S_BOX[(int) ((key >>> 60) & 0xF)] << 60) | (key & 0x0FFFFFFFFFFFFFFFL);
//             key ^= (i + 1) << 15;
//         }
//         return roundKeys;
//     }

//     // Encrypt a 64-bit block
//     public static long encryptBlock(long block, long key, int rounds) {
//         long[] roundKeys = generateRoundKeys(key, rounds);
//         for (int i = 0; i < rounds - 1; i++) {
//             block ^= roundKeys[i];
//             block = sBoxLayer(block);
//             block = pLayer(block);
//         }
//         block ^= roundKeys[rounds - 1];
//         return block;
//     }

//     // Decrypt a 64-bit block
//     public static long decryptBlock(long block, long key, int rounds) {
//         long[] roundKeys = generateRoundKeys(key, rounds);
//         block ^= roundKeys[rounds - 1];
//         for (int i = rounds - 2; i >= 0; i--) {
//             block = inversePLayer(block);
//             block = inverseSBoxLayer(block);
//             block ^= roundKeys[i];
//         }
//         return block;
//     }

//     // Apply S-box layer
//     private static long sBoxLayer(long block) {
//         long result = 0;
//         for (int i = 0; i < 16; i++) {
//             result |= (long) S_BOX[(int) ((block >>> (i * 4)) & 0xF)] << (i * 4);
//         }
//         return result;
//     }

//     // Apply inverse S-box layer
//     private static long inverseSBoxLayer(long block) {
//         long result = 0;
//         for (int i = 0; i < 16; i++) {
//             result |= (long) INVERSE_S_BOX[(int) ((block >>> (i * 4)) & 0xF)] << (i * 4);
//         }
//         return result;
//     }

//     // Apply permutation layer
//     private static long pLayer(long block) {
//         long result = 0;
//         for (int i = 0; i < 64; i++) {
//             result |= ((block >>> i) & 0x1) << P_LAYER[i];
//         }
//         return result;
//     }

//     // Apply inverse permutation layer
//     private static long inversePLayer(long block) {
//         long result = 0;
//         for (int i = 0; i < 64; i++) {
//             result |= ((block >>> P_LAYER[i]) & 0x1) << i;
//         }
//         return result;
//     }
// }

public class PRESENT {

    // PRESENT S-box
    private static final int[] S_BOX = {
        0xC, 0x5, 0x6, 0xB, 0x9, 0x0, 0xA, 0xD, 0x3, 0xE, 0xF, 0x8, 0x4, 0x7, 0x1, 0x2
    };

    // PRESENT inverse S-box
    private static final int[] INVERSE_S_BOX = {
        0x5, 0xE, 0xF, 0x8, 0xC, 0x1, 0x2, 0xD, 0xB, 0x4, 0x6, 0x3, 0x0, 0x7, 0x9, 0xA
    };

    // PRESENT permutation layer
    private static final int[] P_LAYER = {
        0, 16, 32, 48, 1, 17, 33, 49, 2, 18, 34, 50, 3, 19, 35, 51,
        4, 20, 36, 52, 5, 21, 37, 53, 6, 22, 38, 54, 7, 23, 39, 55,
        8, 24, 40, 56, 9, 25, 41, 57, 10, 26, 42, 58, 11, 27, 43, 59,
        12, 28, 44, 60, 13, 29, 45, 61, 14, 30, 46, 62, 15, 31, 47, 63
    };

    // Generate round keys
    private static long[] generateRoundKeys(long key, int rounds) {
        long[] roundKeys = new long[rounds];
        for (int i = 0; i < rounds; i++) {
            roundKeys[i] = key >>> 16;
            key = ((key & 0x7) << 61) | (key >>> 3);
            key = (S_BOX[(int) ((key >>> 60) & 0xF)] << 60) | (key & 0x0FFFFFFFFFFFFFFFL);
            key ^= (i + 1) << 15;
        }
        return roundKeys;
    }

    // Encrypt a 64-bit block
    public static long encryptBlock(long block, long key, int rounds) {
        long[] roundKeys = generateRoundKeys(key, rounds);
        for (int i = 0; i < rounds - 1; i++) {
            block ^= roundKeys[i];
            block = sBoxLayer(block);
            block = pLayer(block);
        }
        block ^= roundKeys[rounds - 1];
        return block;
    }

    // Decrypt a 64-bit block
    public static long decryptBlock(long block, long key, int rounds) {
        long[] roundKeys = generateRoundKeys(key, rounds);
        block ^= roundKeys[rounds - 1];
        for (int i = rounds - 2; i >= 0; i--) {
            block = inversePLayer(block);
            block = inverseSBoxLayer(block);
            block ^= roundKeys[i];
        }
        return block;
    }

    // Apply S-box layer
    private static long sBoxLayer(long block) {
        long result = 0;
        for (int i = 0; i < 16; i++) {
            result |= (long) S_BOX[(int) ((block >>> (i * 4)) & 0xF)] << (i * 4);
        }
        return result;
    }

    // Apply inverse S-box layer
    private static long inverseSBoxLayer(long block) {
        long result = 0;
        for (int i = 0; i < 16; i++) {
            result |= (long) INVERSE_S_BOX[(int) ((block >>> (i * 4)) & 0xF)] << (i * 4);
        }
        return result;
    }

    // Apply permutation layer
    private static long pLayer(long block) {
        long result = 0;
        for (int i = 0; i < 64; i++) {
            result |= ((block >>> i) & 0x1) << P_LAYER[i];
        }
        return result;
    }

    // Apply inverse permutation layer
    private static long inversePLayer(long block) {
        long result = 0;
        for (int i = 0; i < 64; i++) {
            result |= ((block >>> P_LAYER[i]) & 0x1) << i;
        }
        return result;
    }
}