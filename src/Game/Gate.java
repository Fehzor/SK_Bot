/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.Field;
import Bot.Fields.UserData;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author FF6EB4
 */
public class Gate{
    public static HashMap<IChannel, Gate> gates = new HashMap<>();
    
    IChannel chan;
    
    public ArrayList<Long> activeUsers;
    public Field<int[]> minerals;
    
    public Gate(IChannel chan){
        this.chan = chan;
        this.activeUsers = new ArrayList<>();
        this.minerals = new Field<>(chan.getStringID(),"minerals",new int[]{0,0,0,0,0});
        
        Gate.gates.put(chan, this);
    }
    
    public static void instantiateGates(IGuild G){
        UserGate.loadUGs();
        
        for(IChannel chan : G.getChannels()){
            if(!gates.containsKey(chan)){
                new Gate(chan);
            }
        }
    }
    
    public int getMaterialAndDeduct(){

        
        int total = 0;
        
        for(int i = 0; i < 5; ++i){
            total+=minerals.getData()[i];
        }
        
        if(total < 10){
            return -1;
        }
        
        int indexA = oRan.nextInt(5);
        int indexB = oRan.nextInt(5);
        int tries = 0;
                
        while(tries<1000 && minerals.getData()[indexA] <= oRan.nextInt(total)){
            indexA = oRan.nextInt(5);
            tries++;
        }
        
        while(tries<1000&&(minerals.getData()[indexB] <= oRan.nextInt(total) || indexA==indexB)){
            indexB = oRan.nextInt(5);
            tries++;
        }
        
        if(indexA == indexB){
            return -1;
        }
        
        /*
        for(int i=0; i <minerals.getData().length;++i){
            if(minerals.getData()[i] >= max){
                otherMax = max;
                max = minerals.getData()[i];
                indexB = indexA;
                indexA = i;
            } else if(minerals.getData()[i] >= otherMax){
                otherMax = minerals.getData()[i];
                indexB = i;
            }
        }
        */
        
        if(minerals.getData()[indexA] <= 0 || minerals.getData()[indexB] <= 0){
            return -1;
        }
        
        int ret = Constants.mineralsToMaterial(indexA, indexB);
        deductMinerals(indexA,indexB);
        
        return ret;
    }
    
    public void deductMinerals(int a, int b){
        minerals.getData()[a] = minerals.getData()[a]-3;
        minerals.getData()[b] = minerals.getData()[b]-3;
        
        if(minerals.getData()[a] < 0)minerals.getData()[a]= 0;
        else minerals.getData()[a] = (int)Math.floor(minerals.getData()[a] * .97);
        if(minerals.getData()[b] < 0)minerals.getData()[b]= 0;
        else minerals.getData()[b] = (int)Math.floor(minerals.getData()[b] * .97);

        minerals.write();
        
    }
    
    public void pruneActiveUsers(){
        for(int i = activeUsers.size()-1; i >= 0; i-=1){
            Long L = activeUsers.get(i);
            UserData UD = UserData.getUD(L);
            if(System.currentTimeMillis() - UD.lastMessage.getData() > 10*60*1000){
                activeUsers.remove(L);
            }
        }
    }
    
    public void tick(){
        pruneActiveUsers();
        
        int mat = getMaterialAndDeduct();
        giveMaterials();
        
        for( Long L : activeUsers ){
            UserData UD = UserData.getUD(L);
            UD.crowns.append(50+oRan.nextInt(100));
            UD.minerals.getData()[0]+=1;
            UD.minerals.getData()[1]+=1;
            UD.minerals.getData()[2]+=1;
            UD.minerals.getData()[3]+=1;
            UD.minerals.getData()[4]+=1;
            UD.minerals.write();
            
            
            String X = UD.A.getData();
            
            Effects.act(this, UD, Gear.effect.get(X), Gear.param1.get(X),
                     Gear.param2.get(X), Gear.param3.get(X));
            
            X = UD.B.getData();
            
            Effects.act(this, UD, Gear.effect.get(X), Gear.param1.get(X),
                     Gear.param2.get(X), Gear.param3.get(X));
            
            X = UD.C.getData();
            
            Effects.act(this, UD, Gear.effect.get(X), Gear.param1.get(X),
                     Gear.param2.get(X), Gear.param3.get(X));
            
            X = UD.armor.getData();
            
            Effects.act(this, UD, Gear.effect.get(X), Gear.param1.get(X),
                     Gear.param2.get(X), Gear.param3.get(X));
            
            X = UD.helmet.getData();
            
            Effects.act(this, UD, Gear.effect.get(X), Gear.param1.get(X),
                     Gear.param2.get(X), Gear.param3.get(X));
            
            X = UD.shield.getData();
            
            Effects.act(this, UD, Gear.effect.get(X), Gear.param1.get(X),
                     Gear.param2.get(X), Gear.param3.get(X));
        }
    }
    
    public void giveMaterials(){
        pruneActiveUsers();
        
        int mat = getMaterialAndDeduct();
        
        for( Long L : activeUsers ){
            UserData UD = UserData.getUD(L);
            if(mat != -1){
                UD.materials.getData()[mat] += 1;
                UD.materials.write();
            }
        }
    }
}
