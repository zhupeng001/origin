package wonder.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by apple on 2018/1/20.
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {
    private int id;
    private String openId;
    private String userName;
    private String mobile;
    private String sex;
    private String address;
    public User(){}
    public User(String openId, String userName, String mobile, String sex, String address) {
        this.openId = openId;
        this.userName = userName;
        this.mobile = mobile;
        this.sex = sex;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ" ,allocationSize = 1,sequenceName = "USER_SEQ")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "OPEN_ID")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    @Column(name= "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
