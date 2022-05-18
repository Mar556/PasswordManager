<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
<p align="center" style="margin-top: 10px">${info}&nbsp</p>
<div class="row justify-content-start">
    <c:forEach var="entry" items="${mapSneakers}">
    <div class="col-4">
        <div class="card" style="margin-bottom: 25px">
            <img src="insertFile/${entry.value.fileName}" class="card-img-top" alt="..." height="200px">
           <center><div class="card-body">
            <small><p class="card-title" >${entry.key.sneakerFirm} ${entry.key.sneakerModel}</p></small>
            <small><p class="card-title" >${entry.key.sneakerQuantity} шт.</p></small>
            <small><strong><p class="card-text">€${entry.key.sneakerPrice}</p></strong></small>
            </div></center>
        </div>
    </div>
    </c:forEach>
  </div>
</div>
