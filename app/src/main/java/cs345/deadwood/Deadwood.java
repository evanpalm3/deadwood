/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cs345.deadwood;

import cs345.deadwood.controller.CardRandom;
import cs345.deadwood.controller.CardSorter;
import cs345.deadwood.controller.GameController;
import cs345.deadwood.controller.IStrategy;
import cs345.deadwood.model.CardParser;
import cs345.deadwood.model.GameEngine;
import cs345.deadwood.model.SetParser;
import cs345.deadwood.view.BoardView;
import cs345.deadwood.view.GameLog;

public class Deadwood {

    public static void main(String[] args) {
        /* Get number of players from command line arg
         * Usage: $ ./graldew run --args="2"
         */
        int numberOfPlayers = 3;  // default number of players
        if (args.length > 0) {
            try {
                numberOfPlayers = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Usage: ./gradlew run --args \"NUM\", where NUM should be an integer between 2 and 8.");
                return;
            }
            if (numberOfPlayers > 8 || numberOfPlayers < 2) {
                System.out.println("Usage: ./gradlew run --args \"NUM\", where NUM should be an integer between 2 and 8.");
                return;
            }
        }

        IStrategy strat=null;
        if((int)(Math.random()*2) == 0){  //sorting at random for milestone 4
            GameLog.logg("cards will be sorted by budget");
            strat = new CardRandom();
        }else{
            strat = new CardSorter();
            GameLog.logg("cards will be sorted randomly");
        }

        SetParser setParser = new SetParser();
        CardParser cardParser = new CardParser();

        GameEngine model = new GameEngine(numberOfPlayers, setParser.getSets(), cardParser.getCards());
        GameController controller = new GameController(model, strat);
        BoardView view = new BoardView(model, controller);
        view.init();
    }
}
