<style>
	/* Sidebar Styles */
.main-sidebar {
    background-color: #343a40; /* Dark background color for sidebar */
    color: #ffffff; /* White text color for better readability */
    height: 100%;
}

.brand-link {
    background-color: #c23b3b; /* Blue background for the brand logo */
    color: #ffffff;
    text-align: center;
    padding: 10px;
}

.brand-link .brand-image {
    width: 40px;
    height: 40px;
}

.brand-text {
    font-size: 1.25rem;
    font-weight: bold;
    color: #ffffff;
}

.user-panel {
    background-color: #495057; /* Slightly lighter background for user panel */
    border-bottom: 1px solid #6c757d; /* Subtle border for separation */
    padding: 10px;
}

.user-panel .info a {
    color: #ffffff;
    font-weight: 500;
    text-decoration: none;
}

.user-panel .info a:hover {
    color: #ffc107; /* Highlight color on hover */
}

.form-inline {
    margin: 10px;
}

.form-control-sidebar {
    background-color: #6c757d; /* Light grey background for search input */
    border: none;
    color: #ffffff;
}

.form-control-sidebar::placeholder {
    color: #ffffff; /* Placeholder text color */
}

.btn-sidebar {
    background-color: #007bff; /* Blue button for search */
    border: none;
    color: #ffffff;
}

.btn-sidebar i {
    font-size: 1.25rem;
}

.sidebar-menu {
    margin-top: 10px;
}

.sidebar-menu a {
    color: #ffffff;
    padding: 10px;
    display: block;
    text-decoration: none;
}

.sidebar-menu a:hover {
    background-color: #495057; /* Darker background on hover for menu items */
    color: #ffc107; /* Highlight color on hover */
}
	
</style>

<aside class="main-sidebar sidebar-dark-primary elevation-4">
  <!-- Brand Logo -->
  <a href="index3.html" class="brand-link"> <img src="${pageContext.request.contextPath}/page-admin/dist/img/AdminLTELogo.png" alt="AdminLTE Logo"
      class="brand-image img-circle elevation-3" style="opacity: .8">
    <span class="brand-text font-weight-light">FORUM KCT</span>
  </a>

  <!-- Sidebar -->
  <div class="sidebar">
    <!-- Sidebar user panel (optional) -->
    <div class="user-panel mt-3 pb-3 mb-3 d-flex">
      <div class="image">
        <img src="${pageContext.request.contextPath}/page-admin/dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
      </div>
      <div class="info">
        <a href="#" class="d-block"><span id="txtDisplayName">Alexander Pierce</span></a>
      </div>	
    </div>

    <!-- SidebarSearch Form -->
    <div class="form-inline">
      <div class="input-group" data-widget="sidebar-search">
        <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
        <div class="input-group-append">
          <button class="btn btn-sidebar">
            <i class="fas fa-search fa-fw"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- Sidebar Menu -->
    <jsp:include page="sidebar-menu.jsp" />
    <!-- /.sidebar-menu -->
  </div>
  <!-- /.sidebar -->
</aside>