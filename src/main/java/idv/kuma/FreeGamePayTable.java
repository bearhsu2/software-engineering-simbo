package idv.kuma;

public class FreeGamePayTable {

    public int getOddFreeGame(Screen screen) {
        int odd = 0;

        int lines = screen.countStraightLines();
        if (lines == 3) {
            odd = 500;

        } else if (lines == 2) {
            odd = 300;
        } else if (lines == 1) {
            odd = 100;

        }
        return odd;
    }
}