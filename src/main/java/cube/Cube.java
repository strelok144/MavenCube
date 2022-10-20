package cube;

import java.util.Random;

public class Cube {
    public static int throwCube() throws InterruptedException {
        Random random = new Random();
        int rand = random.nextInt(1, 7);
        //Thread.sleep(1000);
        return rand;
    }
}