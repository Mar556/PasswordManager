<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <div class="col-12">          
        <center><p>${info}&nbsp;</p></center>
         <section class="vh-50">
          <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <center><div class="col-12 col-md-8 col-lg-6 col-xl-5" style="">
                <div class="card shadow-2-strong " style="border-radius: 1rem; width: 100%">
                  <div class="card-body p-4 text-center">
                    <h3 class="mb-4">Загрузить файл</h3>
                    <form action="uploadCover" method="POST" enctype="multipart/form-data">
                    <div class="form-outline mb-4">
                      <input type="file" name="file" id="file" class="form-control" />
                    </div>
                    <div class="form-outline mb-4">
                      <input type="text" name="description" id="description" placeholder="Описание" class="form-control" />
                    </div>      
                    <button class="btn btn-primary btn-block" type="submit">Загрузить</button>   
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>  
            
    </div>
        
