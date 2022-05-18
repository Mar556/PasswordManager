<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <div class="col-10" style="margin-left: 80px">
            <center><legend>Список покупателей</legend></center>
            <table class="table table-primary-bordered" style="margin-left: 30px">
              <thead>
                <tr class="table-info">
                  <th scope="col">Id</th>
                  <th scope="col">Имя</th>
                  <th scope="col">Фамилия</th>
                  <th scope="col">Телефон</th>
                  <th scope="col">Деньги</th>
                </tr>
              </thead>
              <tbody>
                  <c:forEach var="buyers" items="${buyers}">    
                <tr>
                  <td>${buyers.id}</td>
                  <td>${buyers.buyerFirstName}</td>
                  <td>${buyers.buyerLastName}</td>
                  <td>${buyers.buyerPhone}</td>
                  <td>${buyers.buyerMoney}</td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
    </div>

