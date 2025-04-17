# FinalProject

## Текущая функциональность
-  Пополнение счета
-  Списание средств
-  Проверка баланса
-  Перевод
-  История операций


## Endpoints
GET  /getBalance/{id} - Счет клиента
POST  /putMoney/{id}/money - Пополнение счета 
POST  /takeMoney/{id}/money - Списание средств 
GET  /getOperationList/{id} - Получить весь список операций или по дате
PUT /transferMoney/{id}/{toId} - Перевод средств


![image](https://github.com/user-attachments/assets/9f02bc4b-c5a1-4803-bae7-83760c7eca8a)
![image](https://github.com/user-attachments/assets/ff8f7f94-43fc-4640-8fb7-2daac9f9a733)
