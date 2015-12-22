package estate.entity.database;

/**
 * Created by dxx on 2015/12/17.
 */
public class VertifyInfoEnity {
    private String phone;
    private String id;
    private String name;
    private Byte status;
    private int type;
    private String imageIdList;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(String imageIdList) {
        this.imageIdList = imageIdList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
