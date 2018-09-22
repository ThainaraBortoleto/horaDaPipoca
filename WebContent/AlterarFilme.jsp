<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Hora da Pipoca - Alterar Filme</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>

<body>
	<!-- Barra superior com os menus de navegação -->
	<c:import url="Menu.jsp" />
	<!-- Container Principal -->
	<div id="main" class="container">
		<h3 class="page-header">Alterar Filme #${filme.id }</h3>
		<!-- Formulario para alteração de clientes -->
		<form action="manterfilmes.do" method="post">
			<!-- area de campos do form -->
			<input type="hidden" name="id" value="${filme.id }" />
			<div class="row">
				<div class="form-group col-md-4">
					<label for="genero">Gênero</label> <select class="form-control"
						name="genero.id" id="genero">
						<c:forEach var="genero" items="${generos}">
							<option value="${genero.id}">${genero.nome}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-8">
					<label for="titulo">Título</label> 
					<input type="text"
						class="form-control" name="titulo" id="titulo"
						maxlength="100" placeholder="${filme.titulo}">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label for="descricao">Descrição</label>
					<textarea class="form-control rounded-0" id="descricao"
						name="descricao" maxlength="4000" rows="10" placeholder="${filme.descricao }"></textarea>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-8">
					<label for="diretor">Direção</label> <input type="text"
						class="form-control" name="diretor" id="diretor" maxlength="60"
						placeholder="${filme.diretor }">
				</div>

				<div class="form-group col-md-4">
					<label for="dataLancamento">Lançamento</label> <input type="text"
						class="form-control" name="dataLancamento" id="dataLancamento"
						placeholder="${filme.dataLancamento }">
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-8">
					<label for="posterpath">Poster</label> <input type="text"
						class="form-control" name="posterPath" id="posterpath"
						maxlength="200" placeholder="${filme.posterPath }">
				</div>
				<div class="form-group col-md-4">
					<label for="popularidade">Popularidade</label> <input type="number"
						class="form-control" name="popularidade" id="popularidade"
						min="0.0" max="100.0" placeholder="${filme.popularidade }">
				</div>
			</div>
			<hr />
			<div id="actions" class="row">
				<div class="col-md-12">
					<button type="submit" class="btn btn-primary" name="acao"
						value="atualizar">Salvar</button>
					<a href="index.jsp" class="btn btn-default">Cancelar</a>
				</div>
			</div>
		</form>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>

</html>