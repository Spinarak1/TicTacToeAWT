import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class GmeSaveLoadTest{
    @Test
    public void test()throws Exception{
        Game game = new Game();
        game.start();
        game.move(1, 2);
        game.move(2, 2);
        game.move(0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        game.save(baos);
        Assert.assertEquals("O . . " +
                ". . . " +
                ". O X " +
                "R " +
                "X ", baos.toString());
    }
    @Test
    public void loadTest()throws Exception{
        String s = "X . O " +
                "O . . " +
                "X . . " +
                "R " +
                "O ";
        InputStream i = new ByteArrayInputStream(s.getBytes());
        Game game = new Game();
        game.load(i);
        Assert.assertEquals(game.getCell(0, 0), Player.CROSS);
        Assert.assertEquals(game.getCell(0, 1), Player.CIRCLE);
        Assert.assertEquals(game.getCell(0, 2), Player.CROSS);
        Assert.assertEquals(game.getCell(1, 0), null);
        Assert.assertEquals(game.getCell(1, 1), null);
        Assert.assertEquals(game.getCell(1, 2), null);
        Assert.assertEquals(game.getCell(2, 0), Player.CIRCLE);
        Assert.assertEquals(game.getCell(2, 1), null);
        Assert.assertEquals(game.getCell(2, 2), null);
        Assert.assertEquals(game.getGameState(), GameState.RUNNING);
        Assert.assertEquals(game.getCurrentPlayer(), Player.CIRCLE);

    }
}
