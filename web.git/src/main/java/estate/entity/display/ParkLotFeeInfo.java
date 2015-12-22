package estate.entity.display;

import estate.entity.database.FeeItemEntity;
import estate.entity.json.ParkLotExtra;

/**
 * Created by kangbiao on 15-10-16.
 * 包含完整的停车费信息的实体类,为适应后台显示
 */
public class ParkLotFeeInfo extends FeeItemEntity
{
    private ParkLotExtra parkLotExtra;

    public ParkLotExtra getParkLotExtra()
    {
        return parkLotExtra;
    }

    public void setParkLotExtra(ParkLotExtra parkLotExtra)
    {
        this.parkLotExtra = parkLotExtra;
    }
}
