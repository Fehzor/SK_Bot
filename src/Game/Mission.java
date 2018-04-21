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
import static Game.Constants.BEAST;
import static Game.Constants.FIEND;
import static Game.Gear.ELEMENTAL;
import static Game.Gear.NONE;
import static Game.Gear.NORMAL;
import static Game.Gear.PIERCING;
import static Game.Gear.SHADOW;
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
    
    public int good;
    public int bad;
    
    public Mission(String name, long time, int e, int tier, int good, int bad, String flavor){
        this.name = name;
        this.time = time;
        this.e = e;
        this.tier = tier;
        
        this.flavor = flavor;
        
        this.good = good;
        this.bad = bad;
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
        
        
        
        int points = 0;
        
        String A = UD.A.getData();
        String B = UD.B.getData();
        String C = UD.C.getData();
        
        if(Gear.damageType.get(A) == this.good){
            points += 2;
        } else if(Gear.damageType.get(A) == this.bad){
            points += -1;
        } else {
            points += 1;
        }
        
        if(Gear.damageType.get(B) == this.good){
            points += 2;
        } else if(Gear.damageType.get(A) == this.bad){
            points += -1;
        } else {
            points += 1;
        }
        
        if(Gear.damageType.get(C) == this.good){
            points += 2;
        } else if(Gear.damageType.get(A) == this.bad){
            points += -1;
        } else {
            points += 1;
        }
        
        if(A==B && A==C){
            points -= 2;
        }
        
        if(points >= 5){
            UD.energy.append(e+15);
            Launcher.PM("You've completed your mission perfectly! +"+(e+15)+" energy!", Long.parseLong(UD.ID));
            bonus(UD);
            checkRank(UD);
        } else if(points >= 2){
            UD.energy.append(e+5);
            Launcher.PM("You've completed your mission! +"+(e+5)+" energy!", Long.parseLong(UD.ID));
            if(oRan.nextInt(1000) < 750)bonus(UD);
            checkRank(UD);
        } else if(points >= -1){
            UD.energy.append(e-5);
            Launcher.PM("You've completed your mission!.....barely! +"+(e)+" energy!", Long.parseLong(UD.ID));
            if(oRan.nextInt(1000) < 250)bonus(UD);
        } else {
            Launcher.PM("Mission failed! Next time, try bringing different gear..", Long.parseLong(UD.ID));
        }
        
        
        int times = (int)Math.sqrt(time / (5 * 60 * 1000));
        int num = UD.missions.getData().size();
        
        for(int i = 0; i < times; ++i){
            if(oRan.nextInt(num) == 0){
                grantMission(UD);
            }
        }
        
        
    }
    
    public void bonus(UserData UD){
        return;
    }
    
    public static void startMissions(){
        
        //Create some missions here :D
        
        register(new Mission(
                "Begging In Haven",
                20 * 60 * 1000,
                0,
                1,
                NONE,
                NONE,
                "Stand in haven asking other knights for energy like the trash you are."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) == 23){
                    UD.energy.append(10000);
                    Launcher.PM("...someone hands you 10,000 ce and walks away.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 100){
                    UD.energy.append(100);
                    Launcher.PM("...A few people bite... +100 e",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("...no one gave you anything :(",Long.parseLong(UD.ID));
                }
            }
        });
        
        register(new Mission(
                "Scrubbing Toilets In Haven",
                60 * 60 * 1000,
                25,
                1,
                ELEMENTAL,
                SHADOW,
                "The bathroom in Haven is a disgusting place and it's your job to clean it."
        ));
        
        register(new Mission(
                "Carrying Boxes",
                48 * 60 * 1000,
                20,
                1,
                NORMAL,
                PIERCING,
                "Carry boxes around the clockworks and in Haven with the other knights."
        ));
        
        register(new Mission(
                "Night Shift",
                6 * 60 * 60 * 1000,
                1000,
                5,
                ELEMENTAL,
                SHADOW,
                "Keep watch over Haven. All night long. Sleep during the day like a vampire."
        ));
        
        register(new Mission(
                "Geotech Intern",
                60 * 60 * 1000,
                0,
                1,
                NONE,
                NONE,
                "Work for free helping the geology department. How kind of you."
        ){
            public void bonus(UserData UD){
                Launcher.PM("Your hard work as an intern pays off- your superior lets you take home 200 of each mineral.",Long.parseLong(UD.ID));
                UD.minerals.getData()[0]+=200;
                UD.minerals.getData()[1]+=200;
                UD.minerals.getData()[2]+=200;
                UD.minerals.getData()[3]+=200;
                UD.minerals.getData()[4]+=200;
            }
            
            public boolean eligible(UserData UD){
                if(UD.gear.getData().contains("Shard Bomb"))return true;
                if(UD.gear.getData().contains("Dark Matter Bomb"))return true;
                if(UD.gear.getData().contains("Splinter Bomb"))return true;
                if(UD.gear.getData().contains("Crystal Bomb"))return true;
                
                return false;
            }
        });
        
        register(new Mission(
                "Geotech",
                90 * 60 * 1000,
                50,
                3,
                NONE,
                NONE,
                "Study the minerals out in the field. Get paid. Get minerals."
        ){
            public void bonus(UserData UD){
                Launcher.PM("Your hard work as a geotech pays off- your superior lets you take home 500 of each mineral.",Long.parseLong(UD.ID));
                UD.minerals.getData()[0]+=500;
                UD.minerals.getData()[1]+=500;
                UD.minerals.getData()[2]+=500;
                UD.minerals.getData()[3]+=500;
                UD.minerals.getData()[4]+=500;
            }
            
            public boolean eligible(UserData UD){
                if(UD.missions.getData().contains("Geotech Intern")){
                    if(UD.gear.getData().contains("Heavy Shard Bomb"))return true;
                    if(UD.gear.getData().contains("Heavy Dark Matter Bomb"))return true;
                    if(UD.gear.getData().contains("Heavy Splinter Bomb"))return true;
                    if(UD.gear.getData().contains("Heavy Crystal Bomb"))return true;
                    if(UD.gear.getData().contains("Shocking Salt Bomb"))return true;
                    if(UD.gear.getData().contains("Radiant Sun Shards"))return true;
                }
                return false;
            }
        });
        
        register(new Mission(
                "Den Defaunation",
                30 * 60 * 1000,
                30,
                1,
                PIERCING,
                ELEMENTAL,
                "Go deep into a Wolver Den and kill them all. Alphas, Wolvers, even the children."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[BEAST]+=10;
                    UD.materials.write();
                    Launcher.PM("With unfettered glee, you kill every single wolver in the den, delighting in their cries of terror and pain. You get 10 beast mats.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[BEAST]+=3;
                    UD.materials.write();
                    Launcher.PM("The wolvers manage to fight you off their den, but not without you killing a respectable number of them. You get 3 beast mats.",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("Your bleeding heart gets the better of you, and you pet the wolvers. The wolvers proceed to make that idiom literal... you barely survive, and are given the energy out of pitty..",Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(Gear.damageType.get(UD.A.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.PIERCING){
                    return true;
                }
                
                return false;
            }
        });
        
        register(new Mission(
                "Fiendish Friends",
                30 * 60 * 1000,
                30,
                1,
                PIERCING,
                SHADOW,
                "Explore the fiendish office space and slay anything you find in the name of the Spiral Order."
        ){
            public void bonus(UserData UD){
                if(oRan.nextInt(1000) < 100){
                    UD.materials.getData()[FIEND]+=10;
                    UD.materials.write();
                    Launcher.PM("A lone stapler, fallen from a nearby desk lies on the floor silently. The only sign of life. +10 fiend mats.",Long.parseLong(UD.ID));
                } else if(oRan.nextInt(1000) < 300){
                    UD.materials.getData()[FIEND]+=3;
                    UD.materials.write();
                    Launcher.PM("The fiendish foes hurl stapler after chair after mug at you.. you're able to slay most of them, but a few get away. +3 Fiend mats",Long.parseLong(UD.ID));
                } else {
                    Launcher.PM("You run into the middle of the pack only to realize your grim mistake... you manage to make it out alive, but not without consequence.",Long.parseLong(UD.ID));
                }
            }
            
            public boolean eligible(UserData UD){
                if(Gear.damageType.get(UD.A.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.B.getData()) == Gear.PIERCING){
                    return true;
                }
                
                if(Gear.damageType.get(UD.C.getData()) == Gear.PIERCING){
                    return true;
                }
                
                return false;
            }
        });
        
        
        
        Thread T = new Thread(){
            public void run(){
                while(true){
                    try{
                        sleep(15000);
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
        int t = UD.maxStars.getData();
        String miss = missionNames.get(oRan.nextInt(missionNames.size()));
        Mission M = missions.get(miss);
        
        int tries = 100;
        while(tries > 0){
            tries = tries - 1;
            
            if(UD.missions.getData().contains(miss)){
                miss = missionNames.get(oRan.nextInt(missionNames.size()));
                M = missions.get(miss);
            } else {
                if(t >= M.tier && M.eligible(UD)){
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
    
    
    public boolean eligible(UserData UD){
        return true;
    }
    
    public void checkRank(UserData UD){
        int above = UD.maxStars.getData();
        
        int a = Gear.star.get(UD.A.getData());
        int b = Gear.star.get(UD.B.getData());
        int c = Gear.star.get(UD.C.getData());
        int d = Gear.star.get(UD.armor.getData());
        int e = Gear.star.get(UD.helmet.getData());
        int f = Gear.star.get(UD.shield.getData());
        
        if(a>above&&b>above&&c>above&&d>above&&e>above&&f>above){
            UD.maxStars.append(1);
            Launcher.PM("Congratulations! You've advanced to rank "+(above+1)+"!",Long.parseLong(UD.ID));
        }
    }
}
