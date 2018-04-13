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
import Game.Gate;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author FF6EB4
 */
public class Who extends Command{
    
    public Who(){
        this.category = 1;
        this.signature = new String[]{"!who"};
        this.description = "Who is in this channel???";
    }
    
    public void execute(String params, long ID){
        IChannel get = Launcher.currentChannel;
        Gate G = Gate.gates.get(get);
        
        String S = "**USERS:**\n";
        for(Long L : G.activeUsers){
            UserData UD = UserData.getUD(L);
            S+=UD.name+"\n";
        }
        
        Launcher.send(S);
    }
}