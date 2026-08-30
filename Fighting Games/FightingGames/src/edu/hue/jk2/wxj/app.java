package edu.hue.jk2.wxj;

import edu.hue.jk2.wxj.ui.FightingGame;
import edu.hue.jk2.wxj.ui.Login;

public class app {
    public static void main(String[] args) {
        //本类只是一个启动类
       /* Login l = new Login();
        l.start();*/
        FightingGame fg = new FightingGame();
        fg.gameStart("wanwan");

    }

}
