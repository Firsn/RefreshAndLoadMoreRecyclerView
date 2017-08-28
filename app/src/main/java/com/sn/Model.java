package com.sn;

/**
 * Created by 16Fan.Saila2007 on 2017/8/25.
 */

public class Model {
    private String type_="";
    private String content_="";
    private int codeViewType=0;

    public String getType_() {
        return type_;
    }

    public void setType_(String type_) {
        this.type_ = type_;
    }

    public String getContent_() {
        return content_;
    }

    public void setContent_(String content_) {
        this.content_ = content_;
    }

    public int getCodeViewType() {
        return codeViewType;
    }

    public void setCodeViewType(int codeViewType) {
        this.codeViewType = codeViewType;
    }
}
