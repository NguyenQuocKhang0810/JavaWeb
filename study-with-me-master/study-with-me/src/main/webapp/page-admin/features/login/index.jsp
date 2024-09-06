<jsp:include page="/page-admin/components/head.jsp" />
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="card card-outline card-primary">
			<div class="card-header text-center">
				<a href="#" class="h1"><b>Admin</b></a>
			</div>
			<div class="card-body">
				<p class="login-box-msg">Log in to access your account</p>

				<!-- Login Form -->
				<form id="loginForm">
					<div class="input-group mb-3">
						<input id="inpLoginEmail" type="text" class="form-control"
							placeholder="Username or email">
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-envelope"></span>
							</div>
						</div>
					</div>
					<div class="input-group mb-3">
						<input id="inpLoginPassword" type="password" class="form-control"
							placeholder="Password">
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-lock"></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-8">
							<div class="icheck-primary">
								<input type="checkbox" id="remember"> <label
									for="remember"> Remember Me </label>
							</div>
						</div>
						<div class="col-4">
							<button type="button" class="btn btn-primary btn-block"
								onclick="login()">Sign In</button>
						</div>
					</div>
				</form>


				<!-- Forgot Password Form -->
				<div id="forgotPasswordForm" style="display: none;">
					<p class="login-box-msg">Enter your email to receive a password
						reset link</p>
					<div class="input-group mb-3">
						<input id="forgotPasswordEmail" type="text" class="form-control"
							placeholder="Email">
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-envelope"></span>
							</div>
						</div>
					</div>
					<button type="button" class="btn btn-primary btn-block"
						onclick="forgotPassword()">Send Reset Link</button>
					<button type="button" class="btn btn-secondary btn-block"
						onclick="hideForgotPasswordForm()">Cancel</button>
				</div>

				<!-- Forgot Password Link -->
				<p class="mb-1">
					<a href="javascript:void(0);" onclick="showForgotPasswordForm()">I
						forgot my password</a>
				</p>

				<p class="mb-0">
					<a href="#" class="text-center">Create a new account</a>
				</p>
			</div>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/page-admin/features/login/index.js"></script>
	<jsp:include page="/page-admin/components/required-script.jsp" />
</body>
</html>
