package com.ninetripods.sydialoglib.manager;


import com.ninetripods.sydialoglib.SYDialog;

/**
 * 管理多个dialog 按照dialog的优先级依次弹出
 * Created by mq on 2018/9/16 下午9:44
 * mqcoder90@gmail.com
 */

public class DialogWrapper {

    private SYDialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(SYDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public SYDialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(SYDialog.Builder dialog) {
        this.dialog = dialog;
    }

}
