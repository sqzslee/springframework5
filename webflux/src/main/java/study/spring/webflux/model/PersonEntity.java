package study.spring.webflux.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author: lihong58
 * @date: 2017/11/1
 * description: 个人信息实体
 */
@Document(collection = "person")
public class PersonEntity {

    private ObjectId id;

    private String name;

    private String mobile;

    private String card;

    public PersonEntity() {
    }

    public PersonEntity(String name, String mobile, String card) {
        this.id = new ObjectId();
        this.name = name;
        this.mobile = mobile;
        this.card = card;
    }

    public PersonEntity(ObjectId id, String name, String mobile, String card) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.card = card;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
