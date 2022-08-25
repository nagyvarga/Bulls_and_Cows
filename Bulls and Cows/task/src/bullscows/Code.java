package bullscows;

public class Code extends Digits {
    boolean guessed = false;

    public Code(int length, int range) {
        super(length, range, generateRandomCode(length, range));
    }

    private static char generateSymbol(int index) {
        return (char) ((index < 10) ? '0' + index : 'a' + index - 10);
    }

    private static String generateRandomCode(int length, int range) {
        int i = 0;
        boolean[] allSymbols = new boolean[MAX];
        StringBuilder randomSecretCode = new StringBuilder();

        while (i < length && length <= MAX) {
            int randomDigit = Math.floorDiv((int) (Math.random() * range), 1);
            if (!allSymbols[randomDigit]) {
                randomSecretCode.append(generateSymbol(randomDigit));
                allSymbols[randomDigit] = true;
                i++;
            }
        }

        return randomSecretCode.toString();
    }

    public String getCode() {
        return this.guessed ? this.toString() : "*".repeat(super.size);
    }


    public boolean isAllEqual(Digits guessingCode) {
        if (countBulls(guessingCode) == size) {
            this.guessed = true;
            return true;
        }
        return false;
    }

    public int countBulls(Digits guessingCode) {
        int bullsCount = 0;
        for (int i = 0; i < size; i++) {
            if (this.digitCode.charAt(i) == guessingCode.digitCode.charAt(i)) {
                bullsCount++;
            }
        }
        return bullsCount;
    }

    public int countCows(Digits guessingCode) {
        int cowsCount = 0;
        for (int i = 0; i < size; i++) {
            int j = 0;
            while (j < size && this.digitCode.charAt(j) != guessingCode.digitCode.charAt(i)) {
                j++;
            }
            if (j < size) {
                cowsCount++;
            }
        }
        return cowsCount - countBulls(guessingCode);
    }
}
