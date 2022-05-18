<%@page contentType="text/html" pageEncoding="UTF-8"%>  
          <div class="col-12">                
        <center><p>${info}&nbsp;</p></center>
     <section class="vh-50">
      <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <center><div class="col-12 col-md-8 col-lg-6 col-xl-5" style="">
            <div class="card shadow-2-strong " style="border-radius: 1rem; width: 100%">
              <div class="card-body p-4 text-center">
                <h3 class="mb-4">Регистрация</h3>
                <form action="registration" method="POST">
                <div class="form-outline mb-4">
                  <input type="text" name="firstName" value="${firstName}" placeholder="Имя" class="form-control"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="text" name="lastName" value="${lastName}" placeholder="Фамилия" class="form-control"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="text" name="buyerPhone" value="${buyerPhone}" placeholder="Телефон" class="form-control"</>
                <div class="form-outline mb-4">
                  <input type="number" step="0.01" name="buyerMoney" value="${buyerMoney}" placeholder="Баланс" class="form-control"</>
                </div>      
                <div class="form-outline mb-4">
                  <input type="text" name="login" value="${login}" placeholder="Логин" class="form-control"</>
                </div> 
                <div class="form-outline mb-4">
                  <input type="password" name="password" value="${password}" placeholder="Пароль" class="form-control"</>
                </div> 
                <div class="form-outline mb-4">
                  <input type="password" name="password2" value="${password2}" placeholder="Повторите пароль" class="form-control"</>
                </div>  
                <button class="btn btn-success btn-block" type="submit">Зарегистрироваться</button>   
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>           
    </div>