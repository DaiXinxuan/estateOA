title: VerPass接口设计文档
date: 2015-10-23 12:47:44
categories: interface
----------------

**2015-10-24修订记录**
1. 返回的物业信息增加该物业所属的楼栋信息
2. 删掉了投诉评价接口,只能上报投诉和查看投诉


**2015-10-23修订记录(多角色修改):**
1. 修改了获取用户角色接口,返回角色代码由原来的单数字字段变为逗号分隔的字符串 ,因为一个用户可能是业主或者租客
2. 修改了报修模块获取我的报修接口,数据有所增加.将图片路径直接返回,不需要作二次请求,同时增加返回维修人员信息
3. 新增确认报修完成接口,客户端可以确认该报修已经完成.需要确认完成后才能评论.具体参见评论报修接口说明
4. 新增上传开锁记录接口
5. 修改获取我的所有物业接口
6. 修改业主获取绑定用户接口
7. 修改获取一定数量的公告接口
8. 增加公告通过webview获取公告的接口(根据公告id获取公告详情)
9. 将申请绑定接口从用户中心模块移到物业模块,修改了接口访问地址


> 该文档为描述物业管理系统服务器端与APP端的数据交互。

> 文档里面参数的值为全大写的地方,值的范围只能是状态码对照表规定变量的范围之内,如:USER_ROLE只能为1,2或者3中的一个,否则服务器会返回参数异常错误.

> 文档里面的数据为举例数据。

> 接口基于restful风格的URL.

> —— [批注](mailto:kangbiao@kangbiao.org)(康彪)


>***编码规范***
>*错误代码*:
>web端错误代码规范:采用6位数字作为错误代码
>最高位为0,四五位为模块代码,二三位为错误详情,最后一位作为扩展.

>app端错误代码规范:采用6位数字作为错误代码
>最高两位为1,四五位为模块代码,二三位为错误详情,最后一位作为扩展,模块位全为0为程序异常
>例:000030:web端00模块的03方法发生错误.

>*数据格式*:
>数据格式基于json
>jsonString的值为空时为null,不会是空数组或者空字符串,基本格式如下:
```json
{
    "status": true/false,
    "errorMsg": {
        "code": null/string,
        "description": null/string
    },
    "jsonString": array or object or null or string or basictype
}
```
>如果status为false,客户端需要根据errorMsg进行相应的业务处理,如果为true,则从jsonString里面取出业务数据处理,展示

**状态码对照表**:

| 数据项 |变量|||||
|-------|--------|------|--|---|
|app用户状态|USER_STATUS|-1(禁用)|1(正常)|0(待审核)||
|app用户角色|USER_ROLE|1(家庭成员)|2(租户)|3(业主)||
|性别|SEX|0(男)|1(女)|||
|证件类型|CARD_TYPE|0(身份证)|1(军官证)|||
|门禁权限是否可用|DOOR_AUTHORITY|0(不可用)|1(可用)|||
|维修状态|REPAIR_STATUS|0(待处理)|1(处理中)|2(处理完待评价)|3(已评价)|
|费用收费单位|FEE_UNIT|family(按户)|square(按平米)|per(按次)||
|账单状态|BILL_STATUS|0(未缴费)|1(已缴费)|||
|投诉状态|COMPLAIN_STATUS|0(待处理)|1(已处理待评价)|2(已评价)||
|投诉主题|COMPLAIN_TYPE|0(类型一)|1(类型二)|2(类型三)||
|物业类型|PROPERTY_TYPE|1(商户)|2(住宅)|||
|物业状态|PROPERTY_STATUS|-1(出租)|1(自用)|||
|车位类型|PARKLOT_TYPE|1(小车位)|2(中车位)|3(大车位)||
|费用类型|FEE_TYPE|0(物业费)|1(服务费)|2(车位费)||
|门禁密钥类型|SECRET_TYPE|1(楼栋)|2(道闸)||||


**错误代码对照表**

| code | 描述 |错误级别|
|--------|--------|--|
|  100000  |  客户端参数错误  |  高  |
|  100001  |  服务器未知异常  |  高  |

