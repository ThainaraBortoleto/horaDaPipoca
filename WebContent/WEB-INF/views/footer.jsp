<!-- Footer -->
<footer class="page-footer fixed-bottom font-small black">

	<!-- Copyright -->
	<div class="footer-copyright text-center py-3">© 2018 Copyright</div>
	<!-- Copyright -->

</footer>
<!-- Footer -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/carouseller.min.js"></script>
<script>
	$(function() {
		$('.carouseller').each(function(i, obj) {
			$(obj).carouseller({
				//options
				easing : 'linear'
			});
		})
	});
</script>
</body>

</html>