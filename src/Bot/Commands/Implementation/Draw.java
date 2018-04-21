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
public class Draw extends Command{
    
    public Draw(){
        this.category = 2;
        this.signature = new String[]{"!prizebox","!draw"};
        this.description = "Gets and opens a prize box!";
    }
    
    public void execute(String params, long ID){
        UserData UD = UserData.getUD(ID);
        if(UD.crowns.getData() >= 250000){
            UD.crowns.append(-250000);
            String get = Costumes.draw(UD);
            Launcher.send(get+" has been unboxed!!");
        } else {
            Launcher.send("You don't have enough crowns to open a prize box! 250K are needed!");
        }
    }
}