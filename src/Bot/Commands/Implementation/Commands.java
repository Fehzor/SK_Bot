/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Launcher;
import java.util.ArrayList;

/**
 *
 * @author FF6EB4
 */
public class Commands extends Command{
    
    public Commands(){
        this.category = 0;
        this.signature = new String[] {"!commands"};
        this.description = "Display the list of commands!";
    }
    
    public void execute(String params, long ID){
        String message = "**Commands are as follows:**\n\n";
        
        String a = "**→View Commands**\n";
        String b = "**→Active Commands**\n";
        String c = "**→Commands**\n";
        String d = "**→Other Commands**\n";
        
        ArrayList<Command> taken = new ArrayList<>();
        for(String s : CommandParser.commandList.keySet()){
            Command C = CommandParser.commandList.get(s);
            if(C.category == 1){
                if(!taken.contains(C)){
                    a+=C.toString()+"\n";
                    taken.add(C);
                }
            } else if(C.category == 2){
                if(!taken.contains(C)){
                    b+=C.toString()+"\n";
                    taken.add(C);
                }
            } else if(C.category == 3) {
                if(!taken.contains(C)){
                    c+=C.toString()+"\n";
                    taken.add(C);
                }
            } else if(C.category == -1){
                //Drop it. It's hidden.
            } else {
                if(!taken.contains(C)){
                    d+=C.toString()+"\n";
                    taken.add(C);
                }
            }
        }
        
        message+=a+"\n";
        message+=b+"\n";
        ////message+=c+"\n";
        message+=d+"\n";
        
        Launcher.PM(message,ID);
    }
}