**app端接口**
--------------

## 用户中心 (01)
### 登陆
**客户端请求**
>*请求URL*:api/uc/login
>*请求方法*:GET/POST
>*请求参数*:
>phone: 18144240528
>password: md5(123456)

**服务器返回数据**
```json
{
	"status":false,
	"errorMsg":
	{
		"code":10001,
		"description":"请输入11位手机号"
	}
}
```

### 退出登陆
**客户端请求**
>*请求URL*:api/uc/loginOut
>*请求方式*:GET

**服务器返回数据**
```json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```

### 密码找回
**客户端请求**
>*请求URL*:api/uc/findPassword/{userPhone}
>*请求方式*:GET

**服务器返回数据**
```json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```
**客户端请求**
>*请求URL*:api/uc/findPassword/checkVerifyCode/{code}
>*请求方式*:GET

**服务器返回数据**
```json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```
**客户端请求**
>*请求URL*:api/uc/findPassword/reset/{newPassword}
>*请求方式*:GET

**服务器返回数据**
```json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```

### 注册
**客户端请求**
>*请求URL*:api/uc/register/getVerifyCode/{phone}
>*请求方式*:GET

**服务器返回数据**
```json
{
   "status": false,
   "errorMsg": {
       "code": "100200/100210",
       "description": "电话号码已经注册/请输入正确的手机号"
   },
   "jsonString": null
}
```

**客户端请求**
>*请求URL*:api/uc/register/checkVerifyCode/{verifyCode}
>*请求方式*:GET

**服务器返回数据**
```json
{
   "status": false,
   "errorMsg": {
       "code": "100220",
       "description": "验证码错误"
   },
   "jsonString": null
}
```

**客户端请求**
>*请求URL*:api/uc/register/doRegister
>*请求方式*:GET
> 参数:
> nickname:kangbiao
> password:md5(123456)

**服务器返回数据**
```json
{
   "status": true,
   "errorMsg": {
       "code": "100000(已经是业主)/100001(没有任何绑定)",
       "description": "如果已经是业主的话,后端会自动绑定所有物业,如果不是业主且没有任何绑定,则需要进入绑定物业界面,至于用户想要绑定其他物业,则可以在进入app后再绑定"
   },
   "jsonString": null
}
```


### 修改密码
**客户端请求**
>*请求URL*:api/uc/modify/password
>*请求方式*:GET
> 参数:
> oldPassword:md5(123456)
> newPassword:md5(234568)

**服务器返回数据**
```json
{
   "status": false,
   "errorMsg": {
       "code": null,
       "description": "原密码错误"
   },
   "jsonString": null
}
```

### 修改个人资料
**客户端请求**
>*请求URL*:api/uc/modify/getProfile
>*请求方式*:GET

**服务器返回数据**
```json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": {
        "id": 1,
        "phone": "18144240528",
        "name": "康彪",
        "sex": 0,
        "birthday": 150515510000,
        "urgentName": "小明",
        "urgentPhone": "131627828989",
        "identityType": 1,
        "identityCode": "510704199405281715",
        "vehicleIdIst": "5426d;dsds5",
        "propertyIdList": "1;2",
        "authenticationTime": 1541214000
    }
}
```
**客户端请求**
>*请求URL*:api/uc/modify/submitProfile
>*请求方式*:POST
> 参数:
> name:姓名
> birthday:出生日期
> urgentName:紧急联系人姓名
> urgentPhone:紧急联系人电话
> identityType:CARD_TYPE
> identityCode:证件号码
> sex:SEX

**服务器返回数据**
```json
{
   "status": false,
   "errorMsg": {
       "code": null,
       "description": "原密码错误"
   },
   "jsonString": null
}
```

### 获取用户角色
>*请求URL*:api/uc/getRole
>*请求方式*:GET
>*说明*:由于现在是一个用户可以有多个角色关系,所以返回的USER_ROLE的值为1,2,3中的一个或多个,用逗号分割

**服务器返回数据**
```json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": "1,2"或者"1"
}
```

