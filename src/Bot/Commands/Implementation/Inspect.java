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
import Game.Gear;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author FF6EB4
 */
public class Inspect extends Command{
    
    public Inspect(){
        this.category = 1;
        this.signature = new String[]{"!inspect"};
        this.description = "Retrieves stats for an item.";
    }
    
    public void execute(String params, long ID){
        if(Gear.publicGear.contains(WordUtils.capitalizeFully(params.toLowerCase()))){
            String s = "**"+WordUtils.capitalizeFully(params.toLowerCase())+"**\n";
            s+="*"+Gear.description.get(WordUtils.capitalizeFully(params.toLowerCase()))+"*";
            
            
            Launcher.send(s);
            return;
        }
        
        Launcher.send("GEAR NOT FOUND");
    }
}