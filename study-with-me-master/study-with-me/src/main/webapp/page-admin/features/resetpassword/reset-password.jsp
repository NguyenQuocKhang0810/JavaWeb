<jsp:include page="/page-admin/components/head.jsp" />
<body class="hold-transition reset-password-page">
  <div class="reset-password-box">
    <div class="card card-outline card-primary">
      <div class="card-header text-center">
        <a href="#" class="h1"><b>Admin</b>SWM</a>
      </div>
      <div class="card-body">
        <p class="reset-password-box-msg">Reset your password</p>

        <form id="resetPasswordForm">
          <div class="input-group mb-3">
            <input id="inpResetPassword" type="password" class="form-control" placeholder="New Password">
            <div class="input-group-append">
              <div class="input-group-text">
                <span class="fas fa-lock"></span>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-4">
              <button type="button" class="btn btn-primary btn-block" onclick="resetPassword()">Reset Password</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <script>
    function resetPassword() {
      const token = new URLSearchParams(window.location.search).get('token');
      const password = $("#inpResetPassword").val();

      if (!password) {
        toastr.error("Please enter your new password.");
        return;
      }

      const body = { "token": token, "password": password };
      Http.post(`${domain}/api/reset-password`, body)
        .then(res => {
          if (res.success) {
            toastr.success("Password reset successfully.");
            window.location.href = `${domain}/page-admin/login.jsp`;
          } else {
            toastr.error(res.errMsg);
          }
        })
        .catch(err => {
          toastr.error(err.errMsg);
        });
    }
  </script>
  <jsp:include page="/page-admin/components/required-script.jsp" />
</body>
</html>
