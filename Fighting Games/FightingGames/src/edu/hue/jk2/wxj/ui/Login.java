package edu.hue.jk2.wxj.ui;

import edu.hue.jk2.wxj.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        Scanner sc = new Scanner(System.in);
        //输入用户名
        System.out.println("请输入用户名：");
        String username = sc.next();
        //检查用户名,不存在
        if (!contains(list, username)) {
            System.out.println("用户名不存在" + username + ",请先注册");
            return;
        }
        //用户名存在,检验是否锁定
        int index = findIndex(list, username);
        User u = list.get(index);
        if (u.isStatus()){
            System.out.println("用户" + username + "已锁定，请联系管理员");
            return;
        }


        for (int i = 0; i < 3; i++) {
            while (true) {
                //输入验证码
                String rightCode = getCode();
                System.out.println("正确的验证码为" + rightCode);
                System.out.println("请输入验证码：");
                String code = sc.next();
                if (rightCode.equalsIgnoreCase(code)){
                    System.out.println("验证码正确");
                    break;
                }else {
                    System.out.println("验证码错误");
                    continue;
                }
            }
            //输入密码
            System.out.println("请输入密码：");
            String password = sc.next();
            String rightPassword = u.getPassword();
            if (password.equals(rightPassword)) {
                System.out.println("登陆成功,游戏启动");
                break;
            } else {
                System.out.println("登陆失败,密码错误");
                if (i == 2){
                    System.out.println("三次机会已用完,用户" + username + "已锁定，请联系管理员");
                    u.setStatus(true);
                    return;
                }else {
                    System.out.println("还剩" + (2 - i) + "次机会");
                }


            }
        }


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

    //生成验证码
    public static String getCode() {
        /*
        验证码规则：
        长度为5
        由4位大写或者小写字母和1位数字组成，同一个字母可重复
        数字可以出现在任意位置
        比如：aQa1K
        */
        //把所有大小写字母存到一个容器里面
        ArrayList<Character> list = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            list.add(c);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            list.add(c);
        }
        //从集合中随机抽取字母(四次)
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        //添加任意位置的数据
        //这里的思路是在最后一位添加一个随机数字,然后交换最大索引和随机索引的位置
        //先随机生成数字
        int num = r.nextInt(10);
        sb.append(num);
        //再把sb变为字符数组
        char[] chars = sb.toString().toCharArray();
        //交换最大索引和随机索引的位置
        int index = r.nextInt(chars.length);
        char temp = chars[chars.length - 1];
        chars[chars.length - 1] = chars[index];
        chars[index] = temp;
        //把字符数组变为字符串
        String s = new String(chars);
        return s;


    }

    //查找username所在的索引
    public int findIndex(ArrayList<User> list,String Username){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(Username)) {
                System.out.println("找到用户名" + Username + "，索引为" + i);
                return i;
            }

        }
        return -1;

    }


}
