<%@page contentType="text/html" pageEncoding="UTF-8"%>
<center><p>${info}&nbsp;</p></center>
 <section class="vh-50">
  <div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <center><div class="col-12 col-md-8 col-lg-6 col-xl-5" style="">
        <div class="card shadow-2-strong " style="border-radius: 1rem; width: 100%">
          <div class="card-body p-4 text-center">
            <h3 class="mb-4">Авторизация</h3>
            <form action="login" method="POST">
            <div class="form-outline mb-4">
              <input type="text" name="login" value="${login}" placeholder="Логин" class="form-control form-control" />
            </div>
            <div class="form-outline mb-4">
              <input type="password" name="password" placeholder="Пароль" class="form-control form-control" />
            </div>      
            <div class="form-outline mb-4">
            <a href="showRegistration">Регистрация</a>
            </div>
            <button class="btn btn-success btn-block" type="submit">Войти</button>   
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>