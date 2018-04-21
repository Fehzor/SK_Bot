/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Fields.UserData;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import Game.Costumes;

/**
 *
 * @author FF6EB4
 */
public class Top10 extends Command{
    
    public Top10(){
        this.category = 1;
        this.signature = new String[]{"!top10","!best"};
        this.description = "Who is the best looking?";
    }
    
    public void execute(String params, long ID){
        Launcher.send(""+Costumes.top10());
    }
}