import GameStates.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable
{
    private static final String TITLE = "Gra";
    private static final int WIDTH = 1920, HEIGHT = WIDTH / 16 * 9;
    private double timer = System.currentTimeMillis();
    private int FPS = 0;
    private int UPS = 0;
    private static final int framerate = 60;
    private double delta;
    private long timebefore = System.nanoTime();

    private boolean RUNNING = false;
    private JFrame frame;

    private GameStateManager gsm;

    private Main()
    {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gsm = new GameStateManager(frame);
        frame.add(this, BorderLayout.CENTER);


        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void start()
    {
        if(RUNNING) return;
        RUNNING = true;

        new Thread(this, "GameElements" + TITLE).start();
    }

    private void stop()
    {
        if(!RUNNING) return;
        RUNNING = false;
        frame.dispose();
        System.exit(0);
    }

    public void run()
    {
        while(RUNNING && !GameStateManager.exit)
        {
            long timenow = System.nanoTime();
            double frametime = 1000000000 / framerate;
            delta += (timenow - timebefore) / frametime;
            timebefore = timenow;
            while(delta >= 1)
            {
                update();
                delta--;
                UPS++;
            }

            render();
            FPS++;


            if(System.currentTimeMillis() - timer >= 1000)
            {
                timer = System.currentTimeMillis();
                frame.setTitle(TITLE + "FPS: " + FPS + " UPS: " + UPS);
                FPS = 0;
                UPS = 0;
            }
        }
        stop();
    }

    private void update()
    {
        gsm.update();
    }

    private void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT );
        gsm.render(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args)
    {
        new Main().start();
    }

}