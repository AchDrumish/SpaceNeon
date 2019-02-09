import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;

import java.util.LinkedList;

public class Game {

    private Player playerOne;
    private Player playerTwo;
    private boolean playing = true;
    private boolean paused = false;
    private LinkedList<Bullets> friendlyBullets = new LinkedList<>();
    private SpaceShip ship1 = new SpaceShip(400, 380, friendlyBullets, "./Resources/rsz_arrow.png");
    private SpaceShip ship2 = new SpaceShip(200, 380, friendlyBullets, "./Resources/rsz_arrow.png");



    private LinkedList<Enemy> enemies = new LinkedList<>();



    public Game() {


        for (int i = 0; i < 5; i++) {
            enemies.add(EnemyFactory.getNewEnemy());
        }

        playerOne = new Player(KeyboardEvent.KEY_UP, KeyboardEvent.KEY_DOWN, KeyboardEvent.KEY_LEFT, KeyboardEvent.KEY_RIGHT, KeyboardEvent.KEY_SPACE, ship1);
        playerTwo = new Player(KeyboardEvent.KEY_W, KeyboardEvent.KEY_S, KeyboardEvent.KEY_A, KeyboardEvent.KEY_D, KeyboardEvent.KEY_T, ship2);


    }

    /**
     * Initializes the game
     */
    public void init() {


        Rectangle rect = new Rectangle(10, 10, 800, 800);
        rect.setColor(Color.BLACK);
        rect.fill();

        ship1.getImg().draw();
        ship2.getImg().draw();


    }

    public void start() {

        init();

        long initialTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double numberOfSeconds = 1000000000 / amountOfTicks;
        double delta = 0;

        // Check FPS
        int updates = 0;
        //int frames = 0;
        long timer = System.currentTimeMillis();

        while (playing) {

            if (!paused) {

                long now = System.nanoTime();
                delta += (now - initialTime) / numberOfSeconds;
                initialTime = now;

                if (delta >= 1) {
                    tick();
                    render();
                    updates++;
                    delta--;
                }


                //frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println(updates + " FPS");
                    updates = 0;
                    // frames = 0;
                }
            }
        }
    }

    private void tick() {
        ship1.tick();
        ship2.tick();

        for (Enemy enemy: enemies){
            enemy.tick();
        }

        for (int i = 0; i < friendlyBullets.size(); i++) {

            if (friendlyBullets.get(i).getImgY() <= 0) {
                friendlyBullets.remove(friendlyBullets.get(i));
                i--;
                continue;
            }
            friendlyBullets.get(i).tick();
        }
    }

    private void render() {
        ship1.getImg().draw();
        ship2.getImg().draw();


        for (Enemy enemy: enemies){
            enemy.render();
        }

        for (int i = 0; i < friendlyBullets.size(); i++) {

            friendlyBullets.get(i).render();
        }

    }
}
