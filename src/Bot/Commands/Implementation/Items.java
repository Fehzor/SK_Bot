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
import Game.Weapons;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class Items extends Command{
    
    public Items(){
        this.category = 1;
        this.signature = new String[]{"!items"};
        this.description = "View all items you can currently craft.";
    }
    
    public void execute(String params, long ID){
        IChannel get = Launcher.currentChannel;
        UserData UD = UserData.getUD(ID);
        
        String s = "**Items you can craft:**\n\n";
        
        for(String w : Weapons.names){
            if(Weapons.prev.containsKey(w)){
                if(UD.weapons.getData().contains(Weapons.prev.get(w))){
                    s+="**"+Weapons.prev.get(w)+"** -> **"+w+"**\n";
                    s+="*"+Weapons.description.get(w)+"*\n";
                    s+="*"+Weapons.cost(w)+"*\n";
                }
            } else {
                s+="**"+w+"**\n";
                s+="*"+Weapons.description.get(w)+"*\n";
                s+="*"+Weapons.cost(w)+"*\n";
            }
        }
        
        Launcher.send(s);
    }
}