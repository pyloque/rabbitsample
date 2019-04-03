package rabbitsample;

import com.alibaba.fastjson.JSON;

public class Notify {

    public final static String TYPE_USER = "user";
    public final static String TYPE_BOOK = "book";
    public final static String TYPE_ORDER = "order";

    private String type;
    private String title;
    private String content;

    public Notify() {}

    public Notify(String type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
