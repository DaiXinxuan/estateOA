package estate.entity.statis;

/**
 * Created by kangbiao on 15-11-2.
 * 扇形图数据实体类
 */
public class PieData
{
    private float value;
    private String label;
    private String color;
    private String highlight;

    public float getValue()
    {
        return value;
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getHighlight()
    {
        return highlight;
    }

    public void setHighlight(String highlight)
    {
        this.highlight = highlight;
    }
}
