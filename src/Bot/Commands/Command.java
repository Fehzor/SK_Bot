package Bot.Commands;


import Bot.Launcher;
import Bot.Launcher;

/**
 *
 * @author FF6EB4
 */
public class Command {
    public String [] signature;
    public String parameters = "";
    public String description = "No Description";
    public int category = 0;
    public Command(){
        this.signature = new String[] {""};
        this.description  = "";
        this.category = 1;
    }
    
    public void execute(String params,long ID){
        //LOL.PM(UD+"", UD);
    }
    
    public String toString(){
        return "**Command: "+signature[0]+"** "+parameters+"\n*Description: "+description+"*";
    }
}