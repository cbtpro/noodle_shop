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
        "good": "SET_MEAL_1",
        "count": 5
    },
    {
        "good": "BEEF_CAKE",
        "count": 1
    }
]'
```

请求参数

```
[
    {
        "good": "SET_MEAL_1",
        "count": 1
    },
    {
        "good": "BEEF_CAKE",
        "count": 2
    }
]
```
