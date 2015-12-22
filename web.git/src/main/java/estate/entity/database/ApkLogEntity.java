package estate.entity.database;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
public class ApkLogEntity
{
    private int id;
    private String versionCode;
    private String apkName;
    private String description;
    private Long uploadTime;


    public String getVersionCode()
    {
        return versionCode;
    }

    public void setVersionCode(String versionCode)
    {
        this.versionCode = versionCode;
    }

    public String getApkName()
    {
        return apkName;
    }

    public void setApkName(String apkName)
    {
        this.apkName = apkName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApkLogEntity that = (ApkLogEntity) o;

        if (id != that.id) return false;
        if (versionCode != null ? !versionCode.equals(that.versionCode) : that.versionCode != null) return false;
        if (apkName != null ? !apkName.equals(that.apkName) : that.apkName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (uploadTime != null ? !uploadTime.equals(that.uploadTime) : that.uploadTime != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (versionCode != null ? versionCode.hashCode() : 0);
        result = 31 * result + (apkName != null ? apkName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        return result;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
