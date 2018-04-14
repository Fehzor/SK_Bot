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
import Game.Mission;
import Game.Weapons;
import org.apache.commons.lang3.text.WordUtils;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class Embark extends Command{
    
    public Embark(){
        this.category = 1;
        this.signature = new String[]{"!embark"};
        this.description = "Embark on a mission- E.G. !embark Begging In Haven";
    }
    
    public void execute(String params, long ID){
        IChannel get = Launcher.currentChannel;
        UserData UD = UserData.getUD(ID);
        
        String miss = WordUtils.capitalizeFully(params.toLowerCase());
        
        if(miss.equals("Haven")){
            Mission.knights.getData().remove(Long.parseLong(UD.ID));
            Mission.knights.write();
            Launcher.send("Returning to Haven...");
            return;
        }
        
        if(UD.missions.getData().contains(miss)){
            Mission.embark(UD,miss);
            Launcher.send("Embarking on mission: "+miss);
            return;
        }
        
        Launcher.send("Mission not available or spelled wrong: "+miss);
    }
}