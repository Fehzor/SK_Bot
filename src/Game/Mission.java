/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Bot.Fields.Field;
import Bot.Fields.UserData;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FF6EB4
 */
public class Mission {
    public static Field<ArrayList<Long>> knights = new Field<>("MISSION","KNIGHTS",new ArrayList<>());
    public static Field<HashMap<Long, String>> whichMission = new Field<>("MISSION","WHICH",new HashMap<>());
    public static Field<HashMap<Long, Long>> start = new Field<>("MISSION","START",new HashMap<>());
    
    public static ArrayList<String> missionNames = new ArrayList<>();
    public static HashMap<String, Mission> missions = new HashMap<>();
    
    String name;
    public long time;
    public int e;
    public int tier;
    
    public String flavor;
    
    public Mission(String name, long time, int e, int tier, String flavor){
        this.name = name;
        this.time = time;
        this.e = e;
        this.tier = tier;
        
        this.flavor = flavor;
    }
    
    public static void check(){
        
        for(int i = knights.getData().size()-1; i >= 0; i--){
            long how = System.currentTimeMillis() - start.getData().get(knights.getData().get(i));
            long time = missions.get(whichMission.getData().get(knights.getData().get(i))).time;
            
            if(how > time){
                missions.get(whichMission.getData().get(knights.getData().get(i))).reward(UserData.getUD(knights.getData().get(i)));
            }
        }
    }
    
    private void reward(UserData UD){
        knights.getData().remove(Long.parseLong(UD.ID));
        knights.write();
        UD.energy.append(e);
        Launcher.PM("You've completed your mission! +"+e+" energy!", Long.parseLong(UD.ID));
        
        int times = (int)Math.floor(time / 20 * 60 * 1000);
        int num = UD.missions.getData().size();
        
        for(int i = 0; i < times; ++i){
            if(oRan.nextInt(num) == 0){
                grantMission(UD);
            }
        }
        
        bonus(UD);
    }
    
    public void bonus(UserData UD){
        return;
    }
    
    public static void startMissions(){
        
        //Create some missions here :D
        
        register(new Mission(
                "Begging In Haven",
                20 * 60 * 1000,
                5,
                1,
                "Stand in haven asking other knights for energy like the trash you are."
        ));
        
        register(new Mission(
                "Scrubbing Toilets In Haven",
                60 * 60 * 1000,
                25,
                1,
                "The bathroom in Haven is a disgusting place and it's your job to clean it."
        ));
        
        register(new Mission(
                "Carrying Boxes",
                48 * 60 * 1000,
                20,
                1,
                "Carry boxes around the clockworks in Haven with the other knights."
        ));
        
        register(new Mission(
                "Night Shift",
                6 * 60 * 60 * 1000,
                200,
                1,
                "Keep watch over Haven. All night long. Sleep during the day like a vampire."
        ));
        
        register(new Mission(
                "Geotech Intern",
                60 * 60 * 1000,
                0,
                1,
                "Work for free helping the geology department. How kind of you."
        ){
            public void bonus(UserData UD){
                UD.minerals.getData()[0]+=200;
                UD.minerals.getData()[1]+=200;
                UD.minerals.getData()[2]+=200;
                UD.minerals.getData()[3]+=200;
                UD.minerals.getData()[4]+=200;
            }
        });
        
        Thread T = new Thread(){
            public void run(){
                while(true){
                    try{
                        sleep(30000);
                    } catch (InterruptedException ex) {
                        System.err.println("THREAD PROBLEMS ;)");
                    }
                    
                    Mission.check();
                }
            }
        };
        
        T.start();
    }
    
    private static void register(Mission M){
        missionNames.add(M.name);
        missions.put(M.name, M);
    }
    
    public static void embark(UserData UD, String miss){
        if(knights.getData().contains(Long.parseLong(UD.ID))){
            knights.getData().remove(Long.parseLong(UD.ID));
        }
        
        knights.getData().add(Long.parseLong(UD.ID));
        knights.write();
        whichMission.getData().put(Long.parseLong(UD.ID),miss);
        whichMission.write();
        start.getData().put(Long.parseLong(UD.ID),System.currentTimeMillis());
        start.write();
    }
    
    public static void grantMission(UserData UD){
        int t = UD.tier.getData();
        String miss = missionNames.get(oRan.nextInt(missionNames.size()));
        Mission M = missions.get(miss);
        
        int tries = 100;
        while(tries > 0){
            tries = tries - 1;
            
            if(UD.missions.getData().contains(miss)){
                miss = missionNames.get(oRan.nextInt(missionNames.size()));
                M = missions.get(miss);
            } else {
                if(t >= M.tier){
                    UD.missions.getData().add(miss);
                    UD.missions.write();
                    Launcher.PM("YOU'VE RECEIVED A NEW MISSION: "+miss,Long.parseLong(UD.ID));
                    return;
                } else {
                    miss = missionNames.get(oRan.nextInt(missionNames.size()));
                    M = missions.get(miss);
                }
            }
        }
        
    }
}
