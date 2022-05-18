<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <div class="col-12">                
        <center><p>${info}&nbsp;</p></center>
     <section class="vh-50">
      <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <center><div class="col-12 col-md-8 col-lg-6 col-xl-5" style="">
            <div class="card shadow-2-strong " style="border-radius: 1rem; width: 100%">
              <div class="card-body p-4 text-center">
                <h3 class="mb-4">Поменять роль</h3>
                <form action="changeRole" method="POST">
                <div class="form-outline mb-4">
                  <select name="userId" class="form-select">
                      <c:forEach var="entry" items="${mapUsers}">                       
                      <option value="${entry.key.id}">${entry.key.buyer.buyerFirstName} ${entry.key.buyer.buyerLastName}. ${entry.key.login}. ${entry.value}</option>
                      </c:forEach>
                    </select>
                </div>      
                <div class="form-outline mb-4">
                  <select name="roleId" class="form-select">
                      <c:forEach var="role" items="${roles}">                       
                      <option value="${role.id}">${role.roleName}</option>
                      </c:forEach>
                    </select>
                </div>      
                <button class="btn btn-primary btn-block" type="submit">Поменять</button>   
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>           
    </div>


