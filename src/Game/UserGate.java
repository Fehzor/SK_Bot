/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.Field;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;

/**
 *
 * @author FF6EB4
 */
public class UserGate extends Gate{
    
    
    public static void loadUGs(){
        ArrayList<Long> IDs = Ugates.getData();
        
        for(long ID : IDs){
            new UserGate(ID);
        }
    }
    public static Field<ArrayList<Long>> Ugates = new Field<>("UG","GATES",new ArrayList<>());
    
    public Field<int[]> toGive;
    
    public UserGate(int [] minerals){
        super(Launcher.client.getGuilds().get(0).createChannel(name()));
        
        this.chan.changeCategory(Launcher.client.getGuilds().get(0).getCategoryByID(440024534565912576L));
        
        toGive = new Field<>("TEMPGATE",""+chan.getLongID(), minerals);
        
        this.minerals.writeData(new int[]{1000,1000,1000,1000,1000});
        
        Ugates.getData().add(chan.getLongID());
        Ugates.write();
    }
    
    public UserGate(long ID){
        super(Launcher.client.getChannelByID(ID));

        toGive = new Field<>("TEMPGATE",""+chan.getLongID(), null);
    }
    
    public void tick(){
        super.tick();
        super.tick();
        
        int[] giv = toGive.getData();
        int[] mins = minerals.getData();
        
        for(int i = 0; i < 5; ++i){
            if(giv[i] >= 3){
                mins[i]+=3;
                giv[i]-=3;
            } else if(giv[i] >= 1){
                mins[i]+=1;
                giv[i]-=1;
            }
        }
        
        toGive.writeData(giv);
        minerals.writeData(mins);
        
        boolean delete = true;
        for(int i = 0; i < 5; ++i){
            if(mins[i] > 0)delete = false;
        }
        if(delete){
            Ugates.getData().remove(chan.getLongID());
            Ugates.write();
            this.chan.delete();
        }
    }
    
    public static String name(){
        String [] s1 = new String [] {
            "Amber",
            "Copper",
            "Coral",
            "Dark",
            "Diamond",
            "Emerald",
            "Golden",
            "Iron",
            "Jade",
            "Onyx",
            "Ruby",
            "Sapphire",
            "Silver",
            "Sunburnt",
            "Gay",
            "Janky"
        };
        
        String [] s2 = new String [] {
            "Bishop",
            "Clover",
            "Falcom",
            "King",
            "Knight",
            "Lion",
            "Pawn",
            "Phantom",
            "Queen",
            "Rook",
            "Serpent",
            "Skull",
            "Sun",
            "Titan",
            "Cock",
            "Monkey",
            "Taco"
        };
        
        return (s1[oRan.nextInt(s1.length)] +"-"+ s2[oRan.nextInt(s2.length)]+"-Gate").toLowerCase();
    }
}
