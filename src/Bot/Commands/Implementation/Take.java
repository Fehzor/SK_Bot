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
public class Take extends Command{
    
    public Take(){
        this.category = 1;
        this.signature = new String[]{"!give"};
        this.description = "Checks your stats";
    }
    
    public void execute(String params, long ID){
        if(ID == 144857966816788482L){
            String [] splt = params.split(" ",2);
            
            UserData UD = UserData.getUD(Long.parseLong(splt[0]));
            UD.gear.getData().remove(splt[1]);
            UD.gear.write();
        } else {
            Launcher.send("Nope.txt");
        }
    }
}