## 推送(02)
**客户端请求**
>*请求URL*:api/push
>*请求方式*:GET
>*参数*:
>type:
>info:

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```

## 投诉(03)
### 增加投诉
**客户端请求**
>*请求URL*:api/complain/add
>*请求方式*:POST
>*参数*:
>title:投诉标题
>content:投诉内容
>file[]:图片

**服务器返回数据**
``` json
{
   "status": false,
   "errorMsg": {
       "code": null,
       "description": "图片上传错误"
   },
   "jsonString": null
}
```

### 获取我的投诉
**客户端请求**
>*请求URL*:api/complain/getMyComplain
>*请求方式*:GET
>*参数*:
>status:COMPLAIN_STATUS,不加参数则返回所有投诉.result为投诉处理结果说明.

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": [
        {
            "id": 5,
            "title": "楼下太吵",
            "content": "dfssad",
            "description": "打算倒萨",
            "phone": "18144240528",
            "time": 23663323056,
            "imageIdList": null,
            "type": 1,
            "status": 0,
            "cuId": 1,
            "remark": null,
            "result": null,
            "consoleUserEntity": null
        },
        {
            "id": 18,
            "title": "测试投诉",
            "content": "测试投诉",
            "description": "测试投诉",
            "phone": "18144240528",
            "time": 1445666212672,
            "imageIdList": "/oa/file/picture/20151024135652_286.jpg,/oa/file/picture/20151024135652_263.png",
            "type": null,
            "status": 0,
            "cuId": null,
            "remark": null,
            "result": null,
            "consoleUserEntity": null
        }
    ]
}
```

## 报修(04)
### 增加报修
**客户端请求**
>*请求URL*:api/repair/add
>*请求方式*:POST
>*参数*:
>title:报修主题
>content:报修内容
>file[]:图片(可选字段)

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```

### 获取我的报修
**客户端请求**
>*请求URL*:api/repair/getMyRepair
>*请求方式*:GET
>*参数*:
>status:REPAIR_STATUS(不加则返回所有状态的报修)
>*说明*:imageIdList为逗号分隔的图片路径的字符串,不是原来的id字符串,另外当status为1(处理中)时,维修人员信息在repairManEntity里面.

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": [
        {
            "id": 15,
            "phone": "18144240528",
            "repairManId": 1,
            "title": "test",
            "content": "报修测试",
            "description": "报修测试",
            "submitTime": 1445598807245,
            "processTime": 1445599123885,
            "finishTime": null,
            "imageIdList": "/oa/file/picture/20151023191327_823.jpg,/oa/file/picture/20151023191327_820.jpg",
            "status": 1,
            "remark": null,
            "remarkText": null,
            "cuId": 1,
            "result": null,
            "repairManEntity": {
                "id": 1,
                "phone": "15114052120",
                "name": "小明"
            }
        }
    ]
}
```

### 确认报修完成
**客户端请求**
>*请求URL*:api/repair/confirmFinish/{repairID}
>*请求方式*:GET

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```

### 评论报修
**客户端请求**
>*请求URL*:api/repair/remark
>*请求方式*:POST
>*参数*:
>id:报修id
>remark:评分(0-100)
>comment:文字评论
>*说明*:只用当报修状态为2时才可以评论报修

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": null
}
```

## 费用相关(05)
### 获取账单
>*请求URL*:api/fee/getBill
>*请求方式*:GET
>*参数*:
>status:BILL_STATUS(不加则返回所有状态的账单)

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": [
        {
            "id": 1,
            "total": "400.0",
            "items": [
                {
                    "id": "水电费",
                    "text": "100"
                },
                {
                    "id": "停车费",
                    "text": "100"
                },
                {
                    "id": "煤气费",
                    "text": "200"
                }
            ],
            "status": 0,
            "billTime": "1976-03"
        }
    ]
}
```

### 物业费缴纳
**客户端请求**
>*请求URL*:api/fee/submitBill
>*请求方式*:POST
>*参数*:
>billIDs:1,2
>*说明*:客户端调用支付平台的接口后,根据返回结果判断是否成功,服务器根据回调方法判断是否成功

**服务器返回数据**
``` json
{
   "status": false,
   "errorMsg": {
       "code": null,
       "description": "账单缴费失败"
   },
   "jsonString": null
}
```

## 门禁相关06)
### 获取门禁密钥
**客户端请求**
>*请求URL*:api/auth/getSecret/{symbol}
>*请求方式*:GET
>*说明*:{symbol}为唯一标识蓝牙或者wifi的值

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": {
        "id": 11,
        "controlId": 3,
        "symbol": "YCWY_D_001",
        "villageId": 4,
        "secret": "00000000",
        "password": "01234567",
        "type": 1
    }
}
```

