# 牛肉面算账系统

## 运行

```
mvn spring-boot:run
```

接口测试

```
curl -X POST http://localhost:8888/orders/calculate \
     -H "Content-Type: application/json" \
     -d '["SET_MEAL_1","BEEF_CAKE"]'
```
