package cs345.deadwood.view;

import java.util.ArrayList;
import java.util.List;

public class GameLog {
    private static GameLog log = new GameLog();
    private BoardView console;
    private List<String> lines = new ArrayList<String>();
    public static int numLines=15;
    public GameLog getLogger(){
        return log;
    }

    public static GameLog getInstance(){
        return log;
    }
    public void log(String text){
        lines.add(text);
        String str="";
        if(lines.size()>numLines){
            for(int i=0; i<numLines; i++){
                str+=lines.get(i+lines.size()-numLines)+"\n";
            }
        }else{
            for(int i=0; i<lines.size(); i++){
                str+=lines.get(i)+"\n";
            }
        }
        if(console!=null)
            console.log(str);
    }

    public void registerConsole(BoardView console){
        this.console=console;
    }
    public static void logg(String str){
        log.log(str);
    }
}
