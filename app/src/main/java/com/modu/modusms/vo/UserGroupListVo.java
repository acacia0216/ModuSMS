package com.modu.modusms.vo;

import java.util.List;

public class UserGroupListVo {
    private List<ModuGroupVo> groupList;

    public UserGroupListVo() {
    }

    public UserGroupListVo(List<ModuGroupVo> groupList) {
        this.groupList = groupList;
    }

    public List<ModuGroupVo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<ModuGroupVo> groupList) {
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        return "UserGroupListVo{" +
                "groupList=" + groupList +
                '}';
    }
}
