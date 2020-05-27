package com.example.tracktools.bean;

import java.io.Serializable;

public class ServerBean extends BaseBean implements Serializable {
    private String desc;//服务描述
    private String name;//服务名字
    private String sid;//服务id

    public ServerBean(String desc, String name, String sid) {
        this.desc = desc;
        this.name = name;
        this.sid = sid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
