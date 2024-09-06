$(document).ready(function() {

    // Function to handle login
    this.login = function() {
        const body = {
            "username": $("#inpLoginEmail").val(),
            "password": $("#inpLoginPassword").val()
        };
        Http.post(`${domain}/api/login`, body)
            .then(res => {
                if (res.success) {
                    localStorage.setItem("userInfo", JSON.stringify(res.data)); // userInfo: khóa thông tin user lưu trữ
                    // res.data; chứa dlieu ngdung
                    window.location.href = `${domain}/page-admin`; // chuyển hướng đến trang quản trị 
                } else {
                    localStorage.removeItem("userInfo");
                    toastr.error(res.errMsg);
                }
            })
            .catch(err => {
                localStorage.removeItem("userInfo");
                toastr.error(err.errMsg);
            });
    };

    // Function to handle forgot password
    this.forgotPassword = function() {
        const email = $("#forgotPasswordEmail").val(); // Ensure the correct input ID
        if (!email) {
            toastr.error("Please enter your email address.");
            return;
        }

        const body = { "email": email };
        Http.post(`${domain}/api/forgot-password`, body)
            .then(res => {
                if (res.success) {
                    toastr.success("Password reset email sent.");
                    hideForgotPasswordForm(); // Hide the form after successful request
                } else {
                    toastr.error(res.errMsg);
                }
            })
            .catch(err => {
                toastr.error(err.errMsg);
            });
    };

    // Function to show the forgot password form
    this.showForgotPasswordForm = function() {
        $('#forgotPasswordForm').show();
        $('#loginForm').hide();
    };

    // Function to hide the forgot password form
    this.hideForgotPasswordForm = function() {
        $('#forgotPasswordForm').hide();
        $('#loginForm').show();
    };
});
