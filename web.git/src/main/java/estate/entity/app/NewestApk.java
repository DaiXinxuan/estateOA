package estate.entity.app;

import estate.common.Config;
import estate.entity.database.ApkLogEntity;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
public class NewestApk
{
    private String path;
    private String versionCode;
    private String description;

    public NewestApk(){}

    public NewestApk(ApkLogEntity apkLogEntity)
    {
        this.description=apkLogEntity.getDescription();
        this.versionCode=apkLogEntity.getVersionCode();
    }


    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getVersionCode()
    {
        return versionCode;
    }

    public void setVersionCode(String versionCode)
    {
        this.versionCode = versionCode;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
