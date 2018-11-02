<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:import url="header.jsp" />

<body>
	<c:import url="Menu.jsp" />
	<div class=" container mb-5 mt-5">
		<c:forEach var="lista" items="${listas}" varStatus="i">
			<div class="row">
				<div class="titulo_div">
					<c:if test="${i.index == 0}">
						<fmt:formatDate var="mesFilme" value="${lista[0].dataLancamento}"
							pattern="MMMM" />
						<h1 class="titulo_genero">${mesFilme}</h1>
					</c:if>
					<c:if test="${i.index != 0}">
						<fmt:formatDate var="anoFilme" value="${lista[0].dataLancamento}"
							pattern="yyyy" />
						<h1 class="titulo_genero">${anoFilme}</h1>
					</c:if>
				</div>
				<div class="col-sm-12">
					<div class="carouseller">
						<a href="javascript:void(0)" class="carouseller__left">‹</a>
						<div class="carouseller__wrap">
							<div class="carouseller__list">
								<c:forEach var="filme" items="${lista}">
									<div class="car__3">
										<!-- Card -->
										<div class="card">

											<!-- Card image -->
											<div class="view overlay">
												<img class="card-img-top" src="${filme.posterPath }"
													alt="Card image cap"> <a
													href="manterfilmes.do?acao=visualizar&id=${filme.id}">
													<div class="mask rgba-white-slight"></div>
												</a>
											</div>

											<!-- Card content -->
											<div class="card-body">

												<!-- Title -->
												<h4 class="card-title">
													<a>${filme.titulo }</a>
												</h4>
												<!-- Text -->
												<p class="card-text">${ filme.descricao.substring(0, 80) }</p>
												<!-- Button -->
												<a href="manterfilmes.do?acao=visualizar&id=${filme.id}"
													class="btn btn-primary">Leia Mais</a>

											</div>

										</div>
										<!-- Card -->
									</div>
								</c:forEach>
							</div>
						</div>
						<a href="javascript:void(0)" class="carouseller__right">›</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<c:import url="footer.jsp" />