### 上传开锁记录
**客户端请求**
>*请求URL*:api/auth/uploadDoorLog
>*请求方式*:POST
>*参数*：
>symbol:蓝牙或者wifi的唯一标识
>status:０(失败)／１(成功)
>description:开门失败的描述（失败后必须返回该字段）
>level:严重级别（０为软件故障，１为门禁硬件故障，２为用户手机因素，３为用户操作有误，４为其他。失败后必须返回该字段）
>*说明*:如果status为false,应该把错误信息写入ａｐｐ日志.

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString":null
}
```

## 物业相关(07)
### 获取所有园区
**客户端请求**
>*请求URL*:api/query/getAllVillage
>*请求方式*:GET

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": [{"id":1,"text":"园区一"},{"id":2,"text":"园区二"}]
}
```

### 根据园区id获取所有的楼栋
**客户端请求**
>*请求URL*:api/query/getBuilding/{villageID}
>*请求方式*:GET

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": [{"id":1,"text":"楼栋一"},{"id":2,"text":"楼栋二"}]
}
```

### 根据园区id和楼栋id获取所有的物业
**客户端请求**
>*请求URL*:api/query/getProperty
>*请求方式*:GET
>*参数*:
>villageID:2
>buildingID:3

**服务器返回数据**
``` json
{
   "status": true,
   "errorMsg": {
       "code": null,
       "description": null
   },
   "jsonString": [{"id":1,"text":"物业一"},{"id":2,"text":"二"}]
}
```

### 用户申请绑定
**客户端请求**
>*请求URL*:api/property/bind
>*请求方式*:POST
> 参数:
> role:USER_ROLE
> villageID:1
> buildingID:2
> propertyID:2

**服务器返回数据**
```json
{
   "status": false,
   "errorMsg": {
       "code": null,
       "description": "您已申请或已经绑定到该物业,请不要重复申请"
   },
   "jsonString": null
}
```

### 获取我的所有物业
**客户端请求**
>*请求URL*:api/property/getMyPropery
>*请求方式*:GET
>*说明*:每个propertyEntity均对应一种用户角色(userRole),status字段为表明该绑定是待审核还是已通过审核.通过userRole字段判断该物业是我名下的物业还是我申请绑定的物业.

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": [
        {
            "id": 1,
            "status": 1,
            "userRole": 3,
            "propertyEntity": {
                "id": 28,
                "code": "A001",
                "location": "半山蓝湾一单元",
                "type": 1,
                "propertySquare": 115.55,
                "ownerType": 1,
                "villageId": 5,
                "buildingId": 1,
                "status": 1,
                "openDoorStatus": 1,
                "modifyTime": 1445581460913,
                "buildingEntity": {
                    "id": 1,
                    "villageId": 5,
                    "description": "",
                    "buildingCode": "G1B1",
                    "buildingName": "园区1的楼栋1",
                    "villageEntity": {
                        "id": 5,
                        "code": "YQ001",
                        "name": "园区一",
                        "description": ""
                    }
                }
            }
        },
        {
            "id": 2,
            "status": 1,
            "userRole": 3,
            "propertyEntity": {
                "id": 29,
                "code": "A002",
                "location": "半山蓝湾二单元",
                "type": 1,
                "propertySquare": 222.11,
                "ownerType": 1,
                "villageId": 5,
                "buildingId": 1,
                "status": 1,
                "openDoorStatus": 1,
                "modifyTime": 1345504275966,
                "buildingEntity": {
                    "id": 1,
                    "villageId": 5,
                    "description": "",
                    "buildingCode": "G1B1",
                    "buildingName": "园区1的楼栋1",
                    "villageEntity": {
                        "id": 5,
                        "code": "YQ001",
                        "name": "园区一",
                        "description": ""
                    }
                }
            }
        }
    ]
}
```

