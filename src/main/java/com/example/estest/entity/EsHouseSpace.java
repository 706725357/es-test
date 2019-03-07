package com.example.estest.entity;

import com.example.estest.annotation.EsMappings;
import com.example.estest.enums.EnumFieldTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 房源索引
 * @program: sayyoo-report
 * @author: ruanxupeng
 * @create: 2018-04-28 15:57
 **/
@Getter
@Setter
@ToString
public class EsHouseSpace implements Serializable {

    /**
     * 房源的id
     */
    @EsMappings(objectName = "houseSpaceId",fieldType = EnumFieldTypes.KEYWORD)
    private String houseSpaceId;


    /**
     * 整套房子的id
     */
    @EsMappings(objectName = "houseSpaceManagerId",fieldType = EnumFieldTypes.KEYWORD)
    private String houseSpaceManagerId;


    /**
     *建筑面积
     */
    private Integer buildingArea;

    /**
     * 收房价格
     */
    private Integer getPrice;

    /**
     * 建筑年代
     */
    private Integer buildYear;

    /**
     * 所属公司id
     */
    private String companyId;
    /**
     * 小管家id
     */
    private String housekeeperId;
    /**
     * 小管家姓名
     */
    private String housekeeperName;
    /**
     * 小管家手机号
     */
    private String housekeeperPhone;

    /**
     * 所属房东id
     */
    private String landlordId;

    /**
     * 房东的姓名
     */
    private String landlordName;


    /**
     * 房东的手机号
     */
    private String landlordPhone;


    /**
     * 租客id
     */
    private String tenantId;


    /**
     * 租客姓名
     */
    private String tenantName;

    /**
     * 租客手机
     */
    private String tenantPhone;

    /**
     * 租客身份证号
     */
    private String tenantCertNumber;

    /**
     * 租客身份证类型EnumCertType
     */
    private String tenantCertType;

    /**
     * 录入人员id
     */
    private String inputPersonnelId;
    /**
     * 录入人员名字
     */
    private String inputPersonnelName;
    /**
     * 录入人员手机号
     */
    private String inputPersonnelPhone;
    /**
     * 房源的评价的内容
     */
    private String commentContent;

    /**
     * 当前评价的赞数(星数)
     */
    private Integer starts;
    /**
     * 具体的房源的类型EnumHouseSpaceManagerType
     */
    private String houseSpaceManagerType;

    /**
     * 保险情况EnumInsuranceStatus
     */
    private String insuranceStatus;

    /**
     * 省
     */
    private String province;


    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String road;

    /**
     * 小区名称
     */
    private String blockName;

    /**
     * 栋号
     */
    private String buildingNo;

    /**
     * 单元号
     */
    private String unitNo;

    /**
     * 室号
     */
    private String roomNo;
    
    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;
    /**
     *房间数量
     */
    private Integer countNumber;

    /**
     * 片区
     */
    private String zone;

    /**
     * 片区id
     */
    private String zoneId;


    /**
     * 总楼高
     */
    private Integer floorHeight;

    /**
     * 当前楼高
     */
    private Integer currentFloor;

    /**
     * 装修类型EnumDecorateType
     */
    private String decorateType;

    /**
     * 装修时间
     */
    private Date decorateTime;

    /**
     * 卧室数
     */
    private Integer bedroomNumber;

    /**
     * 客厅数
     */
    private Integer livingRoomNumber;

    /**
     * 厨房数
     */
    private Integer kitchenNumber;

    /**
     * 卫生间数
     */
    private Integer toiletNumber;

    /**
     * 具体的房间名称(A、B、C等)
     */
    private String houseSpaceName;


    /**
     * 拼接了片区、几栋几单元几室以及A、B、C信息
     */
    private String keywordAddress;

    /**
     * 拼接了省市区、几栋几单元几室以及A、B、C信息
     */
    private String keywordAddressGeographic;

    /**
     * 朝向EnumDirection
     */
    private String direction;

    /**
     * 套房面积
     */
    private Integer houseSpaceManagerArea;

    /**
     * 房源面积
     */
    private Integer houseSpaceArea;


    /**
     * 出租指导价
     */
    private Integer rentalPrice;


    /**
     * 实际出租价
     */
    private Integer actualRentalPrice;


    /**
     * 房源描述
     */
    private String houseSpaceDescribe;


    /**
     * 房源编号
     */
    private String businessId;


    /**
     * 房源状态EnumHouseSpaceStatus
     */

    private String houseSpaceStatus;
    private Date startTimeStatus;
    private Date endTimeStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 空置开始时间
     */
    private Long idleStartTime;

    /**
     * 空置结束时间
     */
    private Long idleEndTime;

    /**
     * 空置时间
     */
    private Long idleDuration;

    /**
     * 出租类型（合租、整租）EnumRentType
     */
    private String rentType;
    /**
     * 资产类型(集中式、分散式)EnumPropertyType
     */
    private String propertyType;
    /**
     * md5
     */
    private String md5;

    /**
     * 房源的来源
     */
    private String houseSpaceSource;

    /**
     * 房源类型EnumHouseSpaceType
     */

    private String houseSpaceType;

    /**
     * 拼接了片区以及几栋几单元几室的信息
     */
    private String keywordAddressManager;

    /**
     * 拼接了省市区以及几栋几单元及时信息
     */
    private String keywordAddressManagerGeographic;

    /**
     * 整套房源的朝向EnumDirection
     */

    private String houseSpaceManagerDirection;

    /**
     * 预期出租价(在出租指导价上面加上服务费)
     */
    private Integer expectedRentalPrice;


    /**
     * 定金金额
     */
    private Integer frontMoneyAmount;

    /**
     * 小区id
     */
    private String blockId;

    /**
     * 规定的定金金额
     */
    private Integer regulationFrontMoneyAmount;


    /**
     * 房源的特色集合EnumHouseSpaceFeature
     */

    private List<String> houseSpaceFeatureList = new ArrayList<>();


    /**
     *房子所有权证书号
     */
    private String houseOwnershipCertificateNumber;
    /**
     *房子所有权证书类型
     */
    private String houseOwnershipCertificateType;

    /**
     *最初的出租类型的枚举EnumRentType
     */

    private String originalRentType;


}
