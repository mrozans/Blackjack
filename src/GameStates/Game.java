package GameStates;

import java.awt.*;
import Files.HighScore;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import GameStates.GameElements.Hand;
import Interface.Labels;
import Interface.Input;
import Interface.Controls.Buttons;
import static GameStates.GameStateManager.*;

public class Game extends GameState
{
    public static boolean hit = false;
    public static boolean stand = false;
    public static boolean split = false;
    public static boolean doubledown = false;
    public static boolean surrender = false;
    public static boolean yes = false;
    public static boolean no = false;
    public static boolean play = false;
    public static boolean end = false;
    public static boolean enter = false;
    private int chips;
    private int bet;
    private int decks;
    private int turnstoshuffle;
    private int turns;
    private int[] cards;
    private Random random = new Random();
    private Hand Player;
    private Hand Player2;
    private Hand Dealer;
    private int stage;
    private long timebefore;
    private boolean set = true;
    private boolean makechoice = false;
    private boolean secondhand = false;
    private static final int ENTER_BET = -1;
    private static final int PLAYER_DRAW_FIRST = 0;
    private static final int DEALER_DRAW_FIRST = 1;
    private static final int PLAYER_DRAW_SECOND = 2;
    private static final int DEALER_DRAW_SECOND = 3;
    private static final int PLAYER_FIRST_CHOICE = 4;
    private static final int PLAYER_LATER_CHOICE = 5;
    private static final int RESULT = 6;
    private static final int CONTINUE = 7;
    private static final int PLAYER_LATER_CHOICE2 = 8;
    private static final int RESULT2 = 9;
    private static final int PLAYER_DRAW_SECOND2 = 10;
    private static final int CLEAR = 11;
    private static final int NEW_RECORD = 12;
    private static boolean hidden = false;
    private HighScore highScore;

