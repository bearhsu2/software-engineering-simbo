package idv.kuma;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

class SlotScoreCalculatorSimulatorTest {

    @Disabled
    @Test
    void simulate() throws WrongModeException {


        RandomNumberGenerator randomNumberGenerator = new NativeRandomNumberGenerator(new Random());

        final Reels baseGameReels = new Reels(
                List.of(
                        List.of("A", "A", "3", "A"),
                        List.of("A", "A", "3", "A"),
                        List.of("A", "A", "3"),
                        List.of("A", "A", "3"),
                        List.of("A", "A", "4")
                ), randomNumberGenerator);
        final SlotKingPayTable baseGamePayTable = new SlotKingPayTable();
        final Reels freeGameReels = new Reels(
                List.of(
                        List.of("A", "2", "3", "3", "3"),
                        List.of("A", "2", "3"),
                        List.of("A", "3", "4")
                ), randomNumberGenerator
        );
        final MasterpiecePayTable freeGamePayTable = new MasterpiecePayTable();

        SlotScoreCalculator sut = new SlotScoreCalculator(
                new GameFlow(baseGameReels, baseGamePayTable), new GameFlow(freeGameReels, freeGamePayTable)
        );

        int totalWin = 0;
        int totalBet = 0;
        for (int i = 0; i < 100_000; i++) {

            if (sut.isFreeGame()) {
                SpinResult spinResult = sut.spinFree();
                totalBet += 10;
                totalWin += spinResult.getWin();
            } else {
                SpinResult spinResult = sut.spinBase(10);
                totalBet += 10;
                totalWin += spinResult.getWin();
            }
        }

        double rtp = (totalWin + 0D) / totalBet;
        Assertions.assertThat(Math.abs(rtp - 80) < 1).isTrue();
//        System.out.println(rtp);

    }
}