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

/**
 *
 * @author FF6EB4
 */
public class Artifacts extends Command{
    
    public Artifacts(){
        this.category = 1;
        this.signature = new String[]{"!artifacts"};
        this.description = "Checks the items you own";
    }
    
    public void execute(String params, long ID){
        Launcher.send(""+UserData.getUD(ID).showArtifacts());
    }
}