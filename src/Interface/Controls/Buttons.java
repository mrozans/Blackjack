package Interface.Controls;

import GameStates.GameStateManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static GameStates.GameStateManager.*;
import static GameStates.Game.*;

public class Buttons
{
    private static JButton Exit;
    private static JButton Start;
    private static JButton Back;
    private static JButton LeaderBoard;
    private static JButton Hit;
    private static JButton Stand;
    private static JButton Doubledown;
    private static JButton Split;
    private static JButton Surrender;
    private static JButton Yes;
    private static JButton No;
    private static JButton Play;
    private static JButton End;
    private static JButton Enter;

    private ActionListener click = new ClickAction();

    class ClickAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();
            if(source == Exit)  GameStateManager.change(GAME_STATE_EXIT);
            else if(source == Start) GameStateManager.change(GAME_STATE_GAME);
            else if(source == LeaderBoard)  GameStateManager.change(GAME_STATE_LEADERBOARD);
            else if(source == Back)  GameStateManager.change(GAME_STATE_MENU);
            else if(source == Hit)  hit = true;
            else if(source == Stand)  stand = true;
            else if(source == Doubledown)  doubledown = true;
            else if(source == Split)  split = true;
            else if(source == Surrender)  surrender = true;
            else if(source == Yes)  yes = true;
            else if(source == No)  no = true;
            else if(source == Play)  play = true;
            else if(source == End)  end = true;
            else if(source == Enter)  enter = true;
        }
    }

    public Buttons(JFrame frame)
    {
        Start = addbutton("Start", 20, 20, frame);
        LeaderBoard = addbutton("LeaderBoard", 20, 80, frame);
        Exit = addbutton("Exit", 20, 140, frame);
        Back = addbutton("Back", 20, 200, frame);
        Hit = addbutton("Hit", 500, 600, frame);
        Stand = addbutton("Stand", 600, 600, frame);
        Doubledown = addbutton("Double down", 700, 600, frame);
        Split = addbutton("Split", 800, 600, frame);
        Surrender = addbutton("Surrender", 900, 600, frame);
        Yes = addbutton("Yes", 640, 400, frame);
        No = addbutton("No", 760, 400, frame);
        Play = addbutton("Play", 640, 400, frame);
        End = addbutton("End", 760, 400, frame);
        Enter = addbutton("Enter", 600, 360, frame);
        reset();
    }

    private JButton addbutton(String name, int x, int y, JFrame frame)
    {
        JButton button = new JButton(name);
        button.setBounds(x,y,100, 40);
        frame.add(button);
        button.addActionListener(click);
        return button;
    }

    private static void reset()
    {
        Exit.setVisible(false);
        Start.setVisible(false);
        LeaderBoard.setVisible(false);
        Back.setVisible(false);
        Hit.setVisible(false);
        Stand.setVisible(false);
        Doubledown.setVisible(false);
        Split.setVisible(false);
        Surrender.setVisible(false);
        Play.setVisible(false);
        Yes.setVisible(false);
        No.setVisible(false);
        End.setVisible(false);
        Enter.setVisible(false);
    }

    public static void setMenu()
    {
        reset();
        Exit.setVisible(true);
        Start.setVisible(true);
        LeaderBoard.setVisible(true);
    }

    public static void setLeaderBoard()
    {
        reset();
        Back.setVisible(true);
    }

    public static void setGame()
    {
        reset();
        Hit.setVisible(true);
        Stand.setVisible(true);
        Doubledown.setVisible(true);
        Split.setVisible(true);
        Surrender.setVisible(true);
    }

    public static void setChoice()
    {
        Play.setVisible(true);
        End.setVisible(true);
        Play.setEnabled(true);
    }

    public static void setEnter()
    {
        Enter.setVisible(true);
    }

    public static void hideChoice()
    {
        Play.setVisible(false);
        End.setVisible(false);
    }

    public static void setInsurence()
    {
        Yes.setVisible(true);
        No.setVisible(true);
        Yes.setEnabled(true);
    }

    public static void hideInsurence()
    {
        Yes.setVisible(false);
        No.setVisible(false);
    }

    public static void hideEnter()
    {
        Enter.setVisible(false);
    }

    public static void unlockAll()
    {
        Hit.setEnabled(true);
        Stand.setEnabled(true);
        Doubledown.setEnabled(true);
        Split.setEnabled(true);
        Surrender.setEnabled(true);
    }

    public static void lockAll()
    {
        Hit.setEnabled(false);
        Stand.setEnabled(false);
        Doubledown.setEnabled(false);
        Split.setEnabled(false);
        Surrender.setEnabled(false);
    }

    public static void lockyes()
    {
        Yes.setEnabled(false);
    }

    public static void gameover()
    {
        Play.setEnabled(false);
    }
    public static void locksplit()
    {
        Split.setEnabled(false);
    }

    public static void lockdoubledown()
    {
        Doubledown.setEnabled(false);
    }

    public static void locksurrender()
    {
        Surrender.setEnabled(false);
    }
}
