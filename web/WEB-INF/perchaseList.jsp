<%@page contentType="text/html" pageEncoding="UTF-8"%>
<container>
    <div class="row">
        <div class="col-10" style="margin-left: 55px">
            <center><legend>Список купленой обуви</legend></center>
            <table class="table table-primary-bordered" style="margin-left: 30px">
              <thead>
                <tr class="table-info">
                  <th scope="col">Кроссовки</th>
                  <th scope="col">Цена</th>
                  <th scope="col">Размер</th>
                  <th scope="col">Дата</th>
                </tr>
              </thead>
              <tbody>
                  <c:forEach var="history" items="${history}">    
                <tr>
                  <td>${history.sneaker.sneakerFirm} ${history.sneaker.sneakerModel}</td>
                  <td>${history.sneaker.sneakerPrice}</td>
                  <td>${history.sneaker.sneakerSize}</td>
                  <td>${history.soldSneaker}</td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
    </div>
    </div>
</container>
