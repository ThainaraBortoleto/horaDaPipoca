<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="header.jsp" />

<body>
	<c:import url="Menu.jsp" />
	<div class=" container mb-5 mt-5">
		<c:forEach var="popularidade" items="${popularidades}">


					<h3 class="mt-4">${popularidade }</h3>
					<div class="carouseller mt-5 mb-5">
						<a href="javascript:void(0)" class="carouseller__left">‹</a>
						<div class="carouseller__wrap">
							<div class="carouseller__list">
								<c:forEach var="filme" items="${filmes }">
									<c:if test="${filme.popularidade == popularidade}">
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
									</c:if>
								</c:forEach>
							</div>
						</div>
						<a href="javascript:void(0)" class="carouseller__right">›</a>
					</div>
			</c:forEach>
	</div>
	<c:import url="footer.jsp" />