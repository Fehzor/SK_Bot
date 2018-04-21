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
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class Gate extends Command{
    
    public Gate(){
        this.category = 1;
        this.signature = new String[]{"!gate"};
        this.description = "Checks the gate's stats";
    }
    
    public void execute(String params, long ID){
        try{
        IChannel get = Launcher.currentChannel;
        Game.Gate G = Game.Gate.gates.get(get);
        
        String S = "**MINERALS:**\n"+G.minerals.getData()[0]+" Red, "
                + G.minerals.getData()[1]+" Green, "
                + G.minerals.getData()[2]+" Blue, "
                + G.minerals.getData()[3]+" Yellow, "
                + G.minerals.getData()[4]+" Purple\n";
        
        Launcher.send(S);
        }catch (Exception E){
            E.printStackTrace();
        }
    }
}