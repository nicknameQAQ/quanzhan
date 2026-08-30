package edu.hue.jk2.wxj.ui;

import edu.hue.jk2.wxj.domain.EnemyCharacter;
import edu.hue.jk2.wxj.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Scanner;

public class FightingGame {
    //启动游戏
    public void gameStart(String username){

        //1.显示游戏标题
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("🎮 "+username+"欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════════════╝");

        //2.创建玩家角色(名字+属性分配)
        HeroCharacter player = creatPlayerCharacter(username);

        //3.显示创建角色信息以及技能列表
        System.out.println("角色创建成功");
        System.out.println("\uD83C\uDF1F 初始属性:" + player.show());
        System.out.println("\uD83C\uDF1F 拥有技能:" + player.showSkills());

        //4.创建多个敌人列表
        //敌人名称	生命值	攻击力	防御力	技能（变量）
        //初级战士	80	15	10	猛击（150%伤害）
        //敏捷刺客	60	20	5	快速攻击（2次50%伤害）
        //重装坦克	120	10	20	防御姿态（下回合伤害减半） buff（ boolean defendding）
        //神秘法师	70	25	8	火球术（180%伤害）
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));



    }

    //作用:用来创建一个角色
    //参数:用户名
    public HeroCharacter creatPlayerCharacter(String username){
        System.out.println("创建您的角色");
        System.out.println("您的角色名为" + username);
        /*
        请分配属性点 (共20点):
        1. 生命值 (每点+10 HP)
        2. 攻击力 (每点+2 ATK)
        3. 防御力 (每点+1 DEF)
        */
/*        //属性分配
        int points = 20;
        System.out.println("请分配属性点 (共20点)");
        //分配生命值
        System.out.println("1. 生命值 (每点+10 HP)");
        Scanner sc = new Scanner(System.in);
        int hpPoint = sc.nextInt();
        if (hpPoint < 0){
            System.out.println("无效输入,默认为0点");
            hpPoint = 0;
        }
        if (hpPoint > points){
            System.out.println("属性点不足!剩余属性点全部分配给生命值");
            hpPoint = points;
            points = 0;
        }
        points -= hpPoint;
        //分配攻击力
        System.out.println("2. 攻击力 (每点+2 ATK)");
        int atkPoint = sc.nextInt();
        if (atkPoint < 0){
            System.out.println("无效输入,默认为0点");
            atkPoint = 0;
        }
        if (atkPoint > points){
            System.out.println("属性点不足!剩余属性点全部分配给攻击力");
            atkPoint = points;
            points = 0;
        }
        points -= atkPoint;
        //分配防御力
        System.out.println("3. 防御力 (每点+1 DEF)");
        int defPoint = sc.nextInt();
        if (defPoint < 0){
            System.out.println("无效输入,默认为0点");
            defPoint = 0;
        }
        if (defPoint > points){
            System.out.println("属性点不足!剩余属性点全部分配给防御力");
            defPoint = points;
            points = 0;
        }
        points -= defPoint;
        return new HeroCharacter(username, 100 + hpPoint * 10, 5 + atkPoint * 2, 5 + defPoint);*/
        /*
        请分配属性点 (共20点):
        1. 生命值 (每点+10 HP)
        2. 攻击力 (每点+2 ATK)
        3. 防御力 (每点+1 DEF)
        */
        int points = 20;
        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");
        Scanner sc = new Scanner(System.in);

        //定义数组把要提示的语句存起来
        String[] attributes = {"生命值","攻击力","防御力"};

        //定义数组记录属性点
        int[] value = new int[3];

        //利用一个循环分配属性点
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到" + attributes[i] + "(剩余点数: " + points + "):");
            int input = sc.nextInt();
            if(input < 0){
                System.out.println("无效输入,默认为0点");
                input = 0;
            }
            if (input > points){
                System.out.println("属性点不足!剩余属性点全部分配给" + attributes[i]);
                input = points;
                points = 0;
            }
            points -= input;
            value[i] = input;

        }

        //已经知道了用户分配的属性点
        HeroCharacter player = new HeroCharacter(
                username,//角色名
                value[0] * 10 + 100,// 生命值
                value[1] * 2 + 5,// 攻击力
                value[2] + 5// 防御力
        );
        /*技能名称	消耗	    效果	                    描述
          普通攻击	无	    造成基础伤害	          标准攻击方式
          强力一击	10HP	造成180%攻击力的伤害	  高伤害但消耗生命
          生命汲取	10HP	恢复0-20点生命值	      风险回报型恢复技能
*/
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        return player;
    }

}
