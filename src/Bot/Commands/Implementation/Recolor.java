/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import java.awt.Color;
import java.util.List;
import sx.blah.discord.handle.obj.IRole;

/**
 *
 * @author FF6EB4
 */
public class Recolor extends Command{
    
    public Recolor(){
        this.category = 0;
        this.signature = new String[]{"!colour","!color"};
        this.description = "Recolour your name! Takes a color name, 3 numbers, or hex";
    }
    
    public void execute(String params, long ID){
        Color gnu;
        try{
            String [] split = params.split(" ",3);
            
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            int c =Integer.parseInt(split[2]);

            if(a > 255 || a < 0 || b > 255 || c > 255 || b < 0 || c < 0){
                Launcher.PM("!choose uses values between 0 and 255 for color- google color picker, pick a color and select the 3 numbers it outputs!",ID);
                return;
            }
            
            gnu = new Color(a,b,c);
            List<IRole> rol = Launcher.client.getRoles();
            
            for(IRole R : rol){
                if(R.getName().equals(""+ID)){
                    R.changeColor(gnu);
                    //Launcher.PM("Color has been set!", ID);
                    Launcher.organizeRoles();
                    return;
                }
            }
            
            Launcher.PM("Your role doesn't exist or something!!!! It has no color!!!", ID);
        } catch (Exception E){}
        
        params = params.toLowerCase();
        
        switch(params){
            case "orange":
                gnu = Color.ORANGE;
                break;
            case "blue":
                gnu = Color.BLUE;
                break;
            case "pink":
                gnu = Color.PINK;
                break;
            case "cyan":
                gnu = Color.CYAN;
                break;
            case "red":
                gnu = Color.RED;
                break;
            case "dark grey":
            case "dark gray":
                gnu = Color.DARK_GRAY;
                break;
            case "black":
                gnu = Color.BLACK;
                break;
            case "green":
                gnu = Color.GREEN;
                break;
            case "light grey":
            case "light gray":
                gnu = Color.LIGHT_GRAY;
                break;
            case "gray":
            case "grey":
                gnu = Color.GRAY;
                break;
            case "magenta":
                gnu = Color.MAGENTA;
                break;
            case "white":
                gnu = Color.WHITE;
                break;   
            case "yellow":
                gnu = Color.YELLOW;
                break;
            case "random":
                gnu = new Color(oRan.nextInt(255),oRan.nextInt(255),oRan.nextInt(255));
                break; 
            default:
                long l = Long.parseLong(params, 16);
                gnu = new Color((int)l);
                break;
        }
        
        List<IRole> rol = Launcher.client.getRoles();
            
        for(IRole R : rol){
            if(R.getName().equals(""+ID)){
                R.changeColor(gnu);
                
                Launcher.organizeRoles();
                
                return;
            }
        }
    }
}