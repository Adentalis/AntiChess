package Players;

import logic.Move;

import java.util.Random;

public class RandomBot extends Player {

    Random rnd;
    public RandomBot(String name) {
        super(name);
    }

    @Override
    public Move doNextMove() {
        rnd= new Random();
        int randomNext = rnd.nextInt(movesList.size());
        return movesList.get(randomNext);
    }
}
