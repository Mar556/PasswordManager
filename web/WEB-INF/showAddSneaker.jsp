<%@page contentType="text/html" pageEncoding="UTF-8"%>    
        <div class="col-12">                
        <center><p>${info}&nbsp;</p></center>
     <section class="vh-50">
      <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <center><div class="col-12 col-md-8 col-lg-6 col-xl-5" style="">
            <div class="card shadow-2-strong " style="border-radius: 1rem; width: 100%">
              <div class="card-body p-4 text-center">
                <h3 class="mb-4">Добавить кроссовок</h3>
                <form action="createSneaker" method="POST">
                <div class="form-outline mb-4">
                  <input type="text" class="form-control" name="firm" value="${firm}" placeholder="Фирма"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="text" class="form-control" name="model" value="${model}" placeholder="Модель"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="number" step="0.01" name="size" value="${size}" class="form-control" placeholder="Размер"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="number" step="0.01" name="price" value="${price}" class="form-control" placeholder="Цена"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="number" name="quantity" value="${quantity}" class="form-control" placeholder="Количество"</>
                </div> 
                <div class="form-outline mb-4">
                           <select name="coversId" class="form-select">
                                <c:forEach var="cover" items="${listCovers}">
                            <option value="${cover.id}">${cover.description}</option>
                            </c:forEach>
                            </select> 
                        </div>  
                <button class="btn btn-primary btn-block" type="submit">Добавить</button>   
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>           
    </div>




