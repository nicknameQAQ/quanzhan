package edu.hue.jk2.wxj.ui;

import edu.hue.jk2.wxj.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login {
    // 登录界面
    public void start() {
        ArrayList<User> list = new ArrayList<>();
        System.out.println("游戏的登陆注册页面打开了~");
        //快捷键ctrl + alt + t,包裹代码
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            switch (choice) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);
                    //0表示正常退出，非0表示异常退出
                }
                default -> System.out.println("输入有误，请重新选择");
            }
        }
    }

    // 登录逻辑
    public void login(ArrayList<User> list) {
        System.out.println("用户选择了登陆操作");
    }

    //注册逻辑
    public void register(ArrayList<User> list) {
        System.out.println("用户选择了注册操作");
        //什么叫注册?
        //键盘录入用户名和密码 -> 创建用户对象 -> 添加到集合中
        //1.创建User对象
        User u = new User();
        //2.键盘录入用户名,检验用户名是否合规
        Scanner sc = new Scanner(System.in);
        /*
        用户名username：
        用户名唯一
        长度必须在3 ~ 16位
        只能由字母、数字组成，不能是纯数字*/
        //开发细节
        //一:先验证格式,在验证唯一,因为数据都存在数据库里(联网)
        //二:先判断异常的数据,剩下的都是正确的数据,避免if嵌套
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();
            //判断长度
            if (!checkLen(3, 16, username)) {
                System.out.println("用户名长度必须在3 ~ 16位之间");
                continue;
            }
            //判断格式,只能由字母、数字组成，不能是纯数字
            if (!checkUsername(username)) {
                System.out.println("用户名格式不合规，请重新输入");
                continue;
            }
            //判断用户名是否唯一
            if (contains(list, username)) {
                System.out.println("用户名已存在，请重新输入");
                continue;
            }
            //当代码执行到这里，说明用户名符合要求
            u.setUsername(username);
            break;


        }
        //3.键盘录入密码,检验代码是否合规
        while (true) {
            //密码键盘输入两次，两次一致才可以进行注册
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();
            //长度3 ~ 8位
            if (!checkLen(3, 8, password1)) {
                System.out.println("密码长度必须在3 ~ 8位之间");
                continue;
            }
            //只能是字母加数字的组合，不能有其他符号
            if (!checkPassword(password1)) {
                System.out.println("密码只能由字母和数字组成，不能有其他符号");
                continue;
            }
            //检验两次密码输入是否一致
            if(!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致，请重新输入");
                continue;
            }
            u.setPassword(password1);
            break;
        }

        //4.添加到集合中
        list.add(u);
        //5.提示成功
        System.out.println("用户" + u.getUsername() + "注册成功！");

    }
    //统计字母,数字,其他字符的个数
    //由于要返回三个值,所以用数组存储
    public int[] getCount(String userInfo) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;
        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return new int[]{charCount, numCount, otherCount};
    }

    //检测格式,只能由字母、数字组成，不能是纯数字,不能有其他字符
    public boolean checkUsername(String username) {
        int[] arr = getCount(username);
        //对三个变量进行判断
        return arr[0] > 0 && arr[1] >= 0 && arr[2] == 0;
    }
    //检测密码
    public boolean checkPassword(String password){
        int[] arr = getCount(password);
        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;
    }

    //检测长度
    public boolean checkLen(int minLen, int maxLen, String str) {
        return str.length() >= minLen && str.length() <= maxLen;
    }

    //看用户名在集合中是否包含
    public boolean contains(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getUsername().equals(username)) {
                return true;
            }

        }
        return false;
    }


}