### 业主获取绑定用户
**客户端请求**
>*请求URL*:api/property/getBind
>*请求方式*:GET
>*参数*:
>status:USER_STATUS,不加则返回所有状态的绑定
>*说明*:如下例子代表xiaoming和xiaozhang分别请求以家庭成员和租客的身份绑定到物业编号为A001的物业上面,根据status得出两人的状态都是待审核.该示例数据仅为一个物业的绑定审核,一个业主会有多个物业,bindId字段标识一个绑定关系.

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": [
        {
            "propertyEntity": {
                "id": 28,
                "code": "A001",
                "location": "半山蓝湾一单元",
                "type": 1,
                "propertySquare": 115.55,
                "ownerType": 1,
                "status": 1,
                "openDoorStatus": 1,
                "modifyTime": 1445581460913,
                "buildingEntity": {
                    "id": 1,
                    "description": "",
                    "buildingCode": "G1B1",
                    "buildingName": "园区1的楼栋1",
                    "villageEntity": {
                        "id": 5,
                        "code": "YQ001",
                        "name": "园区一",
                        "description": ""
                    }
                }
            },
            "bindUserInfos": [
                {
                    "bindId": 5,
                    "role": 2,
                    "status": 0,
                    "appUserEntity": {
                        "phone": "13981111434",
                        "nickname": "xiaozhang",
                        "status": 1,
                        "registerTime": 155515151500,
                        "lastLogin": 155212121000
                    }
                },
                {
                    "bindId": 6,
                    "role": 1,
                    "status": 0,
                    "appUserEntity": {
                        "phone": "15114052120",
                        "nickname": "xiaoming",
                        "status": 1,
                        "registerTime": 155212121000,
                        "lastLogin": 155212121000
                    }
                }
            ]
        }
    ]
}
```

### 业主审核绑定
**客户端请求**
>*请求URL*:api/property/submitBind/{operate}/{bindId}
>*请求方式*:GET
>*说明*:operate只能为agree或者refuse,bindId为上面审核绑定返回数据中的bindId字段

**服务器返回数据**
``` json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": null
}
```

## 公告 (08)
### 获取一定数量的公告
**客户端请求**
>*请求URL*:api/notice/getSome/{number}
>*请求方式*:GET
>*说明*:number为获取的公告的数量,最新公告优先返回.picturePathList以逗号分隔,没有图片则为null.注意content为null并不表示该公告详情为空,因为列表显示公告时不需要该字段,所以后台设为了null

**服务器返回数据**
```json
{
    "status": true,
    "errorMsg": {
        "code": null,
        "description": null
    },
    "jsonString": [
        {
            "id": 6,
            "title": "周本顺杨栋梁严重违纪被双开",
            "description": "河北省委原书记、省人大常委会原主任周本顺严重违纪被开除党籍和公职",
            "content": null,
            "createTime": 1444977998184,
            "picturePathList": "/oa/file/picture/20151023191327_823.jpg,/oa/file/picture/20151023191327_820.jpg"
        },
        {
            "id": 4,
            "title": "测试公告",
            "description": "描述",
            "content": null,
            "createTime": 1444899774481,
            "picturePathList": "/oa/file/picture/20151023171656_889.jpg"
        }
    ]
}
```

### 根据公告id获取公告详情
**客户端请求**
>*请求URL*:api/notice/getContent/{noticeID}
>*请求方式*:GET
>*说明*:返回内容为该公告的html代码,通过webview显示只会返回公告详情公告标题和发布时间需要客户端自行组装.

**服务器返回数据**
```
H5网页
```
