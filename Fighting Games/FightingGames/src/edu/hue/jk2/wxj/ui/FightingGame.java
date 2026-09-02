package edu.hue.jk2.wxj.ui;

import edu.hue.jk2.wxj.domain.EnemyCharacter;
import edu.hue.jk2.wxj.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
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

        //5.准备战斗
        //定义和第几个敌人战斗
        int count = 1;
        //定义胜场数
        int wins = 0;
        while (player.isAlive()){

            //5.1重置敌人的属性，敌人属性每场HP+10, ATK+3, DEF+2（敌人：越来越打）（第二场的时候）
            if (wins != 0){
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    //每场HP+10
                    c.maxHP += 10;
                    //ATK+3
                    c.attack += 3;
                    //DEF+2
                    c.defense += 2;
                    //战斗之前清空减伤buff
                    c.defending = false;
                    //重置血量
                    c.HP = c.maxHP;
                }
            }

            //5.2随机选择敌人(Random)
            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println("第" + count + "场战斗: " + enemy.show());

            //5.3开始跟抽取到的敌人进行战斗
            System.out.println("════════════════════════════════════════");
            System.out.println("⚔\uFE0F 第 " + count + " 场战斗开始！对手: " + enemy.name + "\n");

            //定义回合数
            int round = 1;

            //和一个敌人战斗循环
            while (player.isAlive()){
                System.out.println("════════════════════════════════════════");
                System.out.println("⚔\uFE0F 第 " + round + " 回合开始！对手: ");
                System.out.println(getBloodBar(player.name, player.HP, player.maxHP));
                System.out.println(getBloodBar(enemy.name, enemy.HP, enemy.maxHP));

                //5.4玩家回合：选择行动（ 1普通攻击/ 2强力一击/ 3生命汲取 ）
                playerturn(player, enemy);

                //5.5 判断敌人是否被击败（判断敌方血量是否为0）
                if (!enemy.isAlive()){
                    System.out.println("🎉 你击败了 " + enemy.name + "！)");
                    wins++;
                    break;
                }else {

                    //5.6敌人回合：选择行动（ 50%的几率普通攻击 / 50%的几率技能攻击 / 不同的敌人采取不同的技能进行攻击）
                    enemyturn(enemy, player);

                    //5.7判断玩家是否被击败（判断我方血量是否为0）
                    if (!player.isAlive()){
                        System.out.println("💀 你被 " + enemy.name + " 击败了！");
                        break;
                    }

                    //5.8判断玩家是否被击败（判断玩家血量是否为0）
                    if(player.isAlive()){
                        round++;
                    }

                }
            }

            //跟一个敌人的战斗结束后，玩家胜利（恢复生命值继续战斗）玩家失败（游戏停止）
            if (player.isAlive()){
                /*
                    胜利时：
                    恢复20-40点生命值
                    胜场数+1
                    每3胜获得属性提升
                    失败时：游戏结束
             */
                //恢复玩家血量
                int healblood = r.nextInt(21) + 20;
                player.heal(healblood);
                System.out.println("🤰 你恢复了 " + healblood + " 点生命值！");
                System.out.println("🏆 当前胜场: " + wins);
            }

            //5.9 每3胜提升属性
            if (player.isAlive() && wins % 3 == 0 && wins > 0) {
                /*
                HP每三胜提升30点
                ATK每三胜提升5点
                DEF每三胜提升3点
                */
                player.maxHP += 30;
                player.attack += 5;
                player.defense += 3;
                System.out.println("🍼 你获得了属性提升！");
                System.out.println("🚨 最大生命值提升30点！");
                System.out.println("🍄‍🟫 攻击力提升5点！");
                System.out.println("🍻 防御力提升3点！");
                System.out.println(player.show());
            }

            //5.10 询问是否继续战斗
            if (player.isAlive()){
                System.out.println("════════════════════════════════════════");
                System.out.println("是否继续战斗？ y/n");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if ("y".equalsIgnoreCase(choice)){
                    count ++;
                    continue;
                }else if ("n".equalsIgnoreCase(choice)){
                    System.out.println("游戏结束，感谢游玩！");
                    break;
                }else {
                    System.out.println("没有这个选项,默认继续战斗");
                    continue;
                }
            }
        }

        //6.整个游戏结束，最终结算
        System.out.println("😭😭😭😭😭😭😭😭😭😭😭😭😭😭");
        System.out.println("游戏结束，感谢游玩！");
        System.out.println("🏆 最终胜场: " + wins);
        //停止虚拟机的运行
        System.exit(0);

    }

    //敌人回合
    public void enemyturn(EnemyCharacter enemy, HeroCharacter player){
        System.out.println("===== " + enemy.name + " 的回合 =====");

        //设定默认攻击手段
        String action = "普通攻击";

        //随机选择攻击技能
        Random r = new Random();
        int num = r.nextInt(10);
        if (num >= 5){
            action = enemy.skill;
            System.out.println(enemy.name + " 使用了 " + action);
        }
        switch (action){
            case "普通攻击" -> {
                int damage1 = caculateDamage(enemy.attack, player.defense);
                System.out.println("⚔️ " + enemy.name + " 对 " + player.name + " 使用了普通攻击，造成 " + damage1 + " 点伤害！");
                player.takeDamage(damage1);
            }
            case "猛击" -> {
                int damage2 = caculateDamage((int)(enemy.attack * 1.5), player.defense);
                System.out.println("⚔️ " + enemy.name + " 对 " + player.name + " 使用了猛击，造成 " + damage2 + " 点伤害！");
                player.takeDamage(damage2);
            }
            //快速攻击太牢了,后期考虑可以挂流血buff,或者破防
            case "快速攻击" -> {
                int damage3 = caculateDamage(enemy.attack, player.defense);
                int temp = caculateDamage(enemy.attack, player.defense);
                damage3 += temp;
                System.out.println("⚔️ " + enemy.name + " 对 " + player.name + " 使用了快速攻击，造成 " + damage3 + " 点伤害！");
                player.takeDamage(damage3);
            }
            case "火球术" -> {
                int damage4 = caculateDamage((int)(enemy.attack * 1.8), player.defense);
                System.out.println("🔥 "  + enemy.name + " 对 " + player.name + " 使用了火球术，造成 " + damage4 + " 点伤害！");
                player.takeDamage(damage4);
            }
            case "防御姿态" -> {
                enemy.defending = true;
                System.out.println("🛡️ " + enemy.name + " 使用了防御姿态，下回合伤害减半！");
            }
        }

    }

    //玩家回合
    public void playerturn(HeroCharacter player, EnemyCharacter enemy){
        /*===== 你的回合 =====
        1. 普通攻击
        2. 强力一击 (消耗10HP)
        3. 生命汲取 (消耗10HP，恢复生命)*/
        System.out.println("===== 你的回合 =====\n");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击");
        System.out.println("3. 生命汲取");
        System.out.println("请选择行动：1 ~ 3");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1 -> {
                System.out.println("你选择了普通攻击");
                int damage1 = caculateDamage(player.attack, enemy.defense);
                System.out.println("⚔️ 你对 "+enemy.name + " 使用了普通攻击，造成 " + damage1 + " 点伤害！");
                enemy.takeDamage(damage1);
            }
            case 2 -> {
                System.out.println("你选择了强力一击");
                if (player.HP >= 10){
                    int damage2 = caculateDamage((int)(player.attack * 1.8), enemy.defense);
                    //消耗我方10点
                    player.takeDamage(10);
                    System.out.println("💥 消耗10HP，你对 "+enemy.name + " 使用了强力一击，造成 " + damage2 + " 点伤害！");
                    enemy.takeDamage(damage2);
                }else System.out.println("你的生命值不足，无法使用强力一击");
            }
            case 3 -> {
                System.out.println("你选择了生命汲取");
                if (player.HP > 10){
                    //消耗我方10点
                    player.takeDamage(10);
                    //随机恢复
                    Random r = new Random();
                    int healamount = r.nextInt(21);
                    System.out.println("💚 消耗10HP，恢复 " + healamount + " 点生命！");
                    player.heal(healamount);
                }else System.out.println("你的生命值不足，无法使用生命汲取");
            }
            default -> System.out.println("无效输入，请重新选择");
        }



    }

    //计算伤害
    public int caculateDamage(int attact,int defense){
        int damage = attact - defense;
        if (damage > 1){
            return damage;
        }
        //最小受到1点伤害
        return 1;
    }

    //显示血条
    public String getBloodBar(String name,int HP, int maxHP){
        //定义最大血块
        int barLength = 20;
        //定义应该填满的数量
        int fill = (int) (barLength * HP / maxHP * 1.0);
        /*//计算血块数量
        int bar = (int) (barLength*HP/maxHP*1.0);
        //计算空白数量
        int empty = barLength - bar;*/
        //字符串拼接
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < barLength; i++) {
            if (i < fill) sb.append("█");
            else sb.append(" ");
        }
        /*for (int i = 0; i < bar; i++) {
            sb.append("█");
        }
        for (int i = 0; i < empty; i++) {
            sb.append(" ");
        }*/
        sb.append("]").append(HP).append("/").append(maxHP);
        return sb.toString();
    }

    //作用:用来创建一个角色,参数:用户名
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
