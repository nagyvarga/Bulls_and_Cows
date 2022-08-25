package bullscows;

public class Digits {
    final static int MAX = 36;
    int size;
    int range;
    protected final String digitCode;

    public Digits(int length, int range, String code) {
        this.size = length;
        this.range = range;
        if (code.length() != length || !isAllSymbolsExcepted(range, code)) {
            throw new RuntimeException("Length is not the exception.");
        }
        this.digitCode = code;
    }

    public String getCode() {
        return digitCode;
    }

    protected static int getCharArrayIndex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'z') {
            return c + 10 - 'a';
        }
        return -1;
    }

    protected static boolean isAllSymbolsExcepted(int range, String code) {
        for (char c : code.toCharArray()) {
            int index = getCharArrayIndex(c);
            if (index < 0 || index >= range) {
                return false;
            }
        }
        return true;
    }

}
