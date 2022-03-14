package com.blaife.jsch;

import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.FilterInputStream;
import java.io.IOException;

/**
 * @Description: 远程测试
 * @Author: magd39318
 * @Date: 2021/11/11 17:11
 */
public class RemoteTest {

    /**
     * 存在一些问题，使用 junit测试方法启动时，登陆成功后程序会直接中断，而在main方法中启动可以正常操作
     * 启动并填入账号密码后会提示输入 用户名 和 口令是什么， （不论输入什么都可以正常操作）
     * @param args
     * @throws JSchException
     */
    public static void main(String[] args) throws JSchException {
        JSch jSch = new JSch();

        String host = JOptionPane.showInputDialog("Enter hostname", "47.111.234.189");
        int port = 22;
        String user = "root";
        Session session = jSch.getSession(user, host, port);
        String password = JOptionPane.showInputDialog("Enter password","52BlackKnife");
        session.setPassword(password);
        session.setUserInfo(new MyUserInfo());
        session.connect(30000);
        Channel channel = session.openChannel("shell");
        channel.setInputStream(new FilterInputStream(System.in) {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return in.read(b, off, (len > 1024 ? 1024 : len));
            }
        });
        channel.setOutputStream(System.out);
        //去除控制台彩色输出
        ((ChannelShell) channel).setPtyType("vt102");
        channel.connect(3 * 1000);
    }

}
