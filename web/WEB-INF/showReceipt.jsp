<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <div class="col-12">           
            <center><p>${info}&nbsp;</p></center>
             <section class="vh-50">
              <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <center><div class="col-12 col-md-8 col-lg-6 col-xl-5" style="">
                    <div class="card shadow-2-strong " style="border-radius: 1rem; width: 100%">
                      <div class="card-body p-4 text-center">
                        <h3 class="mb-4">Сделать заказ</h3>
                        <form action="createReceipt" method="POST">
                        <div class="form-outline mb-4">
                           <select name="sneakers" class="form-select">
                                <c:forEach var="sneaker" items="${listSneakers}">
                            <option value="${sneaker.id}">${sneaker.sneakerFirm} ${sneaker.sneakerModel} ${sneaker.sneakerQuantity} шт.</option>
                            </c:forEach>
                            </select> 
                        </div>
                        <div class="form-outline mb-4">
                          <input type="number" name="receipt" value="${receipt}" placeholder="Заказ" class="form-control" />
                        </div>      
                        <button class="btn btn-primary btn-block" type="submit">Сделать поступление</button>   
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </section>     
    </div>
 

