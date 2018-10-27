<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<c:import url="header.jsp" />

<body>
    <!-- Barra superior com os menus de navegação -->
	<c:import url="Menu.jsp"/>
    <!-- Container Principal -->
    <div id="main" class="container mt-4">
        <h3 class="page-header">Incluir Filme</h3>
        <!-- Formulario para inclusao de clientes -->
        <form action="inserir_filme" method="post">
            <!-- area de campos do form -->
            <div class="row">
            		<div class="form-group col-md-4">
                    <label for="genero">Gênero</label>
                    <select class="form-control" name="genero.id" id="genero">
                    		<c:forEach var="genero" items="${generos}">
                    		<option value="${genero.id}">${genero.nome}</option>
                    		</c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-8">
                    <label for="titulo">Título</label>
                    <input type="text" class="form-control" name="titulo" id="titulo" required maxlength="100" placeholder="nome do filme">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-12">
                    <label for="descricao">Descrição</label>
    				    <textarea class="form-control rounded-0" id="descricao" name ="descricao" maxlength="4000" rows="10"></textarea>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-md-8">
                    <label for="diretor">Direção</label>
                    <input type="text" class="form-control" name="diretor" id="diretor" maxlength="60" placeholder="nome do(s) diretor(es)">
                </div>

                <div class="form-group col-md-4">
                    <label for="dataLancamento">Lançamento</label>
                    <input type="text" class="form-control" name="dataLancamento" id="dataLancamento" placeholder="formato dd/mm/yyyy">
                </div>
            </div>
            <div class="row">
            	    <div class="form-group col-md-8">
                    <label for="posterpath">Poster</label>
                    <input type="text" class="form-control" name="posterPath" id="posterpath" maxlength="200" placeholder="caminho da imagem do poster">
                </div>
                <div class="form-group col-md-4">
                    <label for="popularidade">Popularidade</label>
                    <input type="number" class="form-control" name="popularidade" id="popularidade" min="0.0" max="100.0">
                </div>
            </div>
            <hr />
            <div id="actions" class="row">
                <div class="col-md-12">
                    <button type="submit" class="btn btn-primary" name="acao" value="criar">Salvar</button>
                    <a href="index.jsp" class="btn btn-default">Cancelar</a>
                </div>
            </div>
        </form>
    </div>
    <c:import url="footer.jsp" />
