# technicalShop :paperclip:

Implements a functionality `E-Shop` which provides 
selling/creating any orders for devices as customer want.

Project was developed with  `Java/Spring Boot` technology stack
which produces next accessible endpoints:

## Customer Endpoints


:black_circle: `GET /order/{id}` - get created customer order by id.

:black_circle: `POST /order/{id}` - creates order for customer.

:black_circle: `POST /order/{id}/pay` - pay already created order for customer.

## Order Endpoints

:black_circle: `POST /order` - allows site moderators adds new goods to shop.

:black_circle: `GET /orders` - shows all available orders.

## Notice :recycle:

If customer order wont be payed by 10 minutes - order will automatically delete and returned to all-accessible order list.
