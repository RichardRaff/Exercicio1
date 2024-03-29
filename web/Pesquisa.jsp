<%@page import="javax.persistence.Persistence"%>
<%@page import="controller.ContatoJpaController"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.Contato"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Pesquisa</title>
  </head>
  <body>
       <!-- Navbar rustic mode -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand"> Agenda On-line </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp"> Cadastrar </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp"> Visualizar Todos </a>
                    </li>
                </ul>

                <form class="form-inline my-2 my-lg-0" action="ContatoServlet" method="POST">
                    <input class="form-control mr-sm-2" type="search" placeholder="Pesquise o contato aqui" name="pesqNome" aria-label="Search">
                    <button class="btn btn-success" type="submit" name="pesquisar"> Pesquisar </button>
                </form>
            </div>
        </nav>
        <!-- Navbar finish -->
     <div class="container">
            <table class="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Sobrenome</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Telefone</th>
                        <th scope="col"> </th>
                        <th scope="col"> </th>
                    </tr>
                </thead>
        <%
                    String pesq = request.getAttribute("retornoPesq").toString();
                    List<Contato> listacontato = new ArrayList<Contato>();
                    ContatoJpaController cjpa = new ContatoJpaController(Persistence.createEntityManagerFactory("Exercicio1PU"));
                    listacontato = cjpa.findContatoByName(pesq);

                    for (Contato c : listacontato) {
                        if (c.getVisibilidade() == 1) {
                %>
                <tbody>
                    <tr>
                        <td> <%=c.getIdcontato()%> </td>
                        <td> <%=c.getNome()%> </td>
                        <td> <%=c.getSobrenome()%> </td>
                        <td> <%=c.getEmail()%> </td>
                        <td> <%=c.getTelefone()%> </td>

                        <!-- Bot�es de a��o -->
                        <td> <button type="button" data-toggle="modal" data-target="#modalAtualizar" name="alt" id="alt" class="btn btn-warning"
                                     data-id="<%=c.getIdcontato()%>"
                                     data-nome="<%=c.getNome()%>"
                                     data-sobrenome="<%=c.getSobrenome()%>"
                                     data-email="<%=c.getEmail()%>"
                                     data-telefone="<%=c.getTelefone()%>"
                                     > Editar </button> </td>

                        <td><form action="ContatoServlet" method="post">
                                <button type="submit" class="btn btn-danger" name="excluir" id="excluir" value="<%=c.getIdcontato()%>">
                                    Excluir</button></form></td>
                    </tr>
                    <% }
                        }%>
                </tbody>
            </table><div class="modal fade" id="modalAtualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel"> Atualizar dados </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">

                            <form name="formContato" id="formContato" method="post" action="ContatoServlet">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"> ID </span>
                                    </div>
                                    <input class="form-control" type="text" placeholder="" id="id" name="id" readonly>
                                </div>
                                </br>
                                <div class="form-group row">
                                    <div class="col-4">
                                        <label for="nome"> Nome: </label>
                                        <input type="text" class="form-control" id="nome" name="nome" required="">
                                    </div>
                                    <div class="col-8">
                                        <label for="sobrenome"> Sobrenome: </label>
                                        <input type="text" class="form-control" id="sobrenome" name="sobrenome" required="">
                                    </div>
                                </div>

                                </br>

                                <div class="form-group row">
                                    <div class="col-8">
                                        <label for="email"> E-mail: </label>
                                        <input type="email" class="form-control" id="email" name="email" required="">
                                    </div>

                                    <div class="col-4">
                                        <label for="telefone"> Telefone: </label>
                                        <input type="text" class="form-control" id="telefone" name="telefone" required="">
                                    </div>
                                </div> 
                                
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal"> Fechar </button>
                                    <button type="submit" class="btn btn-primary" name="atualizar"> Atualizar </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="featurette-divider">

            <footer class="container">
                <p class="float-right"><a href="index.jsp"> Voltar para o in�cio </a></p>
                <p>&copy; 2019 by Richard Raff. </p>
            </footer>
        </div>

    </body>

    <!-- External JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $('#modalAtualizar').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget) // Button that triggered the modal

            // Vari�veis que pegam os atributos do formul�rio
            var id = button.data('id')
            var nome = button.data('nome')
            var sobrenome = button.data('sobrenome')
            var email = button.data('email')
            var telefone = button.data('telefone')

            // usando o modal para encontrar as vari�veis do meu modal
            var modal = $(this)
            modal.find('#id').val(id)
            modal.find('#nome').val(nome)
            modal.find('#sobrenome').val(sobrenome)
            modal.find('#email').val(email)
            modal.find('#telefone').val(telefone)
        })
    </script>
</html>
