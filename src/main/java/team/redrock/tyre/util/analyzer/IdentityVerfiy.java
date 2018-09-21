package team.redrock.tyre.util.analyzer;

import org.jsoup.nodes.Document;

public class IdentityVerfiy {
    public boolean identityVerify(Document document) {
        if (document.body().text().equals("未查到匹配学生信息..")) {
            return false;
        } else {
            return true;
        }
    }
}
