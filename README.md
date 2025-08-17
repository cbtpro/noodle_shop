# 牛肉面算账系统

## 运行

```
mvn spring-boot:run
```

## 单元测试

```
mvn test
```

接口测试

```
curl --location --request POST 'http://localhost:8888/orders/calculate' \
--header 'User-Agent: Apifox/1.0.0 (https://apifox.com)' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "id": "beef_noodle_large",
        "type": "good",
        "goodName": "大碗牛肉面",
        "count": 2
    },
    {
        "id": "beef_noodle_medium",
        "type": "good",
        "goodName": "中碗牛肉面",
        "count": 0
    },
    {
        "id": "BEEF_NOODLE_SMALL",
        "type": "good",
        "goodName": "小碗牛肉面",
        "count": 0
    },
    {
        "id": "intestine_noodle_large",
        "type": "good",
        "goodName": "大碗肥肠面",
        "count": 0
    },
    {
        "id": "intestine_noodle_medium",
        "type": "good",
        "goodName": "中碗肥肠面",
        "count": 0
    },
    {
        "id": "intestine_noodle_small",
        "type": "good",
        "goodName": "小碗肥肠面",
        "count": 0
    },
    {
        "id": "beef_cake",
        "type": "good",
        "goodName": "牛肉饼",
        "count": 2
    },
    {
        "id": "milk_tea",
        "type": "good",
        "goodName": "奶茶",
        "count": 2
    },
    {
        "id": "combine_1",
        "type": "combine",
        "goodName": "套餐1",
        "count": 1
    },
    {
        "id": "combine_2",
        "type": "combine",
        "goodName": "套餐2",
        "count": 0
    }
]'
```

请求参数

```
curl --location --request POST 'http://localhost:8888/orders/calculate' \
--header 'User-Agent: Apifox/1.0.0 (https://apifox.com)' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "id": "beef_noodle_large",
        "type": "good",
        "goodName": "大碗牛肉面",
        "count": 2
    },
    {
        "id": "beef_noodle_medium",
        "type": "good",
        "goodName": "中碗牛肉面",
        "count": 0
    },
    {
        "id": "BEEF_NOODLE_SMALL",
        "type": "good",
        "goodName": "小碗牛肉面",
        "count": 0
    },
    {
        "id": "intestine_noodle_large",
        "type": "good",
        "goodName": "大碗肥肠面",
        "count": 0
    },
    {
        "id": "intestine_noodle_medium",
        "type": "good",
        "goodName": "中碗肥肠面",
        "count": 0
    },
    {
        "id": "intestine_noodle_small",
        "type": "good",
        "goodName": "小碗肥肠面",
        "count": 0
    },
    {
        "id": "beef_cake",
        "type": "good",
        "goodName": "牛肉饼",
        "count": 2
    },
    {
        "id": "milk_tea",
        "type": "good",
        "goodName": "奶茶",
        "count": 2
    },
    {
        "id": "combine_1",
        "type": "combine",
        "goodName": "套餐1",
        "count": 1
    },
    {
        "id": "combine_2",
        "type": "combine",
        "goodName": "套餐2",
        "count": 0
    }
]'
```
