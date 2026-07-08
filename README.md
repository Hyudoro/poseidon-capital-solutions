# spring-boot
## Technical:

1. Spring Boot 3.1.0
2. Java 17
3. Thymeleaf
4. Bootstrap v.4.3.1
5. maven 4.0.0

# Endpoints
(Better read with the code mode)
Access levels:
- public: no login needed
- logged in: any authenticated user
- admin only: must have ADMIN role

## Home
GET  /              public       home page
GET  /admin/home    admin only   redirects to /bidList/list

## Login
GET  /app/login                    public       login page
GET  /app/error                    public       403 error page
GET  /app/secure/article-details   admin only   list of users (via secure path)
POST /app-logout                   public       logs out, redirects to /app/login

## Bid List
GET  /bidList/list          logged in   list all bids
GET  /bidList/add           logged in   show add bid form
POST /bidList/validate      logged in   create a bid
GET  /bidList/update/{id}   logged in   show edit form for a bid
POST /bidList/update/{id}   logged in   update a bid
GET  /bidList/delete/{id}   logged in   delete a bid

## Curve Point
GET  /curvePoint/list          logged in   list all curve points
GET  /curvePoint/add           logged in   show add curve point form
POST /curvePoint/validate      logged in   create a curve point
GET  /curvePoint/update/{id}   logged in   show edit form for a curve point
POST /curvePoint/update/{id}   logged in   update a curve point
GET  /curvePoint/delete/{id}   logged in   delete a curve point

## Rating
GET  /rating/list          logged in   list all ratings
GET  /rating/add           logged in   show add rating form
POST /rating/validate      logged in   create a rating
GET  /rating/update/{id}   logged in   show edit form for a rating
POST /rating/update/{id}   logged in   update a rating
GET  /rating/delete/{id}   logged in   delete a rating

## Rule Name
GET  /ruleName/list          logged in   list all rules
GET  /ruleName/add           logged in   show add rule form
POST /ruleName/validate      logged in   create a rule
GET  /ruleName/update/{id}   logged in   show edit form for a rule
POST /ruleName/update/{id}   logged in   update a rule
GET  /ruleName/delete/{id}   logged in   delete a rule

## Trade
GET  /trade/list          logged in   list all trades
GET  /trade/add           logged in   show add trade form
POST /trade/validate      logged in   create a trade
GET  /trade/update/{id}   logged in   show edit form for a trade
POST /trade/update/{id}   logged in   update a trade
GET  /trade/delete/{id}   logged in   delete a trade

## User
GET  /user/list          admin only   list all users
GET  /user/add           admin only   show add user form
POST /user/validate      admin only   create a user
GET  /user/update/{id}   admin only   show edit form for a user
POST /user/update/{id}   admin only   update a user
GET  /user/delete/{id}   admin only   delete a user
