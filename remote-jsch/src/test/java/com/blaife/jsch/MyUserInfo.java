package com.blaife.jsch;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import javax.swing.*;

/**
 * @Description: 我的用户信息 - 用于远程测试
 * @Author: magd39318
 * @Date: 2021/11/11 17:16
 */
public class MyUserInfo implements UserInfo, UIKeyboardInteractive {
    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean promptPassword(String s) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String s) {
        return false;
    }

    @Override
    public boolean promptYesNo(String s) {
        Object[] options = {"yes", "no"};

        int foo = JOptionPane.showOptionDialog(null, s, "Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        return foo == 0;
    }

    @Override
    public void showMessage(String s) {
        JOptionPane.showMessageDialog(null, s);
    }

    @Override
    public String[] promptKeyboardInteractive(String s, String s1, String s2, String[] strings, boolean[] booleans) {
        return null;
    }
}
