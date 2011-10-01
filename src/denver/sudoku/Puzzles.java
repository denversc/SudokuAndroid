package denver.sudoku;

public class Puzzles {

    private static final String PUZZLE_EASY = "360000000004230800000004200"
        + "070460003820000014500013020" + "001900000007048300000000045";

    private static final String PUZZLE_MEDIUM = "650000070000506000014000005"
        + "007009000002314700000700800" + "500000630000201000030000097";

    private static final String PUZZLE_HARD = "009000000080605020501078000"
        + "000000700706040102004000000" + "000720903090301080000000600";

    private Puzzles() {
    }

    private static int charToIntDigit(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return -1;
        }
    }

    public static int[] getEasyPuzzle() {
        return strToIntArray(PUZZLE_EASY);
    }

    public static int[] getHardPuzzle() {
        return strToIntArray(PUZZLE_HARD);
    }

    public static int[] getMediumPuzzle() {
        return strToIntArray(PUZZLE_MEDIUM);
    }

    private static int[] strToIntArray(String s) {
        if (s == null) {
            throw new NullPointerException("s==null");
        }

        final int[] array = new int[s.length()];
        for (int i = array.length - 1; i >= 0; i--) {
            final char c = s.charAt(i);
            final int digit = charToIntDigit(c);
            if (digit < 0) {
                throw new IllegalArgumentException("invalid digit at index "
                    + i + ": '" + c + "'");
            }
            array[i] = digit;
        }

        return array;
    }
}
