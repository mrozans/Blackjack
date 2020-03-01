package GameStates;

import Interface.Controls.Buttons;
import Interface.Labels;
import Interface.Input;

import javax.swing.*;
import java.awt.*;

public class GameStateManager
{
    public static final int GAME_STATE_MENU = 0;
    public static final int GAME_STATE_GAME = 1;
    public static final int GAME_STATE_LEADERBOARD = 2;
    public static final int GAME_STATE_EXIT = 3;

    public static GameState gs;

    public static boolean exit = false;

    public GameStateManager(JFrame frame)
    {
        Buttons buttons = new Buttons(frame);
        Labels labels = new Labels(frame);
        Input input = new Input(frame);
        change(GAME_STATE_MENU);
    }

    public static void change(int ID)
    {
        if(ID == GAME_STATE_MENU)
        {
            gs = new Menu();
            Labels.setMenu();
            Buttons.setMenu();
        }
        if(ID == GAME_STATE_GAME)
        {
            gs = new Game();
            Buttons.setGame();
            Labels.setGame();
        }
        if(ID == GAME_STATE_LEADERBOARD)
        {
            gs = new LeaderBoard();
            Labels.setLeaderBoard();
            Buttons.setLeaderBoard();
        }
        if(ID == GAME_STATE_EXIT) exit = true;
    }

    public void update()
    {
        gs.update();
    }
    public void render(Graphics g)
    {
        gs.render(g);
    }
}