    Game()
    {
        chips = 1000;
        bet = 0;
        decks = 3;
        turnstoshuffle = 10;
        turns = 0;
        cards = new int[52 * decks];
        Player = new Hand(0);
        Player2 = new Hand(1);
        Dealer = new Hand(2);
        shuffle();
        timebefore = System.nanoTime();
        stage = ENTER_BET;
        Buttons.lockAll();
        try {
            highScore = new HighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update()
    {
        if(!makechoice)
        {
            if(System.nanoTime() - timebefore < 2000000000) return;
            timebefore = System.nanoTime();
        }
        switch (stage)
        {
            case(ENTER_BET):
            {
                if(set)
                {
                    Labels.setChips(chips);
                    Buttons.setEnter();
                    Input.setTextField();
                    Labels.setText("Insert your bet:");
                    makechoice = true;
                    set = false;
                }
                if(enter)
                {
                    bet = Input.getnumber();
                    if(bet <= 0 || bet > chips) Labels.setText2("Bet out of bounds, try again");
                    else
                    {
                        stage = PLAYER_DRAW_FIRST;
                        Labels.hideText();
                        Labels.hideText2();
                        Input.hideTextField();
                        Buttons.hideEnter();
                    }
                    enter = false;
                    makechoice = false;
                }
                break;
            }
            case(PLAYER_DRAW_FIRST):    //player draws
            {
                hidden = true;
                chips -= bet;
                Labels.setBet(bet);
                Labels.setChips(chips);
                Player.add(draw());
                Labels.setScore(Player.getscore());
                stage = DEALER_DRAW_FIRST;
                break;
            }
            case(DEALER_DRAW_FIRST):    //dealer draws
            {
                Dealer.add(draw());
                Labels.setDscore(Dealer.getscore());
                stage = PLAYER_DRAW_SECOND;
                break;
            }
            case(PLAYER_DRAW_SECOND):    //player draws 2nd card
            {
                drawsecond(Player);
                break;
            }
            case(PLAYER_DRAW_SECOND2):    //player draws 2nd card
            {
                drawsecond(Player2);
                break;
            }
            case(DEALER_DRAW_SECOND):    //dealer draws hidden card
            {
                Dealer.add(draw());
                stage = PLAYER_FIRST_CHOICE;
                set = true;
                if(Player.isBlackjack())
                {
                    Labels.setText("Blackjack!");
                    stage = RESULT;
                }
                break;
            }
            case(PLAYER_FIRST_CHOICE):    //player 1st choice
            {
                if(set)
                {
                    if(Dealer.chceckiface())    //option for insurence
                    {
                        Labels.setQuestion("Do you want to buy the insurance?");
                        Buttons.lockAll();
                        Buttons.setInsurence();
                        if(chips < bet / 2) Buttons.lockyes();
                    }
                    else
                    {
                        Buttons.unlockAll();
                        if(Player.isnoteven()) Buttons.locksplit();
                        if(chips < bet)
                        {
                            Buttons.locksplit();
                            Buttons.lockdoubledown();
                        }
                    }

                    set = false;
                    makechoice = true;

                }

                if(yes)
                {
                    Labels.hideQuestion();
                    if(Dealer.isBlackjack())
                    {
                        Labels.setText("Dealer has blackjack, bet returned");
                        stage = CLEAR;
                        Buttons.lockAll();
                        chips += bet;
                        set = true;
                    }
                    else
                    {
                        Labels.setText("Dealer doesn't have blackjack");
                        chips -= bet / 2;
                        Labels.setChips(chips);
                        Buttons.unlockAll();
                        if(Player.isnoteven()) Buttons.locksplit();
                        if(chips < bet)
                        {
                            Buttons.locksplit();
                            Buttons.lockdoubledown();
                        }
                    }
                    Buttons.hideInsurence();
                    yes = false;

                }

                if(no)
                {
                    Labels.hideQuestion();
                    if(Dealer.isBlackjack())
                    {
                        Labels.setText("Dealer has blackjack");
                        stage = RESULT;
                    }
                    else
                    {
                        Labels.setText("Dealer doesn't have blackjack");
                        Buttons.unlockAll();
                    }
                    Buttons.hideInsurence();
                    no = false;
                }

                if(hit)
                {
                    makechoice = false;
                    Buttons.lockAll();
                    Player.add(draw());
                    Labels.setScore(Player.getscore());
                    stage = PLAYER_LATER_CHOICE;
                    set = true;
                    hit = false;
                    if(Player.bust())
                    {
                        Labels.setText("You Busted");
                        stage = RESULT;
                    }
                }

                else if(stand)
                {
                    makechoice = false;
                    Buttons.lockAll();
                    stage = RESULT;
                    stand = false;
                }

                else if(doubledown)
                {
                    makechoice = false;
                    chips -= bet;
                    bet *= 2;
                    Labels.setBet(bet);
                    Labels.setChips(chips);
                    Buttons.lockAll();
                    Player.add(draw());
                    Labels.setScore(Player.getscore());
                    if(Player.bust()) Labels.setText("You Busted");
                    stage = RESULT;
                    doubledown = false;
                }

                else if(split)
                {
                    makechoice = false;
                    Buttons.lockAll();
                    Player2.add(Player.splited2());
                    Player2.setsplit();
                    int firstcard = Player.splited1();
                    Player.reset();
                    Player.add(firstcard);
                    Player.setsplit();
                    secondhand = true;
                    Labels.setScore2();
                    Labels.setScore(Player.getscore());
                    Labels.setScore2(Player2.getscore());
                    stage = PLAYER_DRAW_SECOND;
                    set = true;
                    split = false;
                    chips -= bet;
                }

                else if(surrender)
                {
                    makechoice = false;
                    Buttons.lockAll();
                    stage = RESULT;
                }
                break;
            }
            case (PLAYER_LATER_CHOICE):   //later choices
            {
                laterchoice(Player);
                break;
            }
            case (PLAYER_LATER_CHOICE2):   //later choices for 2nd hand
            {
                laterchoice(Player2);
                break;
            }
            case(RESULT):    //outcome
            {
                result(Player);
                break;
            }

            case(RESULT2):    //outcome for 2nd hand
            {
                result(Player2);
                break;
            }

            case(CLEAR):
            {
                bet = 0;
                Labels.setChips(chips);
                Labels.setBet(bet);
                Labels.hideScore2();
                Labels.setDscore(0);
                Labels.setScore(0);
                stage = CONTINUE;
                Player.putaside();
                Player2.putaside();
                Dealer.putaside();
                set = true;
                break;
            }
            case(CONTINUE):     //choice if continue
            {
                if(set)
                {
                    Player.reset();
                    Player2.reset();
                    Dealer.reset();
                    makechoice = true;
                    secondhand = false;
                    Buttons.setChoice();
                    set = false;
                    Labels.setQuestion("Do you want to continue?");
                    if(chips == 0) Buttons.gameover();
                    turns++;
                }
                if(play)
                {
                    Labels.hideText2();
                    Labels.hideQuestion();
                    play = false;
                    makechoice = false;
                    Buttons.hideChoice();
                    stage = ENTER_BET;
                    if(turns % turnstoshuffle == 0)
                    {
                        shuffle();
                    }
                    set = true;
                }
                else if(end)
                {
                    end = false;
                    if(highScore.checknewscore(chips))
                    {
                        stage = NEW_RECORD;
                        set = true;
                    }
                    else GameStateManager.change(GAME_STATE_MENU);
                }
                break;
            }
            case(NEW_RECORD):
            {
                if(set)
                {
                    Buttons.hideChoice();
                    Buttons.setEnter();
                    Input.setTextField();
                    Labels.setText("Insert your name:");
                    Labels.hideQuestion();
                    makechoice = true;
                    set = false;
                }
                if(enter)
                {
                    Input.hideTextField();
                    try
                    {
                        highScore.setnewscore(Input.getname(), chips, turns);
                    }
                    catch (IOException e) {System.exit(-1);}
                    enter = false;
                    GameStateManager.change(GAME_STATE_MENU);
                }
                break;
            }
        }
    }

    public void render(Graphics g)
    {
        Player.render(g);
        Player2.render(g);
        Dealer.render(g);
    }

    private void shuffle()
    {
        Arrays.fill(cards, 1);
        if(turns > 0) Dealer.putback();
    }

    private int draw()
    {
        int number = random.nextInt(52 * decks);
        if(cards[number] == 0) draw();
        cards[number] = 0;
        return number % 52;
    }

    private void laterchoice(Hand hand)
    {
        if(set)
        {
            if(hand == Player2) Labels.setText2("2nd hand choice:");
            makechoice = true;
            Buttons.unlockAll();
            Buttons.lockdoubledown();
            Buttons.locksplit();
            Buttons.locksurrender();
            set = false;
        }

        if(hit)
        {
            makechoice = false;
            Buttons.lockAll();
            hand.add(draw());
            if(hand == Player) Labels.setScore(hand.getscore());
            else Labels.setScore2(hand.getscore());
            hit = false;
            if(hand.bust())
            {
                if(hand == Player) Labels.setText("You Busted");
                else Labels.setText2("2nd hand: You Busted");
                stage = RESULT;
            }
            set = true;
        }

        else if(stand)
        {
            Labels.hideText2();
            makechoice = false;
            Buttons.lockAll();
            if(hand == Player && secondhand)
            {
                stage = PLAYER_DRAW_SECOND2;
                set = true;
            }
            else stage = RESULT;
            stand = false;
        }
    }

    private void result(Hand hand)
    {
        if(hidden)
        {
            hidden = false;
            Dealer.show();
            Labels.setDscore(Dealer.getscore());
            return;
        }
        Labels.setDscore(Dealer.getscore());
        if(surrender)
        {
            Labels.setText("You get half of the bet back");
            chips += bet/2;
            surrender = false;
        }

        else if(hand.isBlackjack() && !Dealer.isBlackjack())
        {
            chips += bet * 2.5;
            if(hand == Player) Labels.setText("You Win with Blackjack");
            else Labels.setText2("2nd hand: You Win with Blackjack");
        }

        else if(hand.bust())      //player busted
        {
            if(hand == Player) Labels.setText("You Busted");
            else Labels.setText2("2nd hand: You Busted");
        }

        else if(Dealer.getscore() < 17)     //dealer draws to 17+
        {
            Dealer.add(draw());
            Labels.setDscore(Dealer.getscore());
            return;
        }

        else if(Dealer.bust()|| hand.getscore() > Dealer.getscore())      //player wins
        {
            chips += bet * 2;
            if(hand == Player) Labels.setText("You Win");
            else Labels.setText2("2nd hand: You Win");
        }

        else if(Dealer.getscore() > hand.getscore())      //player loses
        {
            if(hand == Player) Labels.setText("You Lose");
            else Labels.setText2("2nd hand: You Lose");
        }

        else if(Dealer.getscore() == hand.getscore())     //result is a draw
        {
            chips += bet;
            if(hand == Player) Labels.setText("Draw");
            else Labels.setText2("2nd hand: Draw");
        }
        if(hand == Player && secondhand) stage = RESULT2;
        else stage = CLEAR;
        set = true;
    }

    private void drawsecond(Hand hand)
    {
        hand.add(draw());
        if(hand == Player) Labels.setScore(hand.getscore());
        else Labels.setScore2(hand.getscore());
        if(!secondhand) stage = DEALER_DRAW_SECOND;
        else
        {
            if(hand.isBlackjack())
            {
                if(hand == Player) stage = PLAYER_DRAW_SECOND2;
                else stage = RESULT;
            }
            else if(hand == Player) stage = PLAYER_LATER_CHOICE;
            else stage = PLAYER_LATER_CHOICE2;
        }
    }
}
