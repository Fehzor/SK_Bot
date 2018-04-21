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
import Game.Gear;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class Missions extends Command{
    
    public Missions(){
        this.category = 1;
        this.signature = new String[]{"!missions"};
        this.description = "View your unlocked missions!";
    }
    
    public void execute(String params, long ID){
        IChannel get = Launcher.currentChannel;
        UserData UD = UserData.getUD(ID);
        
        String s = "**Missions You've Unlocked:**\n\n";
        
        for(String m : Mission.missionNames){
            if(UD.missions.getData().contains(m)){
                s+="**"+m+": **"+Mission.missions.get(m).flavor+"\n";
                
                String len = "Short";
                if(Mission.missions.get(m).time >= 1000 * 60 * 30) len = "Medium";
                if(Mission.missions.get(m).time >= 1000 * 60 * 60) len = "Long";
                if(Mission.missions.get(m).time >= 1000 * 60 * 60 * 4) len = "Lengthy Work Shift";
                if(Mission.missions.get(m).time >= 1000 * 60 * 60 * 8) len = "Very Lengthy Work Shift";
                if(Mission.missions.get(m).time >= 1000 * 60 * 60 * 24) len = "One Day+";
                
                s+="Length: "+len+"\n";
                s+="Reward: "+Mission.missions.get(m).e+" energy \n";
                
            }
        }
        
        Launcher.PM(s,ID);
    